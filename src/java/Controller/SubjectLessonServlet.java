package Controller;

import Dal.LessonDAO;
import Dal.SubjectDAO;
import Dal.PackageDAO;
import Model.Lesson;
import Model.LessonTopic;
import Model.LessonType;
import Model.Subject;
import Model.User;
import Model.Package;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubjectLessonServlet extends HttpServlet {

    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final PackageDAO packDAO = new PackageDAO();
    private final LessonDAO lessonDAO = new LessonDAO();

    private static final int LESSONS_PER_PAGE = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        if (service != null) {
            service = service.trim();
        }
        if (null == service) {
            listAllLessons(request, response);
        } else switch (service) {
            case "listAllSubjects" -> listAllLessons(request, response);
            case "toggleStatus" -> toggleLessonStatus(request, response);
            case "deleteLesson" -> deleteLesson(request, response);
            default -> {
            }
        }
    }

    private void listAllLessons(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subjectIdParam = request.getParameter("subjectId");
        String packageIdParam = request.getParameter("packageId");
        String pageParam = request.getParameter("page");
        String lessonType = request.getParameter("lessonType");
        String lessonTopic = request.getParameter("lessonTopic");
        String lessonName = request.getParameter("lessonName");
        String updatedBy = request.getParameter("updatedBy");
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        String lessonOrder = request.getParameter("lessonOrder");

        // Trim parameters if not null
        if (subjectIdParam != null) subjectIdParam = subjectIdParam.trim();
        if (packageIdParam != null) packageIdParam = packageIdParam.trim();
        if (lessonType != null) lessonType = lessonType.trim();
        if (lessonTopic != null) lessonTopic = lessonTopic.trim();
        if (lessonName != null) lessonName = lessonName.trim();
        if (updatedBy != null) updatedBy = updatedBy.trim();
        if (sortBy != null) sortBy = sortBy.trim();
        if (sortOrder != null) sortOrder = sortOrder.trim();
        if (dateFrom != null) dateFrom = dateFrom.trim();
        if (dateTo != null) dateTo = dateTo.trim();
        if (lessonOrder != null) lessonOrder = lessonOrder.trim();

        int subjectId;
        if (subjectIdParam != null && !subjectIdParam.isEmpty()) {
            try {
                subjectId = Integer.parseInt(subjectIdParam);
            } catch (NumberFormatException e) {
                response.sendRedirect("error.jsp?message=Invalid subject ID");
                return;
            }
        } else {
            response.sendRedirect("error.jsp?message=Subject ID is required");
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("account");
        int roleId = user.getRoleId();

        Subject subject = subjectDAO.getSubjectByID(subjectId);
        if (subject == null) {
            throw new ServletException("Subject not found.");
        }

        List<Package> packages = new ArrayList<>();
        Map<Integer, List<Lesson>> packageLessons = new HashMap<>();
        List<Package> packSub = packDAO.getAllPackages();

        int page = 1;
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                response.sendRedirect("error.jsp?message=Invalid page number");
                return;
            }
        }

        Integer packageId = null;
        if (packageIdParam != null && !packageIdParam.isEmpty()) {
            try {
                packageId = Integer.parseInt(packageIdParam);
            } catch (NumberFormatException e) {
                response.sendRedirect("error.jsp?message=Invalid package ID");
                return;
            }
        }

        List<Lesson> filteredLessons = lessonDAO.getFilteredSortedLessons(
                subjectId, packageId, lessonType, lessonTopic,
                lessonName, updatedBy, sortBy, sortOrder, dateFrom, dateTo, lessonOrder);

        int totalLessons = filteredLessons.size();
        int totalPages = (int) Math.ceil((double) totalLessons / LESSONS_PER_PAGE);
        int startIndex = (page - 1) * LESSONS_PER_PAGE;
        int endIndex = Math.min(startIndex + LESSONS_PER_PAGE, totalLessons);

        List<Lesson> paginatedLessons = filteredLessons.subList(startIndex, endIndex);

        if (packageId != null) {
            Package specificPackage = packDAO.getPackageByID(packageId);
            if (specificPackage != null && specificPackage.getSubjectId() == subjectId) {
                packages.add(specificPackage);
                packageLessons.put(packageId, paginatedLessons);
            }
        } else {
            packages = packDAO.getPackagesForSubject(subjectId);
            if (packages == null) {
                packages = new ArrayList<>();
            }
            for (Package pkg : packages) {
                if (pkg.getSubjectId() != subjectId) {
                    continue;
                }
                packageLessons.put(pkg.getPackageId(), paginatedLessons);
            }
        }

        List<Lesson> typeLessons = lessonDAO.getLessonsByTypeAndSubject(1, subjectId);
        List<LessonType> lessonTypes = lessonDAO.getAllLessonTypes();
        List<LessonTopic> lessonTopics = lessonDAO.getAllLessonTopics();
        List<User> users = lessonDAO.getAllUsers();
        List<Lesson> allLessons = lessonDAO.getLessonsBySubjectID(subjectId);
       
        List<Lesson> lessonGroups = lessonDAO.getLessonGroupsBySubject(subjectId);
        request.setAttribute("lessonGroups", lessonGroups);
        request.setAttribute("allLessons", allLessons);
        request.setAttribute("subject", subject);
        request.setAttribute("packages", packages);
        request.setAttribute("packageLessons", packageLessons);
        request.setAttribute("roleId", roleId);
        request.setAttribute("typeLessons", typeLessons);
        request.setAttribute("packS", packSub);
        request.setAttribute("packageId", packageIdParam);
        request.setAttribute("lessonTypes", lessonTypes);
        request.setAttribute("lessonTopics", lessonTopics);
        request.setAttribute("selectedType", lessonType);
        request.setAttribute("selectedTopic", lessonTopic);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("users", users);

        request.getRequestDispatcher("subject_lessons.jsp").forward(request, response);
    }

private void toggleLessonStatus(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String lessonIdParam = request.getParameter("lessonId");
    int lessonId = Integer.parseInt(lessonIdParam);
    Lesson lesson = lessonDAO.getLessonById(lessonId);
    lesson.setStatus(!lesson.getStatus());
    
    HttpSession session = request.getSession();
    User currentUser = (User) session.getAttribute("account");
    lesson.setUpdatedBy(currentUser.getUserId());
    
    lessonDAO.updateLesson(lesson);

    session.setAttribute("statusMessage", "Lesson status changed successfully.");

    String page = request.getParameter("page");
    response.sendRedirect("subject-lesson?subjectId=" + lesson.getSubjectId()
            + "&packageId=" + request.getParameter("packageId")
            + "&page=" + page);
}

    private void deleteLesson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lessonIdParam = request.getParameter("lessonId");
        int lessonId = Integer.parseInt(lessonIdParam);

        boolean deleted = lessonDAO.deleteLesson(lessonId);

        HttpSession session = request.getSession();
        if (deleted) {
            session.setAttribute("statusMessage", "Lesson deleted successfully.");
        } else {
            session.setAttribute("statusMessage", "Failed to delete lesson.");
        }

        String subjectId = request.getParameter("subjectId");
        String packageId = request.getParameter("packageId");
        String page = request.getParameter("page");

        response.sendRedirect("subject-lesson?subjectId=" + subjectId
                + "&packageId=" + packageId
                + "&page=" + page);
    }
}