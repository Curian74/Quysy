package Dal;

import Model.Role;
import jakarta.mail.MessagingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import Model.User;
import Utility.EmailUtility;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO extends DBContext {

    //get all users
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User s = new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getBoolean(5), rs.getString(6),
                        rs.getInt(7), rs.getBoolean(8), rs.getDate(9),
                        rs.getString(10), rs.getString(11),
                        rs.getString(12));
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public List<User> getAllExperts() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users where role_id = 3";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt(1));
                u.setEmail(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setFullname(rs.getString(4));
                u.setGender(rs.getBoolean(5));
                u.setRoleId(rs.getInt(6));
                u.setStatus(rs.getBoolean(7));
                u.setCreatedDate(rs.getDate(8));
                u.setAvatar(rs.getString(9));
                u.setResetToken(rs.getString(10));
                u.setResetTokenExpiry(rs.getString(11));
                u.setMobile(rs.getString(12));
                list.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public User getUserById(int userId) {
        String sql = "SELECT * FROM [Users] WHERE [user_id] = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt(1));
                u.setEmail(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setFullname(rs.getString(4));
                u.setGender(rs.getBoolean(5));
                u.setRoleId(rs.getInt(6));
                u.setStatus(rs.getBoolean(7));
                u.setCreatedDate(rs.getDate(8));
                u.setAvatar(rs.getString(9));
                u.setResetToken(rs.getString(10));
                u.setResetTokenExpiry(rs.getString(11));
                u.setMobile(rs.getString(12));
                return u;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    // Check if user already exists
    public boolean validateLogin(String email, String password) {
        String sql = "SELECT password FROM Users WHERE email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                // Check if the provided password matches the hashed password
                return BCrypt.checkpw(password, hashedPassword);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean validateStatus(String email) {
        String sql = "SELECT * FROM Users WHERE email = ? and status = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean generateUser(String email, String password, String fullname, boolean gender, String mobile) {
        if (isUserExists(email)) {
            System.out.println("Email already exists.");
            return false;
        }
        String sql = "INSERT INTO Users (email, password, full_name, gender, "
                + "role_id, status, created_date, avatar,reset_token,reset_token_expiry, mobile) "
                + "VALUES (?, ?, ?, ?, 2, 0, GETDATE(), 'resources/images/user/default.jpg', NULL, NULL, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            st.setString(3, fullname);
            st.setBoolean(4, gender);
            st.setString(5, mobile);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //check if user already exist
    public boolean isUserExists(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Send email to reset password
    public boolean sendResetPasswordEmail(String email) {
        String token = UUID.randomUUID().toString(); // Create random token
        savePasswordResetToken(email, token);

        String resetLink = "http://localhost:9999/group6/reset_password.jsp?token=" + token;
        String subject = "Password Reset Request";
        String message = "Click here to reset your password:<a href=\"" + resetLink + "\">ResetMyPassword</a> ";
        try {
            EmailUtility.sendHtmlEmail(email, subject, message);
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean genUser(String email, String fullname, boolean gender, String mobile, String password) {
        if (isUserExists(email)) {
            System.out.println("Email already exists.");
            return false;
        }
        String sql = "INSERT INTO [dbo].[Users](email,password,full_name,gender,role_id,status,mobile,avatar)"
                + " VALUES(?,?,?,?,1,0,?,ds)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            st.setString(3, fullname);
            st.setBoolean(4, gender);
            st.setString(5, mobile);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Save token into database
    public void savePasswordResetToken(String email, String token) {
        String sql = "UPDATE Users SET reset_token = ?, reset_token_expiry = DATEADD(HOUR, 1, GETDATE()) WHERE email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, token);
            st.setString(2, email);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Verify token reset password
    public boolean isResetTokenValid(String token) {
        String sql = "SELECT * FROM Users WHERE reset_token = ? AND reset_token_expiry > GETDATE()";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, token);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Update new password, also delete the token after resetting
    public boolean updatePassword(String token, String newPassword) {
        // Hash the new password using BCrypt
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        String sql = "UPDATE Users SET password = ?, reset_token = NULL, reset_token_expiry = NULL WHERE reset_token = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, hashedPassword);
            st.setString(2, token);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean changePassword(int userId, String newPassword) {
        String sql = "UPDATE Users SET password = ? WHERE user_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, newPassword);
            st.setInt(2, userId);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void updateUserProfile(User user) {
        String sql = "UPDATE Users SET full_name = ?, gender = ?, mobile = ?, avatar = ? WHERE user_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, user.getFullname());
            st.setBoolean(2, user.isGender());
            st.setString(3, user.getMobile());
            st.setString(4, user.getAvatar());
            st.setInt(5, user.getUserId());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatePassword(String password, int userid) {
        try {
            String sql = "UPDATE [Users]\n"
                    + "set [password] = ?"
                    + "where [user_id] =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, password);
            stm.setInt(2, userid);
            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public User getUserByEmail(String id) {

        String sql = "SELECT * FROM Users where [email] = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt(1));
                u.setEmail(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setFullname(rs.getString(4));
                u.setGender(rs.getBoolean(5));
                u.setRoleId(rs.getInt(6));
                u.setStatus(rs.getBoolean(7));
                u.setCreatedDate(rs.getDate(8));
                u.setAvatar(rs.getString(9));
                u.setResetToken(rs.getString(10));
                u.setResetTokenExpiry(rs.getString(11));
                u.setMobile(rs.getString(12));
                return u;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Temporarily save user information
    public boolean saveUserTemp(String email, String password, String fullname, boolean gender, String mobile, String token) {
        if (isUserExists(email)) {
            System.out.println("Email already exists.");
            return false;
        }

        if (isMobileExists(mobile)) {
            System.out.println("Mobile already exists");
            return false;
        }

        String sql = "INSERT INTO UserTemp (email, password, full_name, gender, mobile, token, token_expiry)"
                + "VALUES (?, ?, ?, ?, ?, ?, DATEADD(HOUR, 1, GETDATE()))";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            st.setString(3, fullname);
            st.setBoolean(4, gender);
            st.setString(5, mobile);
            st.setString(6, token);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Verify user's info and move it to Users
    public boolean confirmUser(String token) {
        String sql = "SELECT * FROM UserTemp WHERE token = ? AND token_expiry > GETDATE()";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, token);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("password");
                String fullname = rs.getString("full_name");
                boolean gender = rs.getBoolean("gender");
                String mobile = rs.getString("mobile");

                // Move information to Users
                String insertUser = "INSERT INTO Users (email, password, full_name, gender, role_id, status, created_date, avatar, reset_token, reset_token_expiry, mobile) "
                        + "VALUES (?, ?, ?, ?, 2, 1, GETDATE(), 'resources/images/user/default.jpg', NULL, NULL, ?)";
                PreparedStatement insertSt = connection.prepareStatement(insertUser);
                insertSt.setString(1, email);
                insertSt.setString(2, password);
                insertSt.setString(3, fullname);
                insertSt.setBoolean(4, gender);
                insertSt.setString(5, mobile);
                int userRowsAffected = insertSt.executeUpdate();

                return userRowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteUserFromUserTemp(String token) {
        String sql = "DELETE FROM UserTemp WHERE token = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, token);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String getEmailByToken(String token) {
        String sql = "SELECT email FROM UserTemp WHERE token = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, token);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getPasswordByToken(String token) {
        String sql = "SELECT password FROM UserTemp WHERE token = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, token);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Map<Integer, String> getUserNamesByIds(List<Integer> userIds) {
        Map<Integer, String> userNames = new HashMap<>();

        if (userIds == null || userIds.isEmpty()) {
            return userNames;
        }
        String sql = "SELECT user_id, full_name FROM Users WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Integer userId : userIds) {
                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        userNames.put(rs.getInt("user_id"), rs.getString("full_name"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userNames;
    }

    public User getExpertBySubjectId(int id) {

        String sql = """
                     select u.* from Users u
                     JOIN Subject s on s.expert_id = u.user_id
                     where u.role_id = 3 and subject_id = ?""";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt(1));
                u.setEmail(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setFullname(rs.getString(4));
                u.setGender(rs.getBoolean(5));
                u.setRoleId(rs.getInt(6));
                u.setStatus(rs.getBoolean(7));
                u.setCreatedDate(rs.getDate(8));
                u.setAvatar(rs.getString(9));
                u.setResetToken(rs.getString(10));
                u.setResetTokenExpiry(rs.getString(11));
                u.setMobile(rs.getString(12));
                return u;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<User> getUsers(int role, Boolean status, Boolean gender) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT u.*, r.role_name FROM Users u LEFT JOIN Role r ON u.role_id = r.role_id WHERE 1=1";

        if (role > 0) {
            sql += " AND u.role_id = ?";
        }
        if (status != null) {
            sql += " AND u.status = ?";
        }
        if (gender != null) {
            sql += " AND u.gender = ?";
        }

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int paramIndex = 1;
            if (role > 0) {
                st.setInt(paramIndex++, role);
            }
            if (status != null) {
                st.setBoolean(paramIndex++, status);
            }
            if (gender != null) {
                st.setBoolean(paramIndex++, gender);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getBoolean("gender"),
                        rs.getString("mobile"),
                        rs.getInt("role_id"),
                        rs.getBoolean("status"),
                        rs.getDate("created_date"),
                        rs.getString("avatar"),
                        rs.getString("reset_token"),
                        rs.getString("reset_token_expiry")
                );
                user.setRoleName(rs.getString("role_name"));
                list.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET role_id = ?, status = ? WHERE user_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, user.getRoleId());
            st.setBoolean(2, user.isStatus());
            st.setInt(3, user.getUserId());
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT role_id, role_name FROM Role";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Role role = new Role(rs.getInt("role_id"), rs.getString("role_name"));
                list.add(role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean addUser(String fullName, String email, boolean gender, String mobile, int roleId, boolean status, String hashedPassword) {
        String sql = "INSERT INTO Users (full_name, email, gender, mobile, role_id, status, password, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, fullName);
            st.setString(2, email);
            st.setBoolean(3, gender);
            st.setString(4, mobile);
            st.setInt(5, roleId);
            st.setBoolean(6, status);
            st.setString(7, hashedPassword);
            st.setString(8, "default_avatar.jpg"); // Giá trị mặc định cho avatar
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error adding user: " + ex.getMessage(), ex);
        }
        return false;
    }

    public boolean isMobileExists(String mobile) {
        String sql = "SELECT * FROM Users WHERE mobile = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, mobile);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

  public List<User> getExpertsAndAdmins(int subjectId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT DISTINCT u.* FROM Users u "
                + "WHERE u.role_id = 1 "
                + "OR (u.role_id = 3 AND EXISTS (SELECT 1 FROM Subject s WHERE s.expert_id = u.user_id AND s.subject_id = ?))";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, subjectId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setEmail(rs.getString("email"));
                    u.setFullname(rs.getString("full_name"));
                    u.setRoleId(rs.getInt("role_id"));
                    users.add(u);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        List<User> list = dao.getAllExperts();

        for (User u : list) {
            System.out.println(u);
        }
    }

}
