<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<head>
    <title>Combo Box</title>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <link href="${contextPath}/resource/css/dashboard.css" rel="stylesheet">
</head>

<div class="card text-white bg-success">
    <div class="card-body">
        <div class="card-body">
            <h5 class="card-title">${og.name}</h5>
            <p class="card-text">${og.description}</p>
        </div>
        <ul class="list-group  list-group-flush">
            <li class="list-group-item text-white bg-success">
                <strong>None</strong><input class="right radio" type="radio" name="${og.name}" value=0><br>
            </li>
            <c:forEach items="${options}" var="o2">
                <c:if test="${og.id==o2.optionGroup.id}">
                    <li class="list-group-item text-white bg-success">
                        <strong>${o2.name}</strong><input class="right radio myClass" type="radio" price="${o2.priceMonthly}"
                                                optionName="${o2.name}"
                    <c:if test="${o2.existing==true}"> checked </c:if> priceOneTime="${o2.priceOneTime}"
                                                name="${og.name}" value="${o2.id}"><br>
                        <em>${o2.description}</em>
                        <p>Monthly price:<span class="right"><strong>${o2.priceMonthly}$</strong></span>
                            <c:if test="${o2.existing==false}"><br>One Time price:<span class="right"><strong>${o2.priceOneTime}$</strong></span> </c:if>
                        </p>

                        <div class="children" id="${o2.id}" style="display:none">
                            <c:forEach items="${children}" var="c">
                                <c:if test="${o2.id==c.parent.id}">
                                    <br>
                                    <strong>${c.name}</strong><input class="right chk myClass1" right
                                                                           type="checkbox"
                                                                           price="${c.priceMonthly}"
                                                                           priceOneTime="${c.priceOneTime}"
                                                                           optionName="${c.name}" name="optionID"
                                                                           value="${c.id}"
                                    <c:if test="${c.existing==true}"> checked </c:if>></h5>
                                    <p class="card-text text-white bg-success">
                                        <em>${c.description}</em>
                                    <p>Monthly price:<span class="right"><strong>${c.priceMonthly}$</strong></span>
                                        <c:if test="${c.existing==false}"><br>One Time price:<span class="right"><strong>${c.priceOneTime}$</strong></span> </c:if>
                                    </p>
                                </c:if>
                            </c:forEach>
                        </div>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $("input[type='button']").click(function () {
            var radioValue = $("input[name='gender']:checked").val();
            if (radioValue) {
                alert("Your are a - " + radioValue);
            }
        });
    });
</script>