package Controller;

import Dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("reset_request.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("requestReset".equals(action)) {
            requestReset(request, response);
        } else if ("resetPassword".equals(action)) {
            resetPassword(request, response);
        }
    }

    public void requestReset(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDAO d = new UserDAO();
        if (d.isUserExists(email)) {
            if (d.sendResetPasswordEmail(email)) {
                request.setAttribute("message", "A reset link has been sent to your email.");
            } else {
                request.setAttribute("error", "Failed to send reset email.");
            }
        } else {
            request.setAttribute("error1", "Email hasn't been registered yet!");
        }
        request.getRequestDispatcher("reset_request.jsp").forward(request, response);
    }

    public void resetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String cfPass = request.getParameter("cfPassword");
        UserDAO d = new UserDAO();

        if (newPassword.length() < 8) {
            request.setAttribute("error", "Your new password must be at least 8 characters!");
        } else {
            if (!newPassword.equals(cfPass)) {
                request.setAttribute("error", "You've entered the wrong confirm password!");
            } else {
                if (d.isResetTokenValid(token) && d.updatePassword(token, newPassword)) {
                    request.setAttribute("message", "Password has been reset successfully!");
                } else {
                    request.setAttribute("error", "Failed to reset password");
                }
            }
        }
        request.getRequestDispatcher("reset_password.jsp").forward(request, response);
    }
}
