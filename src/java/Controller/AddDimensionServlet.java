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

public class AddDimensionServlet extends HttpServlet {
   
    private final SubjectDAO dao = new SubjectDAO();
    private final SubjectDimensionDAO sddao = new SubjectDimensionDAO();
    private final DimensionTypeDAO dtdao = new DimensionTypeDAO();
    private final DimensionsDAO ddao = new DimensionsDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String subjectId = request.getParameter("subjectId");
       Subject s = dao.getSubjectByID(Integer.parseInt(subjectId));
       List<DimensionType> typeList = dtdao.getAllDimensionTypes();
       List<SubjectDimension> dimList = sddao.getAllSubjectDimensions();
       
       request.setAttribute("dimList", dimList);
       request.setAttribute("typeList", typeList);
       request.setAttribute("subject", s);
       request.getRequestDispatcher("add_dimension.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("mess");
        try {
            String dimName = request.getParameter("dimensionName");
            String dimType = request.getParameter("dimType");
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            int dimensionId = Integer.parseInt(dimName);
            int dimensionTypeId = Integer.parseInt(dimType);
            List<Dimensions> listAll = ddao.getAllDimensions();

            for (Dimensions d : listAll) {
                if (d.getSubjectId() == subjectId
                        && d.getDimensionId() == dimensionId
                        && d.getDimensionTypeId() == dimensionTypeId) {
                    session.setAttribute("addFail", "Add Failed!<br>Duplicated dimension name and dimension type");
                    response.sendRedirect("add-dimension?subjectId=" + subjectId);
                    return;
                }
            }
            
            boolean check = ddao.addSubjectDimension(dimensionId, dimensionTypeId, subjectId);
            if (check) {
                session.setAttribute("addSuccess", "Add new dimension successfully");
            } else {
                session.setAttribute("addFail", "Add new dimension failed");
            }

            response.sendRedirect("add-dimension?subjectId=" + request.getParameter("subjectId"));

        } catch (Exception e) {
            System.out.println(e);
            session.setAttribute("mess", "An error occurred");
            response.sendRedirect("add-dimension?subjectId=" + request.getParameter("subjectId") + "&dimensionId=" + request.getParameter("sId"));
        }
    }

}
