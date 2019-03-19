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
                        <div class="search-box" class="col-sm-4">
                            <i class="material-icons">&#xE8B6;</i>
                            <input type="text" class="form-control" id="myInput" name="phoneNumber"
                                   placeholder="Search by phone number&hellip;">

                        </div>

                        <div class="newtariff col-sm-2">

                                <button type="submit" class="btn btn-success">Find</button>
                        </div>
                        </form>
                    </div>

                </div>
            </div>

            <form action="/newContract/confirmExisting" method="post" command="contract">

                <%@ include file="tableClients.jsp" %>

                <div>
                    <button type="submit" class="btn btn-success">Next</button>
                </div>
            </form>


        </div>
    </div>
</div>
</div>

<!--Checkbox-->
<script>
    $('input.chk').on('change', function () {
        $('input.chk').not(this).prop('checked', false);
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





