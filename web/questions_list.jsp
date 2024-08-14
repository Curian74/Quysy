<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | Questions List</title>
        <link rel="stylesheet" href="vendors/datatables/datatables.min.css">
        <link rel="stylesheet" href="resources/css/sticky_footer.css"/>
    </head>
    <body>
        <div class="flex-column">
            <div class="content">
                <div class="container">
                    <h1 class="display-3 fw-bold text-center my-3">Questions List</h1>

                    <h2 class="fw-bold mt-3">Question Filters</h2>
                    <div class="d-flex">

                        <div id="subjectFilter" class="dropdown me-3">
                            <button class="btn btn-outline-primary dropdown-toggle" type="button" id="subjectFilterButton" data-bs-toggle="dropdown" aria-expanded="false">
                                Subjects
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="subjectFilterButton">
                                <c:forEach var="s" items="${requestScope.subjectList}">
                                    <li><a class="dropdown-item filter-button" data-filter-type="subject" data-filter-id="${s.subjectId}" data-subject-id="${s.subjectId}" href="#">${s.subjectName}</a></li>
                                    </c:forEach>
                            </ul>
                        </div>

                        <div id="lessonFilter" class="dropdown me-3">
                            <button class="btn btn-outline-primary dropdown-toggle" type="button" id="lessonFilterButton" data-bs-toggle="dropdown" aria-expanded="false">
                                Lessons
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="lessonFilterButton">
                                <c:forEach var="ls" items="${requestScope.lessonList}">
                                    <li><a class="dropdown-item filter-button" data-filter-type="lesson" data-filter-id="${ls.lessonId}" data-lesson-id="${ls.lessonId}" href="#">${ls.lessonName}</a></li>
                                    </c:forEach>
                            </ul>
                        </div>

                        <div id="dimensionFilter" class="dropdown me-3">
                            <button class="btn btn-outline-primary dropdown-toggle" type="button" id="dimensionFilterButton" data-bs-toggle="dropdown" aria-expanded="false">
                                Dimensions
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dimensionFilterButton">
                                <c:forEach var="d" items="${requestScope.dimensionList}">
                                    <li><a class="dropdown-item filter-button" data-filter-type="dimension" data-filter-id="${d.dimensionId}" data-dimension-id="${d.dimensionId}" href="#">${d.dimensionName}</a></li>
                                    </c:forEach>
                            </ul>
                        </div>

                        <div id="levelFilter" class="dropdown me-3">
                            <button class="btn btn-outline-primary dropdown-toggle" type="button" id="levelFilterButton" data-bs-toggle="dropdown" aria-expanded="false">
                                Levels
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="levelFilterButton">
                                <c:forEach var="lv" items="${requestScope.levelList}">
                                    <li><a class="dropdown-item filter-button" data-filter-type="level" data-filter-id="${lv.levelId}" data-level-id="${lv.levelId}" href="#">${lv.levelName}</a></li>
                                    </c:forEach>
                            </ul>
                        </div>

                        <a class="btn btn-primary" href="question-list">Clear Filters</a>
                    </div>

                    <c:choose>
                        <c:when test="${updateStatusMessage eq 'toggleSuccess'}">
                            <div class="alert alert-success" role="alert">
                                Question status updated successfully!
                            </div>
                        </c:when>

                        <c:when test="${updateStatusMessage eq 'toggleFail'}">
                            <div class="alert alert-danger" role="alert">
                                Question status updated unsuccessfully!
                            </div>
                        </c:when>
                    </c:choose>

                    <table id="questionList" class="table table-striped my-3 w-100">
                        <thead>
                            <tr>
                                <th class="align-middle text-center">ID</th>
                                <th class="align-middle text-center">Question Details</th>
                                <th class="align-middle text-center">Subject</th>
                                <th class="align-middle text-center">Lesson</th>
                                <th class="align-middle text-center">Dimension</th>
                                <th class="align-middle text-center">Level</th>
                                <th class="align-middle text-center">Created Date</th>
                                <th class="align-middle text-center">Updated Date</th>
                                <th class="align-middle text-center">Status</th>
                                <th class="align-middle text-center">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ql" items="${requestScope.questionList}">
                                <tr>
                                    <td class="text-center">${ql.questionId}</td>
                                    <td>${ql.questionDetail}</td>
                                    <td>${ql.subjectName}</td>
                                    <td>${ql.lessonName}</td>
                                    <td>${ql.dimensionName}</td>
                                    <td>${ql.levelName}</td>
                                    <td class="text-center">${ql.createDate}</td>
                                    <td class="text-center">${ql.updateDate}</td>
                                    <td class="${ql.isStatus() ? 'text-success text-center fw-bold' : 'text-danger text-center fw-bold'}">${ql.isStatus() ? 'Shown':'Hidden'}</td>
                                    <td class="text-center">
                                        <!-- For future question details/edit
                                        <a href="edit-question?questionId=${ql.questionId}&subjectId=${param.subjectId}&lessonId=${param.lessonId}&dimensionId=${param.dimensionId}&levelId=${param.levelId}&page=${param.page}" class="btn btn-primary btn-sm">
                                            Edit
                                        </a>
                                        -->
                                        <a href="error.jsp" class="btn btn-primary btn-sm">
                                            Edit
                                        </a>
                                        <button data-question-id="${ql.questionId}" data-status="${ql.isStatus()}" class="btn btn-sm ${ql.isStatus() ? 'btn-warning' : 'btn-success'} toggle-status-button">
                                            ${ql.isStatus() ? 'Hide' : 'Show'}
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="vendors/datatables/datatables.min.js"></script>
        <script src="resources/js/questions_list.js"></script>
    </body>
</html>
