package Controller;

import Dal.SettingTypeDAO;
import Dal.SettingsDAO;
import Model.SettingType;
import Model.Settings;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SettingListServlet extends HttpServlet {

    SettingsDAO sdao = new SettingsDAO();
    SettingTypeDAO stdao = new SettingTypeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        //get all data
        String typeFilter = request.getParameter("type");
        String statusFilter = request.getParameter("status");

        //check for filtering
        Integer typeId = (typeFilter != null && !typeFilter.isEmpty()) ? Integer.parseInt(typeFilter) : null;
        Boolean status = (statusFilter != null && !statusFilter.isEmpty()) ? Boolean.parseBoolean(statusFilter) : null;

        List<Settings> settingList = sdao.getAllSettings(typeId, status);
        List<SettingType> typeList = stdao.getAllSettingTypes();

        //prepare data to be forwarded
        request.setAttribute("settingsList", settingList);
        request.setAttribute("typeList", typeList);

        // Pass the current filter parameters back to the JSP
        request.setAttribute("currentTypeFilter", typeId);
        request.setAttribute("currentStatusFilter", status);

        //check condition to open the modal
        Boolean openModal = (Boolean) request.getSession().getAttribute("openModal");
        request.setAttribute("openModal", openModal);

        session.removeAttribute("openModal");

        request.getRequestDispatcher("settings_list.jsp").forward(request, response);
    }

}
