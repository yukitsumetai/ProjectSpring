function requiredField(input) {
    if (input.type == "date") {
        var now = new Date();
        var max = new Date();
        max.setYear(now.getYear()-18+1900);
        var min = new Date();
        min.setYear(now.getYear() - 100);
        var nd = new Date(input.value);
        if (nd > min && nd <max) input.style.borderColor = "#2ecc71";
        else input.style.borderColor = "#e74c3c";
    } else {
        if (!input.checkValidity()) input.style.borderColor = "#e74c3c";
        else input.style.borderColor = "#2ecc71";
    }
    input.style.borderWidth = "2px";
}