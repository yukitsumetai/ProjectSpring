<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 17.03.2019
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <script src="${contextPath}/resource/dist/js/modern.js"></script>
    <link href="${contextPath}/resource/css/shoppingCart.css" rel="stylesheet">
    <link href="${contextPath}/resource/css/res.css" rel="stylesheet">
    <script src="${contextPath}/resource/js/shoppingCart.js"></script> <!-- Gem jQuery -->
    <title>Side Cart</title>
</head>
<body class="foo">


<div id="cd-shadow-layer"></div>

<div id="cd-cart">
    <h2>Tariff</h2>
    <ul class="cd-cart-items" id="tariffCart">
        <li>
            <c:choose>
            <c:when test="${contractDto.price!=0.00}">

            <span id="tariffName">${contractDto.tariff.name}</span>
            <div class="right" id="tariffPrice">$${contractDto.tariff.price}</div>
            </c:when>
            <c:otherwise>
        <li class='generated1'></li>
        <span id="tariffName"></span>
        <div class="cd-price" id="tariffPrice"></div>
        </c:otherwise>
        </c:choose>
        </li>
    </ul>
    <br>
    <h2>Options</h2>
    <ul class="cd-cart-items" id="optionsCart">
        <c:choose>
            <c:when test="${urlPath=='/springLine/existingContract/options'}">
                <li class='generated2'></li>
                <li class='generated'></li>
            </c:when>

            <c:when test="${contractDto.options!=null}">
                <c:forEach items="${contractDto.options}" var="o">
                    <li>
                            ${o.name}
                        <div class="cd-price"><p>Monthly price<span class="right">$${o.priceMonthly}</span></p></div>
                        <div class="cd-price">One time price<span class="right">$${o.priceOneTime}</span></div>
                    </li>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li class='generated2'></li>
                <li class='generated'></li>
            </c:otherwise>
        </c:choose>

    </ul> <!-- cd-cart-items -->
    <div class="cd-cart-total">
        <c:choose>
            <c:when test="${urlPath=='/springLine/newContract/options'}">
                <p>Total Monthly Price:
                    <strong id="totalMonthlyPrice" class="right"></strong></p>
                <p>Total One Time Price:
                    <strong id="totalOneTimePrice" class="right"></strong></p>
            </c:when>
            <c:when test="${urlPath=='/springLine/existingContract/options'}">
                <p>Total Monthly Price:
                    <strong id="totalMonthlyPrice" class="right"></strong></p>
                <p>Total One Time Price:
                    <strong id="totalOneTimePrice" class="right"></strong></p>
            </c:when>
            <c:when test="${urlPath=='/springLine/newContract/tariffs'}">
                <p>Total Monthly Price:
                    <strong id="totalMonthlyPrice" class="right"></strong></p>
            </c:when>
            <c:when test="${urlPath=='/springLine/existingContract/tariffs'}">
                <p>Total Monthly Price:
                    <strong id="totalMonthlyPrice" class="right"></strong></p>
            </c:when>

            <c:otherwise>
                <p>Total Monthly Price:
                    <strong class="right">$${contractDto.price}</strong></p>
                <p>Total One Time Price:
                    <strong class="right">$${contractDto.priceOneTime}</strong></p>
            </c:otherwise>
        </c:choose>

    </div> <!-- cd-cart-total -->

</div> <!-- cd-cart -->


</body>
</html>