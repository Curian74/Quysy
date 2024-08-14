package Controller;

import Dal.QuestionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ToggleQuestionStatusServlet extends HttpServlet {

    private final QuestionDAO qDAO = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the parameters from the request
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        // Update the question status in the database
        String updateStatusMessage;
        if (qDAO.updateQuestionStatus(questionId, status)) {
            updateStatusMessage = "toggleSuccess";
        } else {
            updateStatusMessage = "toggleFail";
        }

        // Get the filter parameters
        String subjectIdRaw = request.getParameter("subjectId");
        String lessonIdRaw = request.getParameter("lessonId");
        String dimensionIdRaw = request.getParameter("dimensionId");
        String levelIdRaw = request.getParameter("levelId");
        String pageRaw = request.getParameter("page");

        // Redirect back to the question list with the filter parameters
        String redirectUrl = "question-list";
        String queryParams = "";
        if (subjectIdRaw != null && !subjectIdRaw.isEmpty()) {
            queryParams += "subjectId=" + subjectIdRaw + "&";
        }
        if (lessonIdRaw != null && !lessonIdRaw.isEmpty()) {
            queryParams += "lessonId=" + lessonIdRaw + "&";
        }
        if (dimensionIdRaw != null && !dimensionIdRaw.isEmpty()) {
            queryParams += "dimensionId=" + dimensionIdRaw + "&";
        }
        if (levelIdRaw != null && !levelIdRaw.isEmpty()) {
            queryParams += "levelId=" + levelIdRaw + "&";
        }
        if (pageRaw != null && !pageRaw.isEmpty()) {
            queryParams += "page=" + pageRaw + "&";
        }
        if (!queryParams.isEmpty()) {
            redirectUrl += "?" + queryParams + "updateStatusMessage=" + updateStatusMessage;
        } else {
            redirectUrl += "?updateStatusMessage=" + updateStatusMessage;
        }

        response.sendRedirect(redirectUrl);
    }
    
}
