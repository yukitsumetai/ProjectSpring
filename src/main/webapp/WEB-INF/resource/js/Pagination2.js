function optionPagination(entity, page, table1, id) {
var link;
if (entity==0){
    link="/options/optionPages";
}
    $.ajax({
        url: link,
        data: {
            page: page,
            id: id
        },
        error: function (e) {},
        success: function (data) {

            addRowsOption(data.data,table1)
            document.getElementById('total').innerHTML = ((data.totalPages - 1) * 5 + data.lastPage);
            if (data.currentPage == data.totalPages) {
                document.getElementById('current').innerHTML = data.lastPage;
            } else document.getElementById('current').innerHTML = "5";

            var curr = 0;
            $("#pagination").empty();
            while (data.totalPages > curr) {
                $("<li ><a type='button' class='btn btn-outline-success pg' onclick='optionPagination(0," + (curr + 1) + "," +table1+ "," + id+  ")'class='page_link'>" + (curr + 1) + "</a></li>").appendTo("#pagination");
                curr++;
            }
        }
    });
};

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
            tariffsTable.push(c2+ "<br>");
        })
        var icon;
        var icon2 = ""
        var check = table;
        if (check == 1) {
            icon = "<a href='/options/edit/" + val.id + "/' class='edit' title='Edit'><i class='material-icons'>&#xE254</i></a>"
            if (val.isValid == true) {
                icon2 = "<a href='#deleteModal' class='delete' title='Delete' data-toggle='modal' data-target='#deleteModal' data-id=" + val.id + "><i class='material-icons'>&#xE872;</i></a>"
            }
        }
        else if(val.existing==true) {icon="<input type='checkbox' class='chk' value='"+val.id+"' name='optionID' checked/>"}
        else {icon="<input type='checkbox' class='chk' value='"+val.id+"' name='optionID'/>"}
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

