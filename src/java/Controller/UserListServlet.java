package Controller;

import Dal.UserDAO;
import Model.Role;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();

        HttpSession session = request.getSession();
        String successMessage = (String) session.getAttribute("success");
        String errorMessage = (String) session.getAttribute("error");
        if (successMessage != null) {
            request.setAttribute("success", successMessage);
            session.removeAttribute("success");
        } else if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
            session.removeAttribute("error");
        }

        // Get all roles
        List<Role> roles = userDAO.getAllRoles();
        request.setAttribute("roles", roles);

        // Get filtered users
        String roleParam = request.getParameter("role");
        int role = roleParam == null || roleParam.isEmpty() ? 0 : Integer.parseInt(roleParam);
        String statusParam = request.getParameter("status");
        Boolean status = statusParam == null || statusParam.isEmpty() ? null : Boolean.parseBoolean(statusParam);
        String genderParam = request.getParameter("gender");
        Boolean gender = genderParam == null || genderParam.isEmpty() ? null : Boolean.parseBoolean(genderParam);

        List<User> users = userDAO.getUsers(role, status, gender);

        request.setAttribute("users", users);
        request.setAttribute("role", roleParam);
        request.setAttribute("status", statusParam);
        request.setAttribute("gender", genderParam);

        request.getRequestDispatcher("users_list.jsp").forward(request, response);
    }
}
