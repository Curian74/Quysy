<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="vendors/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/header.css">
    </head>
    <body>
        <c:set var="u" value="${requestScope.user}"/>
        <c:set var="sessionUser" value="${sessionScope.account}"/>
        <header class="sticky-top mb-3 shadow">
            <div class="container">
                <nav class="navbar navbar-expand-lg" data-bs-theme="dark">
                    <div class="container-fluid">
                        <a class="navbar-brand fs-2" href="home">QUYSY</a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbar" aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbar">
                            <ul class="navbar-nav me-auto mb-1 mb-lg-0">    
                                <li class="nav-item">
                                    <a class="nav-link" href="home">HOME</a>
                                </li>
                                <c:choose>
                                    <c:when test="${sessionUser.roleId == 1 || sessionUser.roleId == 3}">
                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="#" id = "subjectDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                SUBJECTS
                                            </a>
                                            <ul class="dropdown-menu" aria-labelledby="blogsDropdown">
                                                <li><a class="dropdown-item" href="subject-control">Subject List</a></li>
                                                <li><a class="dropdown-item" href="dimension-type">Subject Dimensions</a></li>
                                                <li><a class="dropdown-item" href="package-list">Price Packages & Subject Lesson</a></li>
                                            </ul>
                                        </li>
                                    </c:when>

                                    <c:otherwise>
                                        <li class="nav-item">
                                            <a class="nav-link" href="subject">SUBJECTS</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${sessionUser.roleId == 4 || sessionUser.roleId == 1}">
                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="#" id="blogsDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                BLOGS
                                            </a>
                                            <ul class="dropdown-menu" aria-labelledby="blogsDropdown">
                                                <li><a class="dropdown-item" href="blog-list?status=active">Active Blogs</a></li>
                                                <li><a class="dropdown-item" href="blog-list?status=inactive">Inactive Blogs</a></li>
                                            </ul>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="nav-item">
                                            <a class="nav-link" href="blog-list">BLOGS</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${sessionUser.roleId == 4}">
                                    <li class="nav-item">
                                        <a class="nav-link" href="sliders-list">SLIDER</a>
                                    </li>
                                </c:if>

                                <c:choose>
                                    <c:when test="${not empty sessionScope.account}">
                                        <c:choose>
                                            <c:when test="${sessionUser.roleId == 1 || sessionUser.roleId == 5}">
                                                <li class="nav-item dropdown">
                                                    <a class="nav-link dropdown-toggle" href="#" id="registrationsDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                        REGISTRATIONS
                                                    </a>
                                                    <ul class="dropdown-menu" aria-labelledby="blogsDropdown">
                                                        <li><a class="dropdown-item" href="my-registrations">My Registrations</a></li>
                                                        <li><a class="dropdown-item" href="registration-list">Registrations List</a></li>
                                                    </ul>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="nav-item">
                                                    <a class="nav-link" href="my-registrations">REGISTRATIONS</a>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="nav-item">
                                            <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#loginModal">REGISTRATIONS</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>

                                <li class="nav-item">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.account && sessionScope.account.roleId == 2}">
                                            <a class="nav-link" href="practice-list">PRACTICES</a>
                                        </c:when>

                                        <c:when test="${empty sessionScope.account}">
                                            <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#loginModal">PRACTICES</a>
                                        </c:when>     

                                    </c:choose>
                                </li>

                                <li class="nav-item">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.account}">
                                            <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#profileModal">ACCOUNT</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#loginModal">ACCOUNT</a>
                                        </c:otherwise>
                                    </c:choose>
                                </li> 

                                <c:if test="${account.roleId == 1}">
                                    <li class="nav-item dropdown">
                                        <a class="nav-link dropdown-toggle" href="#" id="managedropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            MANAGE
                                        </a>
                                        <ul class="dropdown-menu" aria-labelledby="managedropdown">
                                            <li><a class="dropdown-item" href="user-list">User List</a></li>
                                            <li><a class="dropdown-item" href="settings-list">Settings List</a></li>
                                        </ul>
                                    </li>
                                </c:if>

                                <c:if test="${sessionUser.roleId == 1 || sessionUser.roleId == 3}">
                                    <li class="nav-item dropdown">
                                        <a class="nav-link dropdown-toggle" href="#" id="testdropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            TEST
                                        </a>
                                        <ul class="dropdown-menu" aria-labelledby="blogsDropdown">
                                            <li><a class="dropdown-item" href="question-list">Questions List</a></li>
                                            <li><a class="dropdown-item" href="quizzes-list">Quizzes List</a></li>
                                        </ul>
                                    </li>
                                </c:if>
                            </ul>
                            <c:choose>
                                <c:when test="${not empty sessionScope.account}">
                                    <h4 class="text-light me-3 mt-2">Welcome, ${account.fullname}</h4>
                                    <a class="btn btn-outline-primary" href="log-out">LOG OUT</a>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-outline-primary me-2" data-bs-toggle="modal" data-bs-target="#loginModal">LOG IN</button>
                                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registerAccountModal">SIGN UP</button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </nav>
            </div>
        </header>

        <!-- Profile Modal -->
        <div class="modal fade" id="profileModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        <div id="profileContent"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Login Modal -->
        <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        <jsp:include page="login.jsp" />
                        <p class="mt-3 text-center"><a href="#" id="switchToRegister">Sign up</a> | <a href="reset-password">Forgot password?</a></p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Register Modal -->
        <div class="modal fade" id="registerAccountModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        <jsp:include page="register.jsp"/>
                        <p class="mt-3 text-center"><a href="#" id="switchToLogin">Already have an account? </a></p>
                    </div>
                </div>
            </div>
        </div>


        <script src="vendors/jquery/jquery-3.7.1.min.js"></script>
        <script src="vendors/bootstrap/bootstrap.bundle.min.js"></script>
        <script src="resources/js/header.js"></script>
    </body>
</html>
