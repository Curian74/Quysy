package Controller;

import Dal.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UserDAO d = new UserDAO();

            if (d.validateLogin(email, password)) {
                if (d.validateStatus(email)) {
                    HttpSession session = request.getSession();
                    User u = d.getUserByEmail(email);
                    session.setAttribute("account", u);
                } else {
                    out.write("error:Your account has been locked!");
                }
            } else {
                out.write("error:Wrong account or password!");
            }
        }
    }
}
