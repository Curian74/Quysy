/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.SettingType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asus
 */
public class SettingTypeDAO extends DBContext{
    
    public List<SettingType> getAllSettingTypes() {
        List<SettingType> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [SettingType]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SettingType stt = new SettingType();
                stt.setSettingTypeId(rs.getInt(1));
                stt.setSettingTypeName(rs.getString(2));
                list.add(stt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    
}
