package Controller;

import Dal.CategoryDAO;
import Dal.SubjectDAO;
import Dal.PackageDAO;
import Model.Subject;
import Model.Package;
import Model.Category;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

public class SubjectDetailServlet extends HttpServlet {

    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final CategoryDAO catDAO = new CategoryDAO();
    private final PackageDAO packageDAO = new PackageDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set CORS headers
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Retrieve subject details
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        Subject subject = subjectDAO.getSubjectByID(subjectId);

        // Retrieve all packages for the subject
        List<Package> packages = packageDAO.getPackagesForSubject(subjectId);

        // Find the lowest price package
        Package lowestPricePackage = packageDAO.getLowestPackage(subjectId);

        // Retrieve categories and featured subjects for the sidebar
        List<Category> categories = catDAO.getAllCategories();
        List<Subject> feature = subjectDAO.getFeatureSubjects();

        // Set attributes for JSP
        request.setAttribute("subject", subject);
        request.setAttribute("lowestPack", lowestPricePackage);
        request.setAttribute("packList", packages);

        // Set sidebar attributes
        request.setAttribute("categories", categories);
        request.setAttribute("feature", feature);

        // Forward to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("subject_details.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle preflight request
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
