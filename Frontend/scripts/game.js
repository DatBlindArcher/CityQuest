let game_data;
let mymap;
let watch_id;
let score = 0;

$(document).ready(function() {
    register_template("game", "game-template");
    register_template("question", "question-template");
    register_template("answer", "answer-template");
    register_template("result", "result-template");
    register_template("entry", "entry-template");
    register_template("finish", "finish-template");
    
    var parameters = location.search.substring(1).split("&");
    
    if (parameters.length > 0) { 
        var temp = parameters[0].split("=");
    
        if (temp[0] == "id") { 
            var id = unescape(temp[1]);
            get_game(id);
        }
    }
});

function get_game(id) {
    $.ajax({
        method: "GET",
        crossDomain: true,
        url: host + "/games/" + id,
        dataType: 'json',
        success: function(data) { 
            console.log(data); game_data = data.game; 
            show_game_data(data.game); 
            show_leaderboard(data.leaderboard); 
        },
        error: function(data) { console.log("Failed"); }
    });
}

function show_game_data(game) {
    var html = get_template("game", [
        { key: "id",            value: game.id },
        { key: "name",          value: game.name },
        { key: "location",      value: game.location }, 
        { key: "lat",           value: "Lat: " + game.coordinates.lat }, 
        { key: "lon",           value: "Lon: " + game.coordinates.lon }, 
        { key: "description",   value: game.description }
    ]);

    $("#game").html(html);
    
    mymap = L.map('map').setView([game.coordinates.lat, game.coordinates.lon], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(mymap);

    for (var i = 0; i < game.questions.length; i++) {
        game.questions[i].marker = L.marker([game.questions[i].coordinates.lat, game.questions[i].coordinates.lon]).addTo(mymap);
        game.questions[i].marker.bindPopup("<b>" + game.questions[i].question + "</b>");
    }

    getLocation();
}

function show_leaderboard(leaderboard) {
    var html = "";

    for (var i = 0; i < leaderboard.entries.length; i++) {
        html += get_template("entry", [
            { key: "player",    value: leaderboard.entries[i].player },
            { key: "score",     value: leaderboard.entries[i].score + "/" + game_data.questions.length }
        ]);
    }

    $("#entries").html(html);

    $('#leaderboard').hide();

    $('#leaderboardbutton').click(function() {
        $('#game').hide();
        $('#leaderboard').show();
    });

    $('#backbutton').click(function() {
        $('#leaderboard').hide();
        $('#game').show();
    });
}

function getLocation() {
    if (navigator.geolocation) {
        watch_id = navigator.geolocation.watchPosition(handlePosition);
    } 
    
    else {
        alert("Geolocation is not supported by this browser.");
    }
}

let currentQuestion = -1;

function handlePosition(position) {
    if (currentQuestion != -1) {
        var distance = measureDistance(
            game_data.questions[currentQuestion].coordinates.lat,
            game_data.questions[currentQuestion].coordinates.lon, 
            position.coords.latitude, 
            position.coords.longitude);
        
        if (distance < 50) return;
        else {
            currentQuestion = -1;
            $("#question").hide();
            $("#map").show();
        }
    }

    for (var i = 0; i < game_data.questions.length; i++) {
        if (!game_data.questions[i].answered) {
            var distance = measureDistance(
                game_data.questions[i].coordinates.lat,
                game_data.questions[i].coordinates.lon, 
                position.coords.latitude, 
                position.coords.longitude);

            if (distance < 50) {
                currentQuestion = i;
                $("#map").hide();
                $("#question").show();
                show_question(game_data.questions[i]);
                $('.ui.radio.checkbox').checkbox();
                return;
            }
        }
    }
}

function measureDistance(lat1, lon1, lat2, lon2) { 
    var R = 6378.137; // Radius of earth in KM
    var dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
    var dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
    var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
    Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
    Math.sin(dLon/2) * Math.sin(dLon/2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    var d = R * c;
    return d * 1000; // meters
}

function show_question(question) {
    $("#question").html(get_template("question", [
        { key: "id",           value: question.id },
        { key: "question",     value: question.question },
        { key: "answers",      value: show_answers(question.answers) }
    ]));
}

function show_answers(answers) {
    var html = "";

    for (var i = 0; i < answers.length; i++) {
        html += get_template("answer", [
            { key: "index",      value: i },
            { key: "answer",     value: answers[i] }
        ]);
    }

    return html;
}

function check_answer() {
    var currentAnswer = -1;

    $('.ui.radio.checkbox').each(function() {
        if ($(this).checkbox('is checked')) {
            currentAnswer = parseInt($(this)[0].firstElementChild.id.split('_')[1]);
        }
    });

    var question = game_data.questions[currentQuestion];

    if (question.correctAnswer == currentAnswer) {
        question.correct = true;
    }

    else {
        question.correct = false;
    }

    $("#question").html(get_template("result", [
        { key: "question",           value: question.question },
        { key: "result",     value: question.correct ? "positive" : "negative" },
        { key: "txt",      value: question.correct ? "Correct" : "Incorrect" },
        { key: "answer",      value: question.answers[question.correctAnswer] },
        { key: "info",      value: question.extraInformation }
    ]));
}

function finish_answer() {
    game_data.questions[currentQuestion].answered = true;
    mymap.removeLayer( game_data.questions[currentQuestion].marker); 

    currentQuestion = -1;
    $("#question").hide();
    $("#map").show();
    check_finish();
}

function check_finish() {
    score = 0;
    var finished = true;

    for(var i = 0; i < game_data.questions.length; i++) {
        if (!game_data.questions[i].answered) {
            finished = false;
        }

        else if (game_data.questions[i].correct) {
            score++;
        }
    }

    if (finished) {
        navigator.geolocation.clearWatch(watch_id);
        $("#map").hide();
        $("#question").show();

        $("#question").html(get_template("finish", [
            { key: "score",     value: score + "/" + game_data.questions.length }
        ]));
    }
}

function finish() {
    var name = document.getElementById("name").value;
    if (!name) name = "Anonymous";

    var entry = {
        score: score,
        player: name,
        gameId: game_data.id
    };

    $.ajax({
        method: "POST",
        crossDomain: true,
        url: host + "/games/" + game_data.id + "/entry",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(entry),
        success: function(data) { 
            console.log("Success"); 
            console.log(data); 
            location.replace("index.html");
        },
        error: function(data) { console.log("Failed"); console.log(data); }
    });
}