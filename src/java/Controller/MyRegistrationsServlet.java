package Controller;

import Dal.CategoryDAO;
import Dal.RegistrationDAO;
import Model.Category;
import Model.Registration;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class MyRegistrationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Initiate DAOs
        CategoryDAO cDAO = new CategoryDAO();
        RegistrationDAO rDAO = new RegistrationDAO();

        // Initiate current session
        HttpSession session = request.getSession();

        // Get current user
        User u = (User) session.getAttribute("account");

        // Get categories
        List<Category> categoryList = cDAO.getAllCategories();

        String categoryIdRaw = request.getParameter("categoryId");
        int categoryId;
        try {
            categoryId = Integer.parseInt(categoryIdRaw);
        } catch (NumberFormatException e) {
            categoryId = 0;
        }

        // Get registrations list
        List<Registration> registrationsList = rDAO.getMyRegistrations(u, categoryId);
        
        // Get update status mesage
        String updateStatusMessage = request.getParameter("updateStatusMessage");

        // Set attributes
        if (categoryId != 0) {
            request.setAttribute("categoryId", categoryIdRaw);
        }

        request.setAttribute("registrationsList", registrationsList);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("updateStatusMessage", updateStatusMessage);

        // Forward to my registrations page
        request.getRequestDispatcher("my_registrations.jsp").forward(request, response);
    }
    
}
