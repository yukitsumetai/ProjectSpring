<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/bsvalidate/style.css">

</head>
<body>

<div class="container">
    <div id="basicExample">
        <h2>Add tariff details</h2>
        <div id="basicExampleDemo">
            <form id="simpleForm" action="/tarifs/new" method="post" class="well">
                <div class="form-group">
                    <label class="control-label">Tariff Name</label>
                    <input id="name" type="text" name="name" class="form-control required"/>
                </div>
                <div class="form-group">
                    <label class="control-label">Monthly Price</label>
                    <input id="price" type="text" name="price" class="form-control" required/>
                </div>

                <div class="form-group">
                    <label class="control-label">Description</label>
                    <input id="Description" type="text" name="Description" class="form-control" required/>
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
                    <center>Billing Address</center>
                </div>
                <div class="row">
                    <div class="col-sm-6 form-group">
                        <label path="billingAddress.street">Street</label>
                        <input type="text" placeholder="Enter Billing Address.."
                               class="form-control" path="billingAddress.street">
                    </div>
                    <div class="col-sm-6 form-group">
                        <label path="billingAddress.houseNo">House Number</label>
                        <input type="text" placeholder="Enter your house number"
                               class="form-control" path="billingAddress.state">
                    </div>
               </div>
                <div class="row">
                    <div class="col-sm-6 form-group">
                        <label path="billingAddress.city">City</label>
                        <input type="text" placeholder="Enter your City.."
                               class="form-control" path="billingAddress.city">
                    </div>
                    <div class="col-sm-6 form-group">
                        <label path="billingAddress.zipcode">Zipcode</label>
                        <input type="text" placeholder="Enter Zipcode.."
                               class="form-control" path="billingAddress.zipcode"></input>
                        <errors path="billingAddress.zipcode"></errors>
                    </div>
                </div>
                <div class="form-group">
                    <label path="billingAddress.country">Country</label>
                    <input type="text" placeholder="Enter your City.."
                           class="form-control" path="billingAddress.country"></input>
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


</div><!-- .container -->
<!-- Validating Password -->
<script type="text/javascript">
    function Validate() {
        var name = document.getElementById("name").value;
        if (name.length > 30) {
            alert("Too much symbols in Name");
            return false;
        }
        return true;
    }
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bsvalidate/jquery.bsvalidate.js"></script>
<script type="text/javascript" src="js/bsvalidate/main.js"></script>

</body>
</html>
