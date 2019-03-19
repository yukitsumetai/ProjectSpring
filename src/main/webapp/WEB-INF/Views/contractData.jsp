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
    <h2>Contract details</h2>
</div>
<div class="row">
    <div class="col-sm-4 form-group">
    </div>
    <div class="col-sm-4 form-group">
        <b>Monthly Price</b>
    </div>
    <div class="col-sm-4 form-group">
        <b>One Time Price</b>
    </div>
</div>
<div class="row">
    <div class="col-sm-4 form-group">
        <label>Tariff: </label> ${contractDTO.tariff.name}<br>
        <div class="row description">
            ${DTO.tariff.description}
        </div>
    </div>
    <div class="col-sm-8 form-group price" color=" #a5aebc">
        $${contractDTO.tariff.price}
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
            $${option.priceOneTime}
        </div>
    </div>

</c:forEach>
<div class="row">
    <div class="col-sm-4 form-group">
        <label><b>Total Price: </b></label>
    </div>
    <div class="col-sm-8 form-group price">
        <b>$${contract.price}</b>
    </div>
</div>

</body>
</html>
