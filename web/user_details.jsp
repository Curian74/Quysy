<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <title>User Details</title>
        <link rel="stylesheet" href="vendors/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/user_details.css">
        <link rel="stylesheet" href="vendors/fontawesome/css/all.css">
    </head>
    <body>
        <div class="container">
            <div class="top-bar">
                <a href="user-list" class="btn btn-outline-dark">
                    <i class="fas fa-arrow-left"></i>
                </a>
                <h1 class="text-center fw-bold mb-0">User Details</h1>
            </div>

            <form method="post" action="update-user" onsubmit="return confirmChanges()">
                <input type="hidden" name="user_id" value="${user.userId}">

                <div class="form-group text-center">
                    <div class="avatar-container">
                        <img src="${user.avatar}" alt="Avatar" class="img-thumbnail avatar">
                    </div>
                </div>
                <p>(*)You can only edit User Role & Status</p>
                <div class="form-group">
                    <label for="full_name">Full Name</label>
                    <input type="text" class="form-control" id="full_name" name="full_name" value="${user.fullname}" readonly>
                </div>
                <div class="form-group">
                    <label for="gender">Gender</label>
                    <input type="text" class="form-control" id="gender" name="gender" value="${user.gender ? 'Male' : 'Female'}" readonly>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" value="${user.email}" readonly>
                </div>             
                <div class="form-group">
                    <label for="mobile">Mobile</label>
                    <input type="text" class="form-control" id="mobile" name="mobile" value="${user.mobile}" readonly>
                </div>
                <div class="form-group">
                    <label for="role_id">Role</label>
                    <select class="form-control" id="role_id" name="role_id">
                        <c:forEach var="role" items="${roles}">
                            <option value="${role.roleId}" ${user.roleId == role.roleId ? 'selected' : ''}>${role.roleName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="status">Status</label>
                    <select class="form-control" id="status" name="status">
                        <option value="true" ${user.status ? 'selected' : ''}>Active</option>
                        <option value="false" ${!user.status ? 'selected' : ''}>Inactive</option>
                    </select>
                </div><br>
                <button type="submit" class="btn btn-outline-primary">Update</button>
                <a href="add-user" class="btn btn-outline-secondary">Add New User</a>               
            </form>
        </div>
        <script src="resources/js/user_details.js"></script>    
    </body>
</html>
<%@include file="footer.jsp" %>
