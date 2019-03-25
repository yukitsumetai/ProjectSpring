<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
<head>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <link href="<c:url value="${contextPath}/resource/css/dashboard.css"/>" rel="stylesheet">
</head>

<div class="card text-white bg-success">
    <div class="card-body">
        <div class="card-body">
            <h5 class="card-title">${og.name}</h5>
            <p class="card-text">${og.description}</p>
        </div>
        <ul class="list-group  list-group-flush">
            <li class="list-group-item text-white bg-success">
                <b>None</b><input class="right radio"  type="radio" name="optionID" value=0><br>
            </li>
            <c:forEach items="${og.options}" var="o">
                <c:forEach items="${options}" var="o2">
                    <c:if test="${o.id==o2.id}">
                        <li class="list-group-item text-white bg-success">
                            <b>${o2.name}</b><input class="right radio"  type="radio" name="optionID" value="${o2.id}"><br>
                            <i>${o2.description}</i>
                            <br>
                            <p>Monthly price:<span class="right"><b>${o2.priceMonthly}$</b></span>
                                <br>One Time price:<span class="right"><b>${o2.priceOneTime}$</b></span></p>
                            <div class="children" id="${o2.id}" style="display: none">
                            <c:forEach items="${children}" var="c">
                                <c:if test="${o.id==c.parent.id}">
                                    <br>
                                    <h5 class="card-title">${c.name}<input class="right chk" right type="checkbox"
                                                                           price="${c.priceMonthly}"
                                                                           optionName="${c.name}" name="optionID" value="${c.id}"></h5>
                                    <p class="card-text text-white bg-success">
                                        <em>${o.description}</em>
                                        <br>
                                    <p>Monthly price:<span class="right"><b>${c.priceMonthly}$</b></span>
                                        <br>One Time price:<span class="right"><b>${c.priceOneTime}$</b></span></p>
                                </c:if>
                            </c:forEach>
                            </div>
                        </li>
                        </li>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </ul>
    </div>
</div>

