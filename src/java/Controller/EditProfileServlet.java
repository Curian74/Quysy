package Controller;

import Dal.UserDAO;
import Model.User;
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

public class EditProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("account");

        String fullname = request.getParameter("fullname");
        boolean gender = "male".equals(request.getParameter("gender"));
        String mobile = request.getParameter("mobile");

        // Server-side validation
        if (fullname == null || mobile == null
                || fullname.trim().isEmpty() || mobile.trim().isEmpty()
                || !fullname.equals(fullname.trim()) || !mobile.equals(mobile.trim())) {
            request.setAttribute("error", "Fields cannot be empty, contain only spaces, or have leading/trailing spaces.");
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            return;
        }

        if (!fullname.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)*$")) {
            request.setAttribute("error", "Full name should contain only letters and single spaces between words.");
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            return;
        }

        if (!mobile.matches("^\\d+$")) {
            request.setAttribute("error", "Mobile number should contain only digits.");
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            return;
        }
        Part filePart = request.getPart("avatar");
        String imagePath = user.getAvatar();
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            imagePath = saveImage(fileContent, fileName, request);
        }

        user.setFullname(fullname.trim());
        user.setGender(gender);
        user.setMobile(mobile.trim());
        user.setAvatar(imagePath);

        UserDAO userDAO = new UserDAO();
        userDAO.updateUserProfile(user);

        session.setAttribute("account", user);
        response.sendRedirect("home");
    }

    private String saveImage(InputStream fileContent, String fileName, HttpServletRequest request) throws IOException {
        String uploadDirectory = getServletContext().getRealPath("/resources/images/user");
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        String filePath = uploadDirectory + File.separator + uniqueFileName;

        Files.copy(fileContent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return "resources/images/user/" + uniqueFileName;
    }

}
