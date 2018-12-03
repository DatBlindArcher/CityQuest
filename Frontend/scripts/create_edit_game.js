var questions = [];
var answers = [];
var id = null;

$(document).ready(function() {
    $('.menu .item').tab({ onLoad: function () { 
        $('#game_name').html($('#name').val());
        $('#game_location').html($('#location').val());
        $('#game_questions').html(questions.length + " questions"); 
    } });
    register_template("question", "question-template");
    register_template("add_answer", "add-answer-template");
    
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
        success: function(data) { console.log("Success"); show_game_data(data); },
        error: function(data) { console.log("Failed"); $("#games").html(data); }
    });
}

function change_tab(tab) {
    $('.item.active').removeClass('active');
    $('#tab' + tab).addClass('active');
    $.tab('change tab', tab);

    if (tab == '3') {
        $('#game_name').html($('#name').val());
        $('#game_location').html($('#location').val());
        $('#game_questions').html(questions.length + " questions");
    }
}

function create_question() {
    let correct_answer = -1;
    let answers_strings = [];

    for (var i = 0; i < answers.length; i++) {
        if (answers[i].checked) correct_answer = i;
        answers_strings.push(answers[i].answer);
    }

    var question = { 
        question: $('#question').val(),
        coordinates: {
            lat: parseFloat($('#question_lat').val()),
            lon: parseFloat($('#question_lon').val())
        },
        answers: answers_strings,
        correctAnswer: correct_answer,
        extraInformation: $('#question_extra').val()
    }

    console.log(question);
    questions.push(question);
    show_questions();
}

function remove_question(index) {
    questions.splice(index, 1);
    show_questions();
}

function add_answer() {
    var answer = {
        answer: $('#add_answer').val(),
        checked: false
    }

    answers.push(answer);
    show_answers();
}

function remove_answer(index) {
    answers.splice(index, 1);
    show_answers();
}

function show_answers() {
    var html = "";

    for (var i = 0; i < answers.length; i++) {
        html += get_template("add_answer", [
            { key: "index",      value: i },
            { key: "answer",     value: answers[i].answer },
            { key: "checked",    value: answers[i].checked ? "checked=\"checked\"" : "" }
        ]);
    }

    $("#answers").html(html);

    $('.ui.radio.checkbox').checkbox({
        onChecked: function() {
            for (var i = 0; i < answers.length; i++) {
                answers[i].checked = $(this)[0].id.split("_")[1] == i; 
            }
        }
    });
}

function show_questions() {
    var html = "";

    for (var i = 0; i < questions.length; i++) {
        html += get_template("question", [
            { key: "index",        value: i },
            { key: "question",     value: questions[i].question },
            { key: "answers",      value: questions[i].answers.length }
        ]);
    }

    $("#questions").html(html);
}

function create_game() {
    var game = {
        name: $('#name').val(), 
        location: $('#location').val(),
        description: $('#description').val(),
        coordinates: {
            lat: parseFloat($('#lat').val()),
            lon: parseFloat($('#lon').val())
        },
        questions: questions
    };

    console.log(((id) ? "PUT" : "POST"));
    console.log("http://localhost:8080/games" + ((id) ? "/" + id : ""));

    $.ajax({
        method: ((id) ? "PUT" : "POST"),
        crossDomain: true,
        url: "http://localhost:8080/games" + ((id) ? "/" + id : ""),
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(game),
        success: function(data) { console.log("Success"); console.log(data); location.replace("game.html?id=" + data.id); },
        error: function(data) { console.log("Failed"); alert("Failed to create object."); }
    });
}

function show_game_data(game) {
    id = game.id;
    $('#name').val(game.name);
    $('#location').val(game.location);
    $('#description').val(game.description);
    $('#lat').val(game.coordinates.lat);
    $('#lon').val(game.coordinates.lon);
    questions = game.questions;
    show_questions();
    $('#final').html("Edit Game");
}