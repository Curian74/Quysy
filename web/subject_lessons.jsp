<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="Model.Package"%>
<%@page import="Model.Lesson"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${subject.subjectName} - Lessons</title>
        <link rel="stylesheet" href="vendors/datatables/datatables.min.css">
        <link rel="stylesheet" href="resources/css/lessons.css">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="vendors/datatables/datatables.min.js"></script>
        <script>
            function confirmChange() {
                return confirm("Are you sure you want to save changes?");
            }

            function confirmClear() {
                return confirm("Are you sure you want to clear all filters?");
            }

            $(document).ready(function () {
                var allLessons = [
            <c:forEach var="lesson" items="${allLessons}" varStatus="status">
                {id: "${lesson.lessonId}", order: "${lesson.lessonOrder}", name: "${lesson.lessonName}", type: "${lesson.lessonTypeId}"}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                ];

                var users = [
            <c:forEach var="user" items="${users}" varStatus="status">
                {id: "${user.userId}", name: "${user.fullname}"}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                ];

                function setupSearchDropdown(inputId, resultsId, items, isOrder) {
                    $('#' + inputId).on('input', function () {
                        var searchText = $(this).val().toLowerCase().trim();
                        var matchingItems = items.filter(function (item) {
                            if (isOrder) {
                                return (item.order.toString().includes(searchText) || item.name.toLowerCase().includes(searchText)) && item.type === "1";
                            }
                            return item.name.toLowerCase().includes(searchText);
                        });
                        displayResults(matchingItems, false, resultsId, isOrder);
                    });

                    $('#' + resultsId).on('click', 'div:not(.show-all)', function () {
                        var selectedItem = $(this).text();
                        var selectedId = $(this).data('id');
                        var selectedOrder = $(this).data('order');

                        if (isOrder) {
                            $('input[name="lessonOrder"]').val(selectedOrder);
                        } else {
                            $('#' + inputId).val(selectedItem);
                            $('input[name="' + inputId + '"]').val(selectedId);
                        }

                        $('#' + resultsId).hide();
                    });
                }

                function displayResults(matchingItems, showAll, containerId, isOrder) {
                    var resultsHtml = '';
                    var displayCount = showAll ? matchingItems.length : Math.min(5, matchingItems.length);

                    for (var i = 0; i < displayCount; i++) {
                        if (isOrder) {
                            resultsHtml += '<div data-order="' + matchingItems[i].order + '">' + matchingItems[i].name + ' (Order: ' + matchingItems[i].order + ')</div>';
                        } else {
                            resultsHtml += '<div data-id="' + matchingItems[i].id + '">' + matchingItems[i].name + '</div>';
                        }
                    }

                    if (!showAll && matchingItems.length > 5) {
                        resultsHtml += '<div class="show-all">Show all results...</div>';
                    }

                    $('#' + containerId).html(resultsHtml).show();
                }

                setupSearchDropdown('lessonOrderSearch', 'orderSearchResults', allLessons, true);
                setupSearchDropdown('lessonSearch', 'searchResults', allLessons, false);
                setupSearchDropdown('userSearch', 'userSearchResults', users, false);

                $('.btn-outline-secondary').on('click', function () {
                    var inputId = $(this).prev().attr('id');
                    $('#' + inputId).val('');
                    $('#' + inputId.replace('Search', 'SearchResults')).hide();
                    if (inputId !== 'lessonOrderSearch') {
                        $('input[name="' + inputId + '"]').val('');
                    }
                });
                function openModal() {
                    document.getElementById("myModal").style.display = "block";
                }

                function closeModal() {
                    document.getElementById("myModal").style.display = "none";
                }

                window.onclick = function (event) {
                    if (event.target == document.getElementById("myModal")) {
                        document.getElementById("myModal").style.display = "none";
                    }
                }

                $(document).ready(function () {

                    $('#openModalButton').on('click', openModal);
                });

                $(document).on('click', function (e) {
                    if (!$(e.target).closest('.search-container').length) {
                        $('.search-results').hide();
                    }
                });
            });
        </script>
    </head>
    <body>
        <c:set var="u" value="${requestScope.user}"/>
        <c:set var="sessionUser" value="${sessionScope.account}"/>
        <div class="container mt-4">
            <h1 style="text-align: center;">Subject Lessons</h1>
            <div class="container mt-4">
                <h3>Subject: ${subject.subjectName}</h3>
                <p>Brief: ${subject.briefInfo}</p>
            </div>
            <div class="filter-container">
                <form action="subject-lesson" method="get">
                    <input type="hidden" name="subjectId" value="${subject.subjectId}">
                    <input type="hidden" name="packageId" value="${param.packageId}">

                    <div class="row">
                        <div class="col-md-4">
                            <label for="lessonOrderSearch">Lesson Group:</label>
                            <div class="search-container">
                                <div class="input-group">
                                    <input type="text" id="lessonOrderSearch" name="lessonOrder" class="form-control" placeholder="Search by order..." value="${param.lessonOrder}">
                                    <button id="clearOrderSearch" class="btn btn-outline-secondary" type="button">Clear</button>
                                </div>
                                <div id="orderSearchResults" class="search-results"></div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <label for="lessonSearch">All Lessons:</label>
                            <div class="search-container">
                                <div class="input-group">
                                    <input type="text" id="lessonSearch" name="lessonName" class="form-control" placeholder="Search lesson..." value="${param.lessonName}">
                                    <button id="clearSearch" class="btn btn-outline-secondary" type="button">Clear</button>
                                </div>
                                <div id="searchResults" class="search-results"></div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <label for="userSearch">Creator:</label>
                            <div class="search-container">
                                <div class="input-group">
                                    <input type="text" id="userSearch" name="updatedBy" class="form-control" placeholder="Search user..." value="${param.updatedBy}">
                                    <button id="clearUserSearch" class="btn btn-outline-secondary" type="button">Clear</button>
                                </div>
                                <div id="userSearchResults" class="search-results"></div>
                            </div>
                        </div>

                    </div>

                    <div class="row mt-3">
                        <div class="col-md-4">
                            <label for="lessonType">Lesson Type:</label>
                            <select name="lessonType" id="lessonType" class="form-control">
                                <option value="">All Lesson Types</option>
                                <c:forEach var="type" items="${lessonTypes}">
                                    <option value="${type.lessonTypeId}" ${param.lessonType eq type.lessonTypeId ? 'selected' : ''}>${type.lessonTypeName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-4">
                            <label for="lessonTopic">Lesson Topic:</label>
                            <select name="lessonTopic" id="lessonTopic" class="form-control">
                                <option value="">All Topics</option>
                                <c:forEach var="topic" items="${lessonTopics}">
                                    <option value="${topic.lessonTopicId}" ${param.lessonTopic eq topic.lessonTopicId ? 'selected' : ''}>${topic.lessonTopicName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row mt-3">
                        <div class="col-md-4">
                            <label for="sortBy">Sort By:</label>
                            <select name="sortBy" id="sortBy" class="form-control">
                                <option value="updated_date" ${param.sortBy eq 'updated_date' ? 'selected' : ''}>Updated Date</option>
                                <option value="created_date" ${param.sortBy eq 'created_date' ? 'selected' : ''}>Created Date</option>
                            </select>
                        </div>

                        <div class="col-md-4">
                            <label for="sortOrder">Sort Order:</label>
                            <select name="sortOrder" id="sortOrder" class="form-control">
                                <option value="DESC" ${param.sortOrder eq 'DESC' ? 'selected' : ''}>Descending</option>
                                <option value="ASC" ${param.sortOrder eq 'ASC' ? 'selected' : ''}>Ascending</option>
                            </select>
                        </div>
                    </div>

                    <div class="row mt-3">
                        <div class="col-md-4">
                            <label for="dateFrom">From Date:</label>
                            <input type="date" id="dateFrom" name="dateFrom" class="form-control" value="${param.dateFrom}">
                        </div>

                        <div class="col-md-4">
                            <label for="dateTo">To Date:</label>
                            <input type="date" id="dateTo" name="dateTo" class="form-control" value="${param.dateTo}">
                        </div>
                    </div>

                    <div class="row mt-3">
                        <div class="col-md-12">
                            <input type="submit" value="Filter and Sort" class="btn btn-primary">
                            <a href="subject-lesson?packageId=${param.packageId}&subjectId=${subject.subjectId}" class="btn btn-secondary" onclick="return confirmClear();">Clear all</a>
                            <a href="subject-detail-control?subjectId=${param.subjectId}" class="btn btn-secondary" onclick="return confirmReturn();">Back</a>                    

                        </div>
                    </div>
                </form>
            </div>

            <button id="openModalButton" type="button" class="btn btn-info ml-2">Note</button>


            <c:if test="${not empty sessionScope.statusMessage}">
                <div class="alert alert-success alert-dismissible fade show mt-3" role="alert">
                    ${sessionScope.statusMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("statusMessage"); %>
            </c:if>

            <c:forEach var="pkg" items="${packages}">
                <div class="package-container mt-4">
                    <div class="row">
                        <div class="col-10">
                            <h4>Package: ${pkg.packageName}</h4>
                        </div>
                        <div class="col-2">
                            <a href="new-lesson?packageId=${param.packageId}&subjectId=${subject.subjectId}" class="btn btn-primary">New Lesson</a>
                        </div>
                    </div>
                    <table class="table mt-3">
                        <thead>
                            <tr>
                                <th style="text-align: center;">ID</th>
                                <th style="text-align: center; ">Lesson Name</th>
                                <th style="text-align: center; ">Order</th>
                                <th style="text-align: center; ">Type</th>
                                <th style="text-align: center; ">Topic</th>
                                <th style="text-align: center; ">Status</th>
                                <th style="text-align: center; ">Updated by</th>
                                <th style="text-align: center; ">Quiz</th>
                                <th style="text-align: center; ">Created Date</th>
                                <th style="text-align: center; ">Updated Date</th>
                                    <c:if test = "${sessionUser.roleId == 1 }">
                                    <th style="text-align: center; width: 2%;">Action</th>
                                    </c:if>

                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="lesson" items="${packageLessons[pkg.packageId]}">
                                <tr class="lesson-row" data-order="${lesson.lessonOrder}" data-type="${lesson.lessonTypeId}">
                                    <td style="text-align: center;">${lesson.lessonId}</td>
                                    <td style="text-align: center;">
                                        <c:choose>
                                            <c:when test="${lesson.lessonTypeId != 1}">
                                                <a href="lesson-details?lessonId=${lesson.lessonId}" style="font-style: italic; font-size: 16px ">${lesson.lessonName}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="lesson-details?lessonId=${lesson.lessonId}" style="font-weight: bold; font-size: 18px">${lesson.lessonName}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td style="text-align: center;">${lesson.lessonOrder}</td>
                                    <td style="text-align: center;">${lesson.lessonTypeName}</td>
                                    <td style="text-align: center;">${lesson.topicName}</td>
                                    <td style="text-align: center;">
                                        <a href="subject-lesson?service=toggleStatus&lessonId=${lesson.lessonId}&subjectId=${subject.subjectId}&packageId=${param.packageId}&filterOrder=${filterOrder}&page=${currentPage}" class="btn btn-sm btn-secondary" onclick="return confirmChange();">
                                            ${lesson.status ? 'Activate' : 'Inactivate'}
                                        </a>
                                    </td >
                                    <td style="text-align: center;">${lesson.updatedByName}</td>
                                    <td style="text-align: center;">
                                        <c:choose>
                                            <c:when test="${lesson.lessonTypeId == 1 || lesson.lessonTypeId == 2}">
                                                None
                                            </c:when>
                                            <c:otherwise>
                                                ${lesson.quizName}
                                            </c:otherwise>
                                        </c:choose>
                                    </td> 
                                    <td style="text-align: center;"><fmt:formatDate value="${lesson.createdDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td style="text-align: center;"><fmt:formatDate value="${lesson.updatedDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td style="text-align: center;">
                                        <c:if test = "${sessionUser.roleId == 1 }">
                                            <a href="subject-lesson?service=deleteLesson&lessonId=${lesson.lessonId}&subjectId=${subject.subjectId}&packageId=${packageId}&page=${currentPage}&filterOrder=${filterOrder}" onclick="return confirm('Are you sure you want to delete this lesson?');"  class="btn btn-sm btn-danger">Delete</a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:forEach>

            <c:if test="${totalPages > 1}">
                <c:url value="subject-lesson" var="paginationUrl">
                    <c:param name="subjectId" value="${subject.subjectId}"/>
                    <c:param name="packageId" value="${param.packageId}"/>
                    <c:param name="lessonName" value="${param.lessonName}"/>
                    <c:param name="lessonType" value="${param.lessonType}"/>
                    <c:param name="lessonTopic" value="${param.lessonTopic}"/>
                    <c:param name="updatedBy" value="${param.updatedBy}"/>
                    <c:param name="sortBy" value="${param.sortBy}"/>
                    <c:param name="sortOrder" value="${param.sortOrder}"/>
                    <c:param name="dateFrom" value="${param.dateFrom}"/>
                    <c:param name="dateTo" value="${param.dateTo}"/>
                    <c:param name="lessonOrder" value="${param.lessonOrder}"/>
                </c:url>

                <nav class="mt-3">
                    <ul class="pagination">
                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                            <a class="page-link" href="${paginationUrl}&page=1"><<</a>
                        </li>
                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                            <a class="page-link" href="${paginationUrl}&page=${currentPage - 1}"><</a>
                        </li>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${currentPage eq i ? 'active' : ''}">
                                <a class="page-link" href="${paginationUrl}&page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="${paginationUrl}&page=${currentPage + 1}">></a>
                        </li>
                        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="${paginationUrl}&page=${totalPages}">>></a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </div>
        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h3 style="text-align: center">Notes of Subject Lessons</h3><br>
                <ul>
                    <li>If you want to visit Lesson Details of a lesson, click to Lesson Name of the lesson that you wanted.</li>
                    <li>You can change status of a lesson by click on the Status of lesson that you needed</li>
                    <li>Lesson Group is decided by Lesson Order:</li>
                    <li>Lesson have Lesson Type Subject Topic will be the head of a lesson group</li>                   
                </ul>
            </div>
        </div>
    </body>
</html>
<%@include file="footer.jsp" %>