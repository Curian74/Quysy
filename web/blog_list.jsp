<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | Blogs List</title>
    </head>
    <body>
        <div class="container">
            <div class="row">

                <!-- Sider -->
                <div class="col-3 border-end">

                    <!-- Blog search -->
                    <div class="container py-3 border-bottom">
                        <form action="blog-list">
                            <div class="mb-2">
                                <label class="form-label h2">Search for blogs</label>
                                <div class="d-flex">
                                    <input type="text" class="form-control me-2 shadow-sm" name="searchTerm" placeholder="Search for blogs">
                                    <input type="hidden" name="categoryId" value="${param.categoryId}">
                                    <button type="sumbit" class="btn btn-primary shadow-sm">Search</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- Blog categories -->
                    <div class="container py-3 border-bottom">
                        <h2>Sort by categories</h2>
                        <div class="dropdown">
                            <button class="btn btn-outline-primary dropdown-toggle w-100 shadow-sm" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Categories
                            </button>
                            <ul class="dropdown-menu w-100 text-center">
                                <li><a class="dropdown-item" href="blog-list">All categories</a></li>
                                    <c:forEach var="c" items="${requestScope.categoryList}">
                                    <li><a class="dropdown-item" href="blog-list?categoryId=${c.categoryId}<c:if test="${not empty searchTerm}">&searchTerm=${searchTerm}</c:if>">${c.categoryName}</a></li>
                                    </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <!-- Latest Blogs -->
                    <div class="container py-3">

                        <h2>Latest Blogs</h2>
                        <c:if test="${requestScope.siderBlogMessage}">
                            <div class="alert alert-primary" role="alert">
                                There's no new posts!
                            </div>
                        </c:if>
                        <c:forEach var="b" items="${requestScope.siderBlogList}">
                            <a href="blog-details?blogId=${b.blogID}" class="text-decoration-none">
                                <div class="card my-3 shadow-sm">
                                    <img src="${b.blogThumbnail}" class="card-top-img">
                                    <div class="card-body">
                                        <h5 class="card-title">${b.blogTitle}</h5>
                                        <p class="card-text">${b.briefInfo}</p>
                                    </div>
                                    <div class="card-footer text-muted"><strong>Posted:</strong> ${b.blogCreatedDate}</div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>

                    <!-- Contact card -->
                    <div class="container py-3">
                        <h3>Quiz Practicing System</h3>
                        <h4>Contact Us</h4>
                        <p>Phone: 0123456789<br>Email: quysy.quizpracticingsystem@gmail.com</p>
                    </div>
                </div>

                <!-- Main page -->
                <div class="col-9">
                    <c:if test="${not empty sessionScope.message}">
                        <div class="alert alert-success" role="alert">
                            ${sessionScope.message}
                        </div>
                        <c:remove var="message" scope="session"/>
                    </c:if>

                    <!-- Blogs list -->
                    <div class="container py-3">

                        <c:choose>
                            <c:when test="${sessionUser.roleId == 4}">
                                <c:choose>
                                    <c:when test="${status == 'active'}"> 
                                        <h1 class="text-center mb-3">Active Blogs List</h1>
                                    </c:when>
                                    <c:otherwise>
                                        <h1 class="text-center mb-3">Inactive Blogs List</h1>
                                    </c:otherwise>
                                </c:choose>                             
                            </c:when>
                            <c:otherwise>
                                <h1 class="text-center mb-3">Blogs List</h1>
                            </c:otherwise>
                        </c:choose>

                        <c:if test="${not empty requestScope.searchTerm and empty requestScope.mainBlogMessage}">
                            <div class="alert alert-info" role="alert">
                                You searched for "${requestScope.searchTerm}"
                            </div>
                        </c:if>

                        <c:if test="${requestScope.mainBlogMessage eq 'mainBlogEmpty'}">
                            <div class="alert alert-primary" role="alert">
                                The blogs list is empty!
                            </div>
                        </c:if>

                        <div class="row">
                            <c:forEach var="b" items="${requestScope.mainBlogList}">
                                <div class="col-lg-4 col-md-6 my-3">
                                    <a href="blog-details?blogId=${b.blogID}" class="text-decoration-none">
                                        <div class="card h-100 shadow-sm">
                                            <img src="${b.blogThumbnail}" class="card-top-img">
                                            <div class="card-body">
                                                <h5 class="card-title">${b.blogTitle}</h5>
                                                <p class="card-text">${b.briefInfo}</p>
                                            </div>
                                            <div class="card-footer text-muted"><strong>Last updated:</strong> ${b.blogUpdatedDate}</div>
                                        </div>
                                    </a>
                                </div>
                            </c:forEach>
                        </div>

                    </div>

                    <!-- Pagination -->
                    <c:if test="${not empty requestScope.mainBlogList}">
                        <div class="container border-top py-3 justify-content-center">
                            <nav aria-label="Blogs pagination">
                                <c:set var="page" value="${requestScope.page}" />
                                <c:set var="numberOfPages" value="${requestScope.numberOfPages}" />
                                <c:set var="categoryId" value="${param.categoryId}" />
                                <c:set var="searchTerm" value="${param.searchTerm}" />

                                <ul class="pagination d-flex justify-content-center">
                                    <!-- First page link -->
                                    <li class="page-item ${page == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="blog-list?page=1<c:if test="${not empty categoryId}">&categoryId=${categoryId}</c:if><c:if test="${not empty searchTerm}">&searchTerm=${searchTerm}</c:if>"><<<</a>
                                        </li>
                                        <!-- Previous page link -->
                                            <li class="page-item ${page == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="blog-list?page=${page - 1}<c:if test="${not empty categoryId}">&categoryId=${categoryId}</c:if><c:if test="${not empty searchTerm}">&searchTerm=${searchTerm}</c:if>"><</a>
                                        </li>

                                    <c:choose>
                                        <c:when test="${page == 1}">
                                            <c:forEach var="i" begin="1" end="${numberOfPages > 3 ? 3 : numberOfPages}">
                                                <li class="page-item ${page == i ? 'active' : ''}">
                                                    <a class="page-link" href="blog-list?page=${i}<c:if test="${not empty categoryId}">&categoryId=${categoryId}</c:if><c:if test="${not empty searchTerm}">&searchTerm=${searchTerm}</c:if>">${i}</a>
                                                    </li>
                                            </c:forEach>
                                        </c:when>
                                        <c:when test="${page == numberOfPages}">
                                            <c:forEach var="i" begin="${numberOfPages > 2 ? numberOfPages - 2 : 1}" end="${numberOfPages}">
                                                <li class="page-item ${page == i ? 'active' : ''}">
                                                    <a class="page-link" href="blog-list?page=${i}<c:if test="${not empty categoryId}">&categoryId=${categoryId}</c:if><c:if test="${not empty searchTerm}">&searchTerm=${searchTerm}</c:if>">${i}</a>
                                                    </li>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${page > 2}">
                                                <li class="page-item">
                                                    <a class="page-link">...</a>
                                                </li>
                                            </c:if>
                                            <c:forEach var="i" begin="${page - 1}" end="${page + 1}">
                                                <li class="page-item ${page == i ? 'active' : ''}">
                                                    <a class="page-link" href="blog-list?page=${i}<c:if test="${not empty categoryId}">&categoryId=${categoryId}</c:if><c:if test="${not empty searchTerm}">&searchTerm=${searchTerm}</c:if>">${i}</a>
                                                    </li>
                                            </c:forEach>
                                            <c:if test="${page < numberOfPages - 1}">
                                                <li class="page-item">
                                                    <a class="page-link">...</a>
                                                </li>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>

                                    <!-- Next page link -->
                                    <li class="page-item ${page == numberOfPages ? 'disabled' : ''}">
                                        <a class="page-link" href="blog-list?page=${page + 1}<c:if test="${not empty categoryId}">&categoryId=${categoryId}</c:if><c:if test="${not empty searchTerm}">&searchTerm=${searchTerm}</c:if>">></a>
                                        </li>
                                        <!-- Last page link -->
                                            <li class="page-item ${page == numberOfPages ? 'disabled' : ''}">
                                        <a class="page-link" href="blog-list?page=${numberOfPages}<c:if test="${not empty categoryId}">&categoryId=${categoryId}</c:if><c:if test="${not empty searchTerm}">&searchTerm=${searchTerm}</c:if>">>>></a>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                    </c:if>

                </div>
            </div>
        </div>

    </body>
</html>
<%@include file="footer.jsp" %>