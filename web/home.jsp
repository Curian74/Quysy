<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | Homepage</title>
        <link href="vendors/bootstrap/bootstrap.min.css" rel="stylesheet">
        <link href="resources/css/home.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="row">

                <!-- Sider -->
                <div class="col-3 border-end">

                    <!-- Latest Blogs -->
                    <div class="container py-3">
                        <h2>Latest Blogs</h2>
                        <c:if test="${requestScope.siderBlogMessage}">
                            <div class="alert alert-primary" role="alert">
                                There's no new posts!
                            </div>
                        </c:if>
                        <!-- For loop to display latest blogs -->
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
                        <h2>Quysy - Quiz Practicing System</h2>
                        <h4 class="mb-3">Contact Us</h4>
                        <p>Phone:0123456789</p>
                        <p>Email: quysy.quizpracticingsystem@gmail.com</p>
                    </div>

                </div>

                <!-- Main page -->
                <div class="col-9">
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">
                            ${success}
                        </div>
                    </c:if>
                    <!-- Slider -->
                    <div class="container border-bottom py-3">
                        <div id="slider" class="carousel slide shadow-sm" data-bs-ride="carousel">
                            <c:if test="${empty sliderList}">
                                <div class="alert alert-primary" role="alert">
                                    The sliders list is empty!
                                </div>
                            </c:if>
                            <div class="carousel-inner">
                                <c:forEach var="sl" items="${sliderList}" varStatus="status">
                                    <a href="${sl.backlink}" class="text-decoration-none">
                                        <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                            <img src="${sl.sliderThumbnail}" class="d-block w-100" alt="${sl.title}">
                                            <div class="carousel-caption d-none d-md-block">
                                                <h5>${sl.title}</h5>
                                                <p>${sl.notes}</p>
                                            </div>
                                        </div>
                                    </a>
                                </c:forEach>
                                <button class="carousel-control-prev" type="button" data-bs-target="#slider" data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Previous</span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#slider" data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Next</span>
                                </button>
                            </div>
                        </div>
                    </div>

                    <!-- Top Blogs Slider -->
                    <div class="container border-bottom py-3">
                        <h1 class="text-center">Hot Blogs</h1>
                        <c:if test="${requestScope.featuredBlogMessage eq 'featuredBlogEmpty'}">
                            <div class="alert alert-primary" role="alert">
                                The blogs list is empty!
                            </div>
                        </c:if>
                        <div id="blogSlider" class="carousel slide" data-bs-ride="carousel">
                            <div class="carousel-inner">
                                <!-- For loop to display top blogs in a slider -->
                                <c:forEach var="b" items="${requestScope.featuredBlogList}" varStatus="status">
                                    <a href="blog-details?blogId=${b.blogID}" class="text-decoration-none w-100 h-100">
                                        <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                            <div class="card mx-1 shadow-sm">
                                                <div class="img-wrapper w-100">
                                                    <img src="${b.blogThumbnail}" class="card-top-img w-100 h-100">
                                                </div>
                                                <div class="card-body">
                                                    <h5 class="card-title">${b.blogTitle}</h5>
                                                    <p class="card-text">${b.briefInfo}</p>
                                                </div>
                                                <div class="card-footer text-muted"><strong>Last updated:</strong> ${b.blogUpdatedDate}</div>
                                            </div>
                                        </div>
                                    </a>
                                </c:forEach>

                                <!-- Buttons for slider navigation -->
                                <button class="carousel-control-prev" type="button" data-bs-target="#blogSlider" data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Previous</span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#blogSlider" data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Next</span>
                                </button>
                            </div>
                        </div>
                    </div>

                    <!-- Featured Subjects -->
                    <div class="container border-bottom py-3">
                        <h1 class="text-center">Featured Subjects</h1>
                        <div class="row">
                            <!-- For loop to display featured subjects -->
                            <c:forEach var="s" items="${requestScope.featuredSubjectList}">
                                <div class="col-lg-4 col-md-6 my-3">
                                    <div class="card h-100 shadow-sm">
                                        <img src="${s.thumbnail}" class="card-top-img">
                                        <div class="card-body">
                                            <h5 class="card-title">${s.subjectName}</h5>
                                            <p class="card-text">${s.briefInfo}</p>
                                            <div class="d-flex justify-content-center">
                                                <a href="subjectdetails?subjectId=${s.subjectId}" class="btn btn-primary text-center">Go to subject</a>
                                            </div>
                                        </div>
                                        <div class="card-footer text-muted">${s.tagLine}</div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <script src="resources/js/home.js"></script>
    </body>
</html>
<%@include file="footer.jsp" %>
