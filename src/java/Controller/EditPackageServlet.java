package Controller;

import Model.Package;
import Dal.PackageDAO;
import Dal.SubjectDAO;
import Model.Subject;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class EditPackageServlet extends HttpServlet {

    private final PackageDAO pdao = new PackageDAO();
    private final SubjectDAO sdao = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String packId = request.getParameter("packageId");
        //service to determine the action to be executed
        String service = request.getParameter("service");
        try {
            int pid = Integer.parseInt(packId);
            //get the package information
            Package p = pdao.getPackageByID(pid);

            //edit a package for a subject
            if (service.equalsIgnoreCase("editSubjectPack")) {
                int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                Subject s = sdao.getSubjectByID(subjectId);
                request.setAttribute("subject", s);

            } //edit a package in general
            else if (service.equalsIgnoreCase("viewPackage")) {
                List<Subject> subjectList = sdao.getAllSubjects();
                request.setAttribute("subjectList", subjectList);
            }
            request.setAttribute("service", service);
            request.setAttribute("pack", p);
            request.getRequestDispatcher("edit_package.jsp").forward(request, response);
        } catch (Exception e) {

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        try {
            String packName = request.getParameter("packageName");
            String packId = request.getParameter("pId");
            String duration = request.getParameter("duration");
            String listP = request.getParameter("listP");
            String sellP = request.getParameter("sellP");
            String des = request.getParameter("packageDescription");

            String subjectRaw = request.getParameter("subjectId");

            int subjectId = Integer.parseInt(subjectRaw);

            int pid = Integer.parseInt(packId);
            int du = Integer.parseInt(duration);
            double listPrice = Double.parseDouble(listP);
            double sellPrice = Double.parseDouble(sellP);

            User u = (User) session.getAttribute("account");

            boolean check;

            check = pdao.editPackage(pid, packName.trim(), du, listPrice, sellPrice, des.trim(), u.getUserId(), subjectId);

            if (check) {
                session.setAttribute("mess", "Package updated successfully");
            } else {
                session.setAttribute("mess", "Package updated failed");
            }

            response.sendRedirect("edit-package?subjectId=" + request.getParameter("subjectId") + "&packageId=" + pid + "&service=" + service);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
