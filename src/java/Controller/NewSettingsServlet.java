/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dal.SettingsDAO;
import Model.Settings;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author asus
 */
public class NewSettingsServlet extends HttpServlet {

    SettingsDAO sdao = new SettingsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            //remove the old input after perform insertion
            session.removeAttribute("settingName");
            session.removeAttribute("settingType");
            session.removeAttribute("settingDes");
            session.removeAttribute("settingStatus");

            List<Settings> settingsList = sdao.getAllSettings(null, null);

            //get parameters
            String name = request.getParameter("settingName");
            String typeRaw = request.getParameter("settingType");
            String des = request.getParameter("des");
            String statusRaw = request.getParameter("status");

            int maxOrder = 0;
            Set<Integer> usedOrders = new HashSet<>();
            
            for(Settings s : settingsList) {
                usedOrders.add(s.getOrder());
                maxOrder = Math.max(maxOrder, s.getOrder());
            }

            int nextOrder = 1;
            while(nextOrder <= maxOrder) {
                if(!usedOrders.contains(nextOrder)) {
                    break;
                }
                nextOrder++;
            }

            int orderValue = nextOrder;

            int typeId = Integer.parseInt(typeRaw);
            boolean status = Boolean.parseBoolean(statusRaw);

            session.setAttribute("openModal", true);

            //check for duplicated settings name
            for (Settings s : settingsList) {
                if (name.equalsIgnoreCase(s.getValue())) {
                    session.setAttribute("settingName", name);
                    session.setAttribute("settingType", typeId);
                    session.setAttribute("settingDes", des);
                    session.setAttribute("settingStatus", status);
                    session.setAttribute("error", "Error! The settings already existed");
                    response.sendRedirect("settings-list");
                    return;
                }
            }

            //perform adding and checking if the insertion is successful
            boolean check = sdao.addSettings(name, typeId, orderValue, status, des);

            if (check) {
                session.setAttribute("successMessage", "Added new Settings successfully");
                response.sendRedirect("settings-list");
            } else {
                session.setAttribute("error", "Error! Failed to add new Settings");
                response.sendRedirect("settings-list");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
