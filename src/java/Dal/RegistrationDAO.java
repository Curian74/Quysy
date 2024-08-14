package Dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Registration;
import Model.Subject;
import Model.User;
import Model.Package;
import java.util.HashMap;
import java.util.Map;

public class RegistrationDAO extends DBContext {

    public RegistrationDAO() {
    }

    //Get all registration from the database
    public List<Registration> getAllRegistrations() {
        List<Registration> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Registration]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Registration r = new Registration();
                r.setRegistrationId(rs.getInt(1));
                r.setUserId(rs.getInt(2));
                r.setSubjectId(rs.getInt(3));
                r.setPackageId(rs.getInt(4));
                r.setRegistrationDate(rs.getDate(5));
                r.setValidFrom(rs.getDate(6));
                r.setValidTo(rs.getDate(7));
                r.setTotalCost(rs.getDouble(8));
                r.setNotes(rs.getString(9));
                r.setLastUpdatedBy(rs.getInt(10));
                r.setFullName(rs.getString(11));
                r.setEmail(rs.getString(12));
                r.setMobile(rs.getString(13));
                r.setGender(rs.getBoolean(14));
                r.setUpdatedDate(rs.getDate(15));
                r.setRegistrationStatus(rs.getInt(16));
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    //Get the registration by id
    public Registration getRegistrationByID(int id) {
        String sql = """
                     SELECT
                     \tr.*,
                     \ts.subject_name,
                     \tp.package_name,
                     \trs.status_name 
                     FROM
                     \t[Registration] r
                     \tJOIN [Subject] s ON s.subject_id = r.subject_id
                     \tJOIN [Package] p ON p.package_id = r.package_id
                     \tJOIN [RegistrationStatus] rs ON rs.status_id = r.registration_status
                     WHERE
                     \tr.registration_id = ?""";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Registration r = new Registration();
                r.setRegistrationId(rs.getInt(1));
                r.setUserId(rs.getInt(2));
                r.setSubjectId(rs.getInt(3));
                r.setPackageId(rs.getInt(4));
                r.setRegistrationDate(rs.getDate(5));
                r.setValidFrom(rs.getDate(6));
                r.setValidTo(rs.getDate(7));
                r.setTotalCost(rs.getDouble(8));
                r.setNotes(rs.getString(9));
                r.setLastUpdatedBy(rs.getInt(10));
                r.setFullName(rs.getString(11));
                r.setEmail(rs.getString(12));
                r.setMobile(rs.getString(13));
                r.setGender(rs.getBoolean(14));
                r.setUpdatedDate(rs.getDate(15));
                r.setRegistrationStatus(rs.getInt(16));
                r.setSubjectName(rs.getString(17));
                r.setPackageName(rs.getString(18));
                r.setStatus(rs.getString(19));
                return r;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Get all the registration made by one user
    public List<Registration> getAllRegistrationByUserId(User u) {
        List<Registration> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Registration] WHERE [user_id] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, u.getUserId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Registration r = new Registration();
                r.setRegistrationId(rs.getInt(1));
                r.setUserId(rs.getInt(2));
                r.setSubjectId(rs.getInt(3));
                r.setPackageId(rs.getInt(4));
                r.setRegistrationDate(rs.getDate(5));
                r.setValidFrom(rs.getDate(6));
                r.setValidTo(rs.getDate(7));
                r.setTotalCost(rs.getDouble(8));
                r.setNotes(rs.getString(9));
                r.setLastUpdatedBy(rs.getInt(10));
                r.setFullName(rs.getString(11));
                r.setEmail(rs.getString(12));
                r.setMobile(rs.getString(13));
                r.setGender(rs.getBoolean(14));
                r.setUpdatedDate(rs.getDate(15));
                r.setRegistrationStatus(rs.getInt(16));
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Registration> getMyRegistrations(User u, int categoryId) {
        List<Registration> list = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT "
                    + "    r.*, "
                    + "    s.[subject_name], "
                    + "    p.[package_name], "
                    + "    rs.[status_name] "
                    + "FROM "
                    + "    [Registration] r "
                    + "    JOIN [Subject] s ON r.[subject_id] = s.[subject_id] "
                    + "    JOIN [Package] p ON r.[package_id] = p.[package_id] "
                    + "    JOIN [RegistrationStatus] rs ON r.[registration_status] = rs.[status_id] "
                    + "    JOIN [Category] c ON s.[category_id] = c.[category_id] "
                    + "WHERE r.user_id = ?"
            );

            // Append filters for subjectName and categoryId if provided
            if (categoryId > 0) {
                sql.append(" AND c.[category_id] = ?");
            }

            PreparedStatement st = connection.prepareStatement(sql.toString());
            st.setInt(1, u.getUserId());

            int paramIndex = 2;
            if (categoryId > 0) {
                st.setInt(paramIndex++, categoryId);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Registration r = new Registration();
                r.setRegistrationId(rs.getInt(1));
                r.setUserId(rs.getInt(2));
                r.setSubjectId(rs.getInt(3));
                r.setPackageId(rs.getInt(4));
                r.setRegistrationDate(rs.getDate(5));
                r.setValidFrom(rs.getDate(6));
                r.setValidTo(rs.getDate(7));
                r.setTotalCost(rs.getDouble(8));
                r.setNotes(rs.getString(9));
                r.setLastUpdatedBy(rs.getInt(10));
                r.setFullName(rs.getString(11));
                r.setEmail(rs.getString(12));
                r.setMobile(rs.getString(13));
                r.setGender(rs.getBoolean(14));
                r.setUpdatedDate(rs.getDate(15));
                r.setRegistrationStatus(rs.getInt(16));
                r.setSubjectName(rs.getString(17));
                r.setPackageName(rs.getString(18));
                r.setStatus(rs.getString(19));

                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Registration> getUserPracticesSubjects(User u) {
        List<Registration> list = new ArrayList<>();
        try {
            String sql = "SELECT r.subject_id, s.subject_name "
                    + "FROM Registration r "
                    + "JOIN Subject s ON r.subject_id = s.subject_id "
                    + "WHERE r.user_id = ? "
                    + "AND r.registration_status = 1";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, u.getUserId());

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Registration r = new Registration();
                r.setSubjectId(rs.getInt(1));
                r.setSubjectName(rs.getString(2));

                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean registerSubject(User u, Subject s, Package p, Registration r) {
        try {
            // Calculate the valid_to date
            LocalDateTime validToDate = LocalDateTime.now().plusMonths(p.getDuration());

            String sql = """
                         INSERT INTO [dbo].[Registration]
                                    ([user_id]
                                    ,[subject_id]
                                    ,[package_id]
                                    ,[valid_to]
                                    ,[total_cost]
                                    ,[notes]
                                    ,[registration_status]
                                    ,[full_name]
                                    ,[email]
                                    ,[mobile]
                                    ,[gender])
                              VALUES(?,?,?,?,?,?,?,?,?,?,?)""";

            PreparedStatement st = connection.prepareStatement(sql);

            if (u != null) {
                // Logged-in user case
                st.setInt(1, u.getUserId());
            } else {
                // Non-logged-in user case
                st.setObject(1, null);
            }

            st.setInt(2, s.getSubjectId());
            st.setInt(3, p.getPackageId());
            st.setObject(4, validToDate);
            st.setDouble(5, p.getSalePrice());
            st.setString(6, " "); // Placeholder for notes
            st.setInt(7, r.getRegistrationStatus());
            st.setString(8, r.getFullName());
            st.setString(9, r.getEmail());
            st.setString(10, r.getMobile());
            st.setBoolean(11, r.isGender());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean editRegistration(Package p, String note, Registration r) {

        String sql = """
                     UPDATE [dbo].[Registration] SET
                     \t[package_id] = ?,
                     \t[notes] = ?,
                     \t[full_name] = ?,
                     \t[email] = ?,
                     \t[mobile] = ?,
                     \t[gender] = ?
                     WHERE [registration_id] = ?""";
        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, p.getPackageId());
            st.setString(2, note);
            st.setString(3, r.getFullName());
            st.setString(4, r.getEmail());
            st.setString(5, r.getMobile());
            st.setBoolean(6, r.isGender());
            st.setInt(7, r.getRegistrationId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean cancelRegistration(int registrationId) {

        String sql = "UPDATE [dbo].[Registration] SET [registration_status] = 2 WHERE [registration_id] = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, registrationId);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Registration> getRegistrations(String registrationTimeFrom, String registrationTimeTo, String status) {
        List<Registration> list = new ArrayList<>();
        String sql = "SELECT r.*, s.subject_name, p.package_name, rs.status_name "
                + "FROM Registration r "
                + "JOIN Subject s ON r.subject_id = s.subject_id "
                + "JOIN Package p ON r.package_id = p.package_id "
                + "JOIN RegistrationStatus rs ON r.registration_status = rs.status_id "
                + "WHERE 1=1";

        // Add filters to the query
        if (registrationTimeFrom != null && !registrationTimeFrom.isEmpty()) {
            sql += " AND r.register_date >= ?";
        }
        if (registrationTimeTo != null && !registrationTimeTo.isEmpty()) {
            sql += " AND r.register_date <= ?";
        }
        if (status != null && !status.isEmpty()) {
            sql += " AND rs.status_name = ?";
        }
        sql += " ORDER BY r.registration_id";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            if (registrationTimeFrom != null && !registrationTimeFrom.isEmpty()) {
                ps.setString(paramIndex++, registrationTimeFrom);
            }
            if (registrationTimeTo != null && !registrationTimeTo.isEmpty()) {
                ps.setString(paramIndex++, registrationTimeTo);
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registration reg = new Registration();
                    reg.setRegistrationId(rs.getInt("registration_id"));
                    reg.setUserId(rs.getInt("user_id"));
                    reg.setSubjectId(rs.getInt("subject_id"));
                    reg.setPackageId(rs.getInt("package_id"));
                    reg.setRegistrationDate(rs.getTimestamp("register_date"));
                    reg.setValidFrom(rs.getTimestamp("valid_from"));
                    reg.setValidTo(rs.getTimestamp("valid_to"));
                    reg.setTotalCost(rs.getDouble("total_cost"));
                    reg.setNotes(rs.getString("notes"));
                    reg.setLastUpdatedBy(rs.getInt("last_updated_by"));
                    reg.setFullName(rs.getString("full_name"));
                    reg.setEmail(rs.getString("email"));
                    reg.setMobile(rs.getString("mobile"));
                    reg.setGender(rs.getBoolean("gender"));
                    reg.setUpdatedDate(rs.getTimestamp("updated_date"));
                    reg.setRegistrationStatus(rs.getInt("registration_status"));
                    reg.setSubjectName(rs.getString("subject_name"));
                    reg.setPackageName(rs.getString("package_name"));
                    reg.setStatus(rs.getString("status_name"));
                    list.add(reg);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Map<Integer, String> getLastUpdatedByMap() {
        Map<Integer, String> map = new HashMap<>();
        String sql = "SELECT user_id, full_name FROM Users";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getInt("user_id"), rs.getString("full_name"));
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public List<String> getStatusNames() {
        List<String> statusNames = new ArrayList<>();
            String sql = "SELECT status_name FROM RegistrationStatus";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                statusNames.add(rs.getString("status_name"));
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statusNames;
    }

    public int getNoOfPages(int pageSize) {
        String sql = "SELECT COUNT(*) FROM Registration";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int totalRecords = rs.getInt(1);
                return (int) Math.ceil(totalRecords / (double) pageSize);
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }
    
    public boolean updateRegistration(Registration registration) {
        String sql = "UPDATE [Registration] SET subject_id = ?, package_id = ?, full_name = ?, gender = ?, email = ?, mobile = ?, registration_status = ?, notes = ?, last_updated_by = ?, updated_date = GETDATE() WHERE registration_id = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            
            st.setInt(1, registration.getSubjectId());
            st.setInt(2, registration.getPackageId());
            st.setString(3, registration.getFullName());
            st.setBoolean(4, registration.isGender());
            st.setString(5, registration.getEmail());
            st.setString(6, registration.getMobile());
            st.setInt(7, registration.getRegistrationStatus());
            st.setString(8, registration.getNotes());
            st.setInt(9, registration.getLastUpdatedBy());
            st.setInt(10, registration.getRegistrationId());

            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
