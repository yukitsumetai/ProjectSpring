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
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>



<body>


<table class="table table-striped table-hover table-bordered" id="Table">
    <thead>
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Description</th>
        <th>Compatible Options</th>
        <c:choose>
            <c:when test="${table=='edit'}">
                <th>Actions</th>
            </c:when>
            <c:otherwise>
                <th>Choose Tariff</th>
            </c:otherwise>
        </c:choose>
    </tr>
    </thead>
    <tbody id="myTable">
    </tbody>
</table>
<div id="checked">
<c:forEach items="${existing}" var="e">
    <input name="tariffID2" type="hidden" class="opt2" value=${e.id}>
</c:forEach>
</div>
<div class="col-md-12 text-center">
    <div class="hint-text">Showing <strong id="current"></strong> out of <strong id="total"></strong> entries</div>
    <ul class="pagination" id="pagination">
    </ul>

</div>
<!--highlight-->
<script>
    $(document).on('click', '.chk', function() {
        higlight();
    });
</script>

</body>