<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/bsvalidate/style.css">
    <script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

    <!--CSS-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!--JS-->
    <script  src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="   crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="<c:url value="../resource/js/multiselect.js"/>"></script>

<body>

<div class="container">
    <div id="basicExample">
        <h2>Add option details</h2>
        <div id="basicExampleDemo">
            <form id="optionForm" action="/options/new" method="post">
                <div class="form-group">
                    <label class="control-label">Option Name</label>
                    <input id="name" type="text" name="name" class="form-control required"/>
                </div>
                <div class="form-group">
                    <label class="control-label">One Time Price</label>
                    <input id="priceOneTime" type="number" name="priceOneTime" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label class="control-label">Monthly Price</label>
                    <input id="priceMonthly" type="number" name="priceMonthly" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label class="control-label">Description</label>
                    <input id="Description" type="text" name="Description" class="form-control" required/>
                </div>
                <div class="control-label">
                    <center>Compatible Tariffs</center>
                </div>
                <div class="row">
                    <div class="col-xs-5">
                        <select name="from[]" id="undo_redo" class="form-control" size="13" multiple="multiple">
                            <c:forEach items="${tariffs}" var="tariff">
                                <option value=${tariff.id}>${tariff.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-xs-2">
                        <button type="button" id="undo_redo_undo" class="btn btn-primary btn-block">undo</button>
                        <button type="button" id="undo_redo_rightAll" class="btn btn-default btn-block"><i class="glyphicon glyphicon-forward"></i></button>
                        <button type="button" id="undo_redo_rightSelected" class="btn btn-default btn-block"><i class="glyphicon glyphicon-chevron-right"></i></button>
                        <button type="button" id="undo_redo_leftSelected" class="btn btn-default btn-block"><i class="glyphicon glyphicon-chevron-left"></i></button>
                        <button type="button" id="undo_redo_leftAll" class="btn btn-default btn-block"><i class="glyphicon glyphicon-backward"></i></button>
                        <button type="button" id="undo_redo_redo" class="btn btn-warning btn-block">redo</button>
                    </div>

                    <div class="col-xs-5">
                        <select name="opt" id="undo_redo_to" class="form-control" size="13" multiple="true"></select>
                    </div>

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

<script>
    $(function() {
        $('#undo_redo').multiselect();
    });
</script>
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


</body>
</html>