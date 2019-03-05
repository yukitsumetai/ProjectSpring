<#assign sf=JspTaglibs["http://www.springframework.org/security/tags"]>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome to GlobalNet</title>
    <img ="/resource/images/favicon1.png" alt="logo"/>
<body>
Welcome to GlobalNet!
${msg}

<form action="/view" method="post">
    <input name="Customer" placeholder="Customer" type="submit" value="Customer">
    <input name="Shop" type="submit" placeholder="Shop agent" value="Shop agent">
</form>
</body>
</html>