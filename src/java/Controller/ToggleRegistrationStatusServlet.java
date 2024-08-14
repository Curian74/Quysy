package Controller;

import Dal.RegistrationDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ToggleRegistrationStatusServlet extends HttpServlet {

    private final RegistrationDAO rDAO = new RegistrationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the paramater from the request
        int registrationId = Integer.parseInt(request.getParameter("registrationId"));

        // Update the registration status in the database
        String updateStatusMessage;
        if (rDAO.cancelRegistration(registrationId)) {
            updateStatusMessage = "cancelSuccess";
        } else {
            updateStatusMessage = "cancelFail";
        }
        
        // Get the filter parameter
        String categoryIdRaw = request.getParameter("categoryId");
        
        // Redirect back to my registration page with the filter parameter
        String redirectUrl = "my-registrations";
        String queryParams = "";
        if (categoryIdRaw != null && !categoryIdRaw.isEmpty()) {
            queryParams += "categoryId=" + categoryIdRaw + "&";
        }
        if (!queryParams.isEmpty()) {
            redirectUrl += "?" + queryParams;
        } else {
            redirectUrl += "?updateStatusMessage=" + updateStatusMessage;
        }
        
        response.sendRedirect(redirectUrl);
    }

}
