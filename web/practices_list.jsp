<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>
<%@page import="Utility.TimeUtils"%>
<%@page import="Utility.QuizUtils"%>

<!DOCTYPE html>
<html>
    <head>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | Practices List</title>
        <link rel="stylesheet" href="resources/css/sticky_footer.css"/>
        <link rel="stylesheet" href="vendors/bootstrap/bootstrap.min.css"/>
    </head>
    <body>
        <div class="flex-column">
            <div class="content">
                <div class="container">
                    <h1 class="display-3 fw-bold text-center my-3">Practices List</h1>

                    <div class="d-flex justify-content-between my-3">
                        <!-- Dropdown menu to choose subject -->
                        <div class="d-block">
                            <div class="dropdown">
                                <button class="btn btn-outline-primary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    All subjects
                                </button>
                                <ul class="dropdown-menu">
                                    <c:forEach var="sl" items="${requestScope.subjectList}">
                                        <li><a class="dropdown-item" href="practice-list?subjectId=${sl.subjectId}">${sl.subjectName}</a></li>
                                        </c:forEach>
                                </ul>
                            </div>
                        </div>

                        <!-- Action buttons-->
                        <div class="d-block">
                            <a class="btn btn-primary" href="practice-details?action=new">New Practice</a>
                            <a class="btn btn-primary" href="simulation-exam">Simulation Exam</a>
                        </div>
                    </div>

                    <c:if test="${practiceListMessage eq 'practiceListEmpty'}">
                        <div class="alert alert-warning">The list is empty!</div>
                    </c:if>

                    <table id="practiceList" class="table table-bordered table-striped my-3">
                        <thead>
                            <tr>
                                <th class="align-middle text-center">#</th>
                                <th class="align-middle text-center">Subject Name & Quiz Name</th>
                                <th class="align-middle text-center">Date taken</th>
                                <th class="align-middle text-center">Questions statistics</th>
                                <th class="align-middle text-center">Score</th>
                                <th class="align-middle text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pl" items="${requestScope.practiceList}" varStatus="i">
                                <tr class="main-row">
                                    <td class="align-middle text-center"><strong>${i.index + 1}</strong></td>
                                    <td><strong>Subject Name:</strong> ${pl.subjectName}<br><strong>Quiz Name:</strong> ${pl.quizName}</td>
                                    <td class="align-middle text-center">${pl.finishDate}</td>
                                    <c:set var="correctQuestions" value="${QuizUtils.calculateCorrectQuestions(pl.score, pl.numberOfQuestion)}"/>
                                    <td class="align-middle text-center">${correctQuestions} Correct<br>${pl.numberOfQuestion} Questions</td>
                                    <td class="align-middle text-center">${pl.score * 10}%<br>Correct</td>
                                    <td class="align-middle text-center"><a href="practice-details?action=review&practiceId=${pl.quizRecordId}">View details</a></td>
                                </tr>
                                <tr class="details-row">
                                    <td colspan="6"> <!-- Adjust colspan to cover the whole row -->
                                        <div class="d-flex justify-content-between">
                                            <span><strong>Test type:</strong> ${pl.quizTypeName}</span>
                                            <c:set var="duration" value="${pl.timeSpent}" />
                                            <c:set var="formattedDuration" value="${TimeUtils.convertSecondsToHHMMSS(duration)}" />
                                            <span><strong>Duration</strong> - ${formattedDuration}</span>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <c:if test="${not empty requestScope.practiceList}">
                        <div class="container my-3 justify-content-center">
                            <nav aria-label="Registrations pagination">
                                <c:set var="page" value="${requestScope.page}" />
                                <c:set var="numberOfPages" value="${requestScope.numberOfPages}" />
                                <c:set var="subjectId" value="${param.subjectId}" />

                                <ul class="pagination d-flex justify-content-center">
                                    <!-- First page link -->
                                    <li class="page-item ${page == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="practice-list?page=1<c:if test="${not empty subjectId}">&subjectId=${subjectId}</c:if>">&lt;&lt;&lt;</a>
                                        </li>
                                        <!-- Previous page link -->
                                        <li class="page-item ${page == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="practice-list?page=${page - 1}<c:if test="${not empty subjectId}">&subjectId=${subjectId}</c:if>">&lt;</a>
                                        </li>

                                    <c:choose>
                                        <c:when test="${page == 1}">
                                            <c:forEach var="i" begin="1" end="${numberOfPages > 3 ? 3 : numberOfPages}">
                                                <li class="page-item ${page == i ? 'active' : ''}">
                                                    <a class="page-link" href="practice-list?page=${i}<c:if test="${not empty subjectId}">&subjectId=${subjectId}</c:if>">${i}</a>
                                                    </li>
                                            </c:forEach>
                                        </c:when>
                                        <c:when test="${page == numberOfPages}">
                                            <c:forEach var="i" begin="${numberOfPages > 2 ? numberOfPages - 2 : 1}" end="${numberOfPages}">
                                                <li class="page-item ${page == i ? 'active' : ''}">
                                                    <a class="page-link" href="practice-list?page=${i}<c:if test="${not empty subjectId}">&subjectId=${subjectId}</c:if>">${i}</a>
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
                                                    <a class="page-link" href="practice-list?page=${i}<c:if test="${not empty subjectId}">&subjectId=${subjectId}</c:if>">${i}</a>
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
                                        <a class="page-link" href="practice-list?page=${page + 1}<c:if test="${not empty subjectId}">&subjectId=${subjectId}</c:if>">&gt;</a>
                                        </li>
                                        <!-- Last page link -->
                                        <li class="page-item ${page == numberOfPages ? 'disabled' : ''}">
                                        <a class="page-link" href="practice-list?page=${numberOfPages}<c:if test="${not empty subjectId}">&subjectId=${subjectId}</c:if>">&gt;&gt;&gt;</a>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                    </c:if>
                </div>

                <%@include file="footer.jsp"%>
            </div>

        </div>

    </body>
</html>
