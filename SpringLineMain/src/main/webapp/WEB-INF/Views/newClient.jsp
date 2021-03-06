<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="elements/TopNavBar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <script src="${contextPath}/resource/js/validation.js"></script>
    <script src="${contextPath}/resource/js/clientAjax.js"></script>
    <script src="${contextPath}/resource/js/dropdownAjax.js"></script>
    <script type="text/javascript" src="${contextPath}/resource/dist/js/myWebcam.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
          integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <link href="${contextPath}/resource/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Client details</title>

    <script>
        $(document).ready(function () {
            return phoneNumber();
        });
    </script>
</head>
<body>
<%@ include file="elements/SideBar.jsp" %>
<%@ include file="elements/modal.jsp" %>
<div class="container-fluid">
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <h1 class="page-header">New contract</h1>
        <div id="personal data">
            <div id="table-wrapper">
                <div class="table-title">
                    <h2>Add personal data</h2>
                </div>
            </div>
            <div>
                <div id="my_camera"></div>
                <br>
                <div id="cameraButtons">
                    <button class="btn btn-success" onclick="configure()">Use Camera <em class="fas fa-camera"></em></button>
                </div>
                <div id="results"></div>
            </div>
            <br>
            <ul class="errorMessages"></ul>
            <form:form method="post" name='form1' modelAttribute="client" action="${contextPath}/newContract/confirm" onsubmit="valthisform()" >
               <input type="hidden" id="contract" value="${contractDto}">
                <div class="row">
                    <div class="col-sm-6 form-group">
                        <form:label path="name">Name</form:label>
                        <form:input type="text" placeholder="Enter first name.." pattern="[A-Za-z\s-]{2,20}"
                                    title="Only letters, space and hyphen are allowed. Min 2 max 20 characters"
                                    path="name" class="form-control" required="true" id="name" onblur="requiredField(this)"/>
                    </div>
                    <div class="col-sm-6 form-group">
                        <form:label path="surname">Surname</form:label>
                        <form:input type="text" placeholder="Enter surname.." pattern="[A-Za-z\s-]{2,20}"
                                    title="Only letters, space and hyphen are allowed are allowed. Min 2 max 20 characters"
                                    path="surname" class="form-control" name="surname" required="true" id="surname" onblur="requiredField(this)"/>
                    </div>
                </div>
                <div class="form-group">
                    <form:label path="birthday">Birthday</form:label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><em class="far fa-calendar-alt"></em></span>
                        </div>
                        <form:input path="birthday" type='date' class="form-control" min="${now}-100"    title="Date should be valid. Customer should be older than 18 years old"
                                    max="${now}-18" id="birthday" required="true" onblur="requiredField(this)"/>
                    </div>
                </div>
                <div class="form-group">
                    <label>Passport</label>
                    <form:input path="passport"  placeholder="Enter passport number.." type="text" name="passport" id="passport" class="form-control required"
                                pattern="[0-9]{9}" title="Passport should consist of 8 digits" required="true" onblur="requiredField(this)"/>
                </div>
                <div class="form-group">
                    <form:label path="email">E-mail</form:label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon1">@</span>
                        </div>
                        <form:input path="email" type="email" id="email" name="email" class="form-control"
                                    required="true" onblur="requiredField(this)"/>
                    </div>
                </div>
                <div class="table-title">
                    <h2>Add address</h2>
                </div>
                <div class="row">
                    <div class="col-sm-6 form-group">
                        <form:label path="address.street">Street</form:label>
                        <form:input type="text" placeholder="Enter street.." required="true"
                                    path="address.street" class="form-control required" pattern="[A-Za-z\s-]{0,20}"
                                    title="Only letters, space and hyphen are allowed. Min 2 max 20 characters" onblur="requiredField(this)"/>
                    </div>
                    <div class="col-sm-6 form-group">
                        <form:label path="address.houseNo">House№</form:label>
                        <form:input type="text" placeholder="Enter house number.." pattern="[0-9]{0,4}"
                                    title="Only digits are allowed. Max 4 symbols." required="true"
                                    path="address.houseNo" class="form-control required" onblur="requiredField(this)"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 form-group">
                        <form:label path="address.city">City</form:label>
                        <form:input type="text" placeholder="Enter city.." required="true"
                                    path="address.city" class="form-control required" pattern="[A-Za-z\s-]{0,20}"
                                    title="Only letters and hyphen are allowed. Min 2 max 20 characters" onblur="requiredField(this)"/>
                    </div>
                    <div class="col-sm-6 form-group">
                        <form:label path="address.zip">Zipcode</form:label>
                        <form:input type="text" placeholder="Enter Zipcode.." required="true"
                                    path="address.zip" class="form-control required" pattern="[0-9]{5}"
                                    title="ZIP should consist of 5 digits" onblur="requiredField(this)"/>
                    </div>
                </div>
                <div class="form-group">
                    <form:label path="address.country">Country</form:label>
                    <form:input type="text" placeholder="Enter country.." value="Germany" required="true"
                                path="address.country" class="form-control" pattern="[A-Za-z\s-]{0,20}" onblur="requiredField(this)"/>
                </div>
                <div class="table-title">
                    <h2>Add Contract Details</h2>
                </div>
                <div class="row">
                    <div class="form-group col-sm-6">
                        <form:label path="phoneNumber">Phone number</form:label>
                        <form:select class="form-control" path="phoneNumber"
                                     id="select1" name="group" onscroll="scrolledPhone(this)" data-live-search="true"
                                     required="true"
                                     onfocus='this.size=4;' onblur='this.size=1;' onchange='this.size=1;'>
                        </form:select>
                        <input type="hidden" id="page" value=1>
                    </div>
                    <div class="form-group col-sm-6">
                        <form:label path="password">Password</form:label>
                        <form:input path="password" type="text" name="password" class="form-control required " placeholder="Enter password.."
                                    maxlength="200" required="true" onblur="requiredField(this)"/>
                    </div>
                </div>
                <div class="form-group">
                    <button class="btn btn-success"  onclick="" >Next <em class="fas fa-arrow-right"></em></button>
                </div>

            </form:form>

        </div>
    </main>

</div>
<script>



</script>

</body>
</html>
