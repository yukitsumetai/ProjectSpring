<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>

    <!--CSS-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!--JS-->
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>

<body>

<div class="container">
    <div id="basicExample">
        <h2>Edit tariff details</h2>
        <div id="editTariff">
            <form:form method="post" action="/tariffs/edit" modelAttribute="tariff">
                <div class="form-group">
                    <label class="control-label">Tariff Name: </label>
                    <input value="${tariff.name}" id="name" type="text" name="name" class="form-control" disabled/>
                </div>
                <div class="form-group">
                    <label class="control-label">Monthly Price</label>
                    <input value="${tariff.price}" id="price" type="number" name="price" class="form-control" disabled/>
                </div>

                <div class="form-group">
                    <label class="control-label">Description</label>
                    <textarea rows="4" id="Description" type="text" name="Description"
                              class="form-control">${tariff.description}</textarea>
                </div>
                <div class="form-group">
                    <label class="control-label">Validity: </label>
                    <input type="checkbox" class="chk" name="isValid" id="isValid"
                           value="${tariff.isValid}"
                            <c:if test="${tariff.isValid==true}">
                                checked
                            </c:if>/>&nbsp;
                </div>

                <div class="form-group">
                    <br>
                    <button type="submit" class="btn btn-success">Save <i class="glyphicon glyphicon-floppy-disk"></i>
                    </button>
                </div>

            </form:form>

        </div>
    </div>

</div>


</div><!-- .container -->
<!-- Valid -->
<script type="text/javascript">
    $(document).ready(function () {
        $('#isValid').change(function () {
            if ($(this).is(":checked")) {
                this.value = true;
            } else this.value = false;
        });
    });
</script>


</body>
</html>
