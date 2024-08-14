package Model;

import java.util.Date;

public class Registration {

    private int registrationId;
    private int userId;
    private int subjectId;
    private int packageId;
    private Date registrationDate;
    private Date validFrom;
    private Date validTo;
    private double totalCost;
    private String notes;
    private int lastUpdatedBy;
    private String fullName;
    private String email;
    private String mobile;
    private boolean gender;
    private Date updatedDate;
    private int registrationStatus;
    private String subjectName;
    private String packageName;
    private String status;

    public Registration() {
    }

    public Registration(int registrationId, int userId, int subjectId, int packageId, Date registrationDate, Date validFrom, Date validTo, double totalCost, String notes, int lastUpdatedBy, String fullName, String email, String mobile, boolean gender, Date updatedDate, int registrationStatus) {
        this.registrationId = registrationId;
        this.userId = userId;
        this.subjectId = subjectId;
        this.packageId = packageId;
        this.registrationDate = registrationDate;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.totalCost = totalCost;
        this.notes = notes;
        this.lastUpdatedBy = lastUpdatedBy;
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.updatedDate = updatedDate;
        this.registrationStatus = registrationStatus;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(int lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(int registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Registration{" + "registrationId=" + registrationId + ", userId=" + userId + ", subjectId=" + subjectId + ", packageId=" + packageId + ", registrationDate=" + registrationDate + ", validFrom=" + validFrom + ", validTo=" + validTo + ", totalCost=" + totalCost + ", notes=" + notes + ", lastUpdatedBy=" + lastUpdatedBy + ", fullName=" + fullName + ", email=" + email + ", mobile=" + mobile + ", gender=" + gender + ", updatedDate=" + updatedDate + ", registrationStatus=" + registrationStatus + ", subjectName=" + subjectName + ", packageName=" + packageName + ", status=" + status + '}';
    }

}
