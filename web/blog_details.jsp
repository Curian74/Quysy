<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Get the blog detail for the title -->
        <c:set var="blog" value="${requestScope.blog}"/>
        <title>Quysy | ${blog.blogTitle}</title>
        <link href="vendors/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <!-- Get the author and blog -->
        <c:set var="u" value="${requestScope.user}"/>
        <c:set var="sessionUser" value="${sessionScope.account}"/>

        <!-- Main page -->
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
                                    <button type="submit" class="btn btn-primary shadow-sm">Search</button>
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
                    <div class="container w-auto">
                        <!-- Blog's title -->
                        <h2 class="text-center">${blog.blogTitle}</h2>

                        <div class="d-flex justify-content-between">
                            <!-- Blog's author -->
                            <p><b>Author:</b> ${u.fullname}</p>

                            <!-- Updated date -->
                            <p class="text-muted">Last updated: ${blog.blogUpdatedDate}</p>
                        </div>

                        <!-- Blog's thumbnail -->
                        <c:choose>
                            <c:when test="${empty blog.blogThumbnail}">
                                <img src="path/to/default-image.jpg" class="mx-auto d-block my-4 img-fluid shadow-sm" alt="No image found">
                            </c:when>
                            <c:otherwise>
                                <img src="${blog.blogThumbnail}" class="mx-auto d-block my-4 img-fluid shadow-sm" alt="${blog.blogTitle}">
                            </c:otherwise>
                        </c:choose>

                        <c:forEach var="bc" items="${requestScope.blogContent}">
                            <p class="my-3">${bc.content}</p>
                        </c:forEach>

                        <!-- Form to edit blog post for Marketing role -->
                        <c:if test="${sessionUser.roleId == 4 || sessionUser.roleId == 1}">
                            <br>
                            <button type="button" class="btn btn-primary" onclick="toggleEditForm()">Edit This Post</button>
                            <form id="editForm" action="blog-details" method="POST" style="display:none;" onsubmit="return validateForm();">
                                <br>
                                <h2>Edit Post:</h2>
                                <input type="hidden" name="blogId" value="${blog.blogID}">
                                <div class="mb-3">
                                    <label for="blogTitle" class="form-label">Title</label>
                                    <input type="text" class="form-control" id="blogTitle" name="blogTitle" value="${blog.blogTitle}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="blogThumbnail" class="form-label">Thumbnail</label>
                                    <input type="text" class="form-control" id="blogThumbnail" name="blogThumbnail" value="${blog.blogThumbnail}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="briefInfo" class="form-label">Brief Info</label>
                                    <textarea class="form-control" id="briefInfo" name="briefInfo" required>${blog.briefInfo}</textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="categoryId" class="form-label">Category</label>
                                    <select class="form-select" id="categoryId" name="categoryId" required>
                                        <c:forEach var="category" items="${categoryList}">
                                            <option value="${category.categoryId}" <c:if test="${category.categoryId == blog.categoryId}">selected</c:if>>${category.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="status" class="form-label">Status</label>
                                    <select class="form-select" id="status" name="status" required>
                                        <option value="1" <c:if test="${blog.status == 1}">selected</c:if>>Active</option>
                                        <option value="0" <c:if test="${blog.status == 0}">selected</c:if>>Inactive</option>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label for="is_featured" class="form-label">Is Featured</label>
                                        <select class="form-select" id="is_featured" name="is_featured" required>
                                            <option value="1" <c:if test="${blog.isFeatured == 1}">selected</c:if>>Yes</option>
                                        <option value="0" <c:if test="${blog.isFeatured == 0}">selected</c:if>>No</option>
                                        </select>
                                    </div>

                                    <div class="mb-3">
                                        <label for="combinedContent" class="form-label">Content</label>
                                        <textarea class="form-control" id="combinedContent" name="combinedContent" rows="10" required>${combinedContent}</textarea>
                                </div>
                                <button type="submit" name="action" value="edit" class="btn btn-warning">Save Changes</button>
                                <button type="submit" name="action" value="delete" class="btn btn-danger" onclick="return confirmDelete();">Delete</button>
                            </form>
                        </c:if>
                    </div>
                </div>

            </div>
        </div>
    </body>
    <script>
        function confirmDelete() {
            return confirm("Are you sure you want to delete this blog post?");
        }

        function toggleEditForm() {
            var editForm = document.getElementById('editForm');
            if (editForm.style.display === 'none' || editForm.style.display === '') {
                editForm.style.display = 'block';
            } else {
                editForm.style.display = 'none';
            }
        }
        function confirmChange() {
            return confirm("Are you sure you want to save changes?");
        }
        function validateForm() {
            let isValid = true;
            let formElements = document.querySelectorAll('#editForm input[type="text"], #editForm textarea, #editForm select');

            formElements.forEach(element => {
                if (element.value.trim() === "") {
                    element.value = "";
                    isValid = false;
                }
            });

            return isValid;
        }
    </script>
</html>
<%@include file="footer.jsp" %>
