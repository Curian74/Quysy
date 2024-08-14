<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Exam Details</title>
        <link rel="stylesheet" href="resources/css/exam_detail.css">
    </head>
    <body>
        <h1 class="text-center fw-bold mb-4">Exam Details</h1>
        <div class="container">
            <table class="table table-bordered my-3 table-hover">
                <tr>
                    <th>ID</th>
                    <td>${exam.quizId}</td>
                </tr>
                <tr>
                    <th>Exam Name</th>
                    <td>${exam.quizName}</td>
                </tr>
                <tr>
                    <th>Subject</th>
                    <td>${exam.subjectName}</td>
                </tr>
                <tr>
                    <th>Level</th>
                    <td>${exam.levelName}</td>
                </tr>
                <tr>
                    <th>Number of Questions</th>
                    <td>${exam.numberOfQuestion}</td>
                </tr>
                <tr>
                    <th>Duration (min)</th>
                    <td>${exam.duration}</td>
                </tr>
                <tr>
                    <th>Pass Rate (%)</th>
                    <td>${exam.passRate}</td>
                </tr>
            </table>
            <a href="quiz-handle?examId=${exam.quizId}" class="btn btn-outline-success">Take Exam</a>
            <a href="simulation-exam" class="btn btn-outline-info">Back to Simulation Exam</a>
        </div>
    </body>
</html>
<%@include file="footer.jsp" %>
