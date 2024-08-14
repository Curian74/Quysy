<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | My Registrations</title>
        <link rel="stylesheet" href="resources/css/my_registrations.css"/>
        <link rel="stylesheet" href="resources/css/sticky_footer.css"/>
        <link rel="stylesheet" href="vendors/datatables/datatables.min.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body>
        <div class="flex-column">
            <div class="content">
                <div class="container">
                    <div class="row">

                        <!-- Sider -->
                        <div class="col-3 border-end">
                            <!-- Subject search -->
                            <div class="container py-3 border-bottom">                            
                                <label for="searchBox" class="form-label h2">Search registrations</label>     
                                <input type="text" class="form-control me-2 shadow-sm" id="searchBox" placeholder="Search for registrations...">
                            </div>

                            <!-- Subject categories -->
                            <div class="container py-3 border-bottom">
                                <h2>Filter by categories</h2>
                                <div class="dropdown">
                                    <button class="btn btn-outline-primary dropdown-toggle w-100 shadow-sm" type="button" id="categoryFilterButton" data-bs-toggle="dropdown" aria-expanded="false">
                                        All categories
                                    </button>
                                    <ul class="dropdown-menu w-100 text-center" aria-labelledby="categoryFilterButton">
                                        <li><a class="dropdown-item filter-button" href="#">All categories</a></li>
                                            <c:forEach var="c" items="${requestScope.categoryList}">
                                            <li><a class="dropdown-item filter-button" data-category-id="${c.categoryId}" href="#">${c.categoryName}</a></li>
                                            </c:forEach>
                                    </ul>
                                </div>
                            </div>

                            <!-- Contact card -->
                            <div class="container py-3">
                                <h3>Quiz Practicing System</h3>
                                <h4>Contact Us</h4>
                                <p>Phone: 0123456789<br>Email: quysy.quizpracticingsystem@gmail.com</p>
                            </div>

                        </div>

                        <div class="col-9">
                            <!-- Registrations list-->
                            <div class="container py-3">
                                <h1 class="fw-bold text-center mb-3">My registrations</h1>

                                <c:choose>
                                    <c:when test="${updateStatusMessage eq 'cancelSuccess'}">
                                        <div class="alert alert-success" role="alert">
                                            Successfully cancelled registration!
                                        </div>
                                    </c:when>

                                    <c:when test="${updateStatusMessage eq 'cancelFail'}">
                                        <div class="alert alert-danger" role="alert">
                                            Failed to cancel registration!
                                        </div>
                                    </c:when>
                                </c:choose>

                                <table id="registrationList" class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Subject</th>
                                            <th scope="col">Package</th>
                                            <th scope="col">Total cost</th>
                                            <th scope="col">Registration time</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Valid from</th>
                                            <th scope="col">Valid to</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${requestScope.registrationsListMessage eq 'registrationsListEmpty'}">
                                            <tr>
                                                <td colspan="9">There's no registrations</td>
                                            </tr>
                                        </c:if>

                                        <c:forEach var="rl" items="${requestScope.registrationsList}">
                                            <tr>
                                                <th scope="row">${rl.registrationId}</th>
                                                <td>${rl.subjectName}</td>
                                                <td>${rl.packageName}</td>
                                                <td>${rl.totalCost}</td>
                                                <td>${rl.registrationDate}</td>
                                                <td>${rl.status}</td>
                                                <td>${rl.validFrom}</td>
                                                <td>${rl.validTo}</td>
                                                <td>
                                                    <a href="register-subject?subjectId=${rl.subjectId}&registrationId=${rl.registrationId}" class="btn btn-sm btn-primary">Edit</a>
                                                    <button data-registration-id="${rl.registrationId}" class="btn btn-sm btn-warning ${rl.registrationStatus ne 2 ? '' : 'disabled'} toggle-status-button">
                                                        Cancel
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>

                </div>

                <%@include file="footer.jsp"%>
            </div>

            <script src="vendors/datatables/datatables.min.js"></script>
            <script src="resources/js/my_registrations.js"></script>
    </body>
</html>

