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
import java.util.List;

public class SimulationExamServlet extends HttpServlet {

    private static final int RECORDS_PER_PAGE = 7;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        QuizDAO d = new QuizDAO();

        String examIdParam = request.getParameter("examId");

        if (examIdParam == null) {
            // Handle pagination
            int page = 1;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            List<Quiz> exams = d.getQuizzesByUserAndTypeAndSubject(user, 1, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
            int noOfRecords = d.getNoOfRecords();

            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);

            // Add exams and pagination attributes to the request
            request.setAttribute("exams", exams);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);

            // Forward to the JSP page
            request.getRequestDispatcher("simulation_exams.jsp").forward(request, response);
        } else {
            // Fetch exam details from the database
            int examId = Integer.parseInt(examIdParam);
            Quiz exam = d.getExamById(examId);

            // Add exam to the request attribute
            request.setAttribute("exam", exam);

            // Forward to the JSP page
            request.getRequestDispatcher("exam_details.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle search and filtering logic here
        String subject = request.getParameter("subject").trim();
        String examName = request.getParameter("examName").trim();

        if (subject.isEmpty() && examName.isEmpty()) {
            // Redirect to doGet to handle pagination as usual
            doGet(request, response);
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("account");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            QuizDAO d = new QuizDAO();

            // Handle pagination for search results
            int page = 1;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            List<Quiz> exams = d.searchExams(subject, examName, user.getUserId(), (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
            int noOfRecords = d.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);

            // Add exams and pagination attributes to the request
            request.setAttribute("exams", exams);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);

            // Forward to the JSP page
            request.getRequestDispatcher("simulation_exams.jsp").forward(request, response);
        }
    }

}
