package Controller;

import Dal.SettingsDAO;
import Model.Settings;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author asus
 */
public class SettingStatusServlet extends HttpServlet {

    SettingsDAO dao = new SettingsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            String idRaw = request.getParameter("id");

            int settingId = Integer.parseInt(idRaw);

            Settings s = dao.getSettingById(settingId);

            boolean check = dao.settingStatus(settingId, s.isStatus());
            if (check) {
                session.setAttribute("statusMes", "Updated Settings status successfully");
                response.sendRedirect("settings-list");
            } else {
                session.setAttribute("statusMes", "Error! Failed to update Settings status");
                response.sendRedirect("settings-list");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
