package Controller;

import Dal.RegistrationDAO;
import Dal.UserDAO;
import Model.Registration;
import Model.User;
import Utility.EmailUtility;
import jakarta.mail.MessagingException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

public class UpdateRegistrationServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateRegistrationServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            // Clear session message
            request.getSession().removeAttribute("errorRegistration");
            request.getSession().removeAttribute("successRegistration");
            
            // Initiate DAOs
            RegistrationDAO rDAO = new RegistrationDAO();
            UserDAO uDAO = new UserDAO();

            // Get parameters
            String registrationIdRaw = request.getParameter("registrationId");
            String subjectIdRaw = request.getParameter("subject");
            String packageIdRaw = request.getParameter("package");
            String fullName = request.getParameter("fullName");
            String genderRaw = request.getParameter("gender");
            String email = request.getParameter("email");
            String mobile = request.getParameter("mobile");
            String registrationStatusIdRaw = request.getParameter("registrationStatus");
            String notes = request.getParameter("notes");
            
            // Get current user
            User user = (User) request.getSession().getAttribute("account");

            // Parse and validate inputs
            int registrationId = Integer.parseInt(registrationIdRaw);
            int registrationStatusId = Integer.parseInt(registrationStatusIdRaw);

            // Retrieve existing registration from the database
            Registration registration = rDAO.getRegistrationByID(registrationId);
            
            // Check input boxes
            if (fullName.equals("") || email.equals("") || mobile.equals("") ||  notes.equals("")) {
                request.getSession().setAttribute("errorRegistration", "Please fill out all fields");
                response.sendRedirect("registration-details?registrationId=" + registrationId);
            }

            // Check if current user is the creator of the registration
            if (user.getUserId() == registration.getUserId()) {
                int subjectId = Integer.parseInt(subjectIdRaw);
                int packageId = Integer.parseInt(packageIdRaw);
                boolean gender = Boolean.parseBoolean(genderRaw);

                // Check if the email exists in the database
                if (!uDAO.isUserExists(email)) {
                    if (uDAO.isUserExists(email)) {
                        request.getSession().setAttribute("errorRegistration", "Email already exists.");
                        doGet(request, response);
                        return;
                    } else if (uDAO.isMobileExists(mobile)) {
                        request.getSession().setAttribute("errorRegistration", "Phone number already exists.");
                        doGet(request, response);
                        return;
                    }

                    // Regular expressions for validation
                    String namePattern = "^[\\p{L} ]+$"; // Allows only letters and spaces
                    String mobilePattern = "^[0-9]{10}$"; // Allows digits with exact length of 10

                    // Validate inputs
                    if (Pattern.matches(namePattern, fullName) && Pattern.matches(mobilePattern, mobile)) {
                        // Generate a random password
                        String generatedPassword = UUID.randomUUID().toString().substring(0, 8); // 8-characters password
                        String hashedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt());

                        if (uDAO.addUser(fullName, email, gender, mobile, 2, true, hashedPassword)) {
                            // Send the confirmation email to the user
                            String subjectMail = "Your Account Has Been Created";
                            String message = "Your account has been created. Here is your password: " + generatedPassword;
                            try {
                                EmailUtility.sendHtmlEmail(email, subjectMail, message);
                                // Set success attribute
                                request.getSession().setAttribute("successRegistration", "User added successfully! Email sent to the user.");
                            } catch (MessagingException e) {
                                request.getSession().setAttribute("errorRegistration", "User added, but failed to send email.");
                                LOGGER.log(Level.SEVERE, "User added, but failed to send email.", e);
                            }
                            doGet(request, response);
                        } else {
                            request.setAttribute("errorRegistration", "Failed to add user. Please try again.");
                            doGet(request, response);
                        }
                    } else {
                        String errorMessage = "";
                        if (!Pattern.matches(namePattern, fullName)) {
                            errorMessage += "Invalid name!";
                        }
                        if (!Pattern.matches(mobilePattern, mobile)) {
                            errorMessage += "Mobile number must be numeric and 10 characters long.";
                        }
                        request.setAttribute("errorRegistration", errorMessage);
                        doGet(request, response);
                    }
                }

                // Set editable fields
                registration.setSubjectId(subjectId);
                registration.setPackageId(packageId);
                registration.setFullName(fullName);
                registration.setGender(gender);
                registration.setEmail(email);
                registration.setMobile(mobile);
            }

            // Update registration details
            registration.setRegistrationStatus(registrationStatusId);
            registration.setNotes(notes);
            registration.setLastUpdatedBy(user.getUserId());

            // Save updated registration to the database
            boolean isSuccess = rDAO.updateRegistration(registration);

            // Provide feedback based on the operation outcome
            if (isSuccess) {
                request.getSession().setAttribute("successRegistration", "Registration updated successfully.");
            } else {
                request.getSession().setAttribute("errorRegistration", "Failed to update registration.");
            }

            // Redirect to a confirmation page or back to the registration details page
            response.sendRedirect("registration-details?registrationId=" + registrationId);

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Error parsing number", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating registration", e);
        }
    }

}
