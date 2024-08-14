package Dal;

import Model.QuestionLevel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LevelDAO extends DBContext {
    
    public List<QuestionLevel> getAllLevels() {
        List<QuestionLevel> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Level]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                QuestionLevel l = new QuestionLevel(rs.getInt(1), rs.getString(2));
                list.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LevelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
