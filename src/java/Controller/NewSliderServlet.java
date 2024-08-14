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
import jakarta.servlet.http.Part;
import java.io.File;

@MultipartConfig
public class NewSliderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String backlink = request.getParameter("backlink");
        String statusStr = request.getParameter("status");
        String notes = request.getParameter("notes");
        Part filePart = request.getPart("thumbnail");

        // Validate inputs
        if (title == null || backlink == null || statusStr == null
                || filePart == null || filePart.getSize() == 0
                || title.trim().isEmpty() || backlink.trim().isEmpty()
                || !title.equals(title.trim()) || !backlink.equals(backlink.trim())
                || (notes != null && !notes.equals(notes.trim()))) {
            request.getSession().setAttribute("message", "Fields cannot start with spaces and title/backlink are required. Image is also required for new sliders.");
            response.sendRedirect("sliders-list");
            return;
        }

        // Trim the inputs
        title = title.trim();
        backlink = backlink.trim();
        notes = notes != null ? notes.trim() : "";

        boolean status = Boolean.parseBoolean(statusStr);
        String imagePath = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            imagePath = saveImage(fileContent, fileName);
        }

        Slider slider = new Slider(0, imagePath, title, backlink, status, notes);
        SliderDAO sliderDAO = new SliderDAO();
        boolean check = sliderDAO.insertSlider(slider);

        if (check) {
            request.getSession().setAttribute("message", "Slider added successfully!");
        } else {
            request.getSession().setAttribute("message", "Failed to add slider.");
        }
        response.sendRedirect("sliders-list");
    }

    private String saveImage(InputStream fileContent, String fileName) throws IOException {
        String uploadDirectory = getServletContext().getRealPath("/resources/images/slider");
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        String filePath = uploadDirectory + File.separator + uniqueFileName;
        Files.copy(fileContent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return "resources/images/slider/" + uniqueFileName;
    }
}
