function CheckClient() {
    var email = document.getElementById('email').value;
    var passport = document.getElementById('passport').value;
    if(email!="" && passport.length==8) {
        $.ajax({
            url: "/newContract/client/check",
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
            url: "/clients/search/",
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