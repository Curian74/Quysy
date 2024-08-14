package Controller;

import Dal.DimensionsDAO;
import Dal.LessonDAO;
import Dal.LevelDAO;
import Dal.QuestionDAO;
import Dal.SubjectDAO;
import Model.Dimensions;
import Model.Lesson;
import Model.QuestionLevel;
import Model.Question;
import Model.Subject;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class QuestionListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initiate DAOs
        QuestionDAO qDAO = new QuestionDAO();
        SubjectDAO sDAO = new SubjectDAO();
        LessonDAO lsDAO = new LessonDAO();
        DimensionsDAO dDAO = new DimensionsDAO();
        LevelDAO lvDAO = new LevelDAO();

        // Initiate current session
        HttpSession session = request.getSession();

        // Get current user
        User u = (User) session.getAttribute("account");

        // Check permission
        if (u.getRoleId() != 1 && u.getRoleId() != 3) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        // Get filters
        // subject
        String subjectIdRaw = request.getParameter("subjectId");
        int subjectId;
        try {
            subjectId = Integer.parseInt(subjectIdRaw);
        } catch (NumberFormatException e) {
            subjectId = 0;
        }

        // lesson
        String lessonIdRaw = request.getParameter("lessonId");
        int lessonId;
        try {
            lessonId = Integer.parseInt(lessonIdRaw);
        } catch (NumberFormatException e) {
            lessonId = 0;
        }

        // dimension
        String dimensionIdRaw = request.getParameter("dimensionId");
        int dimensionId;
        try {
            dimensionId = Integer.parseInt(dimensionIdRaw);
        } catch (NumberFormatException e) {
            dimensionId = 0;
        }

        // level
        String levelIdRaw = request.getParameter("levelId");
        int levelId;
        try {
            levelId = Integer.parseInt(levelIdRaw);
        } catch (NumberFormatException e) {
            levelId = 0;
        }

        // status
        String statusRaw = request.getParameter("status");
        Boolean status;
        if (statusRaw != null) {
            status = Boolean.valueOf(statusRaw);
        } else {
            status = null;
        }
        
        // Get questions list
        List<Question> questionList = qDAO.getQuestionsList(subjectId, lessonId, dimensionId, levelId, status);

        // Check if list is empty or not
        if (questionList.isEmpty()) {
            request.setAttribute("questionListMessage", "questionListEmpty");
        } else {
            request.setAttribute("questionList", questionList);
        }

        // Get all contents for filtering list
        List<Subject> subjectList = sDAO.getAllSubjects();
        List<Lesson> lessonList = lsDAO.getAllLessons();

        List<Dimensions> dimensionList = dDAO.getAllDimensions();
        List<QuestionLevel> levelList = lvDAO.getAllLevels();

        // Get update status message
        String updateStatusMessage = request.getParameter("updateStatusMessage");
        
        // Set attributes
        request.setAttribute("subjectList", subjectList);
        request.setAttribute("lessonList", lessonList);
        request.setAttribute("dimensionList", dimensionList);
        request.setAttribute("levelList", levelList);
        request.setAttribute("updateStatusMessage", updateStatusMessage);

        // Forward to the practices list page
        request.getRequestDispatcher("questions_list.jsp").forward(request, response);
    }

}
