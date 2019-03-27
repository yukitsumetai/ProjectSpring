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
    $.each(data, function(val){
        items.push("tr");
        items.push("td >"+val.name+"</td>")
        items.push("/tr");
    });
    $("<tbody/>", {html:items.join("")}).appendto("table")

}