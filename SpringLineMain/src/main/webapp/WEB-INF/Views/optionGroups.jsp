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
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="elements/TopNavBar.jsp" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <title>Option Groups</title>

    <link href="${contextPath}/resource/dist/css/bootstrap.min.css" rel="stylesheet">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
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
    <%-- Modal Script --%>
    <script>
        $(document).on('shown.bs.modal', '#deleteModal', function (e) {
            //get data-id attribute of the clicked element
            var id = $(e.relatedTarget).data('id');
            //populate the textbox
            $(e.currentTarget).find('form[id="action"]').val(id);
            var $form = $('#action');
            $form.attr('action', '/springLine/optionGroups/delete/' + id);
        });
    </script>
</head>

<body>
<%@ include file="elements/SideBar.jsp" %>
<div class="container-fluid">

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

        <h1 class="page-header">Option Groups</h1>
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <h2>Option Groups administration</h2>
                    </div>
                    <div class="col-sm-6">
                        <div class="row float-right">
                            <div class="search-box">
                                <em class="material-icons">&#xE8B6</em>
                                <input type="text" class="form-control" id="myInput" placeholder="Search&hellip;">
                            </div>
                            <div class="newtariff">
                                <form action="${contextPath}/optionGroups/new">
                                    <button type="submit" class="btn btn-success">Add Option Group <em class="fas fa-plus"></em></button>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <table class="table table-striped table-hover table-bordered" id="Table">
                <thead>
                <tr>
                    <th onclick="sortTable(0)">Name<em class="fa fa-sort"></em></th>
                    <th>Description</th>
                    <th>Compatible Options</th>
                    <th>Validity</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody id="myTable">
                <c:forEach items="${optionGroups}" var="optionGroup">
                    <tr class="tableRow">
                        <td>${optionGroup.name}</td>
                        <td>${optionGroup.description}</td>
                        <td>
                            <c:forEach items="${optionGroup.options}" var="option">
                                <ul>
                                <li>${option.name}</li>
                                </ul>
                            </c:forEach>
                        </td>
                        <td>${optionGroup.isValid}</td>

                        <td>
                            <a href="${contextPath}/optionGroups/edit/${optionGroup.id}" class="edit" title="Edit"><em
                                    class="material-icons">&#xE254;</em></a>
                            <c:if test="${optionGroup.isValid==true}">
                                <a href="#deleteModal" class="delete" title="Delete" data-toggle="modal"
                                   data-target="#deleteModal" data-id="${optionGroup.id}"><em
                                        class="material-icons">&#xE872;</em></a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Delete Tariff</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Are you sure that you want to delete the option group?<br>
                        Existing relations with options will be lost.
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
    </main>
    <!--PaginationDao-->
    <script>
        $(document).ready(function () {

            $('#myTable').pageMe({
                pagerSelector: '#pagination',
                showPrevNext: true,
                hidePageNumbers: false,
                perPage: 4
            });

        });
    </script>

    <!--Tooltips-->
    <script type="text/javascript">
        $(document).ready(function () {
            $('[data-toggle="tooltip"]').tooltip();
        });
    </script>
</div>
</body>
</html>