function valthisform() {
    event.preventDefault();
    var email = document.getElementById('email').value;
    var passport = document.getElementById('passport').value;
    if (email != "" && passport.length == 9) {
        $.ajax({
            url: "/springLine/newContract/client/check",
            data: {
                email: email,
                passport: passport
            },
            error: function (e) {
            },
            success: function (data) {
                if (!data) {
                    //var urlLink = "/newContract/client/confirm";
                    // button.action = urlLink;
                    document.form1.submit();
                } else {
                    document.getElementById("modalText").innerHTML = "Cannot create new client";
                    document.getElementById("modalText").innerHTML = "Client with such email/passport already exists";
                    $('#modal').modal('show');
                }
            }
        });
    }
};

function searchClient(table) {
    var number = document.getElementById('phoneNumber').value;
    if (number != "") {
        $.ajax({
            url: "/springLine/clients/search/",
            data: {
                phoneNumber: number
            },
            error: function (e) {
                document.getElementById("modalText").innerHTML = "Client with enterd phone number does not exist";
                document.getElementById("modalHeader").innerHTML = "Client not found";
                $('#modal').modal('show');
            },
            success: function (data) {
                if (data != "") addRowClient(data, table);//table for add
                else {
                    document.getElementById("modalText").innerHTML = "Client with enterd phone number does not exist";
                    document.getElementById("modalHeader").innerHTML = "Client not found";
                    $('#modal').modal('show');
                }
            }
        });
    } else {
        return pagination(2, 1, table);
    }
};

function addRowClient(val, table) {
    var items = [];
    var address = (val.address || {}).street + ", " + (val.address || {}).houseNo + "<br>" + (val.address || {}).zip + " " + (val.address || {}).city + "<br>" + (val.address || {}).country;
    var contracts = [];
    $.each(val.contracts, function (key, option) {
        var c = (option || {}).phoneNumber;
        var c2 = (typeof (c) !== 'undefined' ? c : '');
        icon = "<a href='/existingContract/" + c2 + "' class='edit' title='Edit'><em class='material-icons'>&#xE254;</em></a>";
        contracts.push(c2 + icon + "<br>");
    });
    var icon;
    if (table != 1) {
        icon = "<input type='checkbox' class='chk myChk' value='" + val.id + "' name='clientID'>"
    }
    items.push("<tr>");
    items.push("<td>" + val.name + " " + val.surname + "</td>");
    items.push("<td>" + contracts.join('') + "</td>");
    items.push("<td>" + val.passport + "</td>");
    items.push("<td>" + val.birthday + "</td>");
    items.push("<td>" + address + "</td>");
    items.push("<td>" + val.email + "</td>");
    if (table != 1) {
        items.push("<td>" + icon + "</td>");
    }
    items.push("</tr>");
    $("#Table > tbody").empty();
    $("<tbody/>", {html: items.join("")}).appendTo("#Table")
}

function configure() {
    Webcam.set({
        width: 640,
        height: 360,
        image_format: 'jpeg'

    });
    Webcam.attach('#my_camera');
    document.getElementById('my_camera').style.display = "block";
    document.getElementById('cameraButtons').innerHTML =
        ' <div class="row">\n' +
        '                    <div class="col-sm-2 form-group">' +
        '<button class="btn btn-success" onClick="take_snapshot()">Take Picture <em class="fas fa-camera"></em></button>' +
        '</div>' + '<div class="col-sm-2 form-group">' +
        '<button class="btn btn-success left" onClick="closeCamera()">Close Camera <em class="fas fa-times"></em></button>' +
        '</div>' + '</div>';
}

function take_snapshot() {
    // take snapshot and get image data
    Webcam.snap(function (data_uri) {
        // display results in page
        document.getElementById('my_camera').innerHTML =
            '<img id="imageprev"  value="' + data_uri + '" src="' + data_uri + '"/>' +
            '<span id="image" name="image" value="' + data_uri + '"/>';
    });
    setTimeout(saveSnap(), 10000);

    Webcam.attach('#my_camera');

}

function closeCamera() {
    document.getElementById('cameraButtons').innerHTML =
        '<button class="btn btn-success" onclick="configure()">Use Camera</button>';
    Webcam.reset();
    document.getElementById('my_camera').style.display = "none";
}

function saveSnap() {

    var file = document.getElementById("imageprev").src;
    var id = document.getElementById('contract').value;
    $.ajax({
        type: "POST",
        url: "/springLine/captureImage",
        data: {
            imageprev: file,
            contract: id
        },
        dataType: 'json',
        error: function (result) {
            document.getElementById("modalText").innerHTML = "Please try again!";
            document.getElementById("modalHeader").innerHTML = "Something went wrong";
            $('#modal').modal('show');
        },
        success: function (data) {
            if (data != null) {
                if (data.name != null) {
                    var nameField = document.getElementById("name");
                    nameField.value = data.name;
                    requiredField(nameField);
                }
                if (data.surname != null) {
                    var surnameField = document.getElementById("surname");
                    surnameField.value = data.surname;
                    requiredField(surnameField);
                }
                if (data.passport != null) {
                    var passportField = document.getElementById("passport");
                    passportField.value = data.passport;
                    requiredField(passportField);
                }
                if (data.birthday != null) {
                    var birthdayField = document.getElementById("birthday");
                    birthdayField.value = data.birthday;
                    requiredField(birthdayField);
                }
            } else {
                document.getElementById("modalText").innerHTML = "Please try again!";
                document.getElementById("modalHeader").innerHTML = "Something went wrong";
                $('#modal').modal('show');
            }
        }
    });
}