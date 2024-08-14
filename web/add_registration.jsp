<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Quysy | Add New Registration</title>
        <link rel="stylesheet" href="vendors/fontawesome/css/all.css">
    </head>
    <body>
        <div class="container">
            <div class="d-flex align-items-center justify-content-center py-3">
                <a href="registration-list" class="btn btn-outline-dark">
                    <i class="fas fa-arrow-left"></i>
                </a>
                <h1 class="display-3 fw-bold text-center mb-0 flex-grow-1">Add New Registration</h1>
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

            <form method="post" action="add-registration" class="mt-4" onsubmit="return confirmAdd()">

                <div class="form-group mb-3">
                    <label for="subject" class="form-label">Subject Name</label>
                    <select class="form-select" id="subject" name="subject">
                        <c:forEach var="s" items="${subjectList}" varStatus="i">
                            <option class="subject-option" value="${s.subjectId}" ${i.index eq 0 ? 'selected' : ''}>${s.subjectName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group mb-3">
                    <label for="package" class="form-label">Package</label>
                    <select class="form-select" id="package" name="package">
                        <c:forEach var="p" items="${packageList}" varStatus="i">
                            <option class="package-option" value="${p.packageId}" ${i.index eq 1 ? 'selected' : ''}>${p.packageName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="row mt-1">
                    <div class="col">
                        <div class="input-group mb-3">
                            <span class="input-group-text">List Price</span>
                            <input type="text" id="listPrice" name="listPrice" class="form-control" value="${price.listPrice * 1000} VND" disabled>
                        </div>
                    </div>
                    <div class="col">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Sale Price</span>
                            <input type="text" id="salePrice" name="salePrice" class="form-control" value="${price.salePrice * 1000} VND" disabled>
                        </div>
                    </div>
                </div>

                <div class="form-group mb-3">
                    <label for="fullName" class="form-label">Full Name</label>
                    <input type="text" class="form-control" id="fullName" name="fullName" value="${param.fullName}">
                </div>

                <div class="form-group mb-3">
                    <label for="genderInput" class="form-label">Gender</label>
                    <select class="form-select" id="genderInput" name="gender">
                        <option value="true" selected>Male</option>
                        <option value="false">Female</option>
                    </select>
                </div>

                <div class="form-group mb-3">
                    <label for="emailInput" class="form-label">Email</label>
                    <input type="email" class="form-control" id="emailInput" name="email" value="${param.email}">
                </div>

                <div class="form-group mb-3">
                    <label for="mobileInput" class="form-label">Mobile</label>
                    <input type="text" class="form-control" id="mobileInput" name="mobile" value="${param.mobile}">
                </div>

                <div class="form-group mb-3">
                    <label for="registrationStatus" class="form-label">Registration Status</label>
                    <select class="form-select" id="registrationStatus" name="registrationStatus">
                        <c:forEach var="rs" items="${registrationStatusList}" varStatus="i">
                            <option value="${rs.registrationStatusId}" ${i.index eq 0 ? 'selected' : ''}>${rs.registrationStatusName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group mb-3">
                    <label for="notes" class="form-label">Notes</label>
                    <input type="text" class="form-control" id="notes" name="notes" value="${param.notes}">
                </div>

                <button type="submit" class="btn btn-outline-primary">Add</button>
                <a href="registration-list" class="btn btn-outline-secondary">Back To Registration List</a>               
            </form>
        </div>

        <script src="resources/js/add_registration.js"></script>
    </body>
</html>
<%@include file="footer.jsp" %>
