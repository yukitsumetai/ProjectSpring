<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 30.03.2019
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Response</title>
</head>
<body>
<form action="HelloServlet" method="get">
    ${m}
    <input type="submit" value="Get message" />
</form>
</body>
</html>
