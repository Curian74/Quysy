package Controller;

import Dal.UserDAO;
import Dal.LessonDAO;
import Dal.PackageDAO;
import Dal.QuizDAO;
import Dal.SubjectDAO;
import Model.*;
import Model.Package;
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
public class LessonDetailsServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LessonDetailsServlet.class.getName());
    private final LessonDAO lessonDAO = new LessonDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final PackageDAO packageDAO = new PackageDAO();
    private final QuizDAO quizDAO = new QuizDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int lessonId = getIntParameter(request, "lessonId");
            Lesson lesson = lessonDAO.getLessonById(lessonId);

            if (lesson == null) {
                response.sendRedirect("error.jsp?message=Lesson not found.");
                return;
            }
            if (lesson.getYoutubeLink() != null) {
                lesson.setYoutubeLink(lesson.getYoutubeLink());
            }
            if (lesson.getHtmlContent() != null) {
                lesson.setHtmlContent(processHtmlContent(lesson.getHtmlContent(), request));
            }
            if (lesson.getQuizId() != 0) {
                Quiz quiz = quizDAO.getQuizById(lesson.getQuizId());
                request.setAttribute("selectedQuiz", quiz);
            }
            lesson.setHtmlContent(processHtmlContent(lesson.getHtmlContent(), request));

            Subject subject = subjectDAO.getSubjectByID(lesson.getSubjectId());
            List<Package> packages = packageDAO.getPackagesForSubject(lesson.getSubjectId());
            List<LessonType> lessonTypes = lessonDAO.getAllLessonTypes();
            List<LessonTopic> lessonTopics = lessonDAO.getAllLessonTopics().stream()
                    .filter(topic -> topic.getLessonTopicId() == 1 || topic.getLessonTopicId() == 2 || topic.getLessonTopicId() == 3 || topic.getLessonTopicId() == 4)
                    .collect(Collectors.toList());
            List<Quiz> quizList = quizDAO.getAllQuizBySubject(lesson.getSubjectId());
            List<User> users = userDAO.getExpertsAndAdmins(lesson.getSubjectId());
            List<Integer> existingOrders = lessonDAO.getExistingLessonOrders(lesson.getSubjectId(), lesson.getPackageId());
            if (lesson.getLessonTypeId() == 1) {  // Only for Subject Topic lessons
                List<Lesson> sameOrderLessons = lessonDAO.getLessonsByOrderSubjectAndPackage(lesson.getLessonOrder(), lesson.getSubjectId(), lesson.getPackageId());
                request.setAttribute("sameOrderLessons", sameOrderLessons);
            }

            request.setAttribute("lesson", lesson);
            request.setAttribute("subject", subject);
            request.setAttribute("packages", packages);
            request.setAttribute("lessonTypes", lessonTypes);
            request.setAttribute("lessonTopics", lessonTopics);
            request.setAttribute("quizList", quizList);
            request.setAttribute("users", users);
            request.setAttribute("existingOrders", existingOrders);
            request.getRequestDispatcher("lesson_details.jsp").forward(request, response);
        } catch (ServletException e) {
            LOGGER.log(Level.SEVERE, "Error in doGet", e);
            response.sendRedirect("error.jsp?message=" + e.getMessage());
        }
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
        try {
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
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error handling file upload", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error uploading file: " + e.getMessage());
        }
    }

    private void handleFormSubmission(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int lessonId = getIntParameter(request, "lessonId");
            Lesson existingLesson = lessonDAO.getLessonById(lessonId);

            if (existingLesson == null) {
                response.sendRedirect("error.jsp?message=Lesson not found.");
                return;
            }
            String lessonName = request.getParameter("lessonName").trim();
            if (lessonName.isEmpty() || !lessonName.equals(request.getParameter("lessonName"))) {
                throw new ServletException("Lesson name cannot be empty, contain only spaces, or have spaces before the first letter");
            }

            int lessonTypeId = getIntParameter(request, "lessonTypeId");
            if (lessonTypeId == 2) {
                String youtubeLink = request.getParameter("youtubeLink").trim();
                String htmlContent = request.getParameter("htmlContent").trim();

                if (youtubeLink.isEmpty()) {
                    throw new ServletException("YouTube link cannot be empty for video lessons");
                }

                if (htmlContent.isEmpty()) {
                    throw new ServletException("HTML content cannot be empty for video lessons");
                }
            }
            // Extract updated parameters from request
            int lessonOrder = getIntParameter(request, "lessonOrder");
            String youtubeLink = request.getParameter("youtubeLink");
            String htmlContent = request.getParameter("htmlContent");
            htmlContent = processHtmlContent(htmlContent, request);
            int subjectId = getIntParameter(request, "subjectId");
            int topicId = getIntParameter(request, "topicId");
            int packageId = getIntParameter(request, "packageId");
            boolean status = Boolean.parseBoolean(request.getParameter("status"));
            if (lessonDAO.isLessonNameDuplicate(lessonName, subjectId, lessonId)) {
                throw new ServletException("A lesson with this name already exists!");
            }
            // Validate package existence
            Model.Package selectedPackage = packageDAO.getPackageByID(packageId);
            if (selectedPackage == null) {
                throw new ServletException("Selected package does not exist.");
            }
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("account");
            existingLesson.setUpdatedBy(currentUser.getUserId());
            existingLesson.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            // Validate quiz existence
            int quizId = 0;
            String quizIdStr = request.getParameter("quizId");
            if (quizIdStr != null && !quizIdStr.isEmpty()) {
                quizId = Integer.parseInt(quizIdStr);
                Quiz selectedQuiz = quizDAO.getQuizById(quizId);
                if (selectedQuiz == null) {
                    throw new ServletException("Selected quiz does not exist.");
                }
            }

            // Validate and set expert (userId)
            int expertId;
            String expertIdStr = request.getParameter("userId");
            if (expertIdStr != null && !expertIdStr.isEmpty()) {
                expertId = Integer.parseInt(expertIdStr);
                User selectedExpert = userDAO.getUserById(expertId);
                if (selectedExpert == null) {
                    throw new ServletException("Selected expert does not exist.");
                }
            } else {
                // If no expert is selected, keep the existing expert
                expertId = existingLesson.getUpdatedBy();
            }

            // Update the existing Lesson object
            existingLesson.setLessonName(lessonName);
            existingLesson.setLessonOrder(lessonOrder);
            existingLesson.setYoutubeLink(youtubeLink);
            existingLesson.setHtmlContent(htmlContent);
            existingLesson.setLessonTypeId(lessonTypeId);
            existingLesson.setSubjectId(subjectId);
            existingLesson.setTopicId(topicId);
            existingLesson.setPackageId(packageId);
            existingLesson.setStatus(status);
            existingLesson.setUpdatedBy(expertId);
            existingLesson.setQuizId(quizId);
            existingLesson.setUpdatedDate(new Timestamp(System.currentTimeMillis()));

            // Update lesson in the database
            boolean updated = lessonDAO.updateLesson(existingLesson);
            if (updated) {
                request.getSession().setAttribute("successMessage", "Lesson updated successfully.");
                response.sendRedirect("lesson-details?lessonId=" + lessonId);
            } else {
                request.setAttribute("errorMessage", "Failed to update the lesson. Please try again.");
                doGet(request, response);
            }
        } catch (ServletException e) {
            LOGGER.log(Level.WARNING, "Validation error: " + e.getMessage(), e);
            request.setAttribute("errorMessage", e.getMessage());
            doGet(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error in handleFormSubmission", e);
            request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            doGet(request, response);
        }
    }

    private String processHtmlContent(String htmlContent, HttpServletRequest request) {
        if (htmlContent == null) {
            return "";  // or return null, depending on how you want to handle it
        }
        System.out.println("Original HTML content: " + htmlContent);
        // Replace blob URLs with permanent URLs
        return htmlContent.replaceAll("blob:http://localhost:9999/[a-f0-9-]+", "/resources/images/lesson");
    }

    private String getUniqueFileName(String fileName) {
        return System.currentTimeMillis() + "_" + fileName;
    }

    private int getIntParameter(HttpServletRequest request, String paramName)
            throws ServletException {
        String param = request.getParameter(paramName);
        if (param != null && !param.isEmpty()) {
            try {
                return Integer.parseInt(param);
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid " + paramName, e);
            }
        } else {
            throw new ServletException(paramName + " is required.");
        }
    }
}
