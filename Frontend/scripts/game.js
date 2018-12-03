$(document).ready(function() {
    register_template("game", "game-template");
    register_template("question", "question-template");
    register_template("answer", "answer-template");
    
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
        url: "http://localhost:8080/games/" + id,
        dataType: 'json',
        success: function(data) { console.log(data); show_game_data(data); },
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
        { key: "description",   value: game.description }, 
        { key: "questions",     value: show_question_data(game.questions) }
    ]);

    $("#game").html(html);
    
    var mymap = L.map('map').setView([game.coordinates.lat, game.coordinates.lon], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(mymap);

    for (var i = 0; i < game.questions.length; i++) {
        var marker = L.marker([game.questions[i].coordinates.lat, game.questions[i].coordinates.lon]).addTo(mymap);
        marker.bindPopup("<b>" + game.questions[i].question + "</b>");
    }
}

function show_question_data(questions) {
    var html = "";

    for (var i = 0; i < questions.length; i++) {
        html += get_template("question", [
            { key: "question",     value: questions[i].question },
            { key: "answers",      value: show_answer_data(questions[i].answers) }
        ]);
    }

    return html;
}

function show_answer_data(answers) {
    var html = "";

    for (var i = 0; i < answers.length; i++) {
        html += get_template("answer", [
            { key: "answer",     value: answers[i] }
        ]);
    }

    return html;
}