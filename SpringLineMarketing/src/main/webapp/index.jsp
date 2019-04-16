<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lab 3</title>
</head>
<body>

<c:if test="${register!=false}">
${s}
<form action="HelloServelet" method="post">
    <input type="text" name="login" placeholder="login" />
    <input type="text" name="password" placeholder="password" />
    <input type="submit" value="Submit" />
</form>
</c:if>
<br>
<form action="HelloServelet" method="get">
${m}
<input type="submit" value="Get message" />
</form>
</html>