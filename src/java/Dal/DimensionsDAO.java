package Dal;

import Model.Dimensions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DimensionsDAO extends DBContext {

    public List<Dimensions> getAllDimensionsBySubjectId(int subjectId) {
        List<Dimensions> list = new ArrayList<>();
        try {
            String sql = "SELECT d.id, d.dimension_id, d.dimension_type_id, d.subject_id, s.dimension_name, dt.dimension_type_name "
                    + "FROM Dimensions d "
                    + "JOIN DimensionType dt ON dt.dimension_type_id = d.dimension_type_id "
                    + "JOIN SubjectDimension s ON s.dimension_id = d.dimension_id "
                    + "WHERE d.subject_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, subjectId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Dimensions s = new Dimensions();
                s.setId(rs.getInt("id"));
                s.setDimensionId(rs.getInt("dimension_id"));
                s.setDimensionTypeId(rs.getInt("dimension_type_id"));
                s.setSubjectId(rs.getInt("subject_id"));
                s.setDimensionName(rs.getString("dimension_name"));
                s.setDimensionType(rs.getString("dimension_type_name"));

                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean addSubjectDimension(int dimId, int typeId, int subjectId) {

        String sql = "INSERT INTO Dimensions VALUES(?,?,?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, dimId);
            st.setInt(2, typeId);
            st.setInt(3, subjectId);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DimensionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Dimensions> getAllDimensions() {
        List<Dimensions> list = new ArrayList<>();
        try {
            String sql = """
                         WITH CTE AS (
                             SELECT 
                                 d.*, 
                                 s.dimension_name, 
                                 dt.dimension_type_name,
                                 ROW_NUMBER() OVER (PARTITION BY d.dimension_id ORDER BY dt.dimension_type_name) AS rn
                             FROM 
                                 Dimensions d
                             JOIN 
                                 DimensionType dt ON dt.dimension_type_id = d.dimension_type_id
                             JOIN 
                                 SubjectDimension s ON s.dimension_id = d.dimension_id
                         )
                         SELECT *
                         FROM CTE
                         WHERE rn = 1;""";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Dimensions s = new Dimensions();
                s.setId(rs.getInt(1));
                s.setDimensionId(rs.getInt(2));
                s.setDimensionTypeId(rs.getInt(3));
                s.setSubjectId(rs.getInt(4));
                s.setDimensionName(rs.getString(5));
                s.setDimensionType(rs.getString(6));

                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    //get the exact dimension from many to many table
    public Dimensions getDimensionIdentifierById(int id) {
        try {
            String sql = "SELECT * FROM Dimensions WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Dimensions s = new Dimensions();
                s.setId(rs.getInt(1));
                s.setDimensionId(rs.getInt(2));
                s.setDimensionTypeId(rs.getInt(3));
                s.setSubjectId(rs.getInt(4));
                return s;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean editSubjectDimension(int dimensionId, int typeId, int identifyId) {

        String sql = "UPDATE Dimensions\n"
                + "SET dimension_id = ?, dimension_type_id = ?\n"
                + " WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, dimensionId);
            st.setInt(2, typeId);
            st.setInt(3, identifyId);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DimensionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteSubjectDimension(int dimensionId) {

        String sql = "DELETE Dimensions WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, dimensionId);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DimensionsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
