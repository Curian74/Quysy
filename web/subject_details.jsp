<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subject Detail</title>
        <link rel="stylesheet" href="resources/css/style.css">
        <link rel="stylesheet" href="resources/css/subject_detail.css">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <!-- sider -->
                <div class="col-3 border-end">
                    <div class="container">
                        <!-- search by name -->
                        <form id="subjectForm" action="subject" method="get">
                            <div class="mb-2 py-3 border-bottom">
                                <label class="form-label h2">Search for subjects</label>
                                <div class="d-flex">
                                    <input type="text" class="form-control me-2 shadow-sm" name="search" placeholder="Search by Subject Name" value="${param.search}">
                                    <input type="hidden" name="service" value="listAllSubjects">
                                    <button type="submit" class="btn btn-primary shadow-sm">Search</button>
                                </div>
                            </div>
                            <!-- category dropdown -->
                            <label class="form-label h2">Sort by categories</label>
                            <div class="dropdown py-3 border-bottom">
                                <button class="btn btn-outline-primary dropdown-toggle w-100 shadow-sm" type="button" id="categoryDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                    Categories
                                </button>
                                <ul class="dropdown-menu w-100 text-center">
                                    <li><a class="dropdown-item" id="category-all" onclick="selectCategory('', 'All categories')">All categories</a></li>
                                        <c:forEach items="${categories}" var="cat">
                                        <li><a class="dropdown-item" id="category-${cat.getCategoryId()}" onclick="selectCategory('${cat.getCategoryId()}', '${cat.getCategoryName()}')">${cat.getCategoryName()}</a></li>
                                        </c:forEach>
                                </ul>
                                <select name="category" id="categories" class="d-none">
                                    <option value="">Categories...</option>
                                    <c:forEach items="${categories}" var="cat">
                                        <option value="${cat.getCategoryId()}" ${param.category == cat.getCategoryId() ? 'selected' : ''}>${cat.getCategoryName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </form>
                        <br>
                        <!-- feature subject -->
                        <label class="form-label h4">Featured Subject:</label>
                        <div class="mb-2">
                            <c:forEach items="${feature}" var="fture">
                                <div class="mb-2">
                                    <a href="subjectdetails?subjectId=${fture.getSubjectId()}" class="text-decoration-none">${fture.getSubjectName()} - ${fture.getTagLine()}</a>
                                </div>
                            </c:forEach>
                        </div>
                        <br>
                        <!-- contact -->
                        <div class="contact">
                            <h3>Quiz Practicing System</h3>
                            <h4>Contact Us</h4>
                            <p>Phone: 0123456789<br>Email: quysy.quizpracticingsystem@gmail.com</p>
                        </div>
                    </div>
                </div>
                <!-- Subject Details -->
                <div class="col-9" >
                    <div class="subject-details">

                        <h3>${subject.subjectName} Detail</h3>
                        <!-- Moved subject name to the middle -->
                        <h2 style="text-align: center;">${subject.subjectName}</h2>
                        <p style="text-align: center;">${subject.tagLine}</p> 
                        <p class="fst-italic">${subject.briefInfo}</p>

                        <p class="description">${subject.description}</p>

                        <!-- Lowest price package option -->
                        <h3>Lowest Price Package Option:</h3>
                        <table class="package-table">
                            <tr>
                                <th>Original Price</th>
                                <th>Sale Price</th>
                            </tr>
                            <tr>
                                <c:set var="lowPack" value="${lowestPack}"></c:set>
                                <td>${lowPack.listPrice}</td>
                                <td>${lowPack.salePrice}</td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <!-- Register button -->
                                    <div class="register-btn">
                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#registerModal">Register</button>
                                    </div>  
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- Register Modal -->
        <div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="registerModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content" id = "register_content">
                    <div class="modal-header">
                        <h1 style="margin-left: 40px" class="modal-title">REGISTER SUBJECT</h1>
                    </div>
                    <div class="modal-body" id = "register_body">
                        <form action="register-subject" method="POST">
                            <c:set var="r" value="${sessionScope.registration}"/>
                            <c:if test="${not empty r}">
                                <input type="hidden" name="action" value="edit">
                                <input type="hidden" name="registrationId" value="${r.registrationId}">
                            </c:if>
                            <h2 class = "register_h2">SUBJECT PACKAGES</h2>
                            <div class="package" style="">
                                <c:forEach items="${packList}" var="p">
                                    <input type="radio" name="pack" value="${p.packageId}" required
                                           <c:if test="${spackage != null}"> checked</c:if>
                                           <c:if test="${p.packageId == r.packageId}"> checked</c:if>>
                                    <span>${p.packageName}: ${p.description} - ${p.salePrice}00 VND</span>
                                    <br>
                                </c:forEach>
                            </div>
                            <h2 class = "register_h2">YOUR INFORMATION</h2>
                            <c:set var="info" value="${sessionScope.account}"/>
                            <c:set var="sname" value="${sessionScope.sname}"/>
                            <c:set var="semail" value="${sessionScope.semail}"/>
                            <c:set var="smobile" value="${sessionScope.smobile}"/>
                            <c:set var="sgender" value="${sessionScope.sgender}"/>
                            <c:set var="snotes" value="${sessionScope.snotes}"/>

                            <div class="form-group">
                                <input type="hidden" class="form-control" name="subject" value="${subject.subjectId}" required>
                            </div>
                            <div class="form-group">
                                <label class = "register_lb" for="name">Full name:</label>
                                <input type="text" class="form-control" id="name" name="name"
                                       value="${info != null ? info.fullname : sname}" required
                                       <c:if test="${info != null && r == null}">readonly</c:if> >
                                </div>
                                <div class="form-group">
                                    <label class = "register_lb" for="email">Email:</label>
                                    <input type="email" class="form-control" id="email" name="email" 
                                           value="${info != null ? info.email : semail}" required
                                    <c:if test="${info != null && r == null}">readonly</c:if> >
                                </div>
                                <div class="form-group">
                                    <label class = "register_lb" for="mobile">Mobile:</label>
                                    <input type="tel" class="form-control" id="mobile" name="mobile" 
                                           value="${info != null ? info.mobile : smobile}" required
                                    <c:if test="${info != null && r == null}">readonly</c:if> >
                                </div>
                                <div class="form-group">
                                    <label class = "register_lb">Gender:</label>
                                <c:choose>
                                    <c:when test="${info.gender == true || sgender == true}">
                                        <select id="gender" class="form-control mt-1" name="gender" required readonly>
                                            <option value="true">Male</option>
                                        </select>
                                    </c:when>
                                    <c:when test="${info.gender == false || sgender == false}">
                                        <select id="gender" class="form-control mt-1" name="gender" required readonly>
                                            <option value="false">Female</option>
                                        </select>
                                    </c:when>
                                    <c:otherwise>
                                        <select id="gender" class="form-control mt-1" name="gender" required>
                                            <option value="true">Male</option>
                                            <option value="false">Female</option>
                                        </select>
                                    </c:otherwise>
                                </c:choose>

                            </div>

                            <c:if test="${not empty r}">
                                <div class="form-group">
                                    <label class="register_lb" for="notes">Notes:</label>
                                    <input type="text" class="form-control" id="notes" name="notes"
                                           value="${not empty snotes ? snotes : r.notes}">
                                </div>
                            </c:if>

                            <c:if test="${register_subject_error != null && register_subject_error ne ''}">
                                <div class="alert alert-danger" role="alert">
                                    ${sessionScope.register_subject_error}
                                </div>
                                <c:set var="register_subject_error" value="" scope="session"/>
                            </c:if>


                            <c:if test= "${register_subject_success != null && register_subject_success ne ''}">
                                <div class="alert alert-success" role="alert">
                                    ${sessionScope.register_subject_success}
                                </div>
                                <c:set var="register_subject_success" value="" scope="session"/>
                            </c:if>
                            <div class="modal-footer" id = "register_footer">
                                <button type="submit" class="btn btn-primary" id = "btn_register">
                                    <c:choose>
                                        <c:when test="${not empty r}">
                                            EDIT
                                        </c:when>
                                        <c:otherwise>
                                            REGISTER
                                        </c:otherwise>
                                    </c:choose>
                                </button>

                                <c:choose>
                                    <c:when test="${not empty r}">
                                        <a class="btn btn-secondary" href="my-registrations">
                                            RETURN
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button" class="btn btn-secondary" onclick="$('#registerModal').modal('hide');">
                                            CANCEL
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>
        <script src="resources/js/subject_details.js"></script>
    </body>
</html>

<%@include file="footer.jsp" %>
