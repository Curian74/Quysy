package Controller;

import Dal.CategoryDAO;
import Dal.LessonDAO;
import Dal.SubjectDAO;
import Dal.UserDAO;
import Model.Category;
import Model.Subject;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubjectsListControllerServlet extends HttpServlet {

    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final CategoryDAO catDAO = new CategoryDAO();
    private final LessonDAO lessonDAO = new LessonDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        if (service == null) {
            service = "listAllSubjects";
        }
        if (service.equals("listAllSubjects")) {
            listAllSubjects(request, response);
        }
    }

    private void listAllSubjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("account");
        int roleId = user.getRoleId();
        List<Category> categories = catDAO.getAllCategories();

        String searchName = trimParameter(request.getParameter("searchName"));
        String categoryIdStr = trimParameter(request.getParameter("category"));
        String statusStr = trimParameter(request.getParameter("status"));
        String expertName = trimParameter(request.getParameter("expertName"));
        String featuredStr = trimParameter(request.getParameter("featured"));
        String orderBy = trimParameter(request.getParameter("orderBy"));

        Integer categoryId = (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) ? Integer.valueOf(categoryIdStr) : null;
        Boolean status = (statusStr != null && !statusStr.trim().isEmpty()) ? Boolean.valueOf(statusStr) : null;
        Boolean isFeatured = (featuredStr != null && !featuredStr.trim().isEmpty()) ? Boolean.valueOf(featuredStr) : null;

        List<Subject> subjects = subjectDAO.searchSubjects(
                searchName,
                categoryId,
                status,
                (roleId == 3) ? user.getUserId() : null,
                expertName,
                isFeatured,
                orderBy
        );

        Map<Integer, Integer> subjectLessonCount = new HashMap<>();
        for (Subject subject : subjects) {
            int lessonCount = lessonDAO.getNumberOfLessons(subject.getSubjectId());
            subjectLessonCount.put(subject.getSubjectId(), lessonCount);
        }

        List<Integer> userIds = subjects.stream().map(Subject::getExpertId).distinct().collect(Collectors.toList());
        Map<Integer, String> userNames = userDAO.getUserNamesByIds(userIds);

        int pageSize = 5;
        int totalSubjects = subjects.size();
        int totalPages = (int) Math.ceil((double) totalSubjects / pageSize);
        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
                if (currentPage < 1) {
                    currentPage = 1; 
                }
            } catch (NumberFormatException e) {
                currentPage = 1; 
            }
        }

        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, totalSubjects);

        List<Subject> subjectsOnPage = subjects.subList(start, end);

        request.setAttribute("subjects", subjectsOnPage);
        request.setAttribute("categories", categories);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("subjectLessonCount", subjectLessonCount);
        request.setAttribute("userNames", userNames);
        request.setAttribute("searchName", searchName);
        request.setAttribute("categoryId", categoryIdStr);
        request.setAttribute("status", statusStr);
        request.setAttribute("expertName", expertName);
        request.setAttribute("isFeatured", featuredStr);
        request.setAttribute("orderBy", orderBy);

        request.getRequestDispatcher("subject_list.jsp").forward(request, response);
    }

    private String trimParameter(String param) {
        return (param != null) ? param.trim() : null;
    }
    
}
