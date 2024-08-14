package Controller;

import Dal.DimensionTypeDAO;
import Dal.DimensionsDAO;
import Dal.SubjectDAO;
import Dal.SubjectDimensionDAO;
import Model.DimensionType;
import Model.Dimensions;
import Model.Subject;
import Model.SubjectDimension;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class EditDimensionServlet extends HttpServlet {

    private final SubjectDimensionDAO sddao = new SubjectDimensionDAO();
    private final SubjectDAO sdao = new SubjectDAO();
    private final DimensionTypeDAO dtdao = new DimensionTypeDAO();
    private final DimensionsDAO ddao = new DimensionsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (request.getParameter("subjectId") != null
                    && request.getParameter("dimensionId") != null
                    && request.getParameter("dimensionType") != null) {
                int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                int dimensionId = Integer.parseInt(request.getParameter("dimensionId"));
                int dimensionTypeId = Integer.parseInt(request.getParameter("dimensionType"));
                int dId = Integer.parseInt(request.getParameter("dId"));

                // If update failed, use old values
                if (request.getParameter("oldDimensionId") != null) {
                    dimensionId = Integer.parseInt(request.getParameter("oldDimensionId"));
                }
                if (request.getParameter("oldDimensionType") != null) {
                    dimensionTypeId = Integer.parseInt(request.getParameter("oldDimensionType"));
                }

                SubjectDimension sd = sddao.getSubjectDimensionByID(dimensionId);
                DimensionType dt = dtdao.getSubjectDimensionTypeByID(dimensionTypeId);
                Subject s = sdao.getSubjectByID(subjectId);
                Dimensions d = ddao.getDimensionIdentifierById(dId);

                List<DimensionType> dtList = dtdao.getAllDimensionTypes();
                List<SubjectDimension> sdList = sddao.getAllSubjectDimensions();

                request.setAttribute("identify", d);
                request.setAttribute("dimList", sdList);
                request.setAttribute("typeList", dtList);
                request.setAttribute("type", dt);
                request.setAttribute("subject", s);
                request.setAttribute("dimension", sd);
            }

            request.getRequestDispatcher("edit_dimension.jsp").forward(request, response);
        } catch (ServletException | IOException | NumberFormatException e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("mess");
        try {
            String dimName = request.getParameter("dimensionName");
            String dimType = request.getParameter("dimType");

            int dimensionId = Integer.parseInt(dimName);
            int typeId = Integer.parseInt(dimType);
            int identify = Integer.parseInt(request.getParameter("dId"));
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            List<Dimensions> listAll = ddao.getAllDimensions();

            for (Dimensions d : listAll) {
                if (d.getSubjectId() == subjectId
                        && d.getDimensionId() == dimensionId
                        && d.getDimensionTypeId() == typeId) {
                    session.setAttribute("failmess", "Update Failed!<br>Duplicated dimension name and dimension type");
                    response.sendRedirect("edit-dimension?subjectId=" + subjectId
                            + "&dimensionId=" + request.getParameter("oldDimensionId")
                            + "&dimensionType=" + request.getParameter("oldDimensionType")
                            + "&dId=" + identify);
                    return;
                }
            }

            boolean check = ddao.editSubjectDimension(dimensionId, typeId, identify);

            if (check) {
                session.setAttribute("mess", "Dimension updated successfully");
            } else {
                session.setAttribute("failmess", "Dimension update failed");
            }

            response.sendRedirect("edit-dimension?subjectId=" + subjectId
                    + "&dimensionId=" + dimensionId
                    + "&dimensionType=" + typeId
                    + "&dId=" + identify);

        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
            session.setAttribute("mess", "An error occurred");
            response.sendRedirect("edit-dimension?subjectId=" + request.getParameter("subjectId")
                    + "&dimensionId=" + request.getParameter("oldDimensionId")
                    + "&dimensionType=" + request.getParameter("oldDimensionType")
                    + "&dId=" + request.getParameter("dId"));
        }
    }

}