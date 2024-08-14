package Model;

import java.util.Date;

public class User {

    private int userId;
    private String email;
    private String password;
    private String fullname;
    private boolean gender;
    private String mobile;
    private int roleId;
    private boolean status;
    private Date createdDate;
    private String avatar;
    private String resetToken;
    private String resetTokenExpiry;
    private String roleName;

    public User() {
    }

    public User(int userId, String email, String password, String fullname, boolean gender, String mobile, int roleId, boolean status, Date createdDate, String avatar, String resetToken, String resetTokenExpiry) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.mobile = mobile;
        this.roleId = roleId;
        this.status = status;
        this.createdDate = createdDate;
        this.avatar = avatar;
        this.resetToken = resetToken;
        this.resetTokenExpiry = resetTokenExpiry;
    }

    public User(int userId, String email, String password, String fullname, boolean gender, String mobile, int roleId, boolean status, Date createdDate, String avatar, String resetToken, String resetTokenExpiry, String roleName) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.mobile = mobile;
        this.roleId = roleId;
        this.status = status;
        this.createdDate = createdDate;
        this.avatar = avatar;
        this.resetToken = resetToken;
        this.resetTokenExpiry = resetTokenExpiry;
        this.roleName = roleName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getResetTokenExpiry() {
        return resetTokenExpiry;
    }

    public void setResetTokenExpiry(String resetTokenExpiry) {
        this.resetTokenExpiry = resetTokenExpiry;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", email=" + email
                + ", password=" + password + ", fullname=" + fullname
                + ", gender=" + gender + ", mobile=" + mobile
                + ", roleId=" + roleId + ", status=" + status
                + ", createdDate=" + createdDate + ", avatar=" + avatar
                + ", resetToken=" + resetToken + ", resetTokenExpiry=" + resetTokenExpiry + '}';
    }

}
