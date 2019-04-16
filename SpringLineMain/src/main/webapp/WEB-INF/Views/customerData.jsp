<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 17.03.2019
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer data</title>
</head>
<body>


        <div class="table-title">
            <h2>Personal details</h2>
        </div>
        <div id="personal data">
            <div class="row">
                <div class="col-sm-4 form-group">
                    <label>Name: </label> ${contractDto.client.name}
                </div>
                <div class="col-sm-8 form-group">
                    <label>Surname: </label> ${contractDto.client.surname}
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 form-group">
                    <label>eMail: </label> ${contractDto.client.email}
                </div>
                <div class="col-sm-4 form-group">
                    <label>Birthday: </label> ${contractDto.client.birthday}
                </div>
                <div class="col-sm-4 form-group">
                    <label>Passport: </label> ${contractDto.client.passport}
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 form-group">
                    <label>Address: </label> ${contractDto.client.address.street}, ${contractDto.client.address.houseNo}
                </div>
                <div class="col-sm-4 form-group">
                    <label>City: </label> ${contractDto.client.address.city}, ${contractDto.client.address.zip}
                </div>
                <div class="col-sm-4 form-group">
                    <label>Country: </label> ${contractDto.client.address.country}
                </div>
            </div>
        </div>

</body>
</html>
