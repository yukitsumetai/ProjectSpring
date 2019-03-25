<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="<c:url value="${contextPath}/resource/css/dashboard.css"/>" rel="stylesheet">
</head>

<div class="card text-success border-success"">
    <div class="card-body">
        <h5 class="card-title">${o.name}<input class="right chk" right type="checkbox" price="${o.priceMonthly}"
                                               optionName="${o.name}" name="optionID" value="${o.id}"></h5>
        <p class="card-text text-success border-success"">
            <em>${o.description}</em>
            <br>
        <p>Monthly price:<span class="right"><b>${o.priceMonthly}$</b></span>
            <br>One Time price:<span class="right"><b>${o.priceOneTime}$</b></span></p>
        <div class="children" id="${o.id}" style="display: none">
            <c:forEach items="${children}" var="c">
                <c:if test="${o.id==c.parent.id}">
                    <br>
                    <h5 class="card-title">${c.name}<input class="right chk" right type="checkbox"
                                                           price="${c.priceMonthly}"
                                                           optionName="${c.name}" name="optionID" value="${c.id}"></h5>
                    <p class="card-text text-success border-success">
                        <em>${o.description}</em>
                        <br>
                    <p>Monthly price:<span class="right"><b>${c.priceMonthly}$</b></span>
                        <br>One Time price:<span class="right"><b>${c.priceOneTime}$</b></span></p>
                </c:if>
            </c:forEach>
        </div>
        </p>
    </div>
</div>


