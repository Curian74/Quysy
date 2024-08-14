package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Dal.UserDAO;
import Model.User;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

public class ChangePasswordServlet extends HttpServlet {

    private final UserDAO dao = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        String currentPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("newPassword");
        String confirmPass = request.getParameter("confirmPassword");
        if (!BCrypt.checkpw(currentPass, user.getPassword())) {
            out.println("{ \"success\": false, \"message\": \"Wrong password\" }");
            return;
        }
        if (!newPass.equals(confirmPass)) {
            out.println("{ \"success\": false, \"message\": \"Passwords must match\" }");
            return;
        }
        if (BCrypt.checkpw(newPass, user.getPassword())) {
            out.println("{ \"success\": false, \"message\": \"Password must not be the same as current password\" }");
            return;
        }
        if (newPass.length() < 8) {
            out.println("{ \"success\": false, \"message\": \"New password must have at least 8 characters\" }");
            return;
        }
        if (newPass.contains(" ") || currentPass.contains(" ") || confirmPass.contains(" ")) {
            out.println("{ \"success\": false, \"message\": \"Invalid password format\" }");
            return;
        }
        String hashedNewPass = BCrypt.hashpw(newPass, BCrypt.gensalt());
        dao.changePassword(user.getUserId(), hashedNewPass);
        user.setPassword(hashedNewPass);
        out.println("{ \"success\": true, \"message\": \"Password updated successfully\" }");
    }

}
