<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="TopNavBar.jsp" %>
<%@ include file="SideBar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!DOCTYPE html>
    <html lang="en">
    <head>

        <title>Current Clients</title>

    </head>

<body>

<div class="container-fluid">

    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

        <h1 class="page-header">Clients</h1>

        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">

                        <h2>Clients Administration</h2>

                    </div>
                    <div class="col-sm-6">
                        <form action="/getUser" method="post">
                        <div class="search-box">
                            <i class="material-icons">&#xE8B6;</i>
                            <input type="text" class="form-control" id="myInput" name="phoneNumber"
                                   placeholder="Search by phone number&hellip;">

                        </div>

                        <div class="newtariff">

                                <button type="submit" class="btn btn-success">Find</button>
                        </div>
                        </form>
                    </div>

                </div>
            </div>

            <form action="/newContract/client" method="post" command="contract">

                <%@ include file="tableClients.jsp" %>

                <div>
                    <button type="submit" class="btn btn-success">Next</button>
                </div>
            </form>


        </div>
    </div>
</div>
</div>

<script>
    $('#deleteModal').on('show.bs.modal', function (e) {

        //get data-id attribute of the clicked element
        var id = $(e.relatedTarget).data('id');

        //populate the textbox
        $(e.currentTarget).find('form[id="action"]').val(id);
        var $form = $('#action');
        $form.attr('action', '/options/delete/' + id);
    });
</script>
<!--Checkbox-->
<script>
    $('input.chk').on('change', function () {
        var generated = document.getElementById('optionsCart').getElementsByClassName('generated');
        for (var i = 0; i < generated.length; i++) {
            generated[i].remove();
        }
        var checkboxes = document.getElementsByName('optionID');
        for (var i = 0; i < checkboxes.length; i++) {

            if (checkboxes[i].checked) {

                var name = checkboxes[i].getAttribute('optionName');
                var price = '$' + checkboxes[i].getAttribute('price');

                var newInput = '<li class="generated">' + name +
                    '<div class="cd-price">' + price + '</div></li>';
                document.getElementById('optionsCart').insertAdjacentHTML('beforeend', newInput);
            }

        }

    });

</script>
<!--Highlight-->
<script>
    $(document).ready(function () {
        $('td :checkbox').bind('change click', function () {
            $(this).closest('tr').toggleClass('highlight', this.checked);
        }).change();
    });
</script>

</body>
</html>





