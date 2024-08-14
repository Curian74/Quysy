package Dal;

import Model.Settings;
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
public class SettingsDAO extends DBContext {

    public List<Settings> getAllSettings(Integer typeId, Boolean status) {
        List<Settings> list = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM [Settings] s JOIN [SettingType] st ON st.[setting_type_id] = s.[setting_type_id] WHERE 1=1");

            if (typeId != null) {
                sql.append(" AND s.[setting_type_id] = ?");
            }
            if (status != null) {
                sql.append(" AND s.[status] = ?");
            }

            PreparedStatement st = connection.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (typeId != null) {
                st.setInt(paramIndex++, typeId);
            }
            if (status != null) {
                st.setBoolean(paramIndex++, status);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Settings s = new Settings();
                s.setSettingId(rs.getInt(1));
                s.setValue(rs.getString(2));
                s.setOrder(rs.getInt(3));
                s.setStatus(rs.getBoolean(4));
                s.setDescription(rs.getString(5));
                s.setSettingTypeId(rs.getInt(6));

                s.setTypeName(rs.getString("setting_type_name"));

                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean addSettings(String name, int type, int order, boolean status, String description) {
        String sql = "INSERT INTO [dbo].[Settings]([value],[order],[status],[description],[setting_type_id])\n"
                + "VALUES(?,?,?,?,?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, name);
            st.setInt(2, order);
            st.setBoolean(3, status);
            st.setString(4, description);
            st.setInt(5, type);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error adding user: " + ex.getMessage(), ex);
        }
        return false;
    }

    public Settings getSettingById(int settingId) {
        String sql = "SELECT s.*, st.setting_type_name FROM [Settings] s "
                + "JOIN [SettingType] st ON st.[setting_type_id] = s.[setting_type_id] "
                + "WHERE s.[setting_id] = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, settingId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Settings s = new Settings();
                    s.setSettingId(rs.getInt("setting_id"));
                    s.setValue(rs.getString("value"));
                    s.setOrder(rs.getInt("order"));
                    s.setStatus(rs.getBoolean("status"));
                    s.setDescription(rs.getString("description"));
                    s.setSettingTypeId(rs.getInt("setting_type_id"));
                    s.setTypeName(rs.getString("setting_type_name"));
                    return s;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingsDAO.class.getName()).log(Level.SEVERE, "Error getting setting by ID: " + ex.getMessage(), ex);
        }
        return null;
    }

    public boolean updateSetting(Settings setting) {
        String sql = "UPDATE [dbo].[Settings] SET "
                + "[value] = ?, [order] = ?, [status] = ?, [description] = ?, [setting_type_id] = ? "
                + "WHERE [setting_id] = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, setting.getValue());
            st.setInt(2, setting.getOrder());
            st.setBoolean(3, setting.isStatus());
            st.setString(4, setting.getDescription());
            st.setInt(5, setting.getSettingTypeId());
            st.setInt(6, setting.getSettingId());

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SettingsDAO.class.getName()).log(Level.SEVERE, "Error updating setting: " + ex.getMessage(), ex);
        }
        return false;
    }
    
    public boolean settingStatus(int settingId, boolean status) {
        String sql = "UPDATE [dbo].[Settings] SET [status] = ? where setting_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            if(status){
                st.setBoolean(1, false);
            }
            else{
                st.setBoolean(1, true);
            }
            st.setInt(2, settingId);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SettingsDAO.class.getName()).log(Level.SEVERE, "Error updating setting: " + ex.getMessage(), ex);
        }
        return false;
    }

    public boolean deleteSettings(int settingId) {

        String sql = "DELETE Settings WHERE setting_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, settingId);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DimensionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
