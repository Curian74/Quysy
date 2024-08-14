package Controller;

import Dal.SliderDAO;
import Model.Slider;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SlidersListServlet extends HttpServlet {

    private final SliderDAO sliderDAO = new SliderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        if (service == null) {
            service = "listAllSliders";
        }
        if (service.equals("listAllSliders")) {
            listAllSliders(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        if (service == null) {
            service = "listAllSliders";
        }
        switch (service) {
            case "listAllSliders":
                listAllSliders(request, response);
                break;
            case "updateSliderStatus":
                updateSliderStatus(request, response);
                break;
            case "deleteSlider":
                deleteSlider(request, response);
                break;
            default:
                listAllSliders(request, response);
                break;
        }
    }

    private void updateSliderStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sliderId = Integer.parseInt(request.getParameter("sliderId"));
        String newStatusValue = request.getParameter("newStatus");
        boolean newStatus = Boolean.parseBoolean(newStatusValue);
        boolean success = sliderDAO.updateSliderStatus(sliderId, newStatus);

        if (success) {
            request.getSession().setAttribute("message", "Slider status updated successfully!");
        } else {
            request.getSession().setAttribute("message", "Failed to update slider status.");
        }
        listAllSliders(request, response);
    }

    private void deleteSlider(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sliderId = Integer.parseInt(request.getParameter("sliderId"));
        boolean success = sliderDAO.deleteSlider(sliderId);

        if (success) {
            request.getSession().setAttribute("message", "Slider deleted successfully!");
        } else {
            request.getSession().setAttribute("message", "Failed to delete slider.");
        }
        listAllSliders(request, response);
    }

    private void listAllSliders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTitle = trimParameter(request.getParameter("searchTitle"));
        String searchLink = trimParameter(request.getParameter("searchLink"));
        String statusParam = trimParameter(request.getParameter("status"));

        Boolean status = (statusParam != null && !statusParam.isEmpty()) ? Boolean.valueOf(statusParam) : null;

        List<Slider> sliders = sliderDAO.searchSliders(searchTitle, searchLink, status);

        int currentPage = 1;
        int slidersPerPage = 4;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        int totalSliders = sliders.size();
        int totalPages = (int) Math.ceil((double) totalSliders / slidersPerPage);

        int start = Math.max((currentPage - 1) * slidersPerPage, 0);
        int end = Math.min(start + slidersPerPage, totalSliders);

        List<Slider> paginatedSliders = sliders.subList(start, end);

        request.setAttribute("sliders", paginatedSliders);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchTitle", searchTitle);
        request.setAttribute("searchLink", searchLink);
        request.setAttribute("status", statusParam);

        request.getRequestDispatcher("sliders_list.jsp").forward(request, response);
    }

    private String trimParameter(String param) {
        return (param != null) ? param.trim() : null;
    }
}