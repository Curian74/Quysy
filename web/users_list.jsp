<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Users List</title>
        <link rel="stylesheet" href="resources/css/users_list.css">
        <link rel="stylesheet" href="vendors/datatables/datatables.min.css"/>
        <link rel="stylesheet" href="vendors/fontawesome/css/all.css">
    </head>
    <body>
        <div class="container">
            <div>
                <h1 class="text-center fw-bold mb-4">Users List</h1>
                <c:if test="${not empty success}">
                    <div class="alert alert-success">
                        ${success}
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                        ${error}
                    </div>
                </c:if>
                <a href="add-user" class="btn btn-outline-dark">
                    <i class="fas fa-arrow-right">ADD NEW USER</i>
                </a>
            </div>
            <form id="searchForm" method="get" action="user-list" class="search-form">
                <input type="text" hidden id="searchInput" class="form-control" name="search" value="${param.search}" placeholder="Search by full name, email or mobile">
                <select name="gender" class="form-control" onchange="autoSubmit()">
                    <option value="">All Genders</option>
                    <option value="true" ${param.gender == 'true' ? 'selected' : ''}>Male</option>
                    <option value="false" ${param.gender == 'false' ? 'selected' : ''}>Female</option>
                </select>
                <select name="role" class="form-control" onchange="autoSubmit()">
                    <option value="">All Roles</option>
                    <c:forEach var="role" items="${roles}">
                        <option value="${role.roleId}" ${param.role == role.roleId ? 'selected' : ''}>${role.roleName}</option>
                    </c:forEach>
                </select>
                <select name="status" class="form-control" onchange="autoSubmit()">
                    <option value="">All Statuses</option>
                    <option value="true" ${param.status == 'true' ? 'selected ' : ''}>Active</option>
                    <option value="false" ${param.status == 'false' ? 'selected' : ''}>Inactive</option>
                </select>
                <button type="button" class="btn btn-primary" onclick="trimAndSubmit()">Filter</button>
            </form>
            <p class="sort-instruction">(*) Click on column header to sort</p>
            <table id="userList" class="table table-bordered table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Full Name</th>
                        <th>Gender</th>
                        <th>Email</th>
                        <th>Mobile</th>
                        <th>Role</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.userId}</td>
                            <td><a href="user-details?userId=${user.userId}">${user.fullname}</a></td>
                            <td>${user.gender ? 'Male' : 'Female'}</td>
                            <td>${user.email}</td>
                            <td>${user.mobile}</td>
                            <td>${user.roleName}</td>
                            <td class="${user.status ? 'text-success fw-medium' : 'text-danger fw-medium'}">${user.status ? 'Active' : 'Inactive'}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <script src="vendors/datatables/datatables.min.js"></script>
        <script src="resources/js/users_list.js"></script>
    </body>
</html>
<%@include file="footer.jsp" %>
