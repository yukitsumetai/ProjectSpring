$(document).ready(function() {
    var s = '';
    for (var i = 0; i < 1000; i++) {
        s += '<option>option ' + i + '</option>';
    }

    $('#combobox1').combobox();

    $('#combobox2').html(s);
    $('#combobox2').combobox();
});