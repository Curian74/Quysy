package Controller;

import Dal.QuizDAO;
import Dal.QuestionDAO;
import Dal.SubjectDAO;
import Model.Quiz;
import Model.Subject;
import Model.User;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AddQuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> subjectList = subjectDAO.getAllSubjects();
        request.setAttribute("subjectList", subjectList);

        String subjectIdParam = request.getParameter("subjectId");
        if (subjectIdParam != null) {
            int subjectId;
            try {
                subjectId = Integer.parseInt(subjectIdParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Subject ID");
                return;
            }

            QuestionDAO questionDAO = new QuestionDAO();
            QuizDAO quizDAO = new QuizDAO();

            int noAllQuestion = questionDAO.getNoQuestionsBySubjectId(subjectId);
            int noAllBasic = quizDAO.getNoQuestionBySubjectAndLevel(subjectId, 1);
            int noAllImme = quizDAO.getNoQuestionBySubjectAndLevel(subjectId, 2);
            int noAllAdv = quizDAO.getNoQuestionBySubjectAndLevel(subjectId, 3);

            Map<String, Integer> questionData = new HashMap<>();
            questionData.put("noAllQuestion", noAllQuestion);
            questionData.put("noAllBasic", noAllBasic);
            questionData.put("noAllImme", noAllImme);
            questionData.put("noAllAdv", noAllAdv);

            String json = new Gson().toJson(questionData);
            response.setContentType("application/json");
            response.getWriter().write(json);
            return;
        }

        request.getRequestDispatcher("add_quiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO quizDAO = new QuizDAO();
        QuestionDAO questionDAO = new QuestionDAO();
        SubjectDAO subjectDAO = new SubjectDAO();

        List<Subject> subjectList = subjectDAO.getAllSubjects();
        request.setAttribute("subjectList", subjectList);

        try {
            // Retrieve form parameters
            String quizName = request.getParameter("name");
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            int duration = Integer.parseInt(request.getParameter("duration"));
            String level = request.getParameter("level");
            int quizTypeId = Integer.parseInt(request.getParameter("quizType"));
            int totalQuestions = Integer.parseInt(request.getParameter("totalQuestions"));

            // Determine level ID from level name
            int levelId;
            switch (level) {
                case "Basic":
                    levelId = 1;
                    break;
                case "Intermediate":
                    levelId = 2;
                    break;
                case "Advanced":
                    levelId = 3;
                    break;
                default:
                    throw new ServletException("Invalid level: " + level);
            }

            // Retrieve the user from the session
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("account");
            if (user == null) {
                throw new ServletException("User not logged in");
            }
            int creatorId = user.getUserId();

            // Set up the Quiz object
            Quiz quiz = new Quiz();
            quiz.setQuizName(quizName);
            quiz.setNumberOfQuestion(totalQuestions);
            quiz.setDuration(duration);
            quiz.setLevelId(levelId);
            quiz.setSubjectId(subjectId);
            quiz.setQuizTypeId(quizTypeId);
            quiz.setCreatorId(creatorId);
            quiz.setLastUpdateBy(creatorId);
            // Save the quiz using QuizDAO and retrieve the generated quiz ID
            int quizId = quizDAO.addQuiz(quiz);

            // Retrieve a list of question IDs for the given subject and level
            List<Integer> questionIds = questionDAO.getQuestionIdsBySubjectAndLevel(subjectId, levelId);

            // Randomly select the required number of questions
            if (questionIds.size() >= totalQuestions) {
                Random random = new Random();
                for (int i = 0; i < totalQuestions; i++) {
                    int randomIndex = random.nextInt(questionIds.size());
                    int questionId = questionIds.get(randomIndex);
                    quizDAO.addQuestionToQuiz(quizId, questionId);
                    questionIds.remove(randomIndex); // Remove the selected question to avoid duplication
                }
            } else {
                throw new ServletException("Not enough questions available for the given criteria");
            }

            // Redirect to the quizzes list page after successful submission
            response.sendRedirect("quizzes-list");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid input: " + e.getMessage());
            request.getRequestDispatcher("add_quiz.jsp").forward(request, response);
        } catch (ServletException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("add_quiz.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("add_quiz.jsp").forward(request, response);
        }
    }
}
