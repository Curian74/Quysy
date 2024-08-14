<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resources/css/quizzes_list.css"/>
        <link rel="stylesheet" href="vendors/select2/css/select2.min.css"/>
        <link rel="stylesheet" href="vendors/datatables/datatables.min.css">
        <link href="vendors/bootstrap/bootstrap.min.css" rel="stylesheet">
        <title>Quysy | Deleted Quizzes List</title>
    </head>
    <body>
        <div class="container">
            <h1 class="display-5 fw-bold text-center mb-3">Deleted Quizzes List</h1>
            <div class="filter-box">
                <form class="filter" action="deleted-quizzes-list" method="get">
                    <label>Search for Quizzes:
                        <div class="input-group" id="quiz-drop">
                            <select name="searchQuizName" class="form-control" id="quizzes" >
                                <option value="" selected disabled>Quiz Name</option>
                                <c:forEach items="${allQuizzes}" var="q">
                                    <option>
                                        ${q.quizName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </label>
                    <label>Subject:
                        <div class="input-group" id="subject-drop">
                            <select name="subjectName" class="form-control" id="subjectList" >
                                <option value="" selected disabled>Subject Name</option>
                                <c:forEach items="${subjectList}" var="s">
                                    <option>
                                        ${s.subjectName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </label>
                    <label>Creator:
                        <div class="input-group" id="creator-drop">
                            <select name="creatorName" class="form-control" id="creatorList" >
                                <option value="" selected disabled>Creator Name</option>
                                <c:forEach items="${creatorList}" var="creator">
                                    <option>
                                        ${creator.fullname}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </label>
                    <br>
                    <label>Creator Role:</label>
                    <input type="radio" id="admin" name="role" value="Admin" ${param.role == 'Admin' ? 'checked' : ''}>
                    <label for="admin">Admin</label>
                    <input type="radio" id="expert" name="role" value="Expert" ${param.role == 'Expert' ? 'checked' : ''}>
                    <label for="expert">Expert</label>
                    <input type="radio" id="customer" name="role" value="Customer" ${param.role == 'Customer' ? 'checked' : ''}>
                    <label for="customer">Customer</label>
                    <br>
                    <label>Level:</label>
                    <input type="radio" id="basic" name="level" value="Basic" ${param.level == 'Basic' ? 'checked' : ''}>
                    <label for="basic">Basic</label>
                    <input type="radio" id="intermediate" name="level" value="Intermediate" ${param.level == 'Intermediate' ? 'checked' : ''}>
                    <label for="intermediate">Intermediate</label>
                    <input type="radio" id="advanced" name="level" value="Advanced" ${param.level == 'Advanced' ? 'checked' : ''}>
                    <label for="advanced">Advanced</label>
                    <br>
                    <button type="submit" class="btn btn-primary">Search/Filter</button>
                    <button type="button" onclick="clearFilters()" class="btn btn-secondary">Clear</button>
                </form>
            </div>
            <div class="actions">
                <a href="quizzes-list"><button class="btn btn-outline-primary">Back to Quizzes List</button></a>
            </div>
            <div>
                <table id="quizList" class="tatable table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Quiz Name</th>
                            <th>Subject</th>
                            <th>Quiz Type</th>
                            <th>Level</th>
                            <th>Pass rate(%)</th>
                            <th>Create date</th>
                            <th>Update date</th>
                            <th>Creator</th>
                            <th>Last Updated By</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="quiz" items="${quizzes}">
                            <tr>
                                <td>${quiz.quizId}</td>
                                <td>${quiz.quizName}</td>
                                <td><a href="/group6/subject-details?subjectId=${quiz.subjectId}">${quiz.subjectName}</a></td>
                                <td>${quiz.typeName}</td>
                                <td>${quiz.levelName}</td>
                                <td>${quiz.passRate}</td>
                                <td>${quiz.formattedCreateDate}<br>${quiz.formattedCreateTime}</td>
                                <td>${quiz.formattedUpdateDate}<br>${quiz.formattedUpdateTime}</td>
                                <td>${quiz.creatorName}</td>
                                <td>${quiz.updateName}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${quiz.passRate != 0}">
                                            <button type="button" class="btn btn-link" onclick="window.location.href = '/group6/quiz-detail?quizId=${quiz.quizId}'">View</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="button" class="btn btn-link" onclick="window.location.href = '/group6/quiz-detail?quizId=${quiz.quizId}'">Edit</button>
                                        </c:otherwise>
                                    </c:choose>                                <form action="deleted-quizzes-list" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="restore">
                                        <input type="hidden" name="quizId" value="${quiz.quizId}">
                                        <button type="submit" class="btn btn-link">Restore</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <c:if test="${quizzes == null || quizzes.isEmpty()}">
                <br>
                <div class="alert alert-primary text-center" role="alert">
                    Nothing to show here!
                </div>
            </c:if>
            <div class="pagination">
                <c:if test="${totalPages > 1}">
                    <div class="container py-3 justify-content-center">
                        <nav aria-label="Quizzes pagination">
                            <ul class="pagination d-flex justify-content-center">
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="deleted-quizzes-list?page=1&searchQuizName=${param.searchQuizName}&subjectName=${param.subjectName}&creatorName=${param.creatorName}&role=${param.role}&level=${param.level}">&lt;&lt;</a>
                                    </li>
                                </c:if>
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="deleted-quizzes-list?page=${currentPage - 1}&searchQuizName=${param.searchQuizName}&subjectName=${param.subjectName}&creatorName=${param.creatorName}&role=${param.role}&level=${param.level}">&lt;</a>
                                    </li>
                                </c:if>
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                        <a class="page-link" href="deleted-quizzes-list?page=${i}&searchQuizName=${param.searchQuizName}&subjectName=${param.subjectName}&creatorName=${param.creatorName}&role=${param.role}&level=${param.level}">${i}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="deleted-quizzes-list?page=${currentPage + 1}&searchQuizName=${param.searchQuizName}&subjectName=${param.subjectName}&creatorName=${param.creatorName}&role=${param.role}&level=${param.level}">&gt;</a>
                                    </li>
                                </c:if>
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="deleted-quizzes-list?page=${totalPages}&searchQuizName=${param.searchQuizName}&subjectName=${param.subjectName}&creatorName=${param.creatorName}&role=${param.role}&level=${param.level}">&gt;&gt;</a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </div>
                </c:if>
            </div>

        </div>
        <script src="vendors/select2/js/select2.min.js"></script>
        <script src="resources/js/quizzes_list.js"></script>
        <%@ include file="footer.jsp" %>
    </body>
</html>
