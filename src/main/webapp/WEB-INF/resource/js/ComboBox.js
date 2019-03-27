function Validate(optId) {
    var page = document.getElementById('page').value;
    $.ajax({
        url: "/test",
        data: {
            page: page
        },
        error: function (e) {
            document.getElementById('page').value = 0;
        },
        success: function (data) {
            $.each(data, function (i, item) {
                if (item.id!=optId) {
                var addInput = '<option value=' + item.id + '>' + item.name + '</option>';
                document.getElementById('select1').insertAdjacentHTML('beforeend', addInput);
                }
            });
        }
    });
    page++;
    document.getElementById('page').value = page;
};

function phoneNumber() {
    var page = document.getElementById('page').value;
    $.ajax({
        url: "/phoneNumbers",
        data: {
            page: page
        },
        error: function (e) {
            document.getElementById('page').value = 0;
        },
        success: function (data) {
            $.each(data, function (i, item) {
                    var addInput = '<option value=' + item.number + '>' + item.number + '</option>';
                    document.getElementById('select1').insertAdjacentHTML('beforeend', addInput);
            });
        }
    });
    page++;
    document.getElementById('page').value = page;
};



function Highlight(){
    $('td :checkbox').bind('change click', function () {
        $(this).closest('tr').toggleClass('highlight', this.checked);
    }).change();
}

function scrolled(o, optId) {
    if (document.getElementById('page').value > 0) {
        //visible height + pixel scrolled = total height
        if (o.offsetHeight + o.scrollTop >= o.scrollHeight) {
            Validate(optId);
        }
    }
    scrolled(this);
}

function scrolledPhone(o) {
    if (document.getElementById('page').value > 0) {
        //visible height + pixel scrolled = total height
        if (o.offsetHeight + o.scrollTop >= o.scrollHeight) {
           phoneNumber();
        }
    }
    scrolledPhone(this);
}

function radiobutton(state) {
    var radios = document.getElementsByName('options');
    var group = document.getElementById('selectForm');
    var select = document.getElementById('select1');
    var r = document.getElementById("relation");
    for (var i = 0, length = radios.length; i < length; i++) {
        if (radios[i].checked) {
            r.value = radios[i].value;
            break;
        }
    }

    if (r.value == "parent") {
        select.setAttribute("disabled", true);
        group.style.display = "block";
    } else if (state == "children") {
        if (r.value == "nothing") {
            select.setAttribute("disabled", true);
            group.style.display = "block";
        } else {
            group.style.display = "none";
            select.removeAttribute("disabled");
        }
    } else {
        group.style.display = "none";
        select.removeAttribute("disabled");
    }
}

