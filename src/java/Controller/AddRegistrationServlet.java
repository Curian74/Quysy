package Controller;

import Dal.PackageDAO;
import Dal.RegistrationDAO;
import Dal.RegistrationStatusDAO;
import Dal.SubjectDAO;
import Dal.UserDAO;
import Model.Package;
import Model.Registration;
import Model.RegistrationStatus;
import Model.Subject;
import Model.User;
import Utility.EmailUtility;
import jakarta.mail.MessagingException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

public class AddRegistrationServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateRegistrationServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initiate DAO
        PackageDAO pDAO = new PackageDAO();
        RegistrationStatusDAO rsDAO = new RegistrationStatusDAO();
        SubjectDAO sDAO = new SubjectDAO();

        // Get package prices
        Package price = pDAO.getPackageByID(1);

        // Get neccessary datas for select boxes
        List<Package> packageList = pDAO.getPackagesForSubject(1);
        List<RegistrationStatus> registrationStatusList = rsDAO.getAllRegistrationStatus();
        List<Subject> subjectList = sDAO.getAllSubjects();

        // Set attribute
        request.setAttribute("registrationStatusList", registrationStatusList);
        request.setAttribute("subjectList", subjectList);
        request.setAttribute("packageList", packageList);
        request.setAttribute("price", price);

        // Forward to page
        request.getRequestDispatcher("add_registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            // Initiate DAOs
            PackageDAO pDAO = new PackageDAO();
            RegistrationDAO rDAO = new RegistrationDAO();
            SubjectDAO sDAO = new SubjectDAO();
            UserDAO uDAO = new UserDAO();

            // Get parameters
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
            int subjectId = Integer.parseInt(subjectIdRaw);
            int packageId = Integer.parseInt(packageIdRaw);
            boolean gender = Boolean.parseBoolean(genderRaw);
            int registrationStatusId = Integer.parseInt(registrationStatusIdRaw);

            // Retrieve or create the necessary objects
            Package pkg = pDAO.getPackageByID(packageId);
            
            Subject subject = sDAO.getSubjectByID(subjectId);

            // Regular expressions for validation
            String namePattern = "^[\\p{L} ]+$";
            String mobilePattern = "^[0-9]{10}$";

            // Check if email or mobile already exists
            if (uDAO.isUserExists(email)) {
                request.setAttribute("error", "Email already exists.");
                doGet(request, response);
                return;
            } else if (uDAO.isMobileExists(mobile)) {
                request.setAttribute("error", "Phone number already exists.");
                doGet(request, response);
                return;
            }

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
                        request.setAttribute("success", "User added successfully! Email sent to the user.");
                    } catch (MessagingException e) {
                        request.setAttribute("error", "User added, but failed to send email.");
                        LOGGER.log(Level.SEVERE, "User added, but failed to send email.", e);
                    }
                    doGet(request, response);
                } else {
                    request.setAttribute("error", "Failed to add user. Please try again.");
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
                request.setAttribute("error", errorMessage);
                doGet(request, response);
            }

            // Set registration details
            Registration registration = new Registration();
            registration.setSubjectId(subjectId);
            registration.setPackageId(packageId);
            registration.setFullName(fullName);
            registration.setGender(gender);
            registration.setEmail(email);
            registration.setMobile(mobile);
            registration.setRegistrationStatus(registrationStatusId);
            registration.setNotes(notes);
            registration.setLastUpdatedBy(user.getUserId());

            // Save updated registration to the database
            boolean isSuccess = rDAO.registerSubject(user, subject, pkg, registration);

            // Provide feedback based on the operation outcome
            if (isSuccess) {
                request.setAttribute("success", "Registration updated successfully.");
            } else {
                request.setAttribute("error", "Failed to update registration.");
            }

            // Redirect to the registration details page
            request.getRequestDispatcher("add_registrations.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Error parsing number", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating registration", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the registration");
        }
    }

}
