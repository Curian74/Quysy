package Controller;

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

public class AddPackageServlet extends HttpServlet {

    private final SubjectDAO dao = new SubjectDAO();
    private final PackageDAO pdao = new PackageDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        List<Subject> subjectList = dao.getAllSubjects();

        try {
            if (service.equalsIgnoreCase("addPackSubject")) {
                String subjectId = request.getParameter("subjectId");
                Subject s = dao.getSubjectByID(Integer.parseInt(subjectId));
                request.setAttribute("subject", s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("service", service);
        request.setAttribute("subjectList", subjectList);
        request.getRequestDispatcher("add_package.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        session.removeAttribute("mess");
        try {
            
            String packageName = request.getParameter("packageName");
            String duration = request.getParameter("duration");
            String listP = request.getParameter("listP");
            String sellP = request.getParameter("sellP");
            String des = request.getParameter("packageDescription");

            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            int du = Integer.parseInt(duration);
            double list = Double.parseDouble(listP);
            double sell = Double.parseDouble(sellP);

            User u = (User) session.getAttribute("account");

            boolean check = pdao.addPackage(subjectId, packageName.trim(), du, list, sell, des.trim(), u.getUserId());

            if (check) {
                session.setAttribute("mess", "Add new package successfully");
            } else {
                session.setAttribute("mess", "Add new package failed");
            }
            if (service.equalsIgnoreCase("addPackSubject")){
                response.sendRedirect("add-package?subjectId=" + request.getParameter("subjectId") + "&service=" + service);
            }
            
            else if (service.equalsIgnoreCase("addGeneral")){
                response.sendRedirect("add-package?service=" + service);
            }

        } catch (Exception e) {
            System.out.println(e);
            session.setAttribute("mess", "An error occurred");
            response.sendRedirect("add-package?subjectId=" + request.getParameter("subjectId") + "&service=" + service);
        }
    }

}
