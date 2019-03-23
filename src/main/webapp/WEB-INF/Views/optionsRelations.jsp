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
<%@ include file="TopNavBar.jsp" %>
<%@ include file="SideBar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!DOCTYPE html>
    <html lang="en">
    <head>

        <title>Choose related options</title>

        <!--Search-->
        <script>
            $(document).ready(function () {
                $("#myInput").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#myTable tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                    });
                });
            });
        </script>
    </head>

<body>


<div class="container-fluid">

    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header">Choose related options</h1>
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <c:choose>
                            <c:when test="${relation=='parent' ||relation=='parentEdit'}">
                                <h2>Choose parent</h2>
                            </c:when>
                            <c:otherwise>
                                <h2>Choose children</h2>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-sm-6">

                        <div class="search-box">
                            <i class="material-icons">&#xE8B6;</i>
                            <input type="text" class="form-control" id="myInput" placeholder="Search&hellip;">
                        </div>

                    </div>

                </div>
            </div>


            <c:choose>
                <c:when test="${relation=='children'}">
                    <form action="/options/new/children" method="post">
                        <%@ include file="tableOptions.jsp" %>
                        <div class="row">
                            <div class="col-sm-2 form-group">
                                <div class="row">
                                    <button type="submit" class="btn btn-success"
                                    <c:choose>
                                            <c:when test="${tariff==true}">value=true</c:when>
                                            <c:otherwise>value=false</c:otherwise>
                                    </c:choose> name="action">Next
                                </div>
                            </div>
                    </form>
                </c:when>
                <c:when test="${relation=='childrenEdit'}">
                    <form action="/options/edit/children" method="post">
                        <%@ include file="tableOptions.jsp" %>
                        <div class="row">
                            <div class="col-sm-2 form-group">
                                <div class="row">
                                    <button type="submit" class="btn btn-success"
                                    <c:choose>
                                            <c:when test="${tariff==true}">value=true</c:when>
                                            <c:otherwise>value=false</c:otherwise>
                                    </c:choose> name="action">Next
                                </div>
                            </div>
                    </form>
                </c:when>
                <c:when test="${relation=='parent'}">
                    <form action="/options/new/parent" method="post">

                        <%@ include file="tableOptions.jsp" %>
                        <div class="row">
                            <button type="submit" class="btn btn-success" <c:choose>
                                <c:when test="${tariff==true}">value=true</c:when>
                                <c:otherwise>value=false</c:otherwise>
                            </c:choose> name="action">Next
                            </button>
                        </div>
                    </form>
                </c:when>
                <c:when test="${relation=='parentEdit'}">
                    <form action="/options/edit/parent" method="post">

                        <%@ include file="tableOptions.jsp" %>
                        <div class="row">
                            <button type="submit" class="btn btn-success" <c:choose>
                                <c:when test="${tariff==true}">value=true</c:when>
                                <c:otherwise>value=false</c:otherwise>
                            </c:choose> name="action">Next
                            </button>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <%@ include file="tableOptions.jsp" %>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>
</div>

<!--Checkbox-->
<c:if test="${relation=='children'}">
    <script>
        $('input.chk').on('change', function () {
            var generated = document.getElementById('optionsCart').getElementsByClassName('generated');
            for (var i = 0; i < generated.length; i++) {
                generated[i].remove();
            }
            var checkboxes = document.getElementsByName('optionID');
            for (var i = 0; i < checkboxes.length; i++) {

                if (checkboxes[i].checked) {

                    var name = checkboxes[i].getAttribute('optionName');
                    var price = '$' + checkboxes[i].getAttribute('price');

                    var newInput = '<li class="generated">' + name +
                        '<div class="cd-price">' + price + '</div></li>';
                    document.getElementById('optionsCart').insertAdjacentHTML('beforeend', newInput);
                }

            }

        });

    </script>
</c:if>
<c:if test="${relation=='parent'}">
    <script>
        $('input.chk').on('change', function () {
            $('input.chk').not(this).prop('checked', false);
            var name = this.getAttribute('tariffName');
            var price = this.getAttribute('price');
            document.getElementById("tariffName").innerHTML = name;
            document.getElementById("tariffPrice").innerHTML = "$" + price;
        });
    </script>
</c:if>
<!--Highlight-->
<script>
    $(document).ready(function () {
        $('td :checkbox').bind('change click', function () {
            $(this).closest('tr').toggleClass('highlight', this.checked);
        }).change();
    });
</script>

</body>
</html>





