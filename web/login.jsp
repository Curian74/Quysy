<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="vendors/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <form id="loginForm" action="login" method="post">
                <h1 class="mb-4 fw-bold text-center">Login</h1>
                <div id="loginError" class="alert alert-danger" style="display:none;"></div>
                <div id="loginMessage" class="alert alert-success" style="display:none;"></div>
                <div class="mb-3">
                    <label for="emailLogin" class="form-label">Email address</label>
                    <input type="email" id="emailLogin" name="email" class="form-control mt-1" placeholder="Email address" required>
                </div>
                <div class="mb-3">
                    <label for="passwordLogin" class="form-label">Password</label>
                    <input type="password" id="passwordLogin" name="password" class="form-control mt-1" placeholder="Password" required>
                </div>
                <button type="submit" class="btn btn-lg btn-primary w-100 mt-2">Login</button>
            </form>
        </div>
        <script src="resources/js/login.js"></script>
    </body>
</html>
