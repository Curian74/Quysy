package Dal;

import Model.DimensionType;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DimensionTypeDAO extends DBContext {
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<DimensionType> getDimensionTypesBySubjectId(int subjectId) {
        List<DimensionType> list = new ArrayList<>();
        try {
            String sql = """
                         SELECT dt.* from DimensionType dt
                         JOIN Dimensions d on d.dimension_type_id = dt.dimension_type_id
                         WHERE d.subject_id = ?""";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, subjectId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                DimensionType sd = new DimensionType();
                sd.setDimensionTypeId(rs.getInt(1));
                sd.setDimensionTypeName(rs.getString(2));
                sd.setDescription(rs.getString(3));
                list.add(sd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public boolean addDimensionType(String name, String desc, int userId) {
        String sql = "INSERT INTO [dbo].[DimensionType](dimension_type_name, description, updated_by) "
                + "VALUES(?,?,?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, name.trim());
            st.setString(2, desc.trim());
            st.setInt(3, userId);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public List<DimensionType> getAllDimensionTypes() {
        List<DimensionType> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM DimensionType dt JOIN Users u ON dt.updated_by = u.user_id";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                DimensionType sd = new DimensionType();
                sd.setDimensionTypeId(rs.getInt(1));
                sd.setDimensionTypeName(rs.getString(2));
                sd.setDescription(rs.getString(3));
                Timestamp createDate = rs.getTimestamp(4);
                Timestamp updateDate = rs.getTimestamp(5);

                // Format the dates
                String formattedCreateDate = dateFormat.format(createDate);
                String formattedUpdateDate = dateFormat.format(updateDate);

                // Set the formatted dates
                sd.setCreateDate(formattedCreateDate);
                sd.setUpdateDate(formattedUpdateDate);

                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFullname(rs.getString("full_name"));

                sd.setUpdatedBy(u);
                list.add(sd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public DimensionType getSubjectDimensionTypeByID(int id) {
        String sql = "SELECT * FROM [DimensionType] WHERE [dimension_type_id] = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                DimensionType sd = new DimensionType();
                sd.setDimensionTypeId(rs.getInt(1));
                sd.setDimensionTypeName(rs.getString(2));
                sd.setDescription(rs.getString(3));
                return sd;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean editDimensionType(int typeId, String name, String desc, int userId) {
        String sql = "UPDATE [dbo].[DimensionType] SET dimension_type_name = ?, description = ?, update_date = ?, updated_by = ? "
                + "WHERE dimension_type_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, name.trim());
            st.setString(2, desc.trim());
            
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            
            st.setTimestamp(3, timestamp);
            st.setInt(4, userId);
            st.setInt(5, typeId);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
