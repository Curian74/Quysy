package Controller;

import Dal.SettingTypeDAO;
import Dal.SettingsDAO;
import Model.SettingType;
import Model.Settings;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class SettingDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String editParam = request.getParameter("edit");

        HttpSession session = request.getSession();
        String message = (String) session.getAttribute("message");
        String messageType = (String) session.getAttribute("messageType");

        // Remove the message attributes from the session after retrieving them
        if (message != null && messageType != null) {
            request.setAttribute("message", message);
            request.setAttribute("messageType", messageType);
            session.removeAttribute("message");
            session.removeAttribute("messageType");
        }

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int settingId = Integer.parseInt(idParam);
                SettingsDAO settingsDAO = new SettingsDAO();
                SettingTypeDAO settingtypeDAO = new SettingTypeDAO();
                Settings setting = settingsDAO.getSettingById(settingId);
                if (setting != null) {
                    request.setAttribute("setting", setting);
                    request.setAttribute("edit", "true".equals(editParam));
                    List<SettingType> typeList = settingtypeDAO.getAllSettingTypes();
                    request.setAttribute("typeList", typeList);

                    request.getRequestDispatcher("setting_details.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Setting not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid setting ID");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Setting ID is required");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int settingId = Integer.parseInt(request.getParameter("settingId"));
        String name = request.getParameter("name");
        int order = Integer.parseInt(request.getParameter("order"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        String description = request.getParameter("description");
        int settingTypeId = Integer.parseInt(request.getParameter("settingTypeId"));

        SettingsDAO settingsDAO = new SettingsDAO();
        Settings setting = settingsDAO.getSettingById(settingId);

        if (setting != null) {
            List<Settings> allSettings = settingsDAO.getAllSettings(null, null);
            boolean isDuplicate = false;
            for (Settings s : allSettings) {
                if (s.getSettingId() != settingId && s.getValue().equalsIgnoreCase(name)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                HttpSession session = request.getSession();
                session.setAttribute("message", "Error! A setting with this name already exists.");
                session.setAttribute("messageType", "error");
                response.sendRedirect(request.getContextPath() + "/setting-detail?id=" + settingId + "&edit=true");
                return;
            }

            setting.setValue(name);
            setting.setOrder(order);
            setting.setStatus(status);
            setting.setDescription(description);
            setting.setSettingTypeId(settingTypeId); 
            boolean updated = settingsDAO.updateSetting(setting);

            String message = updated ? "Setting updated successfully" : "Failed to update setting";
            String messageType = updated ? "success" : "error";

            HttpSession session = request.getSession();
            session.setAttribute("message", message);
            session.setAttribute("messageType", messageType);

            response.sendRedirect(request.getContextPath() + "/setting-detail?id=" + settingId);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Setting not found");
        }
    }
}
