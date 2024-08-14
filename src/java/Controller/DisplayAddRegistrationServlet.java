package Controller;

import Dal.PackageDAO;
import Model.Package;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class DisplayAddRegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String subjectIdRaw = request.getParameter("subjectId");
        String packageIdRaw = request.getParameter("packageId");

        if (subjectIdRaw != null) {
            int subjectId = Integer.parseInt(subjectIdRaw);
            PackageDAO pDAO = new PackageDAO();
            List<Package> packages = pDAO.getPackagesForSubject(subjectId);

            // Send packages as JSON
            out.print(new Gson().toJson(packages));
        } else if (packageIdRaw != null) {
            int packageId = Integer.parseInt(packageIdRaw);
            PackageDAO pDAO = new PackageDAO();
            Package selectedPackage = pDAO.getPackageByID(packageId);

            // Send selected package prices as JSON
            out.print(new Gson().toJson(selectedPackage));
        }

        out.flush();
    }

}
