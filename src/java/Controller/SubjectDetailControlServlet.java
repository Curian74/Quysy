package Controller;

import Dal.CategoryDAO;
import Dal.DimensionTypeDAO;
import Dal.DimensionsDAO;
import Dal.PackageDAO;
import Dal.SubjectDAO;
import Dal.SubjectDimensionDAO;
import Dal.UserDAO;
import Model.Category;
import Model.DimensionType;
import Model.Dimensions;
import Model.Subject;
import Model.Package;
import Model.SubjectDimension;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;

@MultipartConfig
public class SubjectDetailControlServlet extends HttpServlet {

    private final SubjectDAO sdao = new SubjectDAO();
    private final UserDAO udao = new UserDAO();
    private final CategoryDAO cdao = new CategoryDAO();
    private final SubjectDimensionDAO sddao = new SubjectDimensionDAO();
    private final PackageDAO pdao = new PackageDAO();
    private final DimensionTypeDAO dtdao = new DimensionTypeDAO();
    private final DimensionsDAO ddao = new DimensionsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        HttpSession session = request.getSession();

        User expert = udao.getExpertBySubjectId(subjectId);
        User user = (User) session.getAttribute("account");

        Subject s = sdao.getSubjectByID(subjectId);
        List<Category> categoryList = cdao.getAllCategories();
        List<User> expertList = udao.getAllExperts();
        List<SubjectDimension> dimensionList = sddao.getSubjectDimensionBySubjectId(subjectId);
        List<Package> packageList = pdao.getAllPackages();
        List<Dimensions> dlist = ddao.getAllDimensionsBySubjectId(subjectId);
        List<DimensionType> dType = dtdao.getDimensionTypesBySubjectId(subjectId);
        List<Package> packSub = pdao.allPackageController(subjectId);

        request.setAttribute("dType", dType);
        request.setAttribute("dList", dlist);
        request.setAttribute("packList", packageList);
        request.setAttribute("dimList", dimensionList);
        request.setAttribute("cat", categoryList);
        request.setAttribute("expert", expert);
        request.setAttribute("expertList", expertList);
        request.setAttribute("user", user);
        request.setAttribute("subject", s);
        request.setAttribute("packS", packSub);

        request.getRequestDispatcher("subject_detail_control.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        String subjectId_raw = request.getParameter("sId");
        User u = (User) session.getAttribute("account");
        Subject s;
        try {
            int subjectId = Integer.parseInt(subjectId_raw);
            if ("general".equalsIgnoreCase(service)) {
                String subjectName_raw = request.getParameter("subjectName");
                String cate_raw = request.getParameter("category");
                String feature_raw = request.getParameter("featuredSubject");
                String status_raw = request.getParameter("status");
                String owner_raw = request.getParameter("owner");
                String tagLine_raw = request.getParameter("tagLine");
                String brief_raw = request.getParameter("brief");
                String description_raw = request.getParameter("description");

                int categoryId = Integer.parseInt(cate_raw);
                boolean feature = Boolean.parseBoolean(feature_raw);
                boolean status = Boolean.parseBoolean(status_raw);
                s = new Subject();
                s.setSubjectId(subjectId);
                s.setCategoryId(categoryId);
                s.setSubjectName(subjectName_raw.trim());
                s.setIsFeatured(feature);
                s.setTagLine(tagLine_raw.trim());
                s.setBriefInfo(brief_raw.trim());
                s.setDescription(description_raw.trim());
                s.setStatus(status);

                if (owner_raw != null) {
                    int expertId = Integer.parseInt(owner_raw);
                    s.setExpertId(expertId);
                } else {
                    s.setExpertId(u.getUserId());
                }

                // Handle file upload
                Part filePart = request.getPart("img"); // Retrieves <input type="file" name="img">
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
                    String uploadDir = getServletContext().getRealPath("/") + "resources/images/subjects";
                    java.io.File uploadDirFile = new java.io.File(uploadDir);
                    if (!uploadDirFile.exists()) {
                        uploadDirFile.mkdirs();
                    }
                    String filePath = uploadDir + java.io.File.separator + fileName;
                    filePart.write(filePath);
                    s.setThumbnail("resources/images/subjects/" + fileName);
                } else {
                    // If no new image is uploaded, retain the existing thumbnail path
                    Subject existingSubject = sdao.getSubjectByID(subjectId);
                    s.setThumbnail(existingSubject.getThumbnail());
                }

                boolean check = sdao.editSubjectGeneralInfo(s);

                if (check) {
                    session.setAttribute("updateMessage", "Subject updated successfully");
                    response.sendRedirect("subject-detail-control?subjectId=" + subjectId);
                } else {
                    session.setAttribute("updateMessage", "Subject update failed");
                    response.sendRedirect("subject-detail-control?subjectId=" + subjectId);
                }
            }
        } catch (ServletException | IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

}
