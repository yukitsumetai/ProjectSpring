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