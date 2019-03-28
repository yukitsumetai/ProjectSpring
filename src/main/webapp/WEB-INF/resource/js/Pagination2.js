function optionPagination() {
    var page = document.getElementById('page').value;
    $.ajax({
        url: "/optionPages",
        data: {
            page: page
        },
        error: function (e) {
            document.getElementById('page').value = 0;
        },
        success: function (data) {
           addRows(data.data)
        }
    });
    page++;
    document.getElementById('page').value = page;
};

function addRows(data){
    var items =[];
    $.each(data, function(key, val){
        obj = JSON.parse(data);
        items.push("<tr>");
        items.push("<td>"+val.name+"</td>");
        items.push("<td>"+val.priceOneTime+"</td>");
        items.push("<td>"+val.priceMonthly+"</td>");
        items.push("<td>"+val.description+"</td>");
        //items.push("<td>"+val.optionGroup.name+"</td>");
        items.push("<td>"+$(val.parent, function(key, parent){
               parent.name}) +"</td>");
        items.push("<td>"+$.each(val.children, function(key, val){
                "<li>"+val.name+"</li>";
            })
            +"</td>");
        items.push("<td>"+$.each(val.compatibleTariffs, function(key, tariff){
                "<li>"+tariff.name+"</li>";
            })
            +"</td>");
         items.push("<td></td>");
       items.push("<a href='/options/edit/"+val.id+"class='edit' title='Edit'><iclass='material-icons'>&#xE254</i></a>");

        items.push("</tr>");
    });
    $("<tbody/>", {html:items.join("")}).appendTo("#Table")
}

