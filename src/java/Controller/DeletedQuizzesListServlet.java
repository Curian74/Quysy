package Controller;

import Dal.QuizDAO;
import Dal.SubjectDAO;
import Model.Quiz;
import Model.Subject;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class DeletedQuizzesListServlet extends HttpServlet {

    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO quizDAO = new QuizDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> subjectsList = subjectDAO.getAllSubjects();
        List<User> creators = quizDAO.getAllCreators();
        List<Quiz> allQuizzes = quizDAO.getDeletedQuizzes();
        List<Quiz> quizzes;

        String searchQuizName = request.getParameter("searchQuizName");
        String subjectName = request.getParameter("subjectName");
        String creatorName = request.getParameter("creatorName");
        String role = request.getParameter("role");
        String level = request.getParameter("level");
        String pageStr = request.getParameter("page");
        int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;

        // Get total quizzes count for pagination
        int totalQuizzes = quizDAO.countFilteredDeletedQuizzes(searchQuizName, subjectName, creatorName, role, level);
        int totalPages = (int) Math.ceil((double) totalQuizzes / PAGE_SIZE);

        // Calculate offset for pagination
        int offset = (page - 1) * PAGE_SIZE;

        quizzes = quizDAO.filterDeletedQuizzes(searchQuizName, subjectName, creatorName, role, level, offset, PAGE_SIZE);

        request.setAttribute("allQuizzes", allQuizzes);
        request.setAttribute("quizzes", quizzes);
        request.setAttribute("subjectList", subjectsList);
        request.setAttribute("creatorList", creators);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalQuizzes", totalQuizzes);
        request.getRequestDispatcher("deleted_quizzes_list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("restore".equals(action)) {
            int quizId = Integer.parseInt(request.getParameter("quizId"));
            HttpSession session = request.getSession();
            User updater = (User) session.getAttribute("account");
            int updaterId = updater.getUserId();

            QuizDAO quizDAO = new QuizDAO();
            quizDAO.restoreQuiz(quizId, updaterId);

            response.sendRedirect("deleted-quizzes-list");
        }
    }
}
