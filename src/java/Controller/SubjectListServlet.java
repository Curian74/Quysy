package Controller;

import Dal.CategoryDAO;
import Dal.PackageDAO;
import Dal.SubjectDAO;
import Model.Category;
import Model.Subject;
import Model.Package;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectListServlet extends HttpServlet {

    //interact with DAO
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final CategoryDAO catDAO = new CategoryDAO();
    private final PackageDAO packageDAO = new PackageDAO();

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
        //retrieve data from database using the List instance
        List<Category> categories = catDAO.getAllCategories();
        List<Subject> subjects;
        List<Subject> feature = subjectDAO.getFeatureSubjects();
        List<Package> lowestPackages = packageDAO.getAllLowestPackages();
        //sider interacting
        String search = request.getParameter("search");
        String category = request.getParameter("category");

        if ((search != null && !search.trim().isEmpty()) && (category != null && !category.trim().isEmpty())) {
            subjects = subjectDAO.searchSubjectsByNameAndCategory(search, Integer.parseInt(category));
        } else if (search != null && !search.trim().isEmpty()) {
            subjects = subjectDAO.searchSubjectsByName(search);
        } else if (category != null && !category.trim().isEmpty()) {
            subjects = subjectDAO.getSubjectsByCategory(Integer.parseInt(category));
        } else {
            subjects = subjectDAO.getAllSubjectsSortByCreatedDate();
        }

        // map to store the lowest package for each subject
        Map<Integer, Package> lowestPackageMap = new HashMap<>();
        for (Package pkg : lowestPackages) {
            lowestPackageMap.put(pkg.getSubjectId(), pkg);
        }

        //paginate for main content
        int pageSize = 6;
        int totalSubjects = subjects.size();
        int totalPages = (int) Math.ceil((double) totalSubjects / pageSize);
        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, totalSubjects);

        List<Subject> subjectsOnPage = subjects.subList(start, end);

        //set attribute for data
        request.setAttribute("subject", subjectsOnPage);
        request.setAttribute("feature", feature);
        request.setAttribute("categories", categories);
        request.setAttribute("lowestPackageMap", lowestPackageMap);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        //display data by forward and response to SubjectList.jsp
        request.getRequestDispatcher("subject_list.jsp").forward(request, response);
    }

}
