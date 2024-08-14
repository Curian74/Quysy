<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reset Password</title>
        <link href="vendors/bootstrap/bootstrap.min.css" rel="stylesheet">
        <link href="resources/css/auth.css" rel="stylesheet">
    </head>
    <body>
        <c:if test="${not empty message}">
            <script>
                alert("Password changed successfully!");
                window.location.href = "home";
            </script>
        </c:if>
        <c:if test="${not empty error}">
            <script>
                alert("${error}");
            </script>
        </c:if>

        <div class="background-image"></div>
        <div class="container">
            <form class="form-signin" action="reset-password" method="post">
                <h1 class="h3 mb-3 font-weight-normal">Reset Password</h1>
                <input type="hidden" name="action" value="resetPassword">
                <input type="hidden" name="token" value="${param.token}">
                <div class="form-group">
                    <label for="newPassword">New Password</label>
                    <input type="password" id="newPassword" class="form-control mt-1" name="newPassword" required>
                </div>
                <div class="form-group">
                    <label for="cfPassword">Confirm Password</label>
                    <input type="password" id="cfPassword" class="form-control mt-1" name="cfPassword" required>
                </div>
                <button class="btn btn-lg btn-primary btn-block w-100 mt-3" type="submit">Reset Password</button>
                <p class="text-center">${message}</p>

                <c:if test="${not empty error}">
                    <div class="mt-3 text-center">
                        <a href="home">Back to homepage</a>
                    </div>
                </c:if>
            </form>
            <div class="website-name my-4 text-light text-center">QUYSY</div>
        </div>
    </body>
</html>
