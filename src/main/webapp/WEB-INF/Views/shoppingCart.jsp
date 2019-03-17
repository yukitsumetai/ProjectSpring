<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 17.03.2019
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="../resource/css/reset.css"> <!-- CSS reset -->
    <link rel="stylesheet" href="../resource/css/dashboard.css"> <!-- Gem style -->
    <script src="../resouce/js/modernizr.js"></script> <!-- Modernizr -->
    <link href="../resource/css/style2.css" rel="stylesheet">
    <title>Side Cart</title>
</head>
<body class="foo">
<header>

    <div id="cd-cart-trigger"><a class="cd-img-replace" href="#0">Cart</a></div>
</header>


<div id="cd-shadow-layer"></div>

<div id="cd-cart">
    <h2>Cart</h2>
    <ul class="cd-cart-items">
        <li>
            <span class="cd-qty">1x</span> Product Name
            <div class="cd-price">$9.99</div>
            <a href="#0" class="cd-item-remove cd-img-replace">Remove</a>
        </li>

        <li>
            <span class="cd-qty">2x</span> Product Name
            <div class="cd-price">$19.98</div>
            <a href="#0" class="cd-item-remove cd-img-replace">Remove</a>
        </li>

        <li>
            <span class="cd-qty">1x</span> Product Name
            <div class="cd-price">$9.99</div>
            <a href="#0" class="cd-item-remove cd-img-replace">Remove</a>
        </li>
    </ul> <!-- cd-cart-items -->

    <div class="cd-cart-total">
        <p>Total <span>$39.96</span></p>
    </div> <!-- cd-cart-total -->

    <a href="#0" class="checkout-btn">Checkout</a>

    <p class="cd-go-to-cart"><a href="#0">Go to cart page</a></p>
</div> <!-- cd-cart -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="../resource/js/main.js"></script> <!-- Gem jQuery -->
</body>
</html>