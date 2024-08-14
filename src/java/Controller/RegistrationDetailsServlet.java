package Controller;

import Dal.PackageDAO;
import Dal.RegistrationDAO;
import Dal.RegistrationStatusDAO;
import Dal.SubjectDAO;
import Model.Package;
import Model.Registration;
import Model.RegistrationStatus;
import Model.Subject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class RegistrationDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initiate DAO
        PackageDAO pDAO = new PackageDAO();
        RegistrationDAO rDAO = new RegistrationDAO();
        RegistrationStatusDAO rsDAO = new RegistrationStatusDAO();
        SubjectDAO sDAO = new SubjectDAO();

        // Get registration id
        int registrationId = Integer.parseInt(request.getParameter("registrationId"));

        // Get registration
        Registration registration = rDAO.getRegistrationByID(registrationId);

        // Get package prices
        Package price = pDAO.getPackageByID(registration.getPackageId());

        // Get neccessary datas for select boxes
        List<Package> packageList = pDAO.getPackagesForSubject(registration.getSubjectId());
        List<RegistrationStatus> registrationStatusList = rsDAO.getAllRegistrationStatus();
        List<Subject> subjectList = sDAO.getAllSubjects();

        // Set attribute
        request.setAttribute("registrationStatusList", registrationStatusList);
        request.setAttribute("subjectList", subjectList);
        request.setAttribute("packageList", packageList);
        request.setAttribute("price", price);
        request.setAttribute("registration", registration);

        request.getRequestDispatcher("registration_details.jsp").forward(request, response);
    }

}
