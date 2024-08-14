package Controller;

import Dal.CategoryDAO;
import Dal.PackageDAO;
import Dal.RegistrationDAO;
import Dal.SubjectDAO;
import Dal.UserDAO;
import Model.Category;
import Model.Subject;
import Model.User;
import Model.Package;
import Model.Registration;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class RegisterSubjectServlet extends HttpServlet {

    private final RegistrationDAO rdao = new RegistrationDAO();
    private final UserDAO udao = new UserDAO();
    private final PackageDAO pdao = new PackageDAO();
    private final SubjectDAO sdao = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.removeAttribute("lowestPack");
        session.removeAttribute("subject");
        session.removeAttribute("packList");
        session.removeAttribute("feature");
        session.removeAttribute("categories");
        session.removeAttribute("registration");

        session.removeAttribute("spackage");
        session.removeAttribute("register_subject_success");
        session.removeAttribute("register_subject_error");
        session.removeAttribute("sname");
        session.removeAttribute("semail");
        session.removeAttribute("smobile");
        session.removeAttribute("sgender");
        session.removeAttribute("snotes");

        String subjectId = request.getParameter("subjectId");
        String registrationId = request.getParameter("registrationId");

        PackageDAO dao = new PackageDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        List<Package> list = dao.getPackagesForSubject(Integer.parseInt(subjectId));

        Package p = dao.getLowestPackage(Integer.parseInt(subjectId));

        Subject s = subjectDAO.getSubjectByID(Integer.parseInt(subjectId));

        List<Subject> sList = subjectDAO.getFeatureSubjects();
        List<Category> cList = categoryDAO.getAllCategories();

        if (registrationId != null && !registrationId.isEmpty()) {
            RegistrationDAO rDAO = new RegistrationDAO();
            Registration r = rDAO.getRegistrationByID(Integer.parseInt(registrationId));
            session.setAttribute("registration", r);
        }

        session.setAttribute("feature", sList);
        session.setAttribute("categories", cList);
        session.setAttribute("lowestPack", p);
        session.setAttribute("subject", s);
        session.setAttribute("packList", list);

        response.sendRedirect("subject_details.jsp?showModal=true&subjectId=" + subjectId);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.removeAttribute("spackage");
        session.removeAttribute("register_subject_error");
        session.removeAttribute("sname");
        session.removeAttribute("semail");
        session.removeAttribute("smobile");
        session.removeAttribute("sgender");
        session.removeAttribute("snotes");

        try {
            // Get all the information
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String mobile = request.getParameter("mobile");
            String gender_raw = request.getParameter("gender");
            String pricePackage = request.getParameter("pack");
            String subject_raw = request.getParameter("subject");

            // Get the logged-in account
            User u = (User) session.getAttribute("account");

            // Get the subject by id
            Subject s = sdao.getSubjectByID(Integer.parseInt(subject_raw));

            // Get the packages of subject by id
            Package p = pdao.getPackageByID(Integer.parseInt(pricePackage));

            // Get gender
            boolean gender = Boolean.parseBoolean(gender_raw);

            //check if the registration is inserted successfully
            boolean check = false;

            boolean editCheck = false;

            // Initiate registration
            Registration r = new Registration();
            r.setEmail(email);
            r.setRegistrationStatus(1);
            r.setGender(gender);
            r.setFullName(name);
            r.setMobile(mobile);

            // User not logged in
            if (u == null) {

                // Check if user already exists
                if (udao.isUserExists(email)) {
                    session.setAttribute("register_subject_error", "The user with the email entered already exists!");
                    session.setAttribute("spackage", p);
                    session.setAttribute("sname", name);
                    session.setAttribute("semail", email);
                    session.setAttribute("smobile", mobile);
                    session.setAttribute("sgender", gender);
                    response.sendRedirect("subject_details.jsp?showModal=true&subjectId=" + s.getSubjectId());
                    return;
                }

                // Register non-logged-in user
                check = rdao.registerSubject(u, s, p, r);
            } else {
                // Check action
                String action = request.getParameter("action");
                if (action != null && !action.isEmpty()) {

                    r = rdao.getRegistrationByID(Integer.parseInt(request.getParameter("registrationId")));
                    r.setFullName(name);
                    r.setEmail(email);
                    r.setMobile(mobile);
                    r.setGender(gender);

                    String notes = request.getParameter("notes");
                    editCheck = rdao.editRegistration(p, notes, r);
                    if (editCheck) {
                        session.setAttribute("register_subject_success", "Edited successfully");
                        session.setAttribute("spackage", p);
                        session.setAttribute("sname", name);
                        session.setAttribute("semail", email);
                        session.setAttribute("smobile", mobile);
                        session.setAttribute("sgender", gender);
                        session.setAttribute("snotes", notes);
                    } else {
                        session.setAttribute("register_subject_error", "Edited failed! Please try again");
                    }
                    response.sendRedirect("subject_details.jsp?showModal=true&subjectId=" + s.getSubjectId());
                    return;
                } else {
                    // Logged-in user
                    // Register logged-in user
                    check = rdao.registerSubject(u, s, p, r);
                }

            }

            if (check) {
                session.setAttribute("register_subject_success", "Registered successfully");
            } else {
                session.setAttribute("register_subject_error", "Registration failed! Please try again");
            }

            if (!response.isCommitted()) {
                response.sendRedirect("subject_details.jsp?showModal=true&subjectId=" + s.getSubjectId());
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

}
