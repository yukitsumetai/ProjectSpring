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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/bsvalidate/style.css">

</head>
<body>
       <div class="container">
        <div id="new client">
            <div>
                <center>Add client's personal data</center>
            </div>
            <div id="personal data">
                <form id="simpleForm" method="post" action="${url}" modelAttribute="client">
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <label path="name">Name</label>
                            <input type="text" placeholder="Enter first name.."
                                   id="name" class="form-control" name="name">
                        </div>
                        <div class="col-sm-6 form-group">
                            <label>Surname</label>
                            <input type="text" placeholder="Enter surname.."
                                   id="surname" class="form-control" name="surname">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label">EMail</label>
                        <input id="email" type="text" name="email" class="form-control required"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Birthday</label>
                        <input id="birthday" type="text" name="birthday" class="form-control" required/>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Password</label>
                        <input id="password" type="text" name="password" class="form-control" required/>
                    </div>
                    <!--  <div class="form-group">
                          <label class="control-label">Options</label>
                          <select id="country" class="form-control">
                              <option value="None">-- Select --</option>
                              <option value="China">China</option>
                              <option value="United State">United State</option>
                              <option value="Malaysia">Malaysia</option>
                          </select>
                      </div>-->
                    <div>
                        <center> Address</center>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <label>Street</label>
                            <input type="text" placeholder="Enter Billing Address.."
                                   class="form-control" id="street" name="street">
                        </div>
                        <div class="col-sm-6 form-group">
                            <label>House№</label>
                            <input type="number" placeholder="Enter  house number.."
                                   class="form-control" id="houseNumber" name="houseNumber">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <label>City</label>
                            <input type="text" placeholder="Enter city.."
                                   class="form-control" id="city" name="city">
                        </div>
                        <div class="col-sm-6 form-group">
                            <label>Zipcode</label>
                            <input type="number" placeholder="Enter Zipcode.."
                                   class="form-control" id="zip" name="zip">
                        </div>
                    </div>
                    <div class="form-group">
                        <label path="billingAddress.country">Country</label>
                        <input type="text" placeholder="Enter country.."
                               class="form-control" id="country" name="country">
                    </div>


                    <div class="form-group">
                        <button type="submit" class="btn btn-success"
                                onclick="return Validate()">Add
                        </button>
                    </div>

                </form>

            </div>
        </div>

    </div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bsvalidate/jquery.bsvalidate.js"></script>
<script type="text/javascript" src="js/bsvalidate/main.js"></script>

</body>
</html>
