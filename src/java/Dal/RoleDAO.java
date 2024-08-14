package Dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Role;

public class RoleDAO extends DBContext {
    
    //Get all role from the database
    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Role]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Role r = new Role(rs.getInt(1), rs.getString(2));
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    //Get the role by id
    public Role getRoleByID(int id) {
        String sql = "SELECT * FROM [Role] WHERE [role_id] = ?";
        
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Role r = new Role(rs.getInt(1), rs.getString(2));
                return r;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
