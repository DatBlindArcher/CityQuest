$(document).ready(function() {
    register_template("game", "game-template");
    get_games();
});

function get_games() {
    $.ajax({
        method: "GET",
        crossDomain: true,
        url: host + "/games",
        dataType: 'json',
        success: function(data) { show_games_data(data); },
        error: function(data) { console.log("Failed"); $("#games").html(data); }
    });
}

function show_games_data(games) {
    var html = "";

    for (var i = 0; i < games.length; i++) {
        console.log(games[i].id);

        html += get_template("game", [
            { key: "id",            value: games[i].id },
            { key: "name",          value: games[i].name },
            { key: "location",      value: games[i].location }, 
            { key: "lat",           value: "Lat: " + games[i].coordinates.lat }, 
            { key: "lon",           value: "Lon: " + games[i].coordinates.lon }, 
            { key: "description",   value: games[i].description }
        ]);
    }

    $("#games").html(html);
}