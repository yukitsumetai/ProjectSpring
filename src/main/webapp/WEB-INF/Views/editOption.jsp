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
    <script src="<c:url value="../resource/js/multiselect.js"/>"></script>

<body>

<div class="container">
    <div id="basicExample">
        <h2>Add option details</h2>
        <div id="basicExampleDemo">
            <form id="optionForm" action="/options/edit" method="post">
                <div class="form-group">
                    <label class="control-label">Option Name</label>
                    <input id="name" value="${option.name}" type="text" name="name" class="form-control" disabled/>
                </div>
                <div class="form-group">
                    <label class="control-label">One Time Price</label>
                    <input id="priceOneTime" value="${option.priceOneTime}" type="number" name="priceOneTime"
                           class="form-control" disabled/>
                </div>
                <div class="form-group">
                    <label class="control-label">Monthly Price</label>
                    <input id="priceMonthly" value="${option.priceMonthly}" type="number" name="priceMonthly"
                           class="form-control" disabled/>
                </div>
                <div class="form-group">
                    <label class="control-label">Description</label>
                    <textarea row="4" id="Description"  type="text" name="Description"
                              class="form-control">${option.description}</textarea>
                </div>
                <div class="form-group">
                    <label class="control-label">Validity: </label>
                    <input type="checkbox" class="chk" name="isValid" id="isValid" value=true
                                <c:if test="${option.isValid==true}">
                                    checked
                                </c:if>
                    />&nbsp;
                </div>

                Set with existing tariffs/options:
                <div class="form-group">
                    <label class="control-label">Belongs to option group: </label>
                    <input type="checkbox" class="chk" name="" id="" value=true/>&nbsp;
                </div>
                <div class="form-group">
                    <label class="control-label">Has compatible tariffs: </label>
                    <input type="checkbox" class="chk" name="compatibleTariffs" id="compatibleTariffs" value=true/>&nbsp;
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
                        <button type="button" id="undo_redo_rightAll" class="btn btn-default btn-block"><i
                                class="glyphicon glyphicon-forward"></i></button>
                        <button type="button" id="undo_redo_rightSelected" class="btn btn-default btn-block"><i
                                class="glyphicon glyphicon-chevron-right"></i></button>
                        <button type="button" id="undo_redo_leftSelected" class="btn btn-default btn-block"><i
                                class="glyphicon glyphicon-chevron-left"></i></button>
                        <button type="button" id="undo_redo_leftAll" class="btn btn-default btn-block"><i
                                class="glyphicon glyphicon-backward"></i></button>
                        <button type="button" id="undo_redo_redo" class="btn btn-warning btn-block">redo</button>
                    </div>

                    <div class="col-xs-5">
                        <select name="opt" id="undo_redo_to" class="form-control" size="13" multiple="true"></select>
                    </div>

                </div>


                <div class="form-group">
                    <br>
                    <button type="submit" class="btn btn-success">Save <i class="glyphicon glyphicon-floppy-disk"></i>
                    </button>
                </div>

            </form>

        </div>
    </div>

</div>


</div><!-- .container -->
<!-- Validating Password -->

<script>
    $(function () {
        $('#undo_redo').multiselect();
    });
</script>

<!--option validity-->
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
