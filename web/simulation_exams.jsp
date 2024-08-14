<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Simulation Exams</title>
        <link rel="stylesheet" href="resources/css/pagination.css">
    </head>
    <body>
        <h1 class="text-center fw-bold mb-4">Simulation Exam</h1>
        <div class="container">
            <form method="post" action="simulation-exam">
                <label for="examName">EXAM NAME:</label>
                <input type="text" id="examName" name="examName" value="${param.examName}">

                <label for="subject">SUBJECT:</label>
                <input type="text" id="subject" name="subject" value="${param.subject}">

                <button type="submit">SEARCH</button>
            </form>

            <table class="table table-bordered my-3">
                <thead>
                    <tr>
                        <th>Exam List</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="exam" items="${exams}">
                        <tr>
                            <td>${exam.quizName}</td>
                            <td><a href="simulation-exam?examId=${exam.quizId}" class="btn btn-primary">Take Exam</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${exams == null || exams.isEmpty()}">
                <div class="col-12 text-center">
                    <h5>Nothing to show here...</h5>
                </div>
            </c:if>
            <c:if test="${not empty exams}">
                <div class="pagination">
                    <c:forEach var="i" begin="1" end="${noOfPages}">
                        <a href="simulation-exam?page=${i}&examName=${param.examName}&subject=${param.subject}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </body>
</html>
<%@include file="footer.jsp" %>
