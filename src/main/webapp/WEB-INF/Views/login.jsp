
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../resources/images/favicon.ico">

    <title>Spring Line</title>

    <!-- Custom styles for this template -->
    <link href="resource/css/cover.css" rel="stylesheet">

    <!--JS-->
    <script  src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="   crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <!--CSS-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>

<body>

<div class="site-wrapper">

    <div class="site-wrapper-inner">

        <div class="cover-container">

            <div class="masthead clearfix">
                <div class="inner">
                    <h3 class="masthead-brand">TeleSpring</h3>
                    <nav>
                        <ul class="nav masthead-nav">
                            <li class="active"><a href="#">Home</a></li>
                            <li><a href="#">Sign in</a></li>
                            <li><a href="#">PHelp</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
            <div class="inner cover">

                <div class="wrapper  <c:if test="${param.error == false}">fadeInDown </c:if>">
                    <div id="formContent">
                        <!-- Tabs Titles -->
                        <!-- Icon -->
                        <div class="<c:if test="${param.error == false}">fadeIn first </c:if> ">
                            <br>
                            <h1 class="cover-heading">Spring Line</h1>
                            <p class="lead">We make your life easier</p> <p class="lead">
                        </div>
                        <!-- Login Form -->
                        <form action="${urlPath}/process" method="post">
                            <input type="text" id="login" class="<c:if test="${param.error == false}">fadeIn second </c:if>" name="login" placeholder="login">
                            <input type="text" id="password" class="<c:if test="${param.error == false}">fadeIn third </c:if>" name="password" placeholder="password">
                            <input type="submit" class="<c:if test="${param.error == false}">fadeIn fourth </c:if>" value="Log In">
                        </form>


                        <div id="formFooter">

                            <c:if test="${param.error == true}">
                            <p class="underlineHover">Bad credentials</p>
                        </c:if>
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