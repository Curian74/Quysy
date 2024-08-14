package Controller;

import Dal.LessonDAO;
import Dal.SubjectDAO;
import Dal.PackageDAO;
import Dal.QuizDAO;
import Dal.UserDAO;
import Model.Lesson;
import Model.LessonTopic;
import Model.LessonType;
import Model.Subject;
import Model.Package;
import Model.Quiz;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import java.util.logging.Logger;
import java.util.logging.Level;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class AddLessonServlet extends HttpServlet {

    private final LessonDAO lessonDAO = new LessonDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final PackageDAO packageDAO = new PackageDAO();
    private final QuizDAO quizDAO = new QuizDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int subjectId = getIntParameter(request, "subjectId");
        int packageId = getIntParameter(request, "packageId");

        Subject subject = subjectDAO.getSubjectByID(subjectId);
        if (subject == null) {
            response.sendRedirect("error.jsp?message=Subject not found.");
            return;
        }

        List<Quiz> quizList = quizDAO.getAllQuizBySubject(subjectId);
        List<Package> packages = packageDAO.getPackagesForSubject(subjectId);
        List<LessonType> lessonTypes = lessonDAO.getAllLessonTypes();
        List<LessonTopic> lessonTopics = lessonDAO.getAllLessonTopics().stream()
                .filter(topic -> topic.getLessonTopicId() == 1 || topic.getLessonTopicId() == 2 || topic.getLessonTopicId() == 3 || topic.getLessonTopicId() == 4)
                .collect(Collectors.toList());
        List<User> users = userDAO.getExpertsAndAdmins(subjectId);
        int latestOrder = lessonDAO.getLatestLessonOrder(subjectId, packageId);
        List<Integer> existingOrders = lessonDAO.getExistingLessonOrders(subjectId, packageId);

        request.setAttribute("subject", subject);
        request.setAttribute("packages", packages);
        request.setAttribute("quizList", quizList);
        request.setAttribute("users", users);
        request.setAttribute("lessonTypes", lessonTypes);
        request.setAttribute("lessonTopics", lessonTopics);
        request.setAttribute("latestOrder", latestOrder);
        request.setAttribute("existingOrders", existingOrders);

        // Check for messages in the session and transfer them to request attributes
        HttpSession session = request.getSession();
        String successMessage = (String) session.getAttribute("successMessage");
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        request.getRequestDispatcher("add_lesson.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contentType = request.getContentType();
        if (contentType != null && contentType.toLowerCase().startsWith("multipart/form-data")) {
            handleCKEditorUpload(request, response);
        } else {
            handleFormSubmission(request, response);
        }
    }

    private void handleCKEditorUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("upload");
        String fileName = getUniqueFileName(filePart.getSubmittedFileName());
        String uploadPath = getServletContext().getRealPath("/uploads/");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        String fileUrl = request.getContextPath() + "/uploads/" + fileName;

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(
                "<script>window.parent.CKEDITOR.tools.callFunction("
                + request.getParameter("CKEditorFuncNum") + ", \"" + fileUrl + "\");</script>"
        );
    }

    private void handleFormSubmission(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            // Extract parameters from request
            String lessonName = request.getParameter("lessonName");
            int lessonOrder = getIntParameter(request, "lessonOrder");
            String youtubeLink = request.getParameter("youtubeLink");
            String htmlContent = request.getParameter("htmlContent");
            int lessonTypeId = getIntParameter(request, "lessonTypeId");
            int subjectId = getIntParameter(request, "subjectId");
            int topicId = getIntParameter(request, "topicId");
            int packageId = getIntParameter(request, "packageId");
            boolean status = Boolean.parseBoolean(request.getParameter("status"));

            // Validations
            if (lessonName == null || lessonName.trim().isEmpty()) {
                throw new IllegalArgumentException("Lesson name cannot be empty or consist only of spaces");
            }
            if (lessonName.charAt(0) == ' ') {
                throw new IllegalArgumentException("Lesson name cannot start with a space");
            }
            if (lessonDAO.isLessonNameDuplicate(lessonName, subjectId, 0)) {
                throw new IllegalArgumentException("A lesson with this name already exists!");
            }
            if (lessonOrder <= 0) {
                throw new IllegalArgumentException("Lesson order must be a positive number");
            }

            if (lessonTypeId == 2) {
                if (htmlContent == null || htmlContent.trim().isEmpty()) {
                    throw new IllegalArgumentException("HTML content must be filled for Video lessons");
                }
                if (youtubeLink == null || youtubeLink.trim().isEmpty()) {
                    throw new IllegalArgumentException("YouTube link must be filled for Video lessons");
                }
            }

            Package selectedPackage = packageDAO.getPackageByID(packageId);
            if (selectedPackage == null) {
                throw new IllegalArgumentException("Selected package does not exist.");
            }

            int quizId = 0;
            String quizIdStr = request.getParameter("quizId");
            if (quizIdStr != null && !quizIdStr.isEmpty()) {
                quizId = Integer.parseInt(quizIdStr);
                Quiz selectedQuiz = quizDAO.getQuizById(quizId);
                if (selectedQuiz == null) {
                    throw new IllegalArgumentException("Selected quiz does not exist.");
                }
            }

            User currentUser = (User) request.getSession().getAttribute("currentUser");
            int expertId;
            String expertIdStr = request.getParameter("userId");
            if (expertIdStr != null && !expertIdStr.isEmpty()) {
                expertId = Integer.parseInt(expertIdStr);
                User selectedExpert = userDAO.getUserById(expertId);
                if (selectedExpert == null) {
                    throw new IllegalArgumentException("Selected expert does not exist.");
                }
            } else {
                expertId = currentUser.getUserId();
            }

            Lesson newLesson = new Lesson();
            newLesson.setLessonName(lessonName);
            newLesson.setLessonOrder(lessonOrder);
            newLesson.setYoutubeLink(youtubeLink);
            newLesson.setHtmlContent(htmlContent);
            newLesson.setLessonTypeId(lessonTypeId);
            newLesson.setSubjectId(subjectId);
            newLesson.setTopicId(topicId);
            newLesson.setPackageId(packageId);
            newLesson.setStatus(status);
            newLesson.setUpdatedBy(expertId);
            newLesson.setQuizId(quizId);

            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            newLesson.setCreatedDate(currentTimestamp);
            newLesson.setUpdatedDate(currentTimestamp);

            boolean inserted = lessonDAO.insertLesson(newLesson);
            if (inserted) {
                session.setAttribute("successMessage", "Lesson added successfully! Please click 'Back' to return!");
            } else {
                throw new IllegalStateException("Failed to add the lesson. Please try again.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            session.setAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            session.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
        }

        // Redirect to the same page with query parameters
        String packageId = request.getParameter("packageId");
        String subjectId = request.getParameter("subjectId");
        response.sendRedirect("new-lesson?packageId=" + packageId + "&subjectId=" + subjectId);
    }

    private String getUniqueFileName(String fileName) {
        return System.currentTimeMillis() + "_" + fileName;
    }

    private int getIntParameter(HttpServletRequest request, String paramName)
            throws ServletException {
        String param = request.getParameter(paramName);
        if (param != null && !param.isEmpty()) {
            int value = Integer.parseInt(param);
            if (value <= 0) {
                throw new ServletException(paramName + " must be a positive number");
            }
            return value;          
        } else {
            throw new ServletException(paramName + " is required.");
        }
    }
}