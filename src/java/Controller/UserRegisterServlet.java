package Controller;

import Dal.UserDAO;
import Utility.EmailUtility;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;

public class UserRegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("email");
            String fullname = request.getParameter("fullname");
            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
            String mobile = request.getParameter("mobile");

            // Generate a random password
            String generatedPassword = UUID.randomUUID().toString().substring(0, 8); // 8-characters password
            String hashedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt());

            // Generate a random token for email confirmation
            String token = UUID.randomUUID().toString();

            UserDAO d = new UserDAO();

            // Regular expressions for validation
            String namePattern = "^[\\p{L} ]+$"; // Allows only letters and spaces
            String mobilePattern = "^[0-9]{10}$"; // Allows digits with exact length of 10
            
            if(d.isMobileExists(mobile)){
                out.write("error:Phone number already exists.");
                return;
            }
            if (fullname.matches(namePattern) && mobile.matches(mobilePattern)) {
                if (d.saveUserTemp(email, hashedPassword, fullname, gender, mobile, token)) {
                    // Send the confirmation email to the user
                    String confirmLink = "http://localhost:9999/group6/confirm?token=" + token;
                    String subject = "Confirm Your Registration";
                    String message = "Click here to confirm your registration: <a href=\"" + confirmLink + "\">VerifyMyAccount</a><br>"
                            + "Once you have confirmed, here is your password: " + generatedPassword;
                    try {
                        EmailUtility.sendHtmlEmail(email, subject, message);
                        out.write("message:Registration successful! Check your email to confirm your registration.");
                    } catch (MessagingException e) {
                        out.write("error:Failed to send email.");
                        e.printStackTrace();
                    }
                } else {
                    out.write("error:User already exists.");
                }
            } else {
                if (!fullname.matches(namePattern)) {
                    out.write("error:Invalid name!.");
                } else if (!mobile.matches(mobilePattern)) {
                    out.write("error:Mobile number must be numeric and 10 characters long");
                }
            }
        }
    }
}
