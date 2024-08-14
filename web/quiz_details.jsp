<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QUYSY | Quiz Details</title>
        <link rel="stylesheet" href="resources/css/quiz_details.css"/>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <h1 class="display-3 fw-bold text-center mb-3">Quiz Details</h1>
                <br>
                <c:choose>
                    <c:when test="${quiz.passRate != 0}">
                        <h3 class="fst-italic text-center mb-3">View Quiz</h3>
                    </c:when>
                    <c:otherwise>
                        <h3 class="fst-italic text-center mb-3">Edit Quiz</h3>
                    </c:otherwise>
                </c:choose>
                <a href="quizzes-list"><button class="btn btn-outline-primary" type="button">Back to Quizzes List</button></a>
                <c:set var="isDisabled" value="${quiz.passRate != 0}" />
                <c:set var="partialDisabled" value="${quiz.passRate == 0}" />
                <form id="quizForm" action="quiz-detail" method="post" class="row">
                    <input type="hidden" name="quizId" value="${quiz.quizId}">
                    <div class="col-md-3">
                        <!-- Overview Section -->
                        <div>
                            <label for="name">Quiz Name:</label>
                            <input type="text" id="name" name="name" value="${quiz.quizName}" ${isDisabled ? 'disabled' : ''} class="form-control"><br>
                            <label for="subject">Subject:</label>
                            <input type="text" id="subject" name="subject" value="${quiz.subjectName}" disabled class="form-control"><br>
                            <label for="duration">Duration (minute):</label>
                            <input type="number" id="duration" name="duration" value="${quiz.duration}" ${isDisabled ? 'disabled' : ''} class="form-control"><br>
                            <label for="passRate">Pass Rate (%):</label>
                            <input type="number" readonly id="passRate" name="passRate" value="${quiz.passRate}" disabled class="form-control"><br>
                            <label for="level">Level:</label><br>
                            <input type="radio" id="basic" name="level" value="1" ${quiz.levelId == 1 ? 'checked' : ''} ${isDisabled ? 'disabled' : ''}>
                            <label for="basic">Basic</label>
                            <input type="radio" id="intermediate" name="level" value="2" ${quiz.levelId == 2 ? 'checked' : ''} ${isDisabled ? 'disabled' : ''}>
                            <label for="intermediate">Intermediate</label>
                            <input type="radio" id="advanced" name="level" value="3" ${quiz.levelId == 3 ? 'checked' : ''} ${isDisabled ? 'disabled' : ''}>
                            <label for="advanced">Advanced</label>
                            <br><br>
                            <label>Quiz Type:</label><br>
                            <input type="radio" id="practice" name="quizType" value="2" ${quiz.quizTypeId == 2 ? 'checked' : ''} ${isDisabled ? 'disabled' : ''}>
                            <label for="practice">Practice</label>
                            <input type="radio" id="simulation" name="quizType" value="1" ${quiz.quizTypeId == 1 ? 'checked' : ''} ${isDisabled ? 'disabled' : ''}>
                            <label for="simulation">Simulation Exam</label><br><br>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <!-- Settings Section -->
                        <div>
                            <label for="totalQuestions">Total number of questions:</label>
                            <input type="number" id="totalQuestions" name="totalQuestions" value="${quiz.numberOfQuestion}" disabled class="form-control"><br>
                            <label for="basicQuestionsCheckbox">Number of Basic Questions:</label>
                            <input type="checkbox" id="basicQuestionsCheckbox" name="basicQuestionsCheckbox" ${noBasic > 0 ? 'checked' : ''} disabled>
                            <div id="basicQuestionsDiv" style="${noBasic > 0 ? 'display:block;' : 'display:none;'}">
                                <input type="number" disabled id="basicQuestions" name="basicQuestions" value="${noBasic}" class="form-control"><br>
                            </div><br>
                            <label for="intermediateQuestionsCheckbox">Number of Intermediate Questions:</label>
                            <input type="checkbox" id="intermediateQuestionsCheckbox" name="intermediateQuestionsCheckbox" ${noIntermediate > 0 ? 'checked' : ''} disabled>
                            <div id="intermediateQuestionsDiv" style="${noIntermediate > 0 ? 'display:block;' : 'display:none;'}">
                                <input type="number" disabled id="intermediateQuestions" name="intermediateQuestions" value="${noIntermediate}" class="form-control"><br>
                            </div><br>
                            <label for="advancedQuestionsCheckbox">Number of Advanced Questions:</label>
                            <input type="checkbox" id="advancedQuestionsCheckbox" name="advancedQuestionsCheckbox" ${noAdvanced > 0 ? 'checked' : ''} disabled>
                            <div id="advancedQuestionsDiv" style="${noAdvanced > 0 ? 'display:block;' : 'display:none;'}">
                                <input type="number" disabled id="advancedQuestions" name="advancedQuestions" value="${noAdvanced}" class="form-control"><br>
                            </div><br>
                        </div>
                    </div>
                    <div>
                        <c:if test="${quiz.passRate == 0}">
                            <button class="btn btn-primary" type="submit">Submit</button>
                        </c:if>
                    </div>
                </form>
                <br>
            </div>
        </div>
        <%@include file="footer.jsp"%>
        <script src="vendors/select2/js/select2.min.js"></script>
        <!-- <script src="resources/js/quiz_details.js"></script> -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Toggle question input fields based on checkbox status
                document.getElementById('basicQuestionsCheckbox').addEventListener('change', function () {
                    var inputField = document.getElementById('basicQuestions');
                    if (this.checked) {
                        inputField.disabled = false;
                        document.getElementById('basicQuestionsDiv').style.display = 'block';
                    } else {
                        inputField.disabled = true;
                        document.getElementById('basicQuestionsDiv').style.display = 'none';
                    }
                });

                document.getElementById('intermediateQuestionsCheckbox').addEventListener('change', function () {
                    var inputField = document.getElementById('intermediateQuestions');
                    if (this.checked) {
                        inputField.disabled = false;
                        document.getElementById('intermediateQuestionsDiv').style.display = 'block';
                    } else {
                        inputField.disabled = true;
                        document.getElementById('intermediateQuestionsDiv').style.display = 'none';
                    }
                });

                document.getElementById('advancedQuestionsCheckbox').addEventListener('change', function () {
                    var inputField = document.getElementById('advancedQuestions');
                    if (this.checked) {
                        inputField.disabled = false;
                        document.getElementById('advancedQuestionsDiv').style.display = 'block';
                    } else {
                        inputField.disabled = true;
                        document.getElementById('advancedQuestionsDiv').style.display = 'none';
                    }
                });

                document.getElementById('quizForm').addEventListener('submit', function (event) {
                    if (!validateQuestions()) {
                        event.preventDefault();
                    }
                });

                function validateQuestions() {
                    const totalQuestions = parseInt(document.getElementById('totalQuestions').value);
                    const basicQuestions = document.getElementById('basicQuestionsCheckbox').checked ? parseInt(document.getElementById('basicQuestions').value) || 0 : 0;
                    const intermediateQuestions = document.getElementById('intermediateQuestionsCheckbox').checked ? parseInt(document.getElementById('intermediateQuestions').value) || 0 : 0;
                    const advancedQuestions = document.getElementById('advancedQuestionsCheckbox').checked ? parseInt(document.getElementById('advancedQuestions').value) || 0 : 0;

                    const sumOfQuestions = basicQuestions + intermediateQuestions + advancedQuestions;

                    if (sumOfQuestions !== totalQuestions) {
                        alert('The sum of Basic, Intermediate, and Advanced questions must equal the total number of questions.');
                        return false;
                    }
                    return true;
                }
            });
        </script>
    </body>
</html>
    