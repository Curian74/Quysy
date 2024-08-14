package Controller;

import Dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmailConfirmationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");

        UserDAO d = new UserDAO();

        if (d.confirmUser(token)) {
            // Get email and password from the database using the token
            String email = d.getEmailByToken(token);
            String password = d.getPasswordByToken(token);

            if (email != null && password != null) {
                d.deleteUserFromUserTemp(token);
                // Redirect to login page with success message
                response.sendRedirect("home?showLoginModal=true&confirmed=true");
            } else {
                response.sendRedirect("home?showLoginModal=true&confirmed=false");
            }
        } else {
            response.sendRedirect("home?showLoginModal=true&confirmed=false");
        }
    }
}
