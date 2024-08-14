package Model;

import java.util.Date;

public class Subject {

    private int subjectId;
    private String subjectName;
    private String thumbnail;
    private int categoryId;
    private boolean isFeatured;
    private boolean status;
    private String tagLine;
    private String briefInfo;
    private String description;
    private Date createdDate;
    private int expertId;

    public Subject() {
    }

    public Subject(int subjectId, String subjectName, String thumbnail, int categoryId, boolean isFeatured, boolean status, String tagLine, String briefInfo, String description, Date createdDate, int expertId) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.thumbnail = thumbnail;
        this.categoryId = categoryId;
        this.isFeatured = isFeatured;
        this.status = status;
        this.tagLine = tagLine;
        this.briefInfo = briefInfo;
        this.description = description;
        this.createdDate = createdDate;
        this.expertId = expertId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getBriefInfo() {
        return briefInfo;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getExpertId() {
        return expertId;
    }

    public void setExpertId(int expertId) {
        this.expertId = expertId;
    }

    @Override
    public String toString() {
        return "Subject{" + "subjectId=" + subjectId + ", subjectName=" + subjectName + ", thumbnail=" + thumbnail + ", categoryId=" + categoryId + ", isFeatured=" + isFeatured + ", status=" + status + ", tagLine=" + tagLine + ", briefInfo=" + briefInfo + ", description=" + description + ", createdDate=" + createdDate + ", expertId=" + expertId + '}';
    }

}
