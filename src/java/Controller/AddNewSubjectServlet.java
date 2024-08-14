package Controller;

import Dal.CategoryDAO;
import Dal.SubjectDAO;
import Dal.UserDAO;
import Model.Category;
import Model.Subject;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@MultipartConfig
public class AddNewSubjectServlet extends HttpServlet {

    private final SubjectDAO sdao = new SubjectDAO();
    private final CategoryDAO cdao = new CategoryDAO();
    private final UserDAO udao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Category> catList = cdao.getAllCategories();
        List<User> expertList = udao.getAllExperts();
        List<Subject> subjectList = sdao.getAllSubjects();

        // Sort the lists alphabetically 
        Collections.sort(expertList, (u1, u2) -> u1.getFullname().compareToIgnoreCase(u2.getFullname()));
        Collections.sort(catList, (u1, u2) -> u1.getCategoryName().compareToIgnoreCase(u2.getCategoryName()));
        Collections.sort(subjectList, (u1, u2) -> u1.getSubjectName().compareToIgnoreCase(u2.getSubjectName()));
        Collections.sort(expertList, (u1, u2) -> u1.getFullname().compareToIgnoreCase(u2.getFullname()));

        // Set attributes and redirect to jsp page
        request.setAttribute("catList", catList);
        request.setAttribute("sList", subjectList);
        request.setAttribute("expertList", expertList);
        request.getRequestDispatcher("new_subject.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Get parameters
        String s_raw = request.getParameter("subject");
        String catId_raw = request.getParameter("cat");
        String feat_raw = request.getParameter("featuredSubject");
        String owner_raw = request.getParameter("owner");
        String status_raw = request.getParameter("status");
        String tagLine = request.getParameter("tag");
        String brief = request.getParameter("brief");
        String description = request.getParameter("description");

        List<Category> catList = cdao.getAllCategories();
        List<User> expertList = udao.getAllExperts();
        List<Subject> subjectList = sdao.getAllSubjects();

        try {
            // Validate lengths
            if (s_raw.length() < 3 || s_raw.length() > 10) {
                session.setAttribute("addFail", "Subject Name must be between 3 - 10 characters long");
                forwardWithAttributes(request, response, s_raw, catId_raw, feat_raw, owner_raw, status_raw, tagLine, brief, description, catList, subjectList, expertList);
                return;
            }
            if (tagLine.length() > 30) {
                session.setAttribute("addFail", "Tag line must be between 1 - 30 characters long");
                forwardWithAttributes(request, response, s_raw, catId_raw, feat_raw, owner_raw, status_raw, tagLine, brief, description, catList, subjectList, expertList);
                return;
            }
            if (brief.length() > 100) {
                session.setAttribute("addFail", "Brief info must be between 1 - 100 characters long");
                forwardWithAttributes(request, response, s_raw, catId_raw, feat_raw, owner_raw, status_raw, tagLine, brief, description, catList, subjectList, expertList);
                return;
            }
            if (description.length() > 255) {
                session.setAttribute("addFail", "Description must be between 1 - 255 characters long");
                forwardWithAttributes(request, response, s_raw, catId_raw, feat_raw, owner_raw, status_raw, tagLine, brief, description, catList, subjectList, expertList);
                return;
            }

            for (Subject s : subjectList) {
                if (s.getSubjectName().equalsIgnoreCase(s_raw)) {
                    session.setAttribute("addFail", "Add failed! The subject \"" + s.getSubjectName() + "\" already exists");
                    forwardWithAttributes(request, response, s_raw, catId_raw, feat_raw, owner_raw, status_raw, tagLine, brief, description, catList, subjectList, expertList);
                    return;
                }
            }

            // Parse raw data
            int categoryId = Integer.parseInt(catId_raw);
            boolean isFeatured = Boolean.parseBoolean(feat_raw);
            int expertId = Integer.parseInt(owner_raw);
            boolean status = Boolean.parseBoolean(status_raw);

            Subject s = new Subject();

            // Image file handle
            Part filePart = request.getPart("thumb");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            String uploadDir = getServletContext().getRealPath("/") + "resources/images/subjects";
            java.io.File uploadDirFile = new java.io.File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            String filePath = uploadDir + java.io.File.separator + fileName;
            filePart.write(filePath);

            // Prepare data to update
            s.setThumbnail("resources/images/subjects/" + fileName);
            s.setSubjectName(s_raw.trim());
            s.setCategoryId(categoryId);
            s.setIsFeatured(isFeatured);
            s.setStatus(status);
            s.setTagLine(tagLine.trim());
            s.setBriefInfo(brief.trim());
            s.setDescription(description.trim());
            s.setExpertId(expertId);

            int newSubjectId = sdao.addSubject(s);

            if (newSubjectId != -1) {
                session.setAttribute("addSuccess", "New subject added successfully");
                session.setAttribute("target", newSubjectId);
                response.sendRedirect("new-subject");
            } else {
                session.setAttribute("addFail", " Failed to add new subject");
                forwardWithAttributes(request, response, s_raw, catId_raw, feat_raw, owner_raw, status_raw, tagLine, brief, description, catList, subjectList, expertList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("addFail", " Failed to add new subject");
            forwardWithAttributes(request, response, s_raw, catId_raw, feat_raw, owner_raw, status_raw, tagLine, brief, description, catList, subjectList, expertList);
        }
    }

    private void forwardWithAttributes(HttpServletRequest request, HttpServletResponse response, String s_raw, String catId_raw, String feat_raw, String owner_raw, String status_raw, String tagLine, String brief, String description, List<Category> catList, List<Subject> subjectList, List<User> expertList) throws ServletException, IOException {
        request.setAttribute("subject", s_raw);
        request.setAttribute("cat", catId_raw);
        request.setAttribute("featuredSubject", feat_raw);
        request.setAttribute("owner", owner_raw);
        request.setAttribute("status", status_raw);
        request.setAttribute("tag", tagLine);
        request.setAttribute("brief", brief);
        request.setAttribute("description", description);
        request.setAttribute("catList", catList);
        request.setAttribute("sList", subjectList);
        request.setAttribute("expertList", expertList);
        request.getRequestDispatcher("new_subject.jsp").forward(request, response);
    }
}
