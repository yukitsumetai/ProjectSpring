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
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="../resource/js/Pagination2.js"></script>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <script src="${contextPath}/resource/js/ComboBox.js"></script>
        <title>Choose related options</title>

        <!--Pagination initial-->
        <script>
            $(document).ready(function () {
                var curr = document.getElementById('page').value;
                if (curr == 0) {
                    var table;
                    <c:if test="${table=='edit'}">
                    var table = 1;
                    </c:if>
                    var parent = "${parent}"
                    var id = "${id}"
                    var optionId = "${optionId}"
                    return pagination(0, 1, table, id, parent, optionId, null);
                }
                document.getElementById('page').value = 1;
            });
        </script>
    </head>

<body>


<main class="container-fluid">

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <h1 class="page-header">Choose related options</h1>
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <c:choose>
                            <c:when test="${parent==true ||relation=='parentEdit'}">
                                <h2>Choose parent</h2>
                            </c:when>
                            <c:otherwise>
                                <h2>Choose children</h2>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>


            <c:choose>
                <c:when test="${parent==false}">
                    <form action="${urlPath}/children" method="post">
                        <%@ include file="tableOptions.jsp" %>
                        <div class="row">
                            <div class="col-sm-2 form-group">
                                <div class="row">
                                    <button type="submit" class="btn btn-success"
                                            <c:choose>
                                                <c:when test="${tariff==true}">value=true</c:when>
                                                <c:otherwise>value=false</c:otherwise>
                                            </c:choose> name="action">Next
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </c:when>
                <c:when test="${parent==true}">
                    <form action="${urlPath}/parent" method="post">

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
            </c:choose>
            <!--Checkbox-->
            <c:if test="${parent==false}">
                <script>
                    $(document).on('click', '.chk', function () {
                        var checkboxes = document.getElementsByClassName('chk');
                        for (var i = 0; i < checkboxes.length; i++) {
                            if (checkboxes[i].checked) {
                                var value = checkboxes[i].value;
                                var flag = true;
                                var existing = document.getElementsByClassName('opt2');
                                if (existing != null) {
                                    for (var j = 0; j < existing.length; j++) {
                                        if (existing[j].value == checkboxes[i].value) flag = false;
                                    }
                                }
                                if (flag) {
                                    var newInput = '<input name="optionID2" type="hidden" class="opt2" value="' + value + '">';
                                    document.getElementById('checked').insertAdjacentHTML('beforeend', newInput);
                                }
                            } else {
                                var existing = document.getElementsByClassName('opt2');
                                if (existing != null) {
                                    for (var j = 0; j < existing.length; j++) {
                                        if (existing[j].value == checkboxes[i].value) existing[j].remove();
                                    }
                                }
                            }
                        }
                    });
                </script>
            </c:if>
            <c:if test="${parent==true}">
                <script>
                    $(document).on('click', '.chk', function () {
                        $('input.chk').not(this).prop('checked', false);
                    });
                </script>
                <script>
                    $(document).on('click', '.chk', function () {
                        var checkboxes = document.getElementsByClassName('chk');
                        $('.opt2').remove();
                        for (var i = 0; i < checkboxes.length; i++) {
                            if (checkboxes[i].checked) {
                                var value = checkboxes[i].value;
                                var newInput = '<input name="optionID2" type="hidden" class="opt2" value="' + value + '">';
                                document.getElementById('checked').insertAdjacentHTML('beforeend', newInput);
                            }
                        }
                    });
                </script>
            </c:if>
        </div>
    </main>
    </div>
    </div>


</body>
</html>





