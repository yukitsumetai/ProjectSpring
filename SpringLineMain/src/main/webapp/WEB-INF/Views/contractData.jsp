<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 17.03.2019
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer data</title>

</head>
<body>

<div class="table-title">
    <h2>Details of contract: <strong> +${contractDto.phoneNumber}</strong></h2>
</div>
<div class="row">
    <div class="col-sm-4 form-group">
    </div>
    <div class="col-sm-4 form-group">
        <strong>Monthly Price</strong>
    </div>

    <div class="col-sm-4 form-group">
        <c:choose>
            <c:when test="${table=='add'}">
                <strong>One Time Price</strong>
            </c:when>
            <c:otherwise>
                <strong>Actions</strong>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<div class="row">
    <div class="col-sm-8 form-group">
        <label><strong>TARRIFF</strong> </label>
    </div>
<c:if test="${contractDto.blocked==false}">
    <div class="col-sm-4 form-group price" color=" #a5aebc">
        <c:if test="${table!='add'}">
            <a href="#changeTariff" class="edit" data-toggle="modal"
               data-target="#changeTariff" id="editIcon" title="Change Tariff"><em
                    class="material-icons">&#xE254;</em></a>
        </c:if>
    </div>
</c:if>
</div>
<div class="row">
    <div class="col-sm-4 form-group">
        ${contractDto.tariff.name}<br>
        <div class="row description">
            ${contractDto.tariff.description}
        </div>
    </div>

    <div class="col-sm-4 form-group price" color=" #a5aebc">

        $${contractDto.tariff.price}
    </div>

</div>
<div class="row">
    <div class="col-sm-8 form-group">
        <label><strong>OPTIONS</strong> </label>
    </div>
    <c:if test="${contractDto.blocked==false}">
    <div class="col-sm-4 form-group price" color=" #a5aebc">
        <c:if test="${table!='add'}">
            <a href="${contextPath}/existingContract/options" class="add" id="editIcon" title="Edit Options" >
                <em class="material-icons">&#xE254;</em>
            </a>
        </c:if>
    </div>
    </c:if>
</div>
<c:forEach items="${contractDto.options}" var="option">
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
        <label><strong>Total Price: </strong></label>
    </div>
    <div class="col-sm-4 form-group price">
        <strong>$${contractDto.price}</strong>
    </div>

    <div class="col-sm-4 form-group price">
        <c:choose>
            <c:when test="${table=='add'}">
                <strong>$${contractDto.priceOneTime}</strong>
            </c:when>
        </c:choose>
    </div>
</div>

</body>
</html>
