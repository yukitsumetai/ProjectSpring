function CheckClient() {
    var email = document.getElementById('email').value;
    var passport = document.getElementById('passport').value;
    if(email!="" && passport.length==8) {
        $.ajax({
            url: "/springLine/newContract/client/check",
            data: {
                email: email,
                passport: passport
            },
            error: function (e) {
            },
            success: function (data) {
                if(!data){
                    var urlLink = "/newContract/client/confirm";
                    search.action = urlLink;
                }
                else alert("There is already a client with the same email and/or passport")
            }
        });
    }
};

function searchClient(table) {
    var number = document.getElementById('phoneNumber').value;
        $.ajax({
            url: "/springLine/clients/search/",
            data: {
                phoneNumber: number
            },
            error: function (e) {
                alert("Customer not found");
            },
            success: function (data) {
                if (data!="") addRowClient(data, table);//table for add
                else  alert("Customer not found");
            }
        });
};

function addRowClient(val, table) {
    var items = [];
        var address = (val.address || {}).street+", "+(val.address || {}).houseNo+"<br>"+(val.address || {}).zip+" "+(val.address || {}).city+"<br>"+(val.address || {}).country;
        var contracts = [];
        $.each(val.contracts, function (key, option) {
            var c = (option || {}).phoneNumber;
            c2 = (typeof (c) !== 'undefined' ? c : '');
            icon = "<a href='/existingContract/"+c2+"' class='edit' title='Edit'><i class='material-icons'>&#xE254;</i></a>";
            contracts.push(c2+icon + "<br>");
        })
        var icon;
        if (table != 1) {
            icon = "<input type='checkbox' class='chk myChk' value='" + val.id + "' name='clientID'>"
        }
        items.push("<tr>");
        items.push("<td>" + val.name+" "+val.surname+ "</td>");
        items.push("<td>" + contracts.join('') + "</td>");
        items.push("<td>" + val.passport + "</td>");
        items.push("<td>" + val.birthday + "</td>");
        items.push("<td>" + address + "</td>");
        items.push("<td>" + val.email + "</td>");
        if (table != 1) {
            items.push("<td>" + icon+ "</td>");
        }
        items.push("</tr>");
    $("#Table > tbody").empty();
    $("<tbody/>", {html: items.join("")}).appendTo("#Table")
}

function configure(){
    Webcam.set({
        width: 1280,
        height: 720,
        image_format: 'jpeg',
        jpeg_quality: 90,

    });
    Webcam.attach( '#my_camera' );
    document.getElementById('my_camera').style.display = "block";
    document.getElementById('cameraButtons').innerHTML =
        ' <div class="row">\n' +
        '                    <div class="col-sm-2 form-group">' +
        '<button class="btn btn-success" onClick="take_snapshot()">Take Picture <i class="fas fa-camera"></i></button>'+
        '</div>' +'<div class="col-sm-2 form-group">' +
        '<button class="btn btn-success left" onClick="closeCamera()">Close Camera <i class="fas fa-times"></i></button>'+
        '</div>'+'</div>';
}

function take_snapshot() {
    // take snapshot and get image data
    Webcam.snap( function(data_uri) {
        // display results in page
        document.getElementById('my_camera').innerHTML =
            '<img id="imageprev"  value="'+data_uri+'" src="'+data_uri+'"/>' +
            '<span id="image" name="image" value="'+data_uri+'"/>';
    } );
    setTimeout( saveSnap(), 10000);

    Webcam.attach( '#my_camera' );

}

function closeCamera(){
    document.getElementById('cameraButtons').innerHTML =
        '<button class="btn btn-success" onclick="configure()">Use Camera</button>';

    Webcam.reset();
    document.getElementById('my_camera').style.display = "none";
}

function saveSnap(){

    var file = document.getElementById("imageprev").src;

    var passdata= {"imageprev" : file}
    $.ajax({
        type: "POST",
        url: "/springLine/captureImage",
        data: {
            imageprev: file,
        },
        dataType : 'json',
        error: function (result)
        {
            alert("Something went wrong. Please try again")
        },
        success :function (data) {
            if (data!=""){
               if(data.name!="") document.getElementById("name").value = data.name;
                if(data.surname!="") document.getElementById("surname").value = data.surname;
                if(data.passport!="") document.getElementById("passport").value = data.passport;
                if(data.birthday!="") document.getElementById("birthday").value = data.birthday;
            } //addRowClient(data, table);//table for add
            else  alert("Something went wrong. Please try again");
        }
    });
}