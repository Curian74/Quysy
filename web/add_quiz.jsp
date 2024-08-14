<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QUYSY | Add Quiz</title>
        <link rel="stylesheet" href="resources/css/quiz_details.css"/>
        <link rel="stylesheet" href="vendors/select2/css/select2.min.css"/>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <h1 class="display-3 fw-bold text-center mb-3">Add Quiz</h1>
                <br>
                <a href="quizzes-list"><button class="btn btn-outline-primary" type="button">Back to Quizzes List</button></a>
                <form id="quizForm" action="add-quiz" method="post" class="row">
                    <div class="col-md-3">
                        <!-- Overview Section -->
                        <div>
                            <label for="name">Quiz Name:</label>
                            <input type="text" id="name" name="name" class="form-control" required><br>
                            <label>Subject Name:
                                <div class="input-group" id="subject-drop">
                                    <select name="subjectId" class="form-control" id="subjectList" required>
                                        <option value="" selected disabled>Subject Name</option>
                                        <c:forEach items="${subjectList}" var="s">
                                            <option value="${s.subjectId}">
                                                ${s.subjectName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </label><br><br>
                            <label for="duration">Duration (minute):</label>
                            <input type="number" id="duration" name="duration" class="form-control" required><br>
                            <label for="level">Level:</label><br>
                            <input type="radio" id="basic" name="level" value="Basic" ${param.level == 'Basic' ? 'checked' : ''}>
                            <label for="basic">Basic</label>
                            <input type="radio" id="intermediate" name="level" value="Intermediate" ${param.level == 'Intermediate' ? 'checked' : ''}>
                            <label for="intermediate">Intermediate</label>
                            <input type="radio" id="advanced" name="level" value="Advanced" ${param.level == 'Advanced' ? 'checked' : ''}>
                            <label for="advanced">Advanced</label>
                            <br><br>
                            <label>Quiz Type:</label><br>
                            <input type="radio" id="practice" name="quizType" value="2">
                            <label for="practice">Practice</label>
                            <input type="radio" id="simulation" name="quizType" value="1">
                            <label for="simulation">Simulation Exam</label><br><br>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <!-- Settings Section -->
                        <div>
                            <label for="totalQuestions">Total number of questions:</label>
                            <input type="number" id="totalQuestions" name="totalQuestions" class="form-control" required><br>
                            <label for="basicQuestionsCheckbox">Number of Basic Questions:</label>
                            <input type="checkbox" id="basicQuestionsCheckbox" name="basicQuestionsCheckbox">
                            <div id="basicQuestionsDiv" style="display:none;">
                                <input type="number" id="basicQuestions" name="basicQuestions" class="form-control"><br>
                            </div><br>
                            <label for="intermediateQuestionsCheckbox">Number of Intermediate Questions:</label>
                            <input type="checkbox" id="intermediateQuestionsCheckbox" name="intermediateQuestionsCheckbox">
                            <div id="intermediateQuestionsDiv" style="display:none;">
                                <input type="number" id="intermediateQuestions" name="intermediateQuestions" class="form-control"><br>
                            </div><br>
                            <label for="advancedQuestionsCheckbox">Number of Advanced Questions:</label>
                            <input type="checkbox" id="advancedQuestionsCheckbox" name="advancedQuestionsCheckbox">
                            <div id="advancedQuestionsDiv" style="display:none;">
                                <input type="number" id="advancedQuestions" name="advancedQuestions" class="form-control"><br>
                            </div><br>
                        </div>
                    </div>
                    <div>
                        <button class="btn btn-primary" type="submit">Submit</button>
                    </div>
                </form>
                <br>
            </div>
        </div>
        <%@include file="footer.jsp"%>
        <script src="vendors/select2/js/select2.min.js"></script>
        <script src="resources/js/quizzes_list.js"></script>
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

                // AJAX call to update available questions based on selected subject
                document.getElementById('subjectList').addEventListener('change', function () {
                    var subjectId = this.value;
                    console.log(`Selected Subject ID: ${subjectId}`); // Debugging line
                    fetch(`add-quiz?subjectId=${subjectId}`)
                            .then(response => {
                                console.log(`Response Status: ${response.status}`); // Debugging line
                                return response.json();
                            })
                            .then(data => {
                                console.log('Received data:', data); // Debugging line
                                document.getElementById('noAllQuestion').textContent = data.noAllQuestion;
                                document.getElementById('noAllBasic').textContent = data.noAllBasic;
                                document.getElementById('noAllImme').textContent = data.noAllImme;
                                document.getElementById('noAllAdv').textContent = data.noAllAdv;
                            })
                            .catch(error => console.error('Error fetching data:', error));
                });
            });
        </script>
    </body>
</html>
