$(document).ready(function() {
    $('.menu .item').tab();
});

function change_tab(tab) {
    $('.item.active').removeClass('active');
    $('#tab' + tab).addClass('active');
    $.tab('change tab', tab);
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
        questions: []
    };

    var question = { 
        question: $('#question').val(),
        coordinates: {
            lat: parseFloat($('#question_lat').val()),
            lon: parseFloat($('#question_lon').val())
        },
        correctAnswer: parseFloat($('#question_correct_answer').val()),
        extraInformation: $('#question_extra').val(),
        answers: []
    }

    question.answers = question.answers.concat($('#question_answers').val().split(","));
    game.questions.push(question);
    
    $.ajax({
        method: "POST",
        crossDomain: true,
        url: "http://localhost:8080/games",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(game),
        success: function(data) { console.log("Success"); console.log(data); },
        error: function(data) { console.log("Failed"); console.log(data); }
    });
}