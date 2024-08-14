package Controller;

import Dal.DimensionTypeDAO;
import Dal.SubjectDimensionDAO;
import Model.DimensionType;
import Model.SubjectDimension;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class AddDimensionListServlet extends HttpServlet {

    private final SubjectDimensionDAO sdao = new SubjectDimensionDAO();
    private final DimensionTypeDAO tdao = new DimensionTypeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //service to determine which function to be executed
        String service = request.getParameter("service".trim());
        try {

            //check and send data based on service obtained
            if (service.equalsIgnoreCase("dimName")) {
                List<SubjectDimension> dimList = sdao.getAllSubjectDimensions();

                request.setAttribute("dimList", dimList);

            } else if (service.equalsIgnoreCase("dimType")) {
                List<DimensionType> typeList = tdao.getAllDimensionTypes();
                request.setAttribute("typeList", typeList);
            }

            request.setAttribute("service", service);
            request.getRequestDispatcher("add_dimension.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //service to determine which function to be executed
        String service = request.getParameter("service".trim());
        HttpSession session = request.getSession();
        try {
            //add dimension name
            if (service.equalsIgnoreCase("dimName")) {
                //data
                List<SubjectDimension> dimList = sdao.getAllSubjectDimensions();
                String dimName = request.getParameter("dimensionName");
                String desc = request.getParameter("nameDescription");

                //get the current user
                User u = (User) session.getAttribute("account");

                //check duplicate dimension name
                for (SubjectDimension s : dimList) {
                    if (s.getDimensionName().trim().equalsIgnoreCase(dimName)) {
                        session.setAttribute("addFail", "Error! The dimension \"" + s.getDimensionName() + "\" already existed");
                        request.setAttribute("dimName", dimName);
                        request.setAttribute("dimList", dimList);
                        request.setAttribute("desc", desc);
                        request.setAttribute("service", service);
                        request.getRequestDispatcher("add_dimension.jsp").forward(request, response);
                        return;
                    }
                }   

                //add the diemension name
                boolean check = sdao.addSubjectDimension(dimName, desc, u.getUserId());

                //determine if the query has succeeded or failed
                if (check) {
                    session.setAttribute("addSuccess", "Added new dimension successfully");
                } else {
                    session.setAttribute("addFail", "Added new dimension failed");
                }

                response.sendRedirect("new-dimension?&service=" + service);

                //send necessary data
                request.setAttribute("dimName", dimName);
                request.setAttribute("desc", desc);
                request.setAttribute("service", service);
                request.setAttribute("dimList", dimList);
            } //add dimension type
            // the rest goes the same for dimension type
            else if (service.equalsIgnoreCase("dimType")) {

                List<DimensionType> typeList = tdao.getAllDimensionTypes();
                String typeName = request.getParameter("typeName");
                String desc = request.getParameter("typeDescription");

                User u = (User) session.getAttribute("account");

                for (DimensionType s : typeList) {
                    if (s.getDimensionTypeName().trim().equalsIgnoreCase(typeName)) {
                        session.setAttribute("addFail", "Error! The dimension type \"" + s.getDimensionTypeName() + "\" already existed");
                        request.setAttribute("dimList", typeList);
                        request.setAttribute("dimType", typeName);
                        request.setAttribute("desc", desc);
                        request.setAttribute("service", service);
                        request.getRequestDispatcher("add_dimension.jsp").forward(request, response);
                        return;
                    }
                }

                boolean check = tdao.addDimensionType(typeName, desc, u.getUserId());

                if (check) {
                    session.setAttribute("addSuccess", "Added new dimension type successfully");
                } else {
                    session.setAttribute("addFail", "Added new dimension type failed");
                    request.setAttribute("dimList", typeList);
                    request.setAttribute("dimType", typeName);
                    request.setAttribute("desc", desc);
                    request.setAttribute("service", service);
                    request.getRequestDispatcher("add_dimension.jsp").forward(request, response);
                    return;
                }

                response.sendRedirect("new-dimension?&service=" + service);

                request.setAttribute("dimType", typeName);
                request.setAttribute("desc", desc);
                request.setAttribute("service", service);
                request.setAttribute("dimList", typeList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
