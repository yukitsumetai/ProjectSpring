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
    <title>New customer</title>
    <style>
        body { font-family: Trebuchet MS; font-size: 80%; padding: 0px 10px; }
        label { display: inline-block; width: 70px; }
        .green { font-family: Comic Sans MS; color: green; }
        .button { border: 0; border-radius: 4px; width: 60px; color: white; opacity: 0.7; padding: 3px 5px;
            text-align: center; background-color: #282; transition-duration: 0.4s; box-shadow: 0 2px 6px 0
        rgba(0,0,0,0.24),0 7px 10px 0 rgba(0,0,0,0.19); text-decoration: none; display: inline-block;
            font-size: 0.9em; cursor: pointer; }
        .button:hover { opacity: 1; transform: scale(1.04); }
        .button[disabled] { background-color: #ddd; }
    </style>

</head>
<body>
Enter customer data<br>
<form id="f" action="/users/new" method="post">


        <label for="name"> Name: </label><input type="text" name="name" id="name"><br>
        <label for="surname"> Surame: </label><input type="text" name="surname" id="surname"><br>
        <label for="email">Email: </label><input type="email" name="email" id="email"><br>
        <label for="birthday">Birthday: </label><input type="date" name="birthday" id="birthday"><br>
        <label for="password">Password: </label><input type="text" name="password" id="password"><br>
        <input type="submit" value="Submit" class="button">
        <span id="msg" class="green"></span>


</form>
</body>
</html>
