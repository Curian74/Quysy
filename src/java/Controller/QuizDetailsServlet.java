package Controller;

import Dal.QuizDAO;
import Model.Quiz;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class QuizDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO quizDAO = new QuizDAO();
        String quizId = request.getParameter("quizId");
        if (quizId != null) {
            Quiz quiz = quizDAO.getQuizById(Integer.parseInt(quizId));
            int noBasic = quizDAO.countQuestionsByQuizIdAndLevelId(Integer.parseInt(quizId), 1);
            int noIntermediate = quizDAO.countQuestionsByQuizIdAndLevelId(Integer.parseInt(quizId), 2);
            int noAdvanced = quizDAO.countQuestionsByQuizIdAndLevelId(Integer.parseInt(quizId), 3);

            request.setAttribute("quiz", quiz);
            request.setAttribute("noBasic", noBasic);
            request.setAttribute("noIntermediate", noIntermediate);
            request.setAttribute("noAdvanced", noAdvanced);
            request.getRequestDispatcher("quiz_details.jsp").forward(request, response);
        } else {
            response.sendRedirect("quizzes-list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO quizDAO = new QuizDAO();
        String name = request.getParameter("name");
        int level = Integer.parseInt(request.getParameter("level"));
        int quizType = Integer.parseInt(request.getParameter("quizType"));
        int duration = Integer.parseInt(request.getParameter("duration"));
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("account");

        if (user == null) {
            throw new ServletException("User not logged in");
        }

        int updater = user.getUserId();
        // Log the received parameters
        System.out.println("Received parameters: " + name + ", " + level + ", " + quizType + ", " + duration + ", " + quizId);
        quizDAO.updateQuiz(quizId, name, level, quizType, duration, updater);
        response.sendRedirect("quizzes-list");
    }
}
