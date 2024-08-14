package Controller;

import Dal.AnswerDAO;
import Dal.AnswerRecordDAO;
import Dal.ExplanationDAO;
import Dal.QuestionDAO;
import Dal.QuizDAO;
import Dal.SubjectDimensionDAO;
import Model.Answer;
import Model.AnswerRecord;
import Model.Explanation;
import Model.Question;
import Model.SubjectDimension;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class QuizReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int quizRecordId = Integer.parseInt(request.getParameter("quizRecordId"));
        String questionIndexParam = request.getParameter("questionIndex");
        int questionIndex = questionIndexParam != null ? Integer.parseInt(questionIndexParam) : 0;
        String filter = request.getParameter("filter");

        QuestionDAO questionDAO = new QuestionDAO();
        AnswerRecordDAO answerRecordDAO = new AnswerRecordDAO();
        AnswerDAO answerDAO = new AnswerDAO();
        ExplanationDAO explanationDAO = new ExplanationDAO();
        SubjectDimensionDAO subjectDimensionDAO = new SubjectDimensionDAO();
        QuizDAO quizDAO = new QuizDAO();

        String quizName = quizDAO.getQuizById(quizId).getQuizName();
        List<Question> questions = questionDAO.getQuestionsByQuizRecordId(quizRecordId);
        int totalQuestion = questions.size();

        if (questionIndex < 0) {
            questionIndex = 0;
        }
        if (questionIndex >= totalQuestion) {
            questionIndex = totalQuestion - 1;
        }

        Question currentQuestion = questions.get(questionIndex);
        AnswerRecord answerRecord = answerRecordDAO.getAnswerRecordByQuestionIdAndQuizRecordId(currentQuestion.getQuestionId(), quizRecordId);
        List<Answer> answers = answerDAO.getAnswersByQuestionId(currentQuestion.getQuestionId());
        boolean isCorrect = false;
        String feedbackMessage;
        if (currentQuestion.getQuestionTypeId() < 4) {
            for (Answer answer : answers) {
                if (answer.getAnswerId() == answerRecord.getSelectedAnswerId() && answer.isIsCorrect()) {
                    isCorrect = true;
                    break;
                }
            }
        } else {
            for (Answer answer : answers) {
                if (answer.getAnswerDetail().equalsIgnoreCase(answerRecord.getAnswerContent())) {
                    isCorrect = true;
                    break;
                }
            }
        }
        Explanation explanation = explanationDAO.getExplanationByQuestionId(currentQuestion.getQuestionId());
        Answer correctExplain = answerDAO.getCorrectAnswersByQuestionId(currentQuestion.getQuestionId());
        SubjectDimension sd = subjectDimensionDAO.getSubjectDimensionByQuestionId(currentQuestion.getQuestionId());

        feedbackMessage = isCorrect ? "Congratulations! You are correct!" : "Your answer is incorrect, don't give up!";
        if (answerRecord.getAnswerContent() == null && answerRecord.getSelectedAnswerId() == 0) {
            feedbackMessage = "You have not answered this question yet!";
        }
        List<AnswerRecord> filteredAnswerRecords = null;


        if (filter == null) {
            filter = "all";
        }

        switch (filter) {
            case "marked":
                filteredAnswerRecords = answerRecordDAO.getMarkedAnswerRecords(quizRecordId);
                break;
            case "answered":
                filteredAnswerRecords = answerRecordDAO.getAnsweredAnswerRecords(quizRecordId);
                break;
            case "unanswered":
                filteredAnswerRecords = answerRecordDAO.getUnansweredAnswerRecords(quizRecordId);
                break;
            case "all":
            default:
                filteredAnswerRecords = answerRecordDAO.getAnswerRecordByQuizRecordId(quizRecordId);
                break;
        }
        if (questionIndex >= 0 && questionIndex < totalQuestion) {
            currentQuestion = questions.get(questionIndex);
        }

        Map<Integer, Integer> questionIndexMap = new HashMap<>();
        for (int i = 0; i < questions.size(); i++) {
            questionIndexMap.put(questions.get(i).getQuestionId(), i);
        }

        request.setAttribute("questionIndexMap", questionIndexMap);
        request.setAttribute("dimension", sd);
        request.setAttribute("correctExplain", correctExplain);
        request.setAttribute("explanation", explanation);
        request.setAttribute("currentQuestion", currentQuestion);
        request.setAttribute("questionIndex", questionIndex);
        request.setAttribute("totalQuestion", totalQuestion);
        request.setAttribute("answerRecord", answerRecord);
        request.setAttribute("answers", answers);
        request.setAttribute("feedbackMessage", feedbackMessage);
        request.setAttribute("filteredAnswerRecords", filteredAnswerRecords);
        request.setAttribute("filter", filter);
        request.setAttribute("quizId", quizId);
        request.setAttribute("quizRecordId", quizRecordId);
        request.setAttribute("quizName", quizName);

        request.getRequestDispatcher("review_quiz_detail.jsp").forward(request, response);
    }

}
