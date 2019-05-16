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


<body>
<table class="table table-striped table-hover table-bordered" id="Table">
    <thead>
    <tr>
        <th>Name</th>
        <th>One Time Price</th>
        <th>Monthly Price</th>
        <th>Description</th>
        <th>Option
            Group
        </th>
        <th>Condition</th>
        <th>Is condition to</th>
        <th>Compatible
            Tariffs
        </th>
        <c:choose>
            <c:when test="${table=='edit'}">
                <th>Actions</th>
            </c:when>
            <c:otherwise>
                <th>Choose Options</th>
            </c:otherwise>
        </c:choose>
    </tr>
    </thead>
    <tbody id="myTable">



    </tbody>
</table>
<div class="col-md-12 text-center">
    <div class="hint-text">Showing <strong id="current">4</strong> out of <strong id="total"></strong> entries</div>
    <ul class="pagination" id="pagination">
    </ul>
    <input type="hidden" id="page" value=0>

</div>
<div id="checked">
    <c:forEach items="${existing}" var="e">
        <input name="optionID2" class="opt2" type="hidden" value=${e.id}>
    </c:forEach>
</div>
</div>
<!--highlight-->
<script>

    $(document).on('click', '.chk', function() {
        var checkboxes = document.getElementsByClassName('chk');
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) checkboxes[i].closest('tr').classList.add("highlight");
            else checkboxes[i].closest('tr').classList.remove("highlight");
        }
    });
</script>

</body>





