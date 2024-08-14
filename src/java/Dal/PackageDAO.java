package Dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Package;
import Model.User;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class PackageDAO extends DBContext {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //Get all package from the database
    public List<Package> getAllPackages() {
        List<Package> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Package p JOIN [Users] u ON u.user_id = p.updated_by JOIN Subject s on s.subject_id = p.subject_id";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Package p = new Package();
                p.setPackageId(rs.getInt(1));
                p.setSubjectId(rs.getInt(2));
                p.setPackageName(rs.getString(3));
                p.setDuration(rs.getInt(4));
                p.setListPrice(rs.getDouble(5));
                p.setSalePrice(rs.getDouble(6));
                p.setStatus(rs.getString(7));
                p.setDescription(rs.getString(8));

                Timestamp createDate = rs.getTimestamp(9);
                Timestamp updateDate = rs.getTimestamp(10);

                // Format the dates
                String formattedCreateDate = dateFormat.format(createDate);
                String formattedUpdateDate = dateFormat.format(updateDate);

                // Set the formatted dates
                p.setCreatedDate(formattedCreateDate);
                p.setUpdateDate(formattedUpdateDate);

                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFullname(rs.getString("full_name"));

                p.setUpdatedBy(u);
                p.setSubjectName(rs.getString("subject_name"));

                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean addPackage(int subjectId, String pname,
            int duration, double listPrice, double sellPrice, String description, int userId) {

        String sql = "Insert into Package(subject_id,package_name,duration,list_price,sell_price,status,description, updated_by) VALUES(?,?,?,?,?,0,?,?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, subjectId);
            st.setString(2, pname);
            st.setInt(3, duration);
            st.setDouble(4, listPrice);
            st.setDouble(5, sellPrice);
            st.setString(6, description);
            st.setInt(7, userId);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Get the package by id
    public Package getPackageByID(int id) {
        String sql = "SELECT * FROM [Package] WHERE [package_id] = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Package p = new Package();
                p.setPackageId(rs.getInt(1));
                p.setSubjectId(rs.getInt(2));
                p.setPackageName(rs.getString(3));
                p.setDuration(rs.getInt(4));
                p.setListPrice(rs.getDouble(5));
                p.setSalePrice(rs.getDouble(6));
                p.setStatus(rs.getString(7));
                p.setDescription(rs.getString(8));

                Timestamp createDate = rs.getTimestamp(9);

                String formattedCreateDate = dateFormat.format(createDate);

                p.setCreatedDate(formattedCreateDate);
                return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean editPackage(int packId, String pname, int duration, double listPrice,
            double sellPrice, String description, int userId, int subjectId) {

        String sql = "UPDATE [dbo].[Package] SET package_name = ?, duration =?, "
                + "list_price = ?, sell_price = ?, description = ?, update_date = ?, updated_by = ?, subject_id = ? WHERE package_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, pname);
            st.setInt(2, duration);
            st.setDouble(3, listPrice);
            st.setDouble(4, sellPrice);
            st.setString(5, description);
            
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            
            st.setTimestamp(6, timestamp);
            st.setInt(7, userId);
            st.setInt(8, subjectId);
            st.setInt(9, packId);
            
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deActivate(int packId) {

        String sql = "UPDATE [dbo].[Package] SET status = 0 where package_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, packId);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean Activate(int packId) {

        String sql = "UPDATE [dbo].[Package] SET status = 1 where package_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, packId);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Get all packages for a given subject ID
    public List<Package> getPackagesForSubject(int subjectId) {
        List<Package> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Package] WHERE [subject_id] = ? AND status = 1";  //Package must be activated
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, subjectId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Package p = new Package();
                p.setPackageId(rs.getInt(1));
                p.setSubjectId(rs.getInt(2));
                p.setPackageName(rs.getString(3));
                p.setDuration(rs.getInt(4));
                p.setListPrice(rs.getDouble(5));
                p.setSalePrice(rs.getDouble(6));
                p.setStatus(rs.getString(7));
                p.setDescription(rs.getString(8));

                Timestamp createDate = rs.getTimestamp(9);

                String formattedCreateDate = dateFormat.format(createDate);

                p.setCreatedDate(formattedCreateDate);
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Package> allPackageController(int subjectId) {
        List<Package> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Package] WHERE [subject_id] = ?";  //All packages including the inactivated one
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, subjectId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Package p = new Package();
                p.setPackageId(rs.getInt(1));
                p.setSubjectId(rs.getInt(2));
                p.setPackageName(rs.getString(3));
                p.setDuration(rs.getInt(4));
                p.setListPrice(rs.getDouble(5));
                p.setSalePrice(rs.getDouble(6));
                p.setStatus(rs.getString(7));
                p.setDescription(rs.getString(8));

                Timestamp createDate = rs.getTimestamp(9);

                String formattedCreateDate = dateFormat.format(createDate);

                p.setCreatedDate(formattedCreateDate);
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Package getLowestPackage(int id) {
        String sql = "SELECT TOP 1 * FROM [Package] WHERE subject_id = ? ORDER BY sell_price ASC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Package p = new Package();
                p.setPackageId(rs.getInt(1));
                p.setSubjectId(rs.getInt(2));
                p.setPackageName(rs.getString(3));
                p.setDuration(rs.getInt(4));
                p.setListPrice(rs.getDouble(5));
                p.setSalePrice(rs.getDouble(6));
                p.setStatus(rs.getString(7));
                p.setDescription(rs.getString(8));

                Timestamp createDate = rs.getTimestamp(9);

                String formattedCreateDate = dateFormat.format(createDate);

                p.setCreatedDate(formattedCreateDate);
                return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Get all lowest package from the database
    public List<Package> getAllLowestPackages() {
        List<Package> list = new ArrayList<>();
        try {
            String sql = "SELECT p1.* FROM [Package] p1 "
                    + "JOIN (SELECT subject_id, MIN(sell_price) AS min_price "
                    + "FROM [Package] GROUP BY subject_id) p2 "
                    + "ON p1.subject_id = p2.subject_id AND p1.sell_price = p2.min_price";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Package p = new Package();
                p.setPackageId(rs.getInt(1));
                p.setSubjectId(rs.getInt(2));
                p.setPackageName(rs.getString(3));
                p.setDuration(rs.getInt(4));
                p.setListPrice(rs.getDouble(5));
                p.setSalePrice(rs.getDouble(6));
                p.setStatus(rs.getString(7));
                p.setDescription(rs.getString(8));

                Timestamp createDate = rs.getTimestamp(9);

                String formattedCreateDate = dateFormat.format(createDate);

                p.setCreatedDate(formattedCreateDate);
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
