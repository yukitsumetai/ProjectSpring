function pagination(entity, page, table1, id, parent, optionId, group) {
    var link;
    if (entity == 0) {
        link = "/options/optionPages";
    } else if (entity == 1) {
        link = "/tariffs/tariffPages";
    }
    else if (entity == 2) {
        link = "/clients/clientPages";
    }
    $.ajax({
        url: link,
        data: {
            page: page,
            id: id,
            parent: parent,
            optionId: optionId,
            group: group
        },
        error: function (e) {
        },
        success: function (data) {

            if (entity == 0) {
                addRowsOption(data.data, table1);
            } else if (entity == 1) {
                addRowsTariff(data.data, table1);
            } else if(entity ==2){
                addRowsClient(data.data, table1);
            }
            checkboxes();

            document.getElementById('total').innerHTML = ((data.totalPages - 1) * 5 + data.lastPage);
            if (data.currentPage == data.totalPages) {
                document.getElementById('current').innerHTML = data.lastPage;
            } else document.getElementById('current').innerHTML = "5";

            var curr = 0;
            $("#pagination").empty();
            while (data.totalPages > curr) {
                $("<li ><a type='button' class='btn btn-outline-success pg' onclick='pagination("+entity+"," + (curr + 1) + "," + table1 + ",\"" + id + "\"," + parent + "," + optionId + ","+group+")'class='page_link'>" + (curr + 1) + "</a></li>").appendTo("#pagination");
                curr++;
            }
        }
    });
};

function checkboxes() {
    var existing = document.getElementsByClassName('opt2');
    if (existing != null) {
    for (var i = 0; i < existing.length; i++) {
            var checkboxes = document.getElementsByClassName('chk');
                for (var j = 0; j < checkboxes.length; j++) {
                    if (existing[i].value == checkboxes[j].value) {checkboxes[j].setAttribute("checked", true);
                        checkboxes[j].closest('tr').classList.add("highlight");}
                }
            }
        }
}

function addRowsOption(data, table) {
    var items = [];
    $.each(data, function (key, val) {
        var parent = (val.parent || {}).name;
        var parentTable = (typeof (parent) !== 'undefined' ? parent : '');

        var group = (val.optionGroup || {}).name;
        var optionGroup = (typeof (group) !== 'undefined' ? group : '');


        var childrenTable = [];
        $.each(val.children, function (key, children) {
            var c = (children || {}).name;
            c2 = (typeof (c) !== 'undefined' ? c : '');
            childrenTable.push(c2 + "<br>");
        })

        var tariffsTable = [];
        $.each(val.compatibleTariffs, function (key, tariff) {
            var c = (tariff || {}).name;
            c2 = (typeof (c) !== 'undefined' ? c : '');
            tariffsTable.push(c2 + "<br>");
        })
        var icon;
        var icon2 = ""
        var check = table;
        if (check == 1) {
            icon = "<a href='/options/edit/" + val.id + "/' class='edit' title='Edit'><i class='material-icons'>&#xE254</i></a>"
            if (val.isValid == true) {
                icon2 = "<a href='#deleteModal' class='delete' title='Delete' data-toggle='modal' data-target='#deleteModal' data-id=" + val.id + "><i class='material-icons'>&#xE872;</i></a>"
            }
        } else if (val.existing == true) {
            icon = "<input type='checkbox' class='chk' value='" + val.id + "' name='optionID' checked/>"
        } else {
            icon = "<input type='checkbox' class='chk' value='" + val.id + "' name='optionID'/>"
        }
        items.push("<tr>");
        items.push("<td>" + val.name + "</td>");
        items.push("<td>" + val.priceOneTime + "</td>");
        items.push("<td>" + val.priceMonthly + "</td>");
        items.push("<td>" + val.description + "</td>");
        items.push("<td>" + optionGroup + "</td>");
        items.push("<td>" + parentTable + "</td>");
        items.push("<td>" + childrenTable.join('') + "</td>");
        items.push("<td>" + tariffsTable.join('') + "</td>");
        items.push("<td>" + icon + icon2 + "</td>");

        items.push("</tr>");
    });
    $("#Table > tbody").empty();
    $("<tbody/>", {html: items.join("")}).appendTo("#Table")
}

function addRowsTariff(data, table) {
    var items = [];
    $.each(data, function (key, val) {
        var tariffsTable = [];
        $.each(val.options, function (key, option) {
            var c = (option || {}).name;
            c2 = (typeof (c) !== 'undefined' ? c : '');
            tariffsTable.push(c2);
        })
        var icon;
        var icon2 = ""
        var check = table;
        if (check == 1) {
            icon = "<a href='/tariffs/edit/" + val.id + "/' class='edit' title='Edit'><i class='material-icons'>&#xE254</i></a>"
            if (val.isValid == true) {
                icon2 = "<a href='#deleteModal' class='delete' title='Delete' data-toggle='modal' data-target='#deleteModal' data-id=" + val.id + "><i class='material-icons'>&#xE872;</i></a>"
            }
        } else if (val.existing == true) {
            icon = "<input type='checkbox' tariffName='"+val.name+"' price='"+val.price+"' id='"+val.id+"' class='chk myChk' value='" + val.id + "' name='tariffID' checked>"
        } else {
            icon = "<input type='checkbox' tariffName='"+val.name+"' price='"+val.price+"' id='"+val.id+"' class='chk myChk' value='" + val.id + "' name='tariffID'>"
        }
        items.push("<tr>");
        items.push("<td>" + val.name + "</td>");
        items.push("<td>" + val.price + "</td>");
        items.push("<td>" + val.description + "</td>");
        items.push("<td>" + tariffsTable.join(', ') + "</td>");
        items.push("<td>" + icon + icon2 + "</td>");

        items.push("</tr>");
    });
    $("#Table > tbody").empty();
    $("<tbody/>", {html: items.join("")}).appendTo("#Table")
}

function addRowsClient(data, table) {
    var items = [];
    $.each(data, function (key, val) {
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
    });
    $("#Table > tbody").empty();
    $("<tbody/>", {html: items.join("")}).appendTo("#Table")
}