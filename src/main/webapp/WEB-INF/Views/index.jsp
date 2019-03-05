
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>GlobalNet</title>

<body>
Welcome to GlobalNet!<br>
${msg}
<img src="resource/images/hobby1.jpg"/>


<img src="<c:url value="/images/favicon1.png"/>"
     alt="logo-image"/>
<form action="/view" method="post">
    <input name="Customer" placeholder="Customer" type="submit" value="Existing customer">
    <input name="Shop" type="submit" placeholder="Shop agent"  value="Shop agent">
</form>
</body>
</html>