/// --- Start --- ///

$(document).ready(function() {
    register_template("game", "game-template");
    get_games();
});

function get_games() {
    $.ajax({
        method: "GET",
        crossDomain: true,
        url: "http://localhost:8080/games",
        dataType: 'json',
        success: function(data) { 
            console.log("Success");
            var html = "";

            for (var i = 0; i < data.length; i++) {
                html += get_template("game", [
                    { key: "city",          value: data[i].city.name }, 
                    { key: "lat",           value: "Lat: " + data[i].city.coordinates.lat }, 
                    { key: "lon",           value: "Lon: " + data[i].city.coordinates.lon }, 
                    { key: "description",   value: "Yet to be implemented" /*data[i].description*/ }
                ]);
            }

            $("#games").html(html);
        },
        error: function(data) { console.log("Failed"); $("#games").html(data); }
    });
}

/// ---- Templates ---- ///

let templates = {};

function register_template(template_name, template_id) {
    var element = document.getElementById(template_id);
    templates[template_name] = element.innerHTML;
    element.parentNode.removeChild(element);
}

function get_template(template_name, key_value_pairs) {
    var html = templates[template_name];

    for (var i = 0; i < key_value_pairs.length; ++i) {
        var regex = new RegExp("{" + key_value_pairs[i].key + "}", "g");
        html = html.replace(regex, key_value_pairs[i].value);
    }

    return html;
}