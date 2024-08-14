<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Request Password Reset</title>
        <link href="vendors/bootstrap/bootstrap.min.css" rel="stylesheet">
        <link href="resources/css/auth.css" rel="stylesheet">

    </head>
    <body>
        <div class="background-image"></div>
        <div class="container">
            <form class="form-signin" action="reset-password" method="post">
                <h1 class="h3 mb-4 font-weight-normal">Request Password Reset</h1>
                <c:if test="${not empty message}">
                    <div class="alert alert-success">
                        ${message}
                    </div>
                </c:if> 
                <c:if test="${not empty error1}">
                    <div class="alert alert-danger">
                        ${error1}
                    </div>
                </c:if> 
                <input type="hidden" name="action" value="requestReset">
                <div class="mb-3">
                    <label for="email">Email</label>
                    <input type="email" id="email" class="form-control mt-1" name="email" placeholder="Your email address" required>
                </div>
                <button class="btn btn-lg btn-primary btn-block w-100 mt-2" type="submit">Send Request</button>
                <div class="mt-3 text-center">
                    <a href="home">Back to homepage</a>
                </div>
            </form>
            <div class="website-name my-4 text-light text-center">QUYSY</div>
        </div>
    </body>
</html>
