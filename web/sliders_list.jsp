<%@ page import="Model.Slider" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sliders List</title>
        <link rel="stylesheet" href="resources/css/sliderlist.css">
        <script src="resources/js/slider.js"></script>
        <link href="vendors/bootstrap/bootstrap.min.css" rel="stylesheet">
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
    </head>
    <body>
        <c:set var="u" value="${requestScope.user}"/>
        <c:set var="sessionUser" value="${sessionScope.account}"/>
        <c:choose>
            <c:when test="${sessionUser.roleId == 4}">            
                <div class="container">

                    <br><h1>Sliders List</h1><br>
                    <!-- Search Form -->
                    <div style="text-align: center">
                        <% if (session.getAttribute("message") != null) { %>
                        <div class="alert ${sessionScope.message.contains('failed') ? 'alert-danger' : 'alert-success'} alert-dismissible fade show p-3" role="alert" style="max-width: 400px; margin: auto;">
                            <%= session.getAttribute("message") %>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                        <% session.removeAttribute("message"); %>
                        <% } %>
                    </div><br>
                    <form action="sliders-list" method="get" class="form-inline mb-4" id="slidersForm" >
                        <input type="hidden" name="service" value="listAllSliders">
                        <div class="row">
                            <div class="col-md-4">
                                <input type="text" class="form-control" id="searchTitle" name="searchTitle" value="${searchTitle}" style="text-align: center;" placeholder="Search by Title">
                            </div>
                            <div class="col-md-4">
                                <input type="text" class="form-control" id="searchLink" name="searchLink" value="${searchLink}" style="text-align: center;" placeholder="Search by Link">
                            </div>
                            <div class="col-md-2">
                                <div class="dropdown">
                                    <button class="btn btn-outline-primary dropdown-toggle w-100 shadow-sm" type="button" id="statusDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                        Status
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-right w-100" aria-labelledby="statusDropdown">
                                        <li><a style="text-align: center;" class="dropdown-item" onclick="selectStatus('', 'All')">All</a></li>
                                        <li><a style="text-align: center;" class="dropdown-item" onclick="selectStatus('true', 'Show')">Show</a></li>
                                        <li><a style="text-align: center;" class="dropdown-item" onclick="selectStatus('false', 'Hide')">Hide</a></li>
                                    </ul>
                                    <select name="status" id="status" class="d-none">
                                        <option value="">Status</option>
                                        <option value="true" ${status == 'true' ? 'selected' : ''}>Show</option>
                                        <option value="false" ${status == 'false' ? 'selected' : ''}>Hide</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary">Search</button>
                                    <button type="button" class="btn btn-success" onclick="openListPopup()">Add New</button>
                                </div>
                            </div>
                        </div>
                    </form>


                    <!-- Sliders Table -->
                    <table class="table table-bordered ">
                        <thead>
                            <tr>
                                <th style="text-align: center;" class="col-id"  >#</th>
                                <th style="text-align: center;" class="col-id" >ID</th>
                                <th style="text-align: center;" class="col-title">Title</th>
                                <th style="text-align: center;" class="col-sl-img">Image</th>
                                <th style="text-align: center;" class="col-link">Backlink</th>
                                <th style="text-align: center;" class="col-status">Status</th>
                                <th style="text-align: center;" class="col-action">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty sliders}">
                                    <c:forEach var="slider" varStatus="loop" items="${sliders}">
                                        <tr>
                                            <td style="text-align: center;">${loop.index + 1}</td>
                                            <td style="text-align: center;">${slider.sliderId}</td>
                                            <td class="text-truncate" style="text-align: center;">${slider.title}</td>
                                            <td style="text-align: center;">
                                                <img src="${slider.sliderThumbnail}" alt="Slider Thumbnail" width="100" height="60">
                                            </td>
                                            <td class="text-truncate" style="text-align: center;">
                                                <a href="${slider.backlink}" target="_blank">${slider.backlink}</a>
                                            </td>
                                            <td style="text-align: center;">
                                                <form action="sliders-list" method="post">
                                                    <input type="hidden" name="service" value="updateSliderStatus">
                                                    <input type="hidden" name="sliderId" value="${slider.sliderId}">
                                                    <div class="dropdown">
                                                        <button class="btn btn-outline-primary dropdown-toggle w-100 shadow-sm" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                            ${slider.status ? 'Show' : 'Hide'}
                                                        </button>
                                                        <ul class="dropdown-menu dropdown-menu-right w-100" aria-labelledby="statusDropdown">
                                                            <li>
                                                                <button type="submit" class="dropdown-item" name="newStatus" value="true">Show</button>
                                                            </li>
                                                            <li>
                                                                <button type="submit" class="dropdown-item" name="newStatus" value="false">Hide</button>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </form>

                                            </td>                        
                                            <td class="action-links" style="text-align: center;">
                                                <a href="slider-details?id=${slider.sliderId}">Detail</a>
                                                <form action="sliders-list" method="post" style="display: inline;">
                                                    <input type="hidden" name="service" value="deleteSlider">
                                                    <input type="hidden" name="sliderId" value="${slider.sliderId}">
                                                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this slider?')">Delete</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6" class="text-center">No sliders found.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>


                    <!-- Pagination Links -->
                    <div class="container py-3 justify-content-center">
                        <nav aria-label="Sliders pagination">
                            <ul class="pagination d-flex justify-content-center">
                                <!-- First page link -->
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=1&searchTitle=${searchTitle}&searchLink=${searchLink}&status=${status}">&lt;&lt;</a>
                                    </li>
                                </c:if>
                                <!-- Previous page link -->
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentPage - 1}&searchTitle=${searchTitle}&searchLink=${searchLink}&status=${status}">&lt;</a>
                                    </li>
                                </c:if>
                                <!-- Page numbers -->
                                <c:forEach begin="1" end="${totalPages}" var="page">
                                    <li class="page-item ${currentPage == page ? 'active' : ''}">
                                        <a class="page-link" href="?page=${page}&searchTitle=${searchTitle}&searchLink=${searchLink}&status=${status}">${page}</a>
                                    </li>
                                </c:forEach>
                                <!-- Next page link -->
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentPage + 1}&searchTitle=${searchTitle}&searchLink=${searchLink}&status=${status}">&gt;</a>
                                    </li>
                                </c:if>
                                <!-- Last page link -->
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${totalPages}&searchTitle=${searchTitle}&searchLink=${searchLink}&status=${status}">&gt;&gt;</a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </div>
                </div>
                <div id="addSliderPopup" class="sl-popup">
                    <div class="sl-popup-content">
                        <span class="close" onclick="closeListPopup()">&times;</span>
                        <h2 class="popup-title">Add New Slider</h2>
                        <form action="add-newslider" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                            <div class="group" style="text-align: center;">
                                <img id="thumbnailPreview" src="${slider.sliderThumbnail}" alt=" " width="300" height="180"><br>
                                <input type="file" name="thumbnail" id="thumbnail" accept="image/*" onchange="previewImage()" ${slider.sliderThumbnail}>
                            </div>
                            <div class="group">
                                <label for="title">Title:</label>
                                <input type="text" id="title" name="title" required>
                            </div>
                            <div class="group">
                                <label for="backlink">Backlink:</label>
                                <input type="text" id="backlink" name="backlink" required>
                            </div>
                            <div class="group">
                                <label for="status">Status:</label>
                                <select id="status" name="status">
                                    <option value="true">Show</option>
                                    <option value="false">Hide</option>
                                </select>
                            </div>
                            <div class="group">
                                <label for="notes">Notes:</label>
                                <textarea id="notes" name="notes"></textarea>
                            </div>
                            <div class="group submit-button">
                                <input type="submit" value="Add Slider">
                                <input type="submit" onclick="cancelForm()" class="cancel-button" value="Cancel">
                            </div>
                        </form>
                    </div>
                </div>

            </c:when>
            <c:otherwise>
                <div class="container">
                    <h1 style="font-size: 64px; color: red; margin-top: 140px; margin-bottom: 136px;">You have no permission to visit this page</h1>
                </div>
                <script src="resources/js/permission_return.js"></script>

            </c:otherwise>
        </c:choose>  
    </body>
</html>
<%@ include file="footer.jsp" %>