<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dimension Types</title>
        <link rel="stylesheet" href="resources/css/dimension_list.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="card-header">
                <h1>Dimension Types List</h1>
            </div>
            <div class="card-body">
                <div class="tabs">
                    <div class="tab active">Dimension Type</div>
                    <button type="button" class="tab" onclick="window.location.href = 'dimension-control'">Dimension Name</button>
                </div>

                <!-- Search and Sort Form -->
                <form id="searchSortForm" action="dimension-type" method="get">
                    <input type="hidden" id="submitType" name="submitType" value="search">
                    <input type="hidden" id="sortBy" name="sortBy" value="${param.sortBy}">
                    <div class="row" id="dimSearch" style="margin-top: 25px;">
                        <div class="col-md-5 position-relative" id="searchContainer">
                            <input type="text" class="form-control shadow-sm text-center" id="searchName" name="searchName" value="${param.searchName}"
                                   placeholder="Search by dimension type name...">
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
                    </div>
                </form>

                <div class="tab-content">
                    <div class="form-row">
                        <div class="dimension-table">
                            <div class="table-header">
                                <h2>All Dimensions Type</h2>
                                <a href="new-dimension?service=dimType"" class="add-new">Add New</a>
                            </div>
                            <c:choose>
                                <c:when test="${not empty typeList}">
                                    <table class = "typeTable">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Type</th>
                                                <th>Create Date</th>
                                                <th>Update Date</th>
                                                <th>Last updated by</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${typeList}" var="d" varStatus="loop">
                                                <tr>
                                                    <td>${d.dimensionTypeId}</td>
                                                    <td><a href="dimension-edit?typeId=${d.dimensionTypeId}&service=dimType">${d.dimensionTypeName}</a></td>
                                                    <td>${d.createDate}</td>
                                                    <td>${d.updateDate}</td>
                                                    <td>${d.updatedBy.fullname}</td>
                                                    <td>
                                                        <a href="dimension-edit?typeId=${d.dimensionTypeId}&service=dimType">Edit</a>
                                                    </td>
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
                    <nav aria-label="Dimension Types pagination">
                        <ul class="pagination d-flex justify-content-center">
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=1&searchName=${param.searchName}&sortBy=${param.sortBy}">&lt;&lt;</a>
                                </li>
                            </c:if>
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentPage - 1}&searchName=${param.searchName}&sortBy=${param.sortBy}">&lt;</a>
                                </li>
                            </c:if>
                            <c:forEach begin="1" end="${totalPages}" var="page">
                                <li class="page-item ${currentPage == page ? 'active' : ''}">
                                    <a class="page-link" href="?page=${page}&searchName=${param.searchName}&sortBy=${param.sortBy}">${page}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentPage + 1}&searchName=${param.searchName}&sortBy=${param.sortBy}">&gt;</a>
                                </li>
                            </c:if>
                            <c:if test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${totalPages}&searchName=${param.searchName}&sortBy=${param.sortBy}">&gt;&gt;</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <%@include file="footer.jsp"%>
        <script src = "resources/js/dimension_type.js"></script>
    </body>
</html>
