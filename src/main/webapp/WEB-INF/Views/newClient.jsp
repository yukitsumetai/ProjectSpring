
<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/bsvalidate/style.css">
    <title>New customer</title>
</head>
<body>
       <div class="container">
        <div id="new client">
            <div>
                <center>Add client's personal data</center>
            </div>
            <div id="personal data">
                <form:form method="post" action="/newContract/confirm" modelAttribute="client">
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="name">Name</form:label>
                            <form:input type="text" placeholder="Enter first name.."
                                   path="name" class="form-control required" />
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="surname">Surname</form:label>
                            <form:input type="text" placeholder="Enter surname.."
                                   path="surname" class="form-control" name="surname"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="email">eMail</form:label>
                        <form:input path="email" type="text" name="email" class="form-control required"/>
                    </div>
                    <div class="form-group">
                        <form:label path="birthday">Birthday</form:label>
                        <form:input path="birthday" type="text" name="birthday" class="form-control required"/>
                    </div>
                    <div class="form-group">
                        <form:label path="password">Password</form:label>
                        <form:input path="password" type="text" name="password" class="form-control required " />
                    </div>

                    <div>
                        <center> Address</center>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="address.street">Street</form:label>
                            <form:input type="text" placeholder="Enter Billing Address.."
                                   path="address.street"  class="form-control required"/>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label  path="address.houseNo">House№</form:label>
                            <form:input type="number" placeholder="Enter  house number.."
                                   path="address.houseNo" class="form-control required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="address.city">City</form:label>
                            <form:input type="text" placeholder="Enter city.."
                                   path="address.city" class="form-control required"/>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="address.zip">Zipcode</form:label>
                            <form:input type="number" placeholder="Enter Zipcode.."
                                   path="address.zip" class="form-control required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="address.country">Country</form:label>
                        <form:input type="text" placeholder="Enter country.."
                               path="address.country" class="form-control required"/>
                    </div>
                    <div class="form-group">
                        <center> Choose Phone number</center>
                    </div>
                    <div class="form-group">
                        <select id="country" class="form-control">
                            <c:forEach items="${numbers}" var="n">
                                <option value=${n}>+${n}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-success"
                                onclick="return Validate()">Add
                        </button>
                    </div>

               </form:form>

            </div>
        </div>

    </div>

       <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
       <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
       <script type="text/javascript" src="js/bsvalidate/jquery.bsvalidate.js"></script>
       <script type="text/javascript" src="js/bsvalidate/main.js"></script>

</body>
</html>
