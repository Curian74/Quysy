package Controller;

import Dal.UserDAO;
import Utility.EmailUtility;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;
import Model.Role;

public class AddUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        List<Role> roles = userDAO.getAllRoles();
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("add_user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("full_name").trim();
        String email = request.getParameter("email").trim();
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String mobile = request.getParameter("mobile").trim();
        int roleId = Integer.parseInt(request.getParameter("role_id"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        // Regular expressions for validation
        String namePattern = "^[\\p{L} ]+$";
        String mobilePattern = "^[0-9]{10}$";

        UserDAO userDAO = new UserDAO();

        // Check if email or mobile already exists
        if (userDAO.isUserExists(email)) {
            request.setAttribute("error", "Email already exists.");
            doGet(request, response);
            return;
        } else if (userDAO.isMobileExists(mobile)) {
            request.setAttribute("error", "Phone number already exists.");
            doGet(request, response);
            return;
        }

        // Validate inputs
        if (Pattern.matches(namePattern, fullName) && Pattern.matches(mobilePattern, mobile)) {
            // Generate a random password
            String generatedPassword = UUID.randomUUID().toString().substring(0, 8); // 8-characters password
            String hashedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt());

            if (userDAO.addUser(fullName, email, gender, mobile, roleId, status, hashedPassword)) {
                // Send the confirmation email to the user
                String subject = "Your Account Has Been Created";
                String message = "Your account has been created. Here is your password: " + generatedPassword;
                try {
                    EmailUtility.sendHtmlEmail(email, subject, message);
                    // Set success attribute
                    request.setAttribute("success", "User added successfully! Email sent to the user.");
                } catch (MessagingException e) {
                    request.setAttribute("error", "User added, but failed to send email.");
                    e.printStackTrace();
                }
                doGet(request, response);
            } else {
                request.setAttribute("error", "Failed to add user. Please try again.");
                doGet(request, response);
            }
        } else {
            String errorMessage = "";
            if (!Pattern.matches(namePattern, fullName)) {
                errorMessage += "Invalid name! ";
            }
            if (!Pattern.matches(mobilePattern, mobile)) {
                errorMessage += "Invalid mobile number! Must be numeric and 10 characters long.";
            }
            request.setAttribute("error", errorMessage);
            doGet(request, response);
        }
    }
}
