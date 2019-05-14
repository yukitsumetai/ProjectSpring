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
    <script src="${contextPath}/resource/js/modern.js"></script>
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
            <c:when test="${contractDTO.price!=0}">

            <span id="tariffName">${contractDTO.tariff.name}</span>
            <div class="right" id="tariffPrice">$${contractDTO.tariff.price}</div>
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
            <c:when test="${urlPath=='/existingContract/options'}">
                <li class='generated2'></li>
                <li class='generated'></li>
            </c:when>

            <c:when test="${contractDTO.options!=null}">
                <c:forEach items="${contractDTO.options}" var="o">
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
                    <b id="totalMonthlyPrice" class="right"></b></p>
                <p>Total One Time Price:
                    <b id="totalOneTimePrice" class="right"></b></p>
            </c:when>
            <c:when test="${urlPath=='/springLine/existingContract/options'}">
                <p>Total Monthly Price:
                    <b id="totalMonthlyPrice" class="right"></b></p>
                <p>Total One Time Price:
                    <b id="totalOneTimePrice" class="right"></b></p>
            </c:when>
            <c:when test="${urlPath=='/springLine/newContract/tariffs'}">
                <p>Total Monthly Price:
                    <b id="totalMonthlyPrice" class="right"></b></p>
            </c:when>
            <c:when test="${urlPath=='/springLine/existingContract/tariffs'}">
                <p>Total Monthly Price:
                    <b id="totalMonthlyPrice" class="right"></b></p>
            </c:when>

            <c:otherwise>
                <p>Total Monthly Price:
                    <b class="right">$${contractDTO.price}</b></p>
                <p>Total One Time Price:
                    <b class="right">$${contractDTO.priceOneTime}</b></p>
            </c:otherwise>
        </c:choose>

    </div> <!-- cd-cart-total -->

</div> <!-- cd-cart -->


</body>
</html>