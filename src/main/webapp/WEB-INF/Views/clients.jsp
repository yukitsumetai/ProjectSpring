<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="elements/TopNavBar.jsp" %>
<%@ include file="elements/SideBar.jsp" %>
<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../resource/dist/js/bootstrap.min.js"></script>
    <script src="../resource/js/pagination.js"></script>
    <script src="../resource/js/ComboBox.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>


    <title>Current Clients</title>
    <script>
        $(document).ready(function () {
            return phoneNumber();
        });
    </script>
</head>

<body>

<div class="container-fluid">

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

        <h1 class="page-header">Clients</h1>

        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">

                        <h2>Clients Administration</h2>

                    </div>
                    <div class="col-sm-6">
                        <form action="/getUser" method="post">
                            <div class="search-box" class="col-sm-4">
                                <i class="material-icons">&#xE8B6;</i>
                                <input type="text" class="form-control" id="myInput" name="phoneNumber"
                                       placeholder="Search by phone number&hellip;">
                            </div>
                            <div class="newtariff col-sm-2">
                                <button type="submit" class="btn btn-success">Find</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>

            <form action="/newContract/confirmExisting" method="post" command="contract">

                <%@ include file="tableClients.jsp" %>
                <c:if test="${urlPath=='/newContract/client'}">
                <br><br>
                <div>
                    <div class="form-group">
                        <label class="control-label">Select phone number: </label>
                        <select class="form-control" name="phoneNumber"
                                id="select1" name="group" onscroll="scrolledPhone(this)" data-live-search="true"
                                onfocus='this.size=5;' onblur='this.size=1;' onchange='this.size=1;' this.blur();>
                        </select>
                        <input type="hidden" id="page" value=1>
                    </div>
                </div>

                <div>
                    <button type="submit" class="btn btn-success">Next</button>
                </div>
                </c:if>

            </form>


        </div>
    </main>
</div>
</div>

<!--Checkbox-->
<script>
    $('input.chk').on('change', function () {
        var checkboxes = document.getElementsByClassName('chk');
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                checkboxes[i].closest('tr').classList.add("highlight");
            } else {
                checkboxes[i].closest('tr').classList.remove("highlight");
                checkboxes[i].checked=false;
            }
        }
    });
</script>

</body>
</html>





