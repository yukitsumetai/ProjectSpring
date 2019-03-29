



function optionPagination(page) {
    //var curr = document.getElementById('page').value;
    $.ajax({
        url: "/options/optionPages",
        data: {
            page: page
        },
        error: function (e) {
            document.getElementById('page').value = 0;
        },
        success: function (data) {
            addRowsOption(data.data)
            document.getElementById('total').innerHTML = ((data.totalPages-1)*5+data.lastPage);
            if(page== data.totalPages){
                document.getElementById('current').innerHTML = data.lastPage;
            }
            else  document.getElementById('current').innerHTML = "5";

            var curr= 0;
            while(data.totalPages > curr){
                $("<li><a href='' onclick='optionPagination("+(curr+1)+")'class='page_link'>"+(curr+1)+"</a></li>").appendTo(pagination);
                curr++;
            }


        }
    });
    page++;
    document.getElementById('page').value = page;
};

function addRowsOption(data) {
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
            tariffsTable.push(c2);
        })
        var icon;
        var icon2 = ""

         icon = "<a href='/options/edit/"+val.id +"/' class='edit' title='Edit'><i class='material-icons'>&#xE254</i></a>"
        if (val.isValid == true) {
            icon2 = "<a href='#deleteModal' class='delete' title='Delete' data-toggle='modal' data-target='#deleteModal' data-id=" + val.id + "><i class='material-icons'>&#xE872;</i></a>"
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
    $("<tbody/>", {html: items.join("")}).appendTo("#Table")
}

