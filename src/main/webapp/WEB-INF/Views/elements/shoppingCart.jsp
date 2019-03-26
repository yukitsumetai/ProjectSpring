<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 17.03.2019
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>

    <script src="${contextPath}/resource/js/modern.js"></script>
    <link href="${contextPath}/resource/css/shoppingCart.css" rel="stylesheet">
    <link href="${contextPath}/resource/css/res.css" rel="stylesheet">
    <title>Side Cart</title>
</head>
<body class="foo">


<div id="cd-shadow-layer"></div>

<div id="cd-cart">
    <h2>Tariff</h2>
    <ul class="cd-cart-items">
        <li>
            <c:choose>
                <c:when test="${contractDTO.price!=null}">
                    <span id="tariffName">${contractDTO.tariff.name}</span>
                    <div class="cd-price" id="tariffPrice">${contractDTO.tariff.price}</div>
                </c:when>
                <c:otherwise>
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
            <c:when test="${contractDTO.options!=null}">
                <c:forEach items="${contractDTO.options}" var="o">
                    <li>
                            ${o.name}

                        <div class="cd-price"><p>Monthly price<span>$${o.priceMonthly}</span></p></div>
                                <div class="cd-price">One time price<span>${o.priceOneTime}</span</div>
                    </li>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li class='generated'></li>
            </c:otherwise>
        </c:choose>

    </ul> <!-- cd-cart-items -->

    <div class="cd-cart-total">
        <p>Total Monthly Price: <span>
             <c:choose>
                 <c:when test="${contractDTO.price!=null}">
                    $${contractDTO.price}
                 </c:when>
                 <c:otherwise>
                     $0.00
                 </c:otherwise>
             </c:choose>

        </span></p>
    </div> <!-- cd-cart-total -->

</div> <!-- cd-cart -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="../../resource/js/shoppingCart.js"></script> <!-- Gem jQuery -->
</body>
</html>