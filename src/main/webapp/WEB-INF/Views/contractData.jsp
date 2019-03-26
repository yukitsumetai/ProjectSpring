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
    <div class="col-sm-4 form-group">
        <label>Tariff: </label> ${contractDTO.tariff.name}<br>
        <div class="row description">
            ${contractDTO.tariff.description}
        </div>
    </div>

    <div class="col-sm-4 form-group price" color=" #a5aebc">

        $${contractDTO.tariff.price}
    </div>
    <div class="col-sm-4 form-group price" color=" #a5aebc">
        <a href=" /existingContract/tariffChange" class="edit" id="editIcon" title="Edit"><i
                class="material-icons">&#xE254;</i></a>

    </div>
</div>

<c:forEach items="${contractDTO.options}" var="option">
            <div class="row">
                <div class="col-sm-4 form-group">
                    <label>Options: </label> ${option.name}<br>
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
                        <c:otherwise>
                            <a href="/existingContract/optionsDelete/${option.id}" name="delete" id="deleteIcon"
                               title="Delete">
                                <i class="material-icons">&#xE872;</i>
                            </a>
                        </c:otherwise>
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
            <c:otherwise>
                <a href="/existingContract/optionsAdd" class="add" id="addIcon" title="Add Options">
                    <i class="material-icons">&#xe854;</i>
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
