<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Price Packages</title>
        <link rel="stylesheet" href="resources/css/package_list.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="card-header">
                <h1>Price package List</h1>
            </div>
            <div class="card-body">

                <!-- Search and Sort Form -->
                <form id="searchSortForm" action="package-list" method="get">
                    <input type="hidden" id="submitType" name="submitType" value="search">
                    <input type="hidden" id="sortBy" name="sortBy" value="${param.sortBy}">
                    <div class="row" id="dimSearch" style="margin-top: 25px;">
                        <div class="col-md-5 position-relative" id="searchContainer">
                            <input type="text" class="form-control shadow-sm text-center" id="searchName" name="searchName" value="${param.searchName}"
                                   placeholder="Search by package name or subject name...">
                            <button type="button" id="clearSearch" onclick="clearSearch()" class="clear-button">&times;</button>
                        </div>


                        <div class="col-md-1" id="searchButtonContainer">
                            <button type="submit" class="btn btn-primary shadow-sm w-100">Search</button>
                        </div>

                        <div class="col-md-2" id="sortContainer">
                            <div class="dropdown">
                                <button class="btn btn-outline-primary dropdown-toggle w-100 shadow-sm" type="button" id="sortByDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                    <c:choose>
                                        <c:when test="${param.sortBy == 'default'}">Default</c:when>
                                        <c:when test="${param.sortBy == 'newlyCreated'}">Newly Created</c:when>
                                        <c:when test="${param.sortBy == 'latestUpdated'}">Latest Updated</c:when>
                                        <c:otherwise>Sort By</c:otherwise>
                                    </c:choose>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-right w-100 text-center">
                                    <li><a class="dropdown-item" onclick="selectSort('default', 'Default')">Default</a></li>
                                    <li><a class="dropdown-item" onclick="selectSort('newlyCreated', 'Newly Created')">Newly Created</a></li>
                                    <li><a class="dropdown-item" onclick="selectSort('latestUpdated', 'Latest Updated')">Latest Updated</a></li>
                                </ul>
                            </div>
                        </div>

                        <div class="col-md-2" id="statusFilterContainer">
                            <div class="dropdown">
                                <button class="btn btn-outline-primary dropdown-toggle w-100 shadow-sm" type="button" id="statusFilterDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                    <c:choose>
                                        <c:when test="${param.statusFilter == 'active'}">Active</c:when>
                                        <c:when test="${param.statusFilter == 'inactive'}">Inactive</c:when>
                                        <c:otherwise>All Status</c:otherwise>
                                    </c:choose>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-right w-100 text-center">
                                    <li><a class="dropdown-item" onclick="selectStatusFilter('all', 'All Status')">All Status</a></li>
                                    <li><a class="dropdown-item" onclick="selectStatusFilter('active', 'Active')">Active</a></li>
                                    <li><a class="dropdown-item" onclick="selectStatusFilter('inactive', 'Inactive')">Inactive</a></li>
                                </ul>
                            </div>
                        </div>

                        <!-- Add this hidden input field -->
                        <input type="hidden" id="statusFilter" name="statusFilter" value="${param.statusFilter}">
                    </div>
                </form>

                <div class="tab-content">
                    <div class="form-row">
                        <div class="dimension-table">
                            <div class="table-header">
                                <h2>All Price Packages</h2>
                                <c:if test = "${account.roleId == 1}">
                                    <a href="add-package?service=addGeneral" class="add-new">Add New</a>
                                </c:if>
                            </div>
                            <c:choose>
                                <c:when test="${not empty packList}">
                                    <table id = "pList">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Subject Name</th>
                                                <th>Package name</th>
                                                <th>Status</th>
                                                <th>Create Date</th>
                                                <th>Update Date</th>
                                                <th>Last updated by</th>
                                                <th style="text-align: center">View Subject Lesson</th>

                                                <c:if test = "${account.roleId == 1 }">
                                                    <th>Action</th>
                                                    </c:if>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${packList}" var="d" varStatus="loop">
                                                <tr>

                                                    <td>${d.packageId}</td>
                                                    <td class ="ref"><a href="subject-detail-control?subjectId=${d.subjectId}">${d.subjectName}</a></td>
                                                    <td class ="ref"><a href="edit-package?packageId=${d.packageId}&service=viewPackage">${d.packageName}</a></td>
                                                    <td class = "btn-secondary" 
                                                        <c:choose>
                                                            <c:when test = "${d.status == 0}">
                                                                style="color:red; font-weight: 600"> Inactive
                                                            </c:when>

                                                            <c:otherwise>
                                                                style="color:green; font-weight: 600"> Active
                                                            </c:otherwise>
                                                        </c:choose>

                                                    </td>   
                                                    <td>${d.createdDate}</td>   
                                                    <td>${d.updateDate}</td>
                                                    <td>${d.updatedBy.fullname}</td>
                                                    <td style="text-align: center">                                       
                                                        <a href="subject-lesson?packageId=${d.packageId}&subjectId=${d.subjectId}">View Subject Lesson for ${d.subjectName}</a>
                                                    </td>
                                                    <c:if test = "${account.roleId == 1}">
                                                        <td>
                                                            <a href="edit-package?packageId=${d.packageId}&service=viewPackage">Edit</a>
                                                        </td>
                                                    </c:if>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <p>Nothing to show here...</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <!-- Pagination -->
                <div class="container py-3 justify-content-center">
                    <nav aria-label="Package pagination">
                        <ul class="pagination d-flex justify-content-center">
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=1&searchName=${param.searchName}&sortBy=${param.sortBy}&statusFilter=${param.statusFilter}">&lt;&lt;</a>
                                </li>
                            </c:if>
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentPage - 1}&searchName=${param.searchName}&sortBy=${param.sortBy}&statusFilter=${param.statusFilter}">&lt;</a>
                                </li>
                            </c:if>
                            <c:forEach begin="1" end="${totalPages}" var="page">
                                <li class="page-item ${currentPage == page ? 'active' : ''}">
                                    <a class="page-link" href="?page=${page}&searchName=${param.searchName}&sortBy=${param.sortBy}&statusFilter=${param.statusFilter}">${page}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentPage + 1}&searchName=${param.searchName}&sortBy=${param.sortBy}&statusFilter=${param.statusFilter}">&gt;</a>
                                </li>
                            </c:if>
                            <c:if test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${totalPages}&searchName=${param.searchName}&sortBy=${param.sortBy}&statusFilter=${param.statusFilter}">&gt;&gt;</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <%@include file="footer.jsp"%>
        <script src="resources/js/package_list.js"></script>
    </body>
</html>
