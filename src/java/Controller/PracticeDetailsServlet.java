package Controller;

import Dal.QuestionDAO;
import Dal.QuizDAO;
import Dal.QuizQuestionDAO;
import Dal.QuizRecordDAO;
import Dal.RegistrationDAO;
import Dal.SubjectDimensionDAO;
import Dal.TopicDAO;
import Model.Question;
import Model.Quiz;
import Model.QuizQuestion;
import Model.QuizRecord;
import Model.Registration;
import Model.SubjectDimension;
import Model.Topic;
import Model.User;
import com.google.gson.Gson;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class PracticeDetailsServlet extends HttpServlet {

    private final QuestionDAO questionDAO = new QuestionDAO();
    private final QuizDAO quizDAO = new QuizDAO();
    private final QuizQuestionDAO quizQuestionDAO = new QuizQuestionDAO();
    private final QuizRecordDAO quizRecordDAO = new QuizRecordDAO();
    private final RegistrationDAO rDAO = new RegistrationDAO();
    private final SubjectDimensionDAO subjectDimensionDAO = new SubjectDimensionDAO();
    private final TopicDAO tDAO = new TopicDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("account");

        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("new")) {
                List<Registration> subjectList = rDAO.getUserPracticesSubjects(u);
                request.setAttribute("subjectList", subjectList);
                request.getRequestDispatcher("practice_details.jsp").forward(request, response);
            } else if (action.equals("review")) {
                handlePracticeDetails(request, response, u);
            } else if (action.equals("groupOptions")) {
                handleGroupOptions(request, response);
            }
        } else {
            response.sendRedirect("error.jsp"); // Handle case where action is missing
        }
    }

    private void handleGroupOptions(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String subjectId = request.getParameter("subjectId");
        String selection = request.getParameter("selection");

        if ("topic".equals(selection)) {
            List<Topic> topics = tDAO.getTopicsBySubjectId(Integer.parseInt(subjectId));
            String json = new Gson().toJson(topics);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } else if ("dimension".equals(selection)) {
            List<SubjectDimension> dimensions = subjectDimensionDAO.getDimensionsBySubjectId(Integer.parseInt(subjectId));
            String json = new Gson().toJson(dimensions);
            response.setContentType("application/json");
            response.getWriter().write(json);
        }
    }

    private void handlePracticeDetails(HttpServletRequest request, HttpServletResponse response, User u)
            throws ServletException, IOException {
        String practiceIdRaw = request.getParameter("practiceId");

        if (practiceIdRaw != null && !practiceIdRaw.isEmpty()) {
            try {
                int practiceId = Integer.parseInt(practiceIdRaw);
                QuizRecord practice = quizRecordDAO.getPracticeById(practiceId);

                request.setAttribute("practice", practice);
                request.getRequestDispatcher("practice_details.jsp").forward(request, response);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        } else {
            List<Registration> subjectList = rDAO.getUserPracticesSubjects(u);
            request.setAttribute("subjectList", subjectList);
            request.getRequestDispatcher("practice_details.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("account");

        String subject = request.getParameter("subject");
        int numberOfQuestions = Integer.parseInt(request.getParameter("numberOfQuestions"));
        String selection = request.getParameter("selection");
        String group = request.getParameter("group");

        Quiz quiz = new Quiz();
        quiz.setQuizName("New Practice Quiz");
        quiz.setNumberOfQuestion(numberOfQuestions);
        quiz.setDuration(60);  // Set duration as 60 minutes or any other value
        quiz.setPassRate(70.0f);  // Set pass rate as 70% or any other value
        quiz.setLevelId(1);  // Set levelId based on the logic or selection
        quiz.setSubjectId(Integer.parseInt(subject));
        quiz.setQuizTypeId(1);  // Set quizTypeId based on the logic or selection
        quiz.setCreatorId(u.getUserId());  // Assuming User has a getUserId() method
        quiz.setLastUpdateBy(u.getUserId());  // Assuming User has a getUserId() method

        int quizId = quizDAO.createQuiz(quiz);
        if (quizId != -1) {
            List<Question> questions;
            if ("topic".equals(selection)) {
                questions = questionDAO.getQuestionsByTopicId(Integer.parseInt(group), numberOfQuestions);
            } else {
                questions = questionDAO.getQuestionsByDimensionId(Integer.parseInt(group), numberOfQuestions);
            }

            if (questions != null && !questions.isEmpty()) {
                for (Question question : questions) {
                    quizQuestionDAO.addQuestionToQuiz(new QuizQuestion(quizId, question.getQuestionId()));
                }
                response.sendRedirect("practice_summary.jsp");  // Redirect to a practice summary page
            } else {
                response.sendRedirect("error.jsp");  // Redirect to an error page if no questions are found
            }
        } else {
            response.sendRedirect("error.jsp");  // Redirect to an error page if quiz creation fails
        }
    }

}