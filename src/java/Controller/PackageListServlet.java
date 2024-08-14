package Controller;

import Dal.PackageDAO;
import Model.Package;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PackageListServlet extends HttpServlet {

    private final PackageDAO pdao = new PackageDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Package> packList = pdao.getAllPackages();

        String searchName = request.getParameter("searchName");
        String sortBy = request.getParameter("sortBy");
        String statusFilter = request.getParameter("statusFilter");
        int currentPage = 1;
        int recordsPerPage = 7;

        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        // Filter by searchName for both package name and subject name
        if (searchName != null && !searchName.isEmpty()) {
            packList = packList.stream()
                    .filter(pack -> pack.getPackageName().toLowerCase().contains(searchName.trim().toLowerCase())
                    || pack.getSubjectName().toLowerCase().contains(searchName.trim().toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Filter by status
        if (statusFilter != null && !statusFilter.isEmpty() && !statusFilter.equals("all")) {
            boolean isActive = statusFilter.equals("active");
            packList = packList.stream()
                    .filter(pack -> (isActive ? pack.getStatus().equals("1") : pack.getStatus().equals("0")))
                    .collect(Collectors.toList());
        }

        // Sort by sortBy parameter
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy) {
                case "newlyCreated":
                    packList = packList.stream()
                            .sorted(Comparator.comparing(Package::getCreatedDate).reversed())
                            .collect(Collectors.toList());
                    break;
                case "latestUpdated":
                    packList = packList.stream()
                            .sorted(Comparator.comparing(Package::getUpdateDate).reversed())
                            .collect(Collectors.toList());
                    break;
                default:
                    // Default sorting logic if any
                    break;
            }
        }

        int totalPackagePages = (int) Math.ceil((double) packList.size() / recordsPerPage);
        int start = (currentPage - 1) * recordsPerPage;

        List<Package> paginatedPackList = packList.stream()
                .skip(start)
                .limit(recordsPerPage)
                .collect(Collectors.toList());

        request.setAttribute("packList", paginatedPackList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPackagePages);
        request.setAttribute("searchName", searchName);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("statusFilter", statusFilter);

        request.getRequestDispatcher("package_list.jsp").forward(request, response);
    }

}
