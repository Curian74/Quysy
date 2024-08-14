package Dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.SubjectDimension;
import Model.User;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class SubjectDimensionDAO extends DBContext {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //Get all subject dimension from the database
    public List<SubjectDimension> getAllSubjectDimensions() {
        List<SubjectDimension> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [SubjectDimension] sd JOIN Users u ON sd.updated_by = u.user_id";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SubjectDimension sd = new SubjectDimension();
                sd.setDimensionId(rs.getInt(1));
                sd.setDimensionName(rs.getString(2));
                sd.setDescription(rs.getString(3));

                // Retrieve the dates as Timestamp
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
            Logger.getLogger(SubjectDimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<SubjectDimension> getSubjectDimensionBySubjectId(int subjectId) {
        List<SubjectDimension> list = new ArrayList<>();
        try {
            String sql = """
                         SELECT s.* from SubjectDimension s
                         JOIN Dimensions d on d.dimension_id = s.dimension_id
                         WHERE d.subject_id = ?""";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, subjectId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SubjectDimension sd = new SubjectDimension();
                sd.setDimensionId(rs.getInt(1));
                sd.setDimensionName(rs.getString(2));
                sd.setDescription(rs.getString(3));
                list.add(sd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean editSubjectDimension(int dimensionId, String name, String desc, int userId) {
        String sql = "UPDATE [dbo].[SubjectDimension] SET dimension_name = ?, description = ?, update_date = ?, updated_by = ? "
                + "WHERE dimension_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, name.trim());
            st.setString(2, desc.trim());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            st.setTimestamp(3, timestamp);
            st.setInt(4, userId);
            st.setInt(5, dimensionId);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addSubjectDimension(String name, String desc, int userId) {
        String sql = "INSERT INTO [dbo].[SubjectDimension](dimension_name, description, updated_by) "
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

    public SubjectDimension getDimensionByQuestionId(int questionId) {
        try {
            String sql = "SELECT d.* FROM SubjectDimension d JOIN Question q ON d.dimension_id = q.dimension_id WHERE q.question_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, questionId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SubjectDimension sd = new SubjectDimension();
                sd.setDimensionId(rs.getInt(1));
                sd.setDimensionName(rs.getString(2));
                sd.setDescription(rs.getString(3));
                return sd;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public SubjectDimension getSubjectDimensionByQuestionId(int questionId) {
        String sql = """
                       SELECT sd.* FROM 
                       [Question] q JOIN Explanation e 
                       ON q.question_id = e.question_id
                       JOIN SubjectDimension sd 
                       ON q.dimension_id = sd.dimension_id
                       WHERE e.question_id=?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SubjectDimension sd = new SubjectDimension();
                sd.setDimensionId(rs.getInt(1));
                sd.setDimensionName(rs.getString(2));
                sd.setDescription(rs.getString(3));
                return sd;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Get the subject dimension by id
    public SubjectDimension getSubjectDimensionByID(int id) {
        String sql = "SELECT * FROM [SubjectDimension] WHERE [dimension_id] = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                SubjectDimension sd = new SubjectDimension();
                sd.setDimensionId(rs.getInt(1));
                sd.setDimensionName(rs.getString(2));
                sd.setDescription(rs.getString(3));
                return sd;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SubjectDimension> getDimensionsBySubjectId(int subjectId) {
        List<SubjectDimension> dimensions = new ArrayList<>();
        String sql = """
                     SELECT sd.dimension_id, sd.dimension_name
                     FROM SubjectDimension sd
                     JOIN Dimensions d ON sd.dimension_id = d.dimension_id
                     WHERE d.subject_id = ?""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, subjectId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SubjectDimension dimension = new SubjectDimension();
                dimension.setDimensionId(rs.getInt("dimension_id"));
                dimension.setDimensionName(rs.getString("dimension_name"));
                dimensions.add(dimension);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dimensions;
    }

}
