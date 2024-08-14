<%@page import="Model.Subject"%>
<%@page import="Model.Category"%>
<%@page import="Model.Package"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resources/css/style.css">
        <link rel="stylesheet" href="resources/css/subject_list_popup.css">
        <link href="vendors/bootstrap/bootstrap.min.css" rel="stylesheet">
        <script src="resources/js/subjects_list.js"></script>

        <script>
            function selectOrder(value, text) {
                document.getElementById('orderBy').value = value;
                document.getElementById('orderByDropdown').innerText = text;
                document.getElementById('subjectForm').submit();
            }
        </script>
        <style>
            body, html {
                height: 100%;
                margin: 0;
                display: flex;
                flex-direction: column;
            }
            .container {
                flex: 1;
                display: flex;
                flex-direction: column;
            }
        </style> 
        <title>Subjects List</title>
    </head>
    <body>
        <c:set var="sessionUser" value="${sessionScope.account}"/>
        <c:choose>
            <c:when test="${sessionUser != null && (sessionUser.roleId == 1 || sessionUser.roleId == 3)}">
                <div class="container">
                    <br><h1 style="text-align: center;">Subject List</h1><br>
                    <!-- Search Form -->
                    <form action="subject-control" method="get" class="form-inline mb-4" id="subjectForm">
                        <input type="hidden" name="service" value="listAllSubjects">

                        <div class="row">
                            <c:choose>
                                <c:when test="${sessionUser.roleId == 3}">
                                    <div class="col-md-6" style="margin-top: 25px;">
                                        <input type="text" class="form-control shadow-sm text-center" id="searchName" name="searchName" value="${param.searchName}" placeholder="Subject Name">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control shadow-sm text-center"  id="searchName" name="searchName" value="${param.searchName}" placeholder="Subject Name">
                                        <input type="text" class="form-control shadow-sm text-center" style="margin-top: 10px;" id="expertName" name="expertName" value="${param.expertName}" placeholder="Expert Name">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="col-md-2"> 
                                <div class="dropdown">

                                    <button class="btn btn-outline-primary dropdown-toggle w-100 shadow-sm" type="button" id="categoryDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                        Categories
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-right w-100 text-center">
                                        <li><a class="dropdown-item" id="category-all" onclick="selectCategory('', 'All categories')">All categories</a></li>
                                            <c:forEach items="${categories}" var="cat">
                                            <li><a class="dropdown-item" id="category-${cat.getCategoryId()}" onclick="selectCategory('${cat.getCategoryId()}', '${cat.getCategoryName()}')">${cat.getCategoryName()}</a></li>
                                            </c:forEach>
                                    </ul>
                                    <select name="category" id="categories" class="d-none">
                                        <option value="">Categories</option>
                                        <c:forEach items="${categories}" var="cat">
                                            <option value="${cat.getCategoryId()}" ${param.category == cat.getCategoryId() ? 'selected' : ''}>${cat.getCategoryName()}</option>
                                        </c:forEach>
                                    </select>
                                </div >
                                <div class="dropdown" style="margin-top: 10px;">
                                    <button class="btn btn-outline-primary dropdown-toggle w-100 shadow-sm" type="button" id="featuredDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                        Featured
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-right w-100 text-center">
                                        <li><a class="dropdown-item" id="feature-all" onclick="selectFeatured('', 'All')">All Feature</a></li>
                                        <li><a class="dropdown-item" id="feature-true" onclick="selectFeatured('true', 'Featured')">Featured</a></li>
                                        <li><a class="dropdown-item" id="feature-false" onclick="selectFeatured('false', 'Not Featured')">Not Featured</a></li>
                                    </ul>
                                </div>
                                <select name="featured" id="featured" class="d-none">
                                    <option value="">All Feature</option>
                                    <option value="true" ${param.featured == 'true' ? 'selected' : ''}>Featured</option>
                                    <option value="false" ${param.featured == 'false' ? 'selected' : ''}>Not Featured</option>
                                </select>
                            </div>
                            <div class="col-md-2">  
                                <div class="dropdown">
                                    <button class="btn btn-outline-primary dropdown-toggle w-100 shadow-sm" type="button" id="statusDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                        Status
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-right w-100 text-center">
                                        <li><a class="dropdown-item" id="status-all" onclick="selectStatus('', 'All statuses')">All statuses</a></li>
                                        <li><a class="dropdown-item" id="status-true" onclick="selectStatus('true', 'Published ')">Published </a></li>
                                        <li><a class="dropdown-item" id="status-false" onclick="selectStatus('false', 'Unpublished ')">Unpublished </a></li>
                                    </ul>
                                    <select name="status" id="status" class="d-none">
                                        <option value="">Status</option>
                                        <option value="true" ${param.status == 'true' ? 'selected' : ''}>Published </option>
                                        <option value="false" ${param.status == 'false' ? 'selected' : ''}>Unpublished </option>
                                    </select>
                                </div>

                                <div class="dropdown" style="margin-top: 10px;">
                                    <button class="btn btn-outline-primary dropdown-toggle w-100 shadow-sm" type="button" id="orderByDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                        Sort by Name
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-right w-100 text-center">
                                        <li><a class="dropdown-item" id="order-default" onclick="selectOrder('', 'Default')">Default</a></li>
                                        <li><a class="dropdown-item" id="order-nameAsc" onclick="selectOrder('nameAsc', 'Name (A-Z)')">Name (A-Z)</a></li>
                                        <li><a class="dropdown-item" id="order-nameDesc" onclick="selectOrder('nameDesc', 'Name (Z-A)')">Name (Z-A)</a></li>
                                    </ul>
                                </div>
                                <select name="orderBy" id="orderBy" class="d-none">
                                    <option value="">Sort</option>
                                    <option value="nameAsc" ${param.orderBy == 'nameAsc' ? 'selected' : ''}>Name (A-Z)</option>
                                    <option value="nameDesc" ${param.orderBy == 'nameDesc' ? 'selected' : ''}>Name (Z-A)</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group-role" style="margin-top: 25px;"> 
                                    <button type="submit"  class="btn btn-primary">Search</button>

                                    <c:if test="${sessionUser != null && sessionUser.roleId == 1 && sessionUser.roleId != 3 }">
                                        <a href="new-subject"  class="btn btn-success">Add New</a>
                                    </c:if>

                                </div>
                            </div>
                        </div>
                    </form>


                    <!-- Subjects Table -->
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th style="text-align: center; width: 5%;">#</th>
                                <th style="text-align: center; width: 5%;">ID</th>
                                <th style="text-align: center; width: 10%;">Subject Name</th>
                                <th style="text-align: center; width: 15%;">Category</th>
                                <th style="text-align: center; width: 15%;">Number Of Lessons</th>
                                    <c:if test="${sessionUser != null && sessionUser.roleId == 1 }">
                                    <th style="text-align: center; width: 10%;">Owner</th>
                                    </c:if>
                                <th style="text-align: center; width: 5%;">Status</th>
                                <th style="text-align: center; width: 10%;">Featured</th>
                                <th style="text-align: center; width: 10%;">Created Date</th>
                                <th style="text-align: center; width: 5%;">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty subjects}">
                                    <c:forEach var="subject" varStatus="loop" items="${subjects}">
                                        <tr>
                                            <td style="text-align: center;">${loop.index + 1}</td>
                                            <td style="text-align: center;">${subject.subjectId}</td>
                                            <td style="text-align: center;">${subject.subjectName}</td>
                                            <td style="text-align: center;">
                                                <c:set var="categoryName" value="" />
                                                <c:forEach var="cat" items="${categories}">
                                                    <c:if test="${cat.categoryId == subject.categoryId}">
                                                        <c:set var="categoryName" value="${cat.categoryName}" />
                                                    </c:if>
                                                </c:forEach>
                                                ${categoryName}
                                            </td>                                            
                                            <td style="text-align: center;">
                                                <c:set var="lessonCount" value="${subjectLessonCount[subject.subjectId]}"/>
                                                ${lessonCount}
                                            </td> 
                                            <c:if test="${sessionUser != null && sessionUser.roleId == 1 }">
                                                <td style="text-align: center;">
                                                    <c:set var="userName" value="${userNames[subject.expertId]}"/>
                                                    ${userName != null ? userName : 'Unknown'}
                                                </td>
                                            </c:if>
                                            <td style="text-align: center;">${subject.status ? 'Published' : 'Unpublished '}</td>
                                            <td style="text-align: center;">${subject.isFeatured ? 'Yes' : 'No'}</td>
                                            <td style="text-align: center;">${subject.createdDate}</td>
                                            <td class="action-links" style="text-align: center;">
                                                <a href="subject-detail-control?subjectId=${subject.subjectId}">Detail</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="10" style="text-align: center;  font-weight: bold; font-style: italic;">
                                            <br>Nothing to show here!
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                    <!-- Pagination Links -->
                    <c:if test="${not empty subjects}">
                        <div class="container py-3 justify-content-center">
                            <nav aria-label="Subjects pagination">
                                <ul class="pagination d-flex justify-content-center">
                                    <c:if test="${currentPage > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="?page=1&searchName=${param.searchName}&status=${param.status}&category=${param.category}&featured=${param.featured}&expertName=${param.expertName}&orderBy=${param.orderBy}">&lt;&lt;</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${currentPage > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="?page=${currentPage - 1}&searchName=${param.searchName}&status=${param.status}&category=${param.category}&featured=${param.featured}&expertName=${param.expertName}&orderBy=${param.orderBy}">&lt;</a>
                                        </li>
                                    </c:if>
                                    <c:forEach begin="1" end="${totalPages}" var="page">
                                        <li class="page-item ${currentPage == page ? 'active' : ''}">
                                            <a class="page-link" href="?page=${page}&searchName=${param.searchName}&status=${param.status}&category=${param.category}&featured=${param.featured}&expertName=${param.expertName}&orderBy=${param.orderBy}">${page}</a>
                                        </li>
                                    </c:forEach>
                                    <c:if test="${currentPage < totalPages}">
                                        <li class="page-item">
                                            <a class="page-link" href="?page=${currentPage + 1}&searchName=${param.searchName}&status=${param.status}&category=${param.category}&featured=${param.featured}&expertName=${param.expertName}&orderBy=${param.orderBy}">&gt;</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${currentPage < totalPages}">
                                        <li class="page-item">
                                            <a class="page-link" href="?page=${totalPages}&searchName=${param.searchName}&status=${param.status}&category=${param.category}&featured=${param.featured}&expertName=${param.expertName}&orderBy=${param.orderBy}">&gt;&gt;</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </nav>
                        </div>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <div class="container">
                    <div class="row">
                        <!-- sider -->
                        <div class="col-3 border-end">
                            <div class="container">
                                <!-- search by name -->
                                <form id="subjectForm" action="subject" method="get">
                                    <div class="mb-2 py-3 border-bottom">
                                        <label class="form-label h2">Search for subjects:</label>
                                        <div class="d-flex">
                                            <input type="text" class="form-control me-2 shadow-sm" name="search" placeholder="Search by Subject Name" value="${param.search}">
                                            <input type="hidden" name="service" value="listAllSubjects">
                                            <button type="submit" class="btn btn-primary shadow-sm">Search</button>
                                        </div>
                                    </div>
                                    <!-- category dropdown -->
                                    <label class="form-label h2">Categories:</label>
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
                                            <a href="subject-details?subjectId=${fture.getSubjectId()}" class="text-decoration-none">${fture.getSubjectName()} - ${fture.getTagLine()}</a>
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
                        <div class="col-9">
                            <div class="main container py-3">
                                <h1 class="text-center mb-3">Subjects List</h1>
                                <div class="row">
                                    <c:if test="${subject == null || subject.isEmpty()}">
                                        <div class="alert alert-primary text-center" role="alert">
                                            Nothing to show here!
                                        </div>
                                    </c:if>
                                    <c:forEach var="subject" items="${subject}" varStatus="status">
                                        <div class="col-md-3 subject-card">
                                            <div class="thumbnail">
                                                <img src="${subject.getThumbnail()}">
                                            </div>                                    
                                            <a href="subject-details?subjectId=${subject.subjectId}"><div class="title">${subject.getSubjectName()}</div></a>
                                            <div class="tagline">${subject.getTagLine()}</div>
                                            <div class="price">
                                                <c:choose>
                                                    <c:when test="${lowestPackageMap[subject.getSubjectId()] != null}">
                                                        List price: ${lowestPackageMap[subject.getSubjectId()].getListPrice()}00 VND
                                                        <br>
                                                        Sale price: ${lowestPackageMap[subject.getSubjectId()].getSalePrice()}00 VND
                                                    </c:when>
                                                    <c:otherwise>
                                                        No packages available
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <form action="register-subject" method="GET">
                                                <input type="hidden" name="subjectId" value="${subject.subjectId}">
                                                <button type="submit" class="btn btn-primary">Register</button>
                                            </form>
                                        </div>
                                        <c:if test="${status.index % 3 == 2}">
                                        </div>
                                        <div class="row">
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <!-- pagination -->
                                <div class="container py-3 justify-content-center">
                                    <nav aria-label="Subjects pagination">
                                        <ul class="pagination d-flex justify-content-center">
                                            <!-- First page link -->
                                            <c:if test="${currentPage > 1}">
                                                <li class="page-item">
                                                    <a class="page-link" href="subject?page=1">&lt;&lt;</a>
                                                </li>
                                            </c:if>
                                            <!-- Previous page link -->
                                            <c:if test="${currentPage > 1}">
                                                <li class="page-item">
                                                    <a class="page-link" href="subject?page=${currentPage - 1}">&lt;</a>
                                                </li>
                                            </c:if>
                                            <!-- Page numbers -->
                                            <c:forEach begin="1" end="${totalPages}" var="page">
                                                <li class="page-item ${currentPage == page ? 'active' : ''}">
                                                    <a class="page-link" href="subject?page=${page}">${page}</a>
                                                </li>
                                            </c:forEach>
                                            <!-- Next page link -->
                                            <c:if test="${currentPage < totalPages}">
                                                <li class="page-item">
                                                    <a class="page-link" href="subject?page=${currentPage + 1}">&gt;</a>
                                                </li>
                                            </c:if>
                                            <!-- Last page link -->
                                            <c:if test="${currentPage < totalPages}">
                                                <li class="page-item">
                                                    <a class="page-link" href="subject?page=${totalPages}">&gt;&gt;</a>
                                                </li>
                                            </c:if>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <!-- Modal HTML -->

                <div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="registerModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 style="margin-left: 13%; font-size:40px" id="regis-title"class="modal-title" id="registerModalLabel">REGISTER SUBJECT</h5>
                            </div>

                            <div class="modal-body">
                                <h5 id ="your-info">YOUR INFORMATION</h5>
                                <c:set var="info" value="${sessionScope.account}"/>
                                <input type="hidden" id="subject-id" name="subjectId" value="">

                                <c:forEach items = "${packList}" var = "p">
                                    <input type="hidden" class="form-control" value="${p.packageId}" name="pack">
                                    <input type="radio"  id="packName" name="packName" required>"${p.packageName}"
                                </c:forEach>    
                                <form action ="register-subject" method="POST">
                                    <div class="form-group">
                                        <label for="fullName">Full name:</label>
                                        <input type="text" class="form-control" value="${info.fullname}" id="fullName">
                                    </div>

                                    <div class="form-group">
                                        <label for="email">Email:</label>
                                        <input type="email" class="form-control" value="${info.email}" id="email">
                                    </div>
                                    <div class="form-group">
                                        <label for="mobile">Mobile:</label>
                                        <input type="text" class="form-control" value="${info.mobile}" id="mobile">
                                    </div>
                                    <div class="form-group">
                                        <label>Gender:</label><br>
                                        <input type="radio" name="gender" value="true" <c:if test="${info.gender == true}">checked</c:if>> Male
                                        <input type="radio" name="gender" value="false" <c:if test="${info.gender == false}">checked</c:if>> Female
                                        </div>

                                        <div class="modal-footer">
                                            <button id ="regis" type="submit" class="btn btn-register" data-target="#registerModal">REGISTER</button>
                                            <button id="cancel" type="button" class="btn btn-cancel" data-dismiss="modal">CANCEL</button>                        
                                        </div>
                                    </form>
                                </div>

                            </div>
                        </div>
                    </div>
            </c:otherwise>
        </c:choose>
    </body>
</html>
<%@include file="footer.jsp" %>