//Sort
function sortTable(n) {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("Table");
    switching = true;
    // Set the sorting direction to ascending:
    dir = "asc";
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            /* Check if the two rows should switch place,
            based on the direction, asc or desc: */
            if (dir == "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            } else if (dir == "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            // Each time a switch is done, increase this count by 1:
            switchcount++;
        } else {
            /* If no switching has been done AND the direction is "asc",
            set the direction to "desc" and run the while loop again. */
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}

function checkChange() {
    {
        function loopThroughItems(items, func) {
            var i;
            for (i = 0; i <= items.length; i++) {
                func(items[i]);
            }
        }
        var checkboxes = document.getElementsByClassName('chk');
        for (var i = 0; i < checkboxes.length; i++) {
            var value = checkboxes[i].value;
            var children = document.getElementById(value);
            if (children != null) {
                if (checkboxes[i].checked) {
                    children.style.display = "block";
                    loopThroughItems(children.getElementsByTagName("input"), function (input) {
                        if (!input || input.type.toLowerCase() == "submit") return;
                        input.disabled = false;
                    });

                } else {
                    children.style.display = "none";
                    loopThroughItems(children.getElementsByTagName("input"), function (input) {
                        if (!input || input.type.toLowerCase() == "submit") return;
                        input.disabled = true;
                        input.checked = false;
                    });
                }
            }
        }
    }
}

function radioChange() {
    {
        function loopThroughItems(items, func) {
            var i;
            for (i = 0; i <= items.length; i++) {
                func(items[i]);
            }
        }

        var checkboxes = document.getElementsByClassName('radio');
        for (var i = 0; i < checkboxes.length; i++) {
            var value = checkboxes[i].value;
            var children = document.getElementById(value);
            if (children != null) {
                if (checkboxes[i].checked) {
                    children.style.display = "block";
                    loopThroughItems(children.getElementsByTagName("input"), function (input) {
                        if (!input || input.type.toLowerCase() == "submit") return;
                        input.disabled = false;
                    });

                } else {
                    children.style.display = "none";
                    loopThroughItems(children.getElementsByTagName("input"), function (input) {
                        if (!input || input.type.toLowerCase() == "submit") return;
                        input.disabled = true;
                        input.checked = false;
                    });
                }
            }
        }
    }
}
function radioBasket(tariffPrice){
    $('.generated2').remove();
    $('.generated3').remove();
    var checkboxes = document.getElementsByClassName('myClass');
    var priceTotal =tariffPrice;
    var priceTotalOneTime=0;
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            var name = checkboxes[i].getAttribute('optionName');
            var value = checkboxes[i].value;
            var price = '$' + checkboxes[i].getAttribute('price');
            priceTotal+=parseFloat(checkboxes[i].getAttribute('price'));
            priceTotalOneTime+=parseFloat(checkboxes[i].getAttribute('priceOneTime'));
            var priceOneTime = '$' + checkboxes[i].getAttribute('priceOneTime');
            var newInput = '<li class="generated2">' + name +
                '<div class="cd-price">Monthly price<span class="right">' + price + '</span></div> ' +
                '<span class="cd-price">One time price<span  class="right">' + priceOneTime + '</span></span>';
            document.getElementById('optionsCart').insertAdjacentHTML('beforeend', newInput);
            var formResults = '<input class="generated3" type="hidden" name="optionID" value="'+value+'">';
            document.getElementById('radios').insertAdjacentHTML('beforeend', formResults);
        }
    }

    $('.generated').remove();
    var checkboxes = document.getElementsByClassName('myClass1');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            var name = checkboxes[i].getAttribute('optionName');
            var price = '$' + checkboxes[i].getAttribute('price');
            priceTotal+=parseFloat(checkboxes[i].getAttribute('price'));
            priceTotalOneTime+=parseFloat(checkboxes[i].getAttribute('priceOneTime'));
            var priceOneTime = '$' + checkboxes[i].getAttribute('priceOneTime');
            var newInput = '<li class="generated">' + name +
                '<div class="cd-price">Monthly price<span class="right">' + price + '</span></div> ' +
                '<span class="cd-price">One time price<span  class="right">' + priceOneTime + '</span></span>';
            document.getElementById('optionsCart').insertAdjacentHTML('beforeend', newInput);
        }
    }
    document.getElementById("totalMonthlyPrice").innerHTML = "$" + priceTotal;
    document.getElementById("totalOneTimePrice").innerHTML = "$" + priceTotalOneTime;

}
function checksBasket(tariffPrice){
    $('.generated').remove();
    var priceTotal =tariffPrice;
    var priceTotalOneTime=0.00;
    var checkboxes = document.getElementsByClassName('myClass1');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            var name = checkboxes[i].getAttribute('optionName');
            var price = '$' + checkboxes[i].getAttribute('price');
            var priceOneTime = '$' + checkboxes[i].getAttribute('priceOneTime');
            priceTotal+=parseFloat(checkboxes[i].getAttribute('price'));
            priceTotalOneTime+=parseFloat(checkboxes[i].getAttribute('priceOneTime'));
            var newInput = '<li class="generated">' + name +
                '<div class="cd-price">Monthly price<span class="right">' + price + '</span></div> ' +
                '<span class="cd-price">One time price<span  class="right">' + priceOneTime + '</span></span>';
            document.getElementById('optionsCart').insertAdjacentHTML('beforeend', newInput);
        }
    }
    var checkboxes = document.getElementsByClassName('myClass');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            priceTotal+=parseFloat(checkboxes[i].getAttribute('price'));
            priceTotalOneTime+=parseFloat(checkboxes[i].getAttribute('priceOneTime'));
        }
    }
    document.getElementById("totalMonthlyPrice").innerHTML = "$" + priceTotal;
    document.getElementById("totalOneTi,ePrice").innerHTML = "$" + priceTotalOneTime;
}