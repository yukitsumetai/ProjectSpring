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

<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="${contextPath}/resource/dist/js/bootstrap.min.js"></script>
    <script src="../resource/js/pagination.js"></script>
    <script src="../resource/js/Pagination2.js"></script>

    <!DOCTYPE html>
    <html lang="en">
    <head>
        <input type="hidden" id="page" value=1>
        <title>Options Overview</title>

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
        <script>
            $(document).ready(function () {
                return optionPagination();
            });
        </script>
        <script>
            $(document).on('shown.bs.modal', '#deleteModal', function (e) {
                //get data-id attribute of the clicked element
                var id = $(e.relatedTarget).data('id');

                //populate the textbox
                $(e.currentTarget).find('form[id="action"]').val(id);
                var $form = $('#action');
                $form.attr('action', '/options/delete/' + id);
            });
        </script>
    </head>

<body>
<%@ include file="elements/TopNavBar.jsp" %>


<div class="container-fluid">
    <%@ include file="elements/SideBar.jsp" %>
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <c:choose>
            <c:when test="${table=='edit'}">
                <h1 class="page-header">Options</h1>
            </c:when>
            <c:when test="${table=='add'}">
                <h1 class="page-header">New contract</h1>
            </c:when>
            <c:otherwise>
                <h2>New contract</h2>
            </c:otherwise>
        </c:choose>


        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <c:choose>
                            <c:when test="${table=='edit'}">
                                <h2>Options administration</h2>
                            </c:when>
                            <c:otherwise>
                                <h2>Choose Options</h2>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-sm-6">

                        <div class="search-box">
                            <i class="material-icons">&#xE8B6;</i>
                            <input type="text" class="form-control" id="myInput" placeholder="Search&hellip;">
                        </div>
                        <div class="newtariff">

                            <c:if test="${table=='edit'}">
                                <form action="/options/new">
                                    <button type="submit" class="btn btn-success">Add Option</button>
                                </form>
                            </c:if>

                        </div>
                    </div>

                </div>
            </div>


            <c:choose>
                <c:when test="${NEB=='yes'}">
                    <form action="${urlPath}/client" method="post" command="contract">
                        <%@ include file="tableOptions.jsp" %>
                        <div class="row">
                            <div class="col-sm-2 form-group">
                                <button type="submit" class="btn btn-success" name="action" value="new">New Client <i
                                        class="glyphicon glyphicon-plus"></i>
                                </button>
                            </div>
                            <div class="form-group col-sm-2">
                                <button type="submit" class="btn btn-success" name="action" value="existing">Existing
                                    client <i class="glyphicon glyphicon-user"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                </c:when>
                <c:when test="${table=='edit'}">
                    <%@ include file="tableOptions.jsp" %>
                </c:when>
                <c:otherwise>
                    <form action="${urlPath}/options/" method="post" command="contract">
                        <%@ include file="tableOptions.jsp" %>
                        <div class="row">
                            <button type="submit" class="btn btn-success" name="action" value="new">Next <i
                                    class="glyphicon glyphicon-chevron-right"></i></button>
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>

        </div>
    </main>
</div>
</div>
<!-- Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Delete Option</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure that you want to delete an option?<br>
               It would not be able to choose it for contracts.
            </div>
            <div class="modal-footer">
                <form>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                </form>

                <form id="action" action="">
                    <button type="submit" class="btn btn-success">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>



</body>
</html>





