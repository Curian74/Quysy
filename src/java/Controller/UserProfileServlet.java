package Controller;

import Dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.User;
import jakarta.servlet.http.HttpSession;

public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("account");
        int userId = u.getUserId();
        
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(userId);
        
        request.setAttribute("user", user);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}
