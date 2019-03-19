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
                    <label>Name: </label> ${contractDTO.client.name}
                </div>
                <div class="col-sm-8 form-group">
                    <label>Surname: </label> ${contractDTO.client.surname}
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 form-group">
                    <label>eMail: </label> ${contractDTO.client.email}
                </div>
                <div class="col-sm-4 form-group">
                    <label>Birthday: </label> ${contractDTO.client.birthday}
                </div>
                <div class="col-sm-4 form-group">
                    <label>Passport: </label>${contractDTO.client.passport}
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 form-group">
                    <label>Address: </label> ${contractDTO.client.address.street}, ${contractDTO.client.address.houseNo}
                </div>
                <div class="col-sm-4 form-group">
                    <label>City: </label> ${contractDTO.client.address.city}, ${contractDTO.client.address.zip}
                </div>
                <div class="col-sm-4 form-group">
                    <label>Country: </label> ${contractDTO.client.address.country}
                </div>
            </div>

            <div class="row">
                <div class="col-sm-4 form-group">
                    <label>Phone Number: </label>+${contractDTO.phoneNumber}
                </div>
                <div class="col-sm-6 form-group">
                    <label>Password: </label> XXXXXXX
                </div>
            </div>
        </div>

</body>
</html>
