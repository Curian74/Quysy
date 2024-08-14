package Dal;

import Model.RegistrationStatus;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationStatusDAO extends DBContext {
    
    public List<RegistrationStatus> getAllRegistrationStatus() {
        List<RegistrationStatus> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [RegistrationStatus]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                RegistrationStatus r = new RegistrationStatus(rs.getInt(1), rs.getString(2));
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationStatusDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}
