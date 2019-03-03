<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<form action="/users/new" method="post">
    <input name="name" type ="text" placeholder="name">
    <input name="email" type ="text" placeholder="email">
    <input type="submit">
</form>
</body>
</html>
