<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 14.03.2019
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Bootstrap core CSS -->
    <link href="../resource/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="../resource/css/dashboard.css" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../resource/dist/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar" id="nav">
                <li class="active"><a href="../tariffs/">Tariffs </a></li>
                <li><a href="../options/">Options</a></li>
                <li><a href="../users/">Customers</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="../contract/">New customer</a></li>
                <li><a href="">Existing customer</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="">Contract Details</a></li>
                <li><a href="">Change Tariff</a></li>
                <li><a href="">Manage Options</a></li>
                <li><a href="">Change Tariff</a></li>
            </ul>
        </div>
    </div>
</div>
<script>
    $(function() {
        $("ul li").on("click", function() {
            $("li").removeClass("active");
            $(this).addClass("active");
        });
    });
</script>
</body>
</html>
