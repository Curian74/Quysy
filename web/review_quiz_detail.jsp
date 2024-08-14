<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Quiz Review</title>
        <link rel="stylesheet" href="resources/css/quiz_review.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    </head>
    <body>
        <div class="quiz-container">
            <header class="header-review">
                <p class="quiz-name">QUIZ REVIEWING: ${quizName}</p>
                <a href="practice-list"><button id="exit-btn">&times;</button></a>
            </header>
            <main>
                <div class="question-box">
                    <h2>
                        <span id="questionNumber">${questionIndex + 1} / ${totalQuestion}</span>
                        <span class="question-id">Question ID: ${currentQuestion.questionId}</span>
                    </h2>
                    <p class="question-text">${currentQuestion.questionDetail}</p>
                    <c:if test='${not empty currentQuestion.media}'>
                        <div class="question-img">
                            <img src="${currentQuestion.media}">
                        </div>
                    </c:if>
                    <div class="options" id="ans">
                        <c:choose>
                            <c:when test="${currentQuestion.questionTypeId < 4}">
                                <c:forEach var="answer" items="${answers}">
                                    <label>
                                        <input type="radio" name="answer" value="${answer.answerId}" disabled
                                               <c:if test="${answer.answerId == answerRecord.selectedAnswerId}">checked</c:if> />
                                        <span style="color: ${answer.isCorrect ? 'green' : 'red'}; font-weight: ${answer.isCorrect ? 'bold' : 'normal'};">
                                            ${answer.answerDetail}
                                        </span>
                                        <c:if test="${answer.answerId == answerRecord.selectedAnswerId}">
                                            <span class="answer-notify">Your answer</span>
                                        </c:if>
                                        <c:if test="${answer.isCorrect && answer.answerId != answerRecord.selectedAnswerId}">
                                            <span class="correct-answer-notify">Correct answer!</span>
                                        </c:if>
                                    </label>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p>
                                    Your answer:
                                    <span style="color: ${answerRecord.answerContent == answers[0].answerDetail ? 'green' : 'red'};">
                                        ${answerRecord.answerContent}
                                    </span>
                                </p>
                                <p>
                                    Correct answer:
                                    <span style="color: green; font-weight: bold;">
                                        ${answers[0].answerDetail}
                                    </span>
                                </p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <p class="feedback-message">${feedbackMessage}</p>
                </div>
            </main>
            <footer id="footer">
                <div class="button-container">
                    <div class="button-group-left">
                        <button class="review-btn">Review Results</button>
                        <c:if test="${explanation.explanationDetail ne null}">
                            <button class="explain-btn">Explanation</button>
                        </c:if>
                    </div>
                    <div class="button-group-right">
                        <c:choose>
                            <c:when test="${questionIndex == 0}">
                                <form action="quiz-review" method="get" style="display:inline;">
                                    <input type="hidden" name="quizId" value="${param.quizId}">
                                    <input type="hidden" name="quizRecordId" value="${param.quizRecordId}">
                                    <input type="hidden" name="questionIndex" value="${questionIndex + 1}">
                                    <button type="submit" class="next-btn">Next</button>
                                </form>
                            </c:when>
                            <c:when test="${questionIndex == totalQuestion - 1}">
                                <form action="quiz-review" method="get" style="display:inline;">
                                    <input type="hidden" name="quizId" value="${param.quizId}">
                                    <input type="hidden" name="quizRecordId" value="${param.quizRecordId}">
                                    <input type="hidden" name="questionIndex" value="${questionIndex - 1}">
                                    <button type="submit" class="prev-btn">Previous</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="quiz-review" method="get" style="display:inline;">
                                    <input type="hidden" name="quizId" value="${param.quizId}">
                                    <input type="hidden" name="quizRecordId" value="${param.quizRecordId}">
                                    <input type="hidden" name="questionIndex" value="${questionIndex - 1}">
                                    <button type="submit" class="prev-btn">Previous</button>
                                </form>
                                <form action="quiz-review" method="get" style="display:inline;">
                                    <input type="hidden" name="quizId" value="${param.quizId}">
                                    <input type="hidden" name="quizRecordId" value="${param.quizRecordId}">
                                    <input type="hidden" name="questionIndex" value="${questionIndex + 1}">
                                    <button type="submit" class="next-btn">Next</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </footer>
        </div>

        <!-- Explanation popup -->
        <div class="overlay">
            <div class="popup">
                <h1><span class="close-btn">&times;</span><div class="exp-header">Explanation</div></h1>
                <div class="popup-content">
                    <p class="exp-ans">The correct answer is ${correctExplain.answerDetail}</p>
                    <p class="exp-content">Content: ${explanation.explanationDetail}</p>
                    <p class="exp-dim">Dimension: ${dimension.dimensionName}</p>
                    <p class="exp-src">Source: ${explanation.source}</p>
                    <p class="exp-page">${explanation.page}</p>
                </div>
            </div>
        </div>
        <!-- Review Results popup -->
        <div class="overlay overlay-review">
            <div class="popup popup-review">
                <h1><span class="close-btn close-btn-review">&times;</span><div class="review-header">Review Results</div></h1>
                <div class="popup-review-content">
                    <div class="filter-buttons">
                        <form action="quiz-review" method="get" style="display:inline;">
                            <input type="hidden" name="quizId" value="${param.quizId}">
                            <input type="hidden" name="quizRecordId" value="${param.quizRecordId}">
                            <input type="hidden" name="filter" value="marked">
                            <button type="submit" class="mark-btn">Marked</button>
                        </form>
                        <form action="quiz-review" method="get" style="display:inline;">
                            <input type="hidden" name="quizId" value="${param.quizId}">
                            <input type="hidden" name="quizRecordId" value="${param.quizRecordId}">
                            <input type="hidden" name="filter" value="answered">
                            <button type="submit" class="filter-btn">Answered</button>
                        </form>
                        <form action="quiz-review" method="get" style="display:inline;">
                            <input type="hidden" name="quizId" value="${param.quizId}">
                            <input type="hidden" name="quizRecordId" value="${param.quizRecordId}">
                            <input type="hidden" name="filter" value="unanswered">
                            <button type="submit" class="filter-btn">Unanswered</button>
                        </form>
                        <form action="quiz-review" method="get" style="display:inline;">
                            <input type="hidden" name="quizId" value="${param.quizId}">
                            <input type="hidden" name="quizRecordId" value="${param.quizRecordId}">
                            <input type="hidden" name="filter" value="all">
                            <button type="submit" class="filter-btn">All Questions</button>
                        </form>
                    </div>
                    <!-- Notification line for chosen filter -->
                    <div class="filter-notification">
                        <c:if test="${not empty param.filter}">
                            <p>You have selected <strong>${param.filter}</strong> questions.</p>
                        </c:if>
                    </div>
                    <div class="filtered-questions">
                        <c:choose>
                            <c:when test="${empty filteredAnswerRecords}">
                                <p style="font-style: italic">Nothing to show here</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="record" items="${filteredAnswerRecords}" varStatus="status">
                                    <c:if test="${not empty record}">
                                        <c:set var="isMarked" value="${record.isMarked}" />
                                        <c:set var="questionId" value="${record.questionId != null ? record.questionId : 'N/A'}" />
                                        <form action="quiz-review" method="get" style="display:inline;">
                                            <button type="submit" class="question-btn ${isMarked ? 'marked' : ''}">
                                                ${questionIndexMap[record.questionId] + 1}
                                            </button>
                                            <input type="hidden" name="quizId" value="${param.quizId}">
                                            <input type="hidden" name="quizRecordId" value="${param.quizRecordId}">
                                            <input type="hidden" name="questionIndex" value="${questionIndexMap[record.questionId]}">
                                        </form>
                                    </c:if>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <script src="resources/js/quiz_review.js"></script>
    </body>
</html>
