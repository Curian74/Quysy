package Controller;

import Dal.PackageDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ActivationServlet extends HttpServlet {

    private final PackageDAO pdao = new PackageDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        String message = null;
        try {
            int id = Integer.parseInt(request.getParameter("packageId"));
            if (service.equals("deactive")) {
                pdao.deActivate(id);
                message = "Package deactivated successfully.";
            } else if (service.equalsIgnoreCase("active")) {
                pdao.Activate(id);
                message = "Package activated successfully.";
            }

            // Set success message
            request.setAttribute("activationSuccess", message);
        } catch (NumberFormatException e) {
            // Set error message
            request.setAttribute("activationFail", "An error occurred.");
            e.printStackTrace();
        }
        // Forward to subject-detail-control with messages
        request.getRequestDispatcher("subject-detail-control?subjectId=" + request.getParameter("subjectId")).forward(request, response);
    }

}
