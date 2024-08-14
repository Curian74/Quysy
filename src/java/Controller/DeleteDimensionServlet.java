package Controller;

import Dal.DimensionsDAO;
import Dal.SubjectDAO;
import Model.Subject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteDimensionServlet extends HttpServlet {

    private final SubjectDAO sdao = new SubjectDAO();
    private final DimensionsDAO ddao = new DimensionsDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dimId = request.getParameter("dId");

        try {
            int id = Integer.parseInt(dimId);
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            Subject s = sdao.getSubjectByID(subjectId);
            ddao.deleteSubjectDimension(id);

            // Set a success message
            request.setAttribute("deleteDimSuccess", "Dimension deleted successfully.");

            request.setAttribute("subject", s);
            request.getRequestDispatcher("subject-detail-control").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, set an error message if needed
            request.setAttribute("deleteDimfail", "Deletion Failed.");
            request.getRequestDispatcher("subject-detail-control").forward(request, response);
        }
    }

}
