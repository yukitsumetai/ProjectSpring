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