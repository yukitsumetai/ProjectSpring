
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">


    <title>Spring Line</title>

    <!-- Custom styles for this template -->
    <link href="${contextPath}/resource/css/cover.css" rel="stylesheet">

    <!--JS-->
    <script  src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="   crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <!--CSS-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script>
        $(document).ready(function () {
            var cookieEnabled = (navigator.cookieEnabled) ? true : false;
            if (typeof navigator.cookieEnabled == "undefined" && !cookieEnabled)
            {
                document.cookie="testcookie";
                cookieEnabled = (document.cookie.indexOf("testcookie") != -1) ? true : false;
            }
            if (!cookieEnabled) {
                document.getElementById("cookies").innerHTML ="Enable cookies to login \n and work with application correctly";
            }

        });
    </script>

</head>

<body>
<!-- Modal Cookies -->
<div class="modal fade" id="cookiesModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModal"> Coockies are disabled</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Cookies are disabled. <br>
                Please enable cookies in your browser so that a website can work corretly.
            </div>
            <div class="modal-footer">
                <form>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Ok</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="site-wrapper">

    <div class="site-wrapper-inner">

        <div class="cover-container">

            <div class="inner cover">

                <div class="wrapper  <c:if test="${param.error !=true}">fadeInDown </c:if>">
                    <div id="formContent">
                        <!-- Tabs Titles -->
                        <!-- Icon -->
                        <div class="<c:if test="${param.error !=true}">fadeIn first </c:if> ">
                            <br>
                            <h1 class="cover-heading">Spring Line</h1>
                            <p class="lead">We make your life easier</p> <p class="lead">
                        </div>
                        <!-- Login Form -->
                        <form action="${urlPath}/process" method="post">
                            <input type="text" id="login" class="<c:if test="${param.error !=true}">fadeIn second </c:if>" name="login" placeholder="login" value="${login}">
                            <input type="text" id="password" class="<c:if test="${param.error !=true}">fadeIn third </c:if>" name="password" placeholder="password">
                            <input type="submit" class="btn-success <c:if test="${param.error !=true}">fadeIn fourth </c:if>" value="Log In">
                        </form>


                        <div id="formFooter">

                            <c:if test="${param.error == true}">
                            <p class="underlineHover">Bad credentials</p>

                        </c:if>
                            <p class="underlineHover" id="cookies"></p>
                           <!-- <a class="underlineHover" href="#">Forgot Password?</a> -->
                        </div>

                    </div>
                </div>

                </p>
            </div>

            <div class="mastfoot">
            </div>

        </div>

    </div>

</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="../../dist/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>