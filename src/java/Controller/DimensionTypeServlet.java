package Controller;

import Dal.DimensionTypeDAO;
import Model.DimensionType;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DimensionTypeServlet extends HttpServlet {

    private final DimensionTypeDAO tdao = new DimensionTypeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<DimensionType> typeList = tdao.getAllDimensionTypes();
        
        String searchName = request.getParameter("searchName");
        String sortBy = request.getParameter("sortBy");
        int currentPage = 1;
        int recordsPerPage = 5;

        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        // Filter by searchName
        if (searchName != null && !searchName.isEmpty()) {
            typeList = typeList.stream()
                    .filter(type -> type.getDimensionTypeName().toLowerCase().contains(searchName.trim().toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Sort by sortBy parameter
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy) {
                case "newlyCreated":
                    typeList = typeList.stream()
                            .sorted(Comparator.comparing(DimensionType::getCreateDate).reversed())
                            .collect(Collectors.toList());
                    break;
                case "latestUpdated":
                    typeList = typeList.stream()
                            .sorted(Comparator.comparing(DimensionType::getUpdateDate).reversed())
                            .collect(Collectors.toList());
                    break;
                default:
                    // Default sorting logic if any
                    break;
            }
        }

        int totalTypePages = (int) Math.ceil((double) typeList.size() / recordsPerPage);
        int start = (currentPage - 1) * recordsPerPage;

        List<DimensionType> paginatedTypeList = typeList.stream()
                .skip(start)
                .limit(recordsPerPage)
                .collect(Collectors.toList());

        request.setAttribute("typeList", paginatedTypeList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalTypePages);
        request.setAttribute("searchName", searchName);
        request.setAttribute("sortBy", sortBy);
        
        request.getRequestDispatcher("dimension_type.jsp").forward(request, response);
    }
    
}
