package Controller;

import Dal.UserDAO;
import Model.Role;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();

        // Get user by their id
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userDAO.getUserById(userId);

        // Get roles
        List<Role> roles = userDAO.getAllRoles();

        request.setAttribute("user", user);
        request.setAttribute("roles", roles);

        request.getRequestDispatcher("user_details.jsp").forward(request, response);
    }
}
