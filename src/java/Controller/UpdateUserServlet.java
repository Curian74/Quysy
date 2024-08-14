package Controller;

import Dal.UserDAO;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UpdateUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("account");

        int userId = Integer.parseInt(request.getParameter("user_id"));
        String fullName = request.getParameter("full_name");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String mobile = request.getParameter("mobile");
        int roleId = Integer.parseInt(request.getParameter("role_id"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        UserDAO dao = new UserDAO();
        User userToUpdate = dao.getUserById(userId);

        // Check if admin change their own account
        if (currentUser.getUserId() == userId && (currentUser.getRoleId() != roleId || currentUser.isStatus() != status)) {
            session.setAttribute("error", "You cannot change your own role or status!");
            response.sendRedirect("user-list");
            return;
        }

        // Check if admin change another admin account
        if (userToUpdate != null && userToUpdate.getRoleId() == 1) {
            session.setAttribute("error", "You cannot change role or status of another admin!");
            response.sendRedirect("user-list");
            return;
        }

        User user = new User(userId, null, null, fullName, gender, mobile, roleId, status, null, null, null, null);
        boolean result = dao.updateUser(user);

        if (result) {
            session.setAttribute("success", "User updated successfully!");
            response.sendRedirect("user-list");
        } else {
            session.setAttribute("error", "User update was unsuccessful!");
            response.sendRedirect("user-list");
        }
    }
}
