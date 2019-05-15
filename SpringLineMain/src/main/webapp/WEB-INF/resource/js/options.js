function radiobutton() {
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

function checkChange(className) {
    {
        function loopThroughItems(items, func) {
            var i;
            for (i = 0; i <= items.length; i++) {
                func(items[i]);
            }
        }
        var checkboxes = document.getElementsByClassName(className);
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
    var checkboxes2 = document.getElementsByClassName('myClass1');
    for (var j = 0; j < checkboxes2.length; j++) {
        if (checkboxes2[j].checked) {
            var name2 = checkboxes2[j].getAttribute('optionName');
            var price2 = '$' + checkboxes2[j].getAttribute('price');
            priceTotal+=parseFloat(checkboxes2[j].getAttribute('price'));
            priceTotalOneTime+=parseFloat(checkboxes2[j].getAttribute('priceOneTime'));
            var priceOneTime2 = '$' + checkboxes2[j].getAttribute('priceOneTime');
            var newInput2 = '<li class="generated">' + name2 +
                '<div class="cd-price">Monthly price<span class="right">' + price2 + '</span></div> ' +
                '<span class="cd-price">One time price<span  class="right">' + priceOneTime2 + '</span></span>';
            document.getElementById('optionsCart').insertAdjacentHTML('beforeend', newInput2);
        }
    }
    document.getElementById("totalMonthlyPrice").innerHTML = "$" + Number(priceTotal.toFixed(2));
    document.getElementById("totalOneTimePrice").innerHTML = "$" + Number(priceTotalOneTime.toFixed(2));

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
    var checkboxes2 = document.getElementsByClassName('myClass');
    for (var j = 0; i < checkboxes.length; j++) {
        if (checkboxes2[j].checked) {
            priceTotal+=parseFloat(checkboxes2[j].getAttribute('price'));
            priceTotalOneTime+=parseFloat(checkboxes2[j].getAttribute('priceOneTime'));
        }
    }
    document.getElementById("totalMonthlyPrice").innerHTML = "$" + Number(priceTotal.toFixed(2));
    document.getElementById("totalOneTimePrice").innerHTML = "$" + Number(priceTotalOneTime.toFixed(2));
}