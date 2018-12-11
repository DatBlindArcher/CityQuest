let templates = {};
let host = "http://193.191.177.8:10008";
//let host = "http://localhost:8080";

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