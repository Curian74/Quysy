<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Add New User</title>
        <link rel="stylesheet" href="vendors/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="vendors/fontawesome/css/all.css">
        <link rel="stylesheet" href="resources/css/user_details.css">
    </head>
    <body>
        <div class="container">
            <div class="top-bar">
                <a href="user-list" class="btn btn-outline-dark">
                    <i class="fas fa-arrow-left"></i>
                </a>
                <h1 class="text-center fw-bold mb-4">Add New User</h1>
            </div>
            <c:if test="${not empty success}">
                <div class="alert alert-success" role="alert">
                    ${success}
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>
            <form method="post" action="add-user" onsubmit="return confirmAdd()">
                <div class="form-group">
                    <label for="full_name">Full Name</label>
                    <input type="text" class="form-control" id="full_name" name="full_name" 
                           value="${param.full_name}" required placeholder="Enter Full Name">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" 
                           value="${param.email}" required placeholder="Enter Email">
                </div>
                <div class="form-group">
                    <label for="gender">Gender</label>
                    <select class="form-control" id="gender" name="gender">
                        <option value="true" ${param.gender == 'true' ? 'selected' : ''}>Male</option>
                        <option value="false" ${param.gender == 'false' ? 'selected' : ''}>Female</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="mobile">Mobile</label>
                    <input type="text" class="form-control" id="mobile" name="mobile" 
                           value="${param.mobile}" required placeholder="Enter Mobile Number">
                </div>
                <div class="form-group">
                    <label for="role_id">Role</label>
                    <select class="form-control" id="role_id" name="role_id" required onchange="removePlaceholder()">
                        <option value="" disabled selected hidden>Role</option>
                        <c:forEach var="role" items="${roles}">                      
                            <option value="${role.roleId}" ${param.role_id == role.roleId ? 'selected' : ''}>${role.roleName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="status">Status</label>
                    <select class="form-control" id="status" name="status">
                        <option value="true" ${param.status == 'true' ? 'selected' : ''}>Active</option>
                        <option value="false" ${param.status == 'false' ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>
                <br>
                <button type="submit" class="btn btn-outline-primary">Save</button>
                <a href="user-list" class="btn btn-outline-secondary">Back to User List</a>
            </form>
        </div>
        <script src="resources/js/add_user.js"></script>
    </body>
</html>
<%@include file="footer.jsp" %>
