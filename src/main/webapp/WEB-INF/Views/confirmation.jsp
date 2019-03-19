<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="TopNavBar.jsp" %>
<%@ include file="SideBar.jsp" %>

<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="../resource/images/favicon1.ico">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../resource/dist/js/bootstrap.min.js"></script>
    <script src="../resource/js/pagination.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <title>Client details</title>
</head>
<body>
<div class="container-fluid">
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header">Confirmation page</h1>

        <%@ include file="customerData.jsp" %>
        <%@ include file="contractData.jsp" %>

        <div class="row">
            <div class="col-sm-1 form-group">
                <form class="form-group" action="/newContract/confirm/true" method="post">
                    <button type="submit" class="btn btn-success">Confirm</button>
                </form>
            </div>
            <div class="col-sm-1 form-group price">
                <form class="form-group" action="/tariffs" >
                    <button type="submit" class="btn btn-danger">Cancel</button>
                </form>
            </div>
        </div>

        </div>
    </div>
</div>
</div>


</body>
</html>