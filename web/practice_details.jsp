<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>
<%@page import="Utility.TimeUtils"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | Practice Details</title>
    </head>
    <body>
        <div class="container mt-5 pt-5">
            <div class="row justify-content-center">
                <div class="col-6 border rounded p-3 shadow">
                    <h1 class="display-3 fw-bold text-center my-3">Practice Details</h1>
                    <c:set var="p" value="${requestScope.practice}"/>
                    <c:choose>
                        <c:when test="${not empty p}">
                            <form method="get" action="quiz-review">
                                <input type="hidden" name="quizRecordId" value="${p.quizRecordId}">
                                <input type="hidden" name="quizId" value="${p.quizId}">

                                <div class="row">
                                    <div class="col-6">
                                        <p class="card-text my-3"><strong>Subject Name:</strong> ${p.subjectName}</p>
                                        <p class="card-text my-3"><strong>Created Date:</strong> ${p.createDate}</p>
                                        <p class="card-text my-3"><strong>Number of Questions:</strong> ${p.numberOfQuestion}</p>
                                        <c:set var="duration" value="${p.timeSpent}" />
                                        <c:set var="formattedDuration" value="${TimeUtils.convertSecondsToHHMMSS(duration)}" />
                                        <p class="card-text my-3"><strong>Duration:</strong> ${formattedDuration}</p>
                                    </div>

                                    <div class="col-6 border-start">
                                        <p class="card-text my-3"><strong>Practice Name:</strong> ${p.quizName}</p>
                                        <p class="card-text my-3"><strong>Finished Date:</strong> ${p.finishDate}</p>
                                        <p class="card-text my-3"><strong>Score:</strong> ${p.score}</p>
                                    </div>
                                </div>

                                <button type="submit" class="btn btn-primary">Review</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form method="post" action="practice-details">
                                <div class="my-3">
                                    <label for="subject" class="form-label">Subject</label>
                                    <select id="subject" name="subject" class="form-select" onchange="updateGroupOptions()">
                                        <option value="" disabled selected>Subject name</option>
                                        <c:forEach var="sl" items="${requestScope.subjectList}">
                                            <option value="${sl.subjectId}">${sl.subjectName}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="my-3">
                                    <label for="numberOfQuestions" class="form-label">Number of practicing questions</label>
                                    <input type="number" min="1" class="form-control" id="numberOfQuestions" name="numberOfQuestions">
                                </div>

                                <div class="my-3">
                                    <label for="selection" class="form-label">Questions are selected by topic(s) or a specific dimension?</label>
                                    <select id="selection" name="selection" class="form-select" onchange="updateGroupOptions()">
                                        <option value="topic">By subject topic</option>
                                        <option value="dimension">By specific dimension</option>
                                    </select>
                                </div>

                                <div class="my-3">
                                    <label for="group" class="form-label">Question group (choose one or all topic/dimension(s))</label>
                                    <select id="group" name="group" class="form-select">
                                        <option value="" disabled selected>All</option>
                                    </select>
                                </div>

                                <button type="submit" class="btn btn-primary">Practice</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <script src="resources/js/practice_details.js"></script>
    </body>
</html>

