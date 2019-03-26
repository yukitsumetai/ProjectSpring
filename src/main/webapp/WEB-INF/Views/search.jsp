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
<%@ include file="elements/TopNavBar.jsp" %>
<%@ include file="elements/SideBar.jsp" %>
<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="../resource/images/favicon1.ico">


    <title>Contract search</title>
</head>
<body>
<div class="container-fluid">
    <main class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header">Contract search</h1>

        <div id="table-wrapper">
            <div class="table-title">
                <h2>Please add number of phone</h2>
            </div>
        </div>
        <div id="personal data">
            <form id="search">
                <div class="row">
                        <div class="search-box" class="col-sm-4">
                            <i class="material-icons">&#xE8B6;</i>
                            <input type="text" class="form-control" id="searchBox" name="searchBox"
                                   placeholder="Search by phone number&hellip;">
                        </div>
                        <div class="newtariff col-sm-2">

                            <button onclick="path()" class="btn btn-success">Find</button>
                        </div>
                    ${message}
                </div>
            </form>
        </div>
    </main>

</div>
<!--Calendar-->
<script>
    function path() {
        var urlLink = "/existingContract/"+document.getElementById("searchBox").value;
       search.action = urlLink;
    }
</script>

</body>
</html>
