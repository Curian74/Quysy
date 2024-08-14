<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Registration Details</title>
        <link rel="stylesheet" href="vendors/fontawesome/css/all.css">
    </head>
    <body>
        <div class="container">
            <div class="d-flex align-items-center justify-content-center py-3">
                <a href="registration-list" class="btn btn-outline-dark">
                    <i class="fas fa-arrow-left"></i>
                </a>
                <h1 class="display-3 fw-bold text-center mb-0 flex-grow-1">Registration Details</h1>
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

            <form method="post" action="update-registration" class="mt-4">
                <input type="hidden" name="registrationId" value="${registration.registrationId}">

                <c:if test="${registration.userId ne account.userId}">
                    <div class="form-text">(*)You can only edit Status & Notes</div>
                </c:if>

                <div class="form-group mb-3">
                    <label for="subject" class="form-label">Subject Name</label>
                    <select class="form-select" id="subject" name="subject" ${registration.userId ne account.userId ? 'disabled' : ''}>
                        <c:forEach var="s" items="${subjectList}">
                            <option class="subject-option" value="${s.subjectId}" ${registration.subjectId eq s.subjectId ? 'selected' : ''}>${s.subjectName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group mb-3">
                    <label for="package" class="form-label">Package</label>
                    <select class="form-select" id="package" name="package" ${registration.userId ne account.userId ? 'disabled' : ''}>
                        <c:forEach var="p" items="${packageList}">
                            <option class="package-option" value="${p.packageId}" ${registration.packageId eq p.packageId ? 'selected' : ''}>${p.packageName}</option>
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
                    <input type="text" class="form-control" id="fullName" name="fullName" value="${registration.fullName}" ${registration.userId ne account.userId ? 'readonly' : ''}>
                </div>

                <div class="form-group mb-3">
                    <label for="genderInput" class="form-label">Gender</label>
                    <select class="form-select" id="genderInput" name="gender" ${registration.userId ne account.userId ? 'disabled' : ''}>
                        <option value="true" ${registration.gender eq account.gender ? 'selected' : ''}>Male</option>
                        <option value="false" ${registration.gender eq account.gender ? 'selected' : ''}>Female</option>
                    </select>
                </div>

                <div class="form-group mb-3">
                    <label for="emailInput" class="form-label">Email</label>
                    <input type="email" class="form-control" id="emailInput" name="email" value="${registration.email}" ${registration.userId ne account.userId ? 'readonly' : ''}>
                </div>

                <div class="form-group mb-3">
                    <label for="mobileInput" class="form-label">Mobile</label>
                    <input type="text" class="form-control" id="mobileInput" name="mobile" value="${registration.mobile}" ${registration.userId ne account.userId ? 'readonly' : ''}>
                </div>

                <div class="form-group mb-3">
                    <label for="registrationDate" class="form-label">Registration Date</label>
                    <input type="date" class="form-control" id="registrationDate" name="registrationDate" value="${registration.registrationDate}" disabled>
                </div>

                <div class="form-group mb-3">
                    <label for="registrationStatus" class="form-label">Registration Status</label>
                    <select class="form-select" id="registrationStatus" name="registrationStatus">
                        <c:forEach var="rs" items="${registrationStatusList}">
                            <option value="${rs.registrationStatusId}" ${registration.registrationStatus eq rs.registrationStatusId ? 'selected' : ''}>${rs.registrationStatusName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="row">
                    <div class="col">
                        <div class="form-group mb-3">
                            <label for="validFrom" class="form-label">Valid from</label>
                            <input type="date" class="form-control" id="validFrom" name="validFrom" value="${registration.validFrom}" disabled>
                        </div>
                    </div>

                    <div class="col">
                        <div class="form-group mb-3">
                            <label for="validTo" class="form-label"">Valid to</label>
                            <input type="date" class="form-control" id="validTo" name="validTo" value="${registration.validTo}" disabled>
                        </div>
                    </div>
                </div>

                <div class="form-group mb-3">
                    <label for="notes" class="form-label">Notes</label>
                    <input type="text" class="form-control" id="notes" name="notes" value="${registration.notes}" required>
                </div>

                <button type="submit" class="btn btn-outline-primary">Update</button>
                <a href="add-registration" class="btn btn-outline-secondary">Add New Registration</a>               
            </form>
        </div>

        <script src="resources/js/registration_details.js"></script>    
    </body>
</html>
<%@include file="footer.jsp" %>
