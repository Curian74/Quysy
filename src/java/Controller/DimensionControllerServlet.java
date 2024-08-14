package Controller;

import Dal.SubjectDimensionDAO;
import Model.SubjectDimension;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DimensionControllerServlet extends HttpServlet {

    private final  SubjectDimensionDAO sdao = new SubjectDimensionDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchName = request.getParameter("searchName");
        String sortBy = request.getParameter("sortBy");

        int currentPage = 1;
        int recordsPerPage = 5;

        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        // Fetch all data
        List<SubjectDimension> dimList = sdao.getAllSubjectDimensions();

        // Filter by search name
        if (searchName != null && !searchName.isEmpty()) {
            dimList = dimList.stream()
                    .filter(d -> d.getDimensionName().toLowerCase().contains(searchName.trim().toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Sort the list
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy) {
                case "newlyCreated":
                    dimList.sort(Comparator.comparing(SubjectDimension::getCreateDate).reversed());
                    break;
                case "latestUpdated":
                    dimList.sort(Comparator.comparing(SubjectDimension::getUpdateDate).reversed());
                    break;
                default:
                    // No sorting for default
                    break;
            }
        }

        // Calculate total pages
        int totalDimPages = (int) Math.ceil((double) dimList.size() / recordsPerPage);
        int totalPages = totalDimPages;

        // Get the sublist for the current page
        int start = (currentPage - 1) * recordsPerPage;
        List<SubjectDimension> paginatedDimList = dimList.stream()
                .skip(start)
                .limit(recordsPerPage)
                .collect(Collectors.toList());


        // Set attributes
        request.setAttribute("dimList", paginatedDimList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchName", searchName);
        request.setAttribute("sortBy", sortBy);

        request.getRequestDispatcher("dimension_list.jsp").forward(request, response);
    }

}
