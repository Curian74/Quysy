package Controller;

import Dal.SettingsDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author asus
 */
public class DeleteSettingsServlet extends HttpServlet {

    SettingsDAO sdao = new SettingsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            //get setting id and convert to integer
            String idRaw = request.getParameter("id");
            int settingId = Integer.parseInt(idRaw);

            //perform deletion
            boolean check = sdao.deleteSettings(settingId);

            //check if the deletion is successful
            if (!check) {
                session.setAttribute("deleteSettingsMess", "Error! Delete settings failed");
            } else {
                session.setAttribute("deleteSettingsMess", "Deleted settings successfully");
            }
            //forward to setting list
            response.sendRedirect("settings-list");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
