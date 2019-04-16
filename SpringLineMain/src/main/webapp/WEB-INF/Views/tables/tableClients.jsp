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
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<head>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../../resource/dist/js/bootstrap.min.js"></script>
    <script src="../../resource/js/pagination.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>

</head>


<div id="checked">
</div>
<table class="table table-striped table-hover table-bordered" id="Table">
    <thead>
    <tr>
        <th>Name</th>
        <th>Contracts</th>
        <th>Passport number</th>
        <th>Birthday</th>
        <th>Address</th>
        <th>eMail</th>
        <c:if test="${table=='add'}">
            <th>Choose Client</th>
        </c:if>
    </tr>
    </thead>
    <tbody id="myTable">
    </tbody>
</table>


<div class="col-md-12 text-center">
    <div class="hint-text">Showing <b id="current">4</b> out of <b id="total"></b> entries</div>
    <ul class="pagination" id="pagination">
    </ul>
    <input type="hidden" id="page" value=0>

</div>


<!--PaginationDao-->
<script>
    $(document).ready(function () {

        $('#myTable').pageMe({pagerSelector: '#pagination', showPrevNext: true, hidePageNumbers: false, perPage: 4});

    });
</script>

<!--Tooltips-->
<script type="text/javascript">
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>
