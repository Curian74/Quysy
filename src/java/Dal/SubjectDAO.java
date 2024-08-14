package Dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Subject;
import java.sql.Statement;

public class SubjectDAO extends DBContext {

    //Get all subject from the database
    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Subject]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int addSubject(Subject s) throws SQLException {
        String sql = """
                     INSERT INTO [dbo].[Subject]
                                ([subject_name]
                                ,[thumbnail]
                                ,[category_id]
                                ,[is_featured]
                                ,[status]
                                ,[tag_line]
                                ,[brief_info]
                                ,[description]
                                ,[expert_id])
                          VALUES
                                (?,?,?,?,?,?,?,?,?)""";
        try (PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, s.getSubjectName());
            st.setString(2, s.getThumbnail());
            st.setInt(3, s.getCategoryId());
            st.setBoolean(4, s.isIsFeatured());
            st.setBoolean(5, false);
            st.setString(6, s.getTagLine());
            st.setString(7, s.getBriefInfo());
            st.setString(8, s.getDescription());
            st.setInt(9, s.getExpertId());

            int rowsUpdated = st.executeUpdate();

            if (rowsUpdated > 0) {
                try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return -1; // Return -1 if the insertion failed
    }

    public List<Subject> getAllSubjectsSortByCreatedDate() {
        List<Subject> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Subject] WHERE status = 1 ORDER BY [created_date] DESC";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    //Get the subject by id
    public Subject getSubjectByID(int id) {
        String sql = "SELECT * FROM [Subject] WHERE [subject_id] = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));
                return s;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Get feature subjects from the database
    public List<Subject> getFeatureSubjects() {
        List<Subject> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Subject] WHERE [is_featured] = 1 AND status = 1";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Subject> searchSubjectsByName(String name) {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Subject WHERE [subject_name] LIKE ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));
                subjects.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

    public List<Subject> getSubjectsByCategory(int categoryId) {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Subject WHERE [category_id] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, categoryId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));
                subjects.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

    public List<Subject> searchSubjectsByNameAndCategory(String name, int categoryId) {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Subject WHERE [subject_name] LIKE ? AND [category_id] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            st.setInt(2, categoryId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));
                subjects.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

    public List<Subject> getSubjectByStatus(boolean status) {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Subject WHERE status = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setBoolean(1, status);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));
                subjects.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

    public List<Subject> searchSubjectsByNameAndStatus(String name, boolean status) {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Subject WHERE [subject_name] LIKE ? AND [status] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            st.setBoolean(2, status);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));
                subjects.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

    public List<Subject> searchSubjectsByNameAndCategoryAndStatus(String name, int categoryId, boolean status) {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Subject WHERE subject_name LIKE ? AND category_id = ? AND status = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            st.setInt(2, categoryId);
            st.setBoolean(3, status);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));

                subjects.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

    public List<Subject> searchSubjectsByCategoryAndStatus(int categoryId, boolean status) {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Subject WHERE category_id = ? AND status = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, categoryId);
            st.setBoolean(2, status);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));

                subjects.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

    public List<Subject> getSubjectsByExpertId(int expertId) {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Subject WHERE [expert_id] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, expertId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getInt(11));
                subjects.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

    public boolean editSubjectGeneralInfo(Subject s) {

        String sql = "UPDATE [dbo].[Subject] SET subject_name = ?, thumbnail = ?, category_id = ?, is_featured = ?, status = ?"
                + ", tag_line = ?, brief_info = ?, description = ?, expert_id = ? "
                + "where subject_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, s.getSubjectName());
            st.setString(2, s.getThumbnail());
            st.setInt(3, s.getCategoryId());
            st.setBoolean(4, s.isIsFeatured());
            st.setBoolean(5, s.isStatus());
            st.setString(6, s.getTagLine());
            st.setString(7, s.getBriefInfo());
            st.setString(8, s.getDescription());
            st.setInt(9, s.getExpertId());
            st.setInt(10, s.getSubjectId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Subject> searchSubjects(String name, Integer categoryId, Boolean status, Integer expertId, String expertName, Boolean isFeatured, String orderBy) {
        List<Subject> subjects = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT s.* FROM Subject s JOIN Users u ON s.expert_id = u.user_id WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND s.[subject_name] LIKE ?");
            params.add("%" + name + "%");
        }
        if (categoryId != null) {
            sql.append(" AND s.[category_id] = ?");
            params.add(categoryId);
        }
        if (status != null) {
            sql.append(" AND s.[status] = ?");
            params.add(status);
        }
        if (expertId != null) {
            sql.append(" AND s.[expert_id] = ?");
            params.add(expertId);
        }
        if (expertName != null && !expertName.trim().isEmpty()) {
            sql.append(" AND u.[full_name] LIKE ?");
            params.add("%" + expertName + "%");
        }
        if (isFeatured != null) {
            sql.append(" AND s.[is_featured] = ?");
            params.add(isFeatured);
        }

        if (orderBy != null && !orderBy.isEmpty()) {
            switch (orderBy) {
                case "nameAsc" ->
                    sql.append(" ORDER BY s.[subject_name] ASC");
                case "nameDesc" ->
                    sql.append(" ORDER BY s.[subject_name] DESC");
                default -> {
                }
            }
        }

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Subject s = new Subject(
                            rs.getInt("subject_id"),
                            rs.getString("subject_name"),
                            rs.getString("thumbnail"),
                            rs.getInt("category_id"),
                            rs.getBoolean("is_featured"),
                            rs.getBoolean("status"),
                            rs.getString("tag_line"),
                            rs.getString("brief_info"),
                            rs.getString("description"),
                            rs.getDate("created_date"),
                            rs.getInt("expert_id")
                    );
                    subjects.add(s);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

    public boolean subjectNameExists(String subjectName) {
        String query = "SELECT COUNT(*) FROM Subject WHERE subject_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subjectName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
