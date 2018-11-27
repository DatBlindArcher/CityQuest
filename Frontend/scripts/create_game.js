$(document).ready(function() {
    $('.menu .item').tab();
});

function change_tab(tab) {
    $('.item.active').removeClass('active');
    $('#tab' + tab).addClass('active');
    $.tab('change tab', tab);
}