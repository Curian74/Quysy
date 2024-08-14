package Controller;

import Dal.SliderDAO;
import Model.Slider;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;

@MultipartConfig
public class SliderDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sliderId = Integer.parseInt(request.getParameter("id"));
        SliderDAO sliderDAO = new SliderDAO();
        Slider slider = sliderDAO.getSliderByID(sliderId);
        request.setAttribute("slider", slider);
        request.getRequestDispatcher("slider_details.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sliderIdParam = request.getParameter("id");
        int sliderId = sliderIdParam != null ? Integer.parseInt(sliderIdParam) : 0;
        String title = request.getParameter("title");
        String backlink = request.getParameter("backlink");
        String statusStr = request.getParameter("status");
        String notes = request.getParameter("notes");
        Part filePart = request.getPart("thumbnail");
        String imagePath = request.getParameter("existingThumbnail");

        // Validate inputs
        if (sliderId == 0 || title == null || backlink == null || statusStr == null
                || title.trim().isEmpty() || backlink.trim().isEmpty()
                || !title.equals(title.trim()) || !backlink.equals(backlink.trim())
                || (notes != null && !notes.equals(notes.trim()))) {
            session.setAttribute("updateMessage", "Fields cannot start with spaces and title/backlink are required.");
            response.sendRedirect("slider-details?id=" + sliderId);
            return;
        }

        // Trim the inputs
        title = title.trim();
        backlink = backlink.trim();
        notes = notes != null ? notes.trim() : "";

        boolean status = Boolean.parseBoolean(statusStr);

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            imagePath = saveImage(fileContent, fileName, request);
        }

        Slider slider = new Slider(sliderId, imagePath, title, backlink, status, notes);
        SliderDAO sliderDAO = new SliderDAO();
        boolean check = sliderDAO.updateSlider(slider);

        if (check) {
            session.setAttribute("updateMessage", "Slider updated successfully");
        } else {
            session.setAttribute("updateMessage", "Slider update failed");
        }
        response.sendRedirect("slider-details?id=" + sliderId);
    }

    private String saveImage(InputStream fileContent, String fileName, HttpServletRequest request) throws IOException {
        String uploadDirectory = getServletContext().getRealPath("/resources/images/slider");
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        String filePath = uploadDirectory + File.separator + uniqueFileName;
        Files.copy(fileContent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return "resources/images/slider/" + uniqueFileName;
    }
}
