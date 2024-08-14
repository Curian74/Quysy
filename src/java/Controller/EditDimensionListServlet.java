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

public class EditDimensionListServlet extends HttpServlet {

    SubjectDimensionDAO sdao = new SubjectDimensionDAO();
    DimensionTypeDAO tdao = new DimensionTypeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //service to determine which function to be executed
        String service = request.getParameter("service".trim());
        try {
            //for edit dimension name
            if (service.equals("dimName")) {

                //get id
                String dimId_raw = request.getParameter("dimensionId");
                // get the list of all dimensions
                List<SubjectDimension> dimList = sdao.getAllSubjectDimensions();

                //convert to Integer
                int dimId = Integer.parseInt(dimId_raw);

                //get the data of the selected dimension
                SubjectDimension sd = sdao.getSubjectDimensionByID(dimId);

                request.setAttribute("dim", sd);
                request.setAttribute("dimList", dimList);
            } //for edit dimension type
            else if (service.equals("dimType")) {
                //same with above 
                String typeId_raw = request.getParameter("typeId");
                List<DimensionType> typeList = tdao.getAllDimensionTypes();
                int typeId = Integer.parseInt(typeId_raw);
                DimensionType dt = tdao.getSubjectDimensionTypeByID(typeId);

                request.setAttribute("type", dt);
                request.setAttribute("typeList", typeList);
            }
            request.setAttribute("service", service);
            request.getRequestDispatcher("edit_dimension.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service".trim());
        HttpSession session = request.getSession();
        try {
            //for edit dimension name
            if (service.equals("dimName")) {

                //necessary data
                List<SubjectDimension> dimList = sdao.getAllSubjectDimensions();
                String dimId_raw = request.getParameter("dimensionId".trim());
                String dimName = request.getParameter("dimensionName".trim());
                String desc = request.getParameter("nameDescription".trim());

                //parse and get the dimension name
                int dimId = Integer.parseInt(dimId_raw);
                SubjectDimension sd = sdao.getSubjectDimensionByID(dimId);

                User u = (User) session.getAttribute("account");

                //check duplicate dimension name
                for (SubjectDimension s : dimList) {
                    // Skip the current dimension being edited
                    if (s.getDimensionId() != dimId && s.getDimensionName().trim().equals(dimName.trim())) {
                        session.setAttribute("failmess", "Error! The dimension \"" + s.getDimensionName() + "\" already exists");
                        response.sendRedirect("dimension-edit?dimensionId=" + dimId + "&service=" + service);
                        return;
                    }
                }

                // executing update query
                boolean check = sdao.editSubjectDimension(dimId, dimName, desc, u.getUserId());

                //check if the query succeeded or failed
                if (check) {
                    session.setAttribute("mess", "Dimension updated successfully");
                } else {
                    session.setAttribute("failmess", "Dimension update failed");
                }

                //forward to the doGet 
                response.sendRedirect("dimension-edit?dimensionId=" + dimId + "&service=" + service);

                request.setAttribute("service", service);
                request.setAttribute("dim", sd);
                request.setAttribute("dimList", dimList);
            } //for edit dimension type
            else if (service.equals("dimType")) {
                //same as above
                List<DimensionType> typeList = tdao.getAllDimensionTypes();
                String typeId_raw = request.getParameter("typeId".trim());
                String typeName = request.getParameter("dimensionType".trim());
                String desc = request.getParameter("typeDescription".trim());

                int typeId = Integer.parseInt(typeId_raw);

                DimensionType dt = tdao.getSubjectDimensionTypeByID(typeId);

                User u = (User) session.getAttribute("account");

                for (DimensionType t : typeList) {
                    // Skip the current dimension type being edited
                    if (t.getDimensionTypeId() != typeId && t.getDimensionTypeName().trim().equals(typeName.trim())) {
                        session.setAttribute("failmess", "Error! The dimension \"" + t.getDimensionTypeName() + "\" already exists");
                        response.sendRedirect("dimension-edit?typeId=" + typeId + "&service=" + service);
                        return;
                    }
                }

                boolean check = tdao.editDimensionType(typeId, typeName, desc, u.getUserId());

                if (check) {
                    session.setAttribute("mess", "Dimension Type updated successfully");
                } else {
                    session.setAttribute("failmess", "Dimension Type update failed");
                }

                response.sendRedirect("dimension-edit?typeId=" + typeId + "&service=" + service);

                request.setAttribute("service", service);
                request.setAttribute("type", dt);
                request.setAttribute("typeList", typeList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
