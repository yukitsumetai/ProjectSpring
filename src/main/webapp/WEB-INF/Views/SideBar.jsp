<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 14.03.2019
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="icon" href="${contextPath}/resource/images/favicon1.png">
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar" id="nav">
                <li class="active"><a href="${contextPath}/tariffs/">Tariffs </a></li>
                <li><a href="${contextPath}/options/">Options</a></li>
                <li><a href="${contextPath}/optionGroups/">Option Groups</a></li>
                <li><a href="${contextPath}/users/">Customers</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="../newContract/tariffs">New Contract</a></li>
                <li><a href="../existingContract/search">Existing Contract</a></li>
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

