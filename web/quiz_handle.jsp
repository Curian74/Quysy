<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Quiz Handle</title>
        <link rel="stylesheet" href="resources/css/quiz_handle.css">
        <link rel="stylesheet" href="vendors/fontawesome/css/all.css">
    </head>
    <body>
        <div class="quiz-container">
            <c:set var="q" value="${quiz}"></c:set>
                <header>
                    <div class="progress">
                        <span id="currentQuestion">${questionIndex + 1}</span> / <span id="totalQuestions">${totalQuestions}</span>
                </div>
                <div id="timer" class="timer"></div>
            </header>
            <main>
                <div class="question-box">
                    <h2><span id="questionNumber">${questionIndex + 1} )</span> <span class="question-id">Question ID: ${currentQuestion.questionId}</span></h2>
                    <p class="question-text">${currentQuestion.questionDetail}</p>
                    <c:if test="${currentQuestion.media != null}">
                        <div class="question-img">
                            <img src="${currentQuestion.media}">
                        </div>
                    </c:if>
                    <c:choose>
                        <c:when test="${currentQuestion.questionTypeId != 4}">
                            <c:forEach items="${ansList}" var="a" varStatus="loop">
                                <div class="options" id="ans">
                                    <label>
                                        <input type="radio" name="answer" value="${a.answerDetail}" ${answer == a.answerDetail ? 'checked' : ''} onclick="updateSelectedAnswer('${a.answerDetail}')">
                                        ${loop.index == 0 ? 'A' : loop.index == 1 ? 'B' : loop.index == 2 ? 'C' : loop.index == 3 ? 'D' : ''}. ${a.answerDetail}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:when test="${currentQuestion.questionTypeId == 4}">
                            <div class="options" id="ans">
                                <label>
                                    <textarea name="answer" rows="4" cols="50" placeholder="Your Answer..." onblur="updateSelectedAnswer(this.value)">${answer}</textarea>
                                </label>
                            </div>
                        </c:when>
                    </c:choose>
                </div>
            </main>

            <div class="spec_btn">
                <c:if test="${q.quizTypeId == 1}">
                    <button id="peek" class="explain-btn">Peek at Answer</button>
                </c:if>
                <button id="review"><i class="fas fa-bookmark"></i> Mark for Review</button>
            </div>

            <footer id="footer">
                <button class="review-btn">Review Progress</button>
                <c:choose>
                    <c:when test="${questionIndex == 0}">
                        <form action="exam-handle" method="get" onsubmit="updateSelectedAnswerBeforeSubmit(event)">
                            <input type="hidden" name="selectedAnswer" id="selectedAnswer">
                            <input type="hidden" name="questionId" value="${currentQuestion.questionId}">
                            <input type="hidden" name="quizId" value="${q.quizId}">
                            <input type="hidden" name="questionIndex" value="${questionIndex + 1}">
                            <button type="submit" class="next-btn">Next</button>
                        </form>
                    </c:when>
                    <c:when test="${questionIndex == totalQuestions - 1}">
                        <form action="exam-handle" method="get" onsubmit="updateSelectedAnswerBeforeSubmit(event)">
                            <input type="hidden" name="selectedAnswer" id="selectedAnswer">
                            <input type="hidden" name="questionId" value="${currentQuestion.questionId}">
                            <input type="hidden" name="quizId" value="${q.quizId}">
                            <input type="hidden" name="questionIndex" value="${questionIndex - 1}">
                            <button type="submit" class="back-btn">Previous</button>
                        </form>
                        <button class="score-btn">Score Exam</button>
                    </c:when>
                    <c:otherwise>
                        <form action="exam-handle" method="get" onsubmit="updateSelectedAnswerBeforeSubmit(event)">
                            <input type="hidden" name="selectedAnswer" id="selectedAnswer">
                            <input type="hidden" name="questionId" value="${currentQuestion.questionId}">
                            <input type="hidden" name="quizId" value="${q.quizId}">
                            <input type="hidden" name="questionIndex" value="${questionIndex - 1}">
                            <button type="submit" class="back-btn">Previous</button>
                        </form>
                        <form action="exam-handle" method="get" onsubmit="updateSelectedAnswerBeforeSubmit(event)">
                            <input type="hidden" name="selectedAnswer" id="selectedAnswer">
                            <input type="hidden" name="questionId" value="${currentQuestion.questionId}">
                            <input type="hidden" name="quizId" value="${q.quizId}">
                            <input type="hidden" name="questionIndex" value="${questionIndex + 1}">
                            <button type="submit" class="next-btn">Next</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </footer>
        </div>

        <!-- Explanation popup -->
        <div class="overlay"></div>
        <div class="popup">
            <h3><span class="close-btn">&times;</span><div class="exp-header">Peek at Answer</div></h3>
            <div class="popup-content">
                <c:if test="${currentQuestion.questionTypeId != 4}">
                    <p class="correct-answer-text">The correct answer is <span id="correctAnswer"></span>.</p>
                </c:if>
                <c:if test="${currentQuestion.questionTypeId == 4}">
                    <p class="correct-answer-text">The correct answer is <span>${correctAns.answerDetail}</span>.</p>
                </c:if>
                <br>
                <p class="exp-content">${explain.explanationDetail}</p>
                <p class="exp-dim">${dimension.type}: ${dimension.dimensionName}</p>
                <p class="exp-src">Source: ${explain.source}</p>
                <p class="exp-page">Page: ${explain.page}</p>
            </div>
        </div>

        <!-- Review Results popup -->
        <div class="overlay overlay-review"></div>
        <div class="popup popup-review">
            <h1><span class="close-btn close-btn-review">&times;</span><div class="review-header">Review Results</div></h1>
            <div class="popup-review-content">
                <p class="review-content">Review content here...</p>
            </div>
        </div>

        <!-- Hidden input to pass duration to JS -->
        <input type="hidden" id="quizDuration" value="${q.duration}">
        <input type="hidden" id="quizTypeId" value="${q.quizTypeId}">

        <script>
            var selectedAnswer = "${answer}";

            function updateSelectedAnswer(answer) {
                selectedAnswer = answer;
                document.getElementById("selectedAnswer").value = selectedAnswer;
            }

            function updateSelectedAnswerBeforeSubmit(event) {
                var form = event.target;
                var hiddenInput = form.querySelector('input[name="selectedAnswer"]');
                hiddenInput.value = selectedAnswer;
            }
        </script>

        <script src="resources/js/quiz_handle.js"></script>
    </body>
</html>
