package Controller;

import Dal.RegistrationDAO;
import Model.Registration;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class RegistrationListServlet extends HttpServlet {

    private final RegistrationDAO rDAO = new RegistrationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get parameters for filtering
        String registrationTimeFrom = request.getParameter("registrationTimeFrom");
        String registrationTimeTo = request.getParameter("registrationTimeTo");
        String status = request.getParameter("status");

        // Get last updated by map
        Map<Integer, String> lastUpdatedByMap = rDAO.getLastUpdatedByMap();

        // Get filtered list of registrations
        List<Registration> registrations = rDAO.getRegistrations(registrationTimeFrom, registrationTimeTo, status);

        // Get list of status names
        List<String> statusList = rDAO.getStatusNames();

        // Set attributes and forward to JSP
        request.setAttribute("registrations", registrations);
        request.setAttribute("lastUpdatedByMap", lastUpdatedByMap);
        request.setAttribute("statusList", statusList);
        request.getRequestDispatcher("registration_list.jsp").forward(request, response);
    }
}
