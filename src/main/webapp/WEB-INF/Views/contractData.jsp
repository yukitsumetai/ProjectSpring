<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 17.03.2019
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer data</title>

</head>
<body>

<div class="table-title">
    <h2>Details of contract: <b> +${contractDTO.phoneNumber}</b></h2>
</div>
<div class="row">
    <div class="col-sm-4 form-group">
    </div>
    <div class="col-sm-4 form-group">
        <b>Monthly Price</b>
    </div>

    <div class="col-sm-4 form-group">
        <c:choose>
            <c:when test="${table=='add'}">
                <b>One Time Price</b>
            </c:when>
            <c:otherwise>
                <b>Actions</b>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<div class="row">
    <div class="col-sm-8 form-group">
        <label><b>TARRIFF</b> </label>
    </div>
    <div class="col-sm-4 form-group price" color=" #a5aebc">
        <c:if test="${table!='add'}">
            <a href=" /existingContract/tariffChange" class="edit" data-toggle="modal"
               data-target="#changeTariff" id="editIcon" title="Change Tariff"  <c:if test="${contractDTO.blocked==true}">disabled</c:if> ><i
                    class="material-icons">&#xE254;</i></a>
        </c:if>
    </div>
</div>
<div class="row">
    <div class="col-sm-4 form-group">
        ${contractDTO.tariff.name}<br>
        <div class="row description">
            ${contractDTO.tariff.description}
        </div>
    </div>

    <div class="col-sm-4 form-group price" color=" #a5aebc">

        $${contractDTO.tariff.price}
    </div>

</div>
<div class="row">
    <div class="col-sm-8 form-group">
        <label><b>OPTIONS</b> </label>
    </div>
    <div class="col-sm-4 form-group price" color=" #a5aebc">
        <c:if test="${table!='add'}">
            <a href="/existingContract/options" class="add" id="editIcon" title="Edit Options"  <c:if test="${contractDTO.blocked==true}">disabled</c:if>>
                <i class="material-icons">&#xE254;</i>
            </a>
        </c:if>
    </div>
</div>
<c:forEach items="${contractDTO.options}" var="option">
            <div class="row">
                <div class="col-sm-4 form-group">
                    ${option.name}<br>
                    <div class="row description">
                            ${option.description}
                    </div>
                </div>
                <div class="col-sm-4 form-group price">
                    $${option.priceMonthly}
                </div>

                <div class="col-sm-4 form-group price">
                    <c:choose>
                        <c:when test="${table=='add'}">
                            $${option.priceOneTime}
                        </c:when>
                    </c:choose>
                </div>
            </div>
</c:forEach>
<div class="row">
    <div class="col-sm-4 form-group">
        <label><b>Total Price: </b></label>
    </div>
    <div class="col-sm-4 form-group price">
        <b>$${contractDTO.price}</b>
    </div>

    <div class="col-sm-4 form-group price">
        <c:choose>
            <c:when test="${table=='add'}">
                <b>$${contractDTO.priceOneTime}</b>
            </c:when>
        </c:choose>
    </div>
</div>

</body>
</html>
