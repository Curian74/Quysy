package Model;

import java.sql.Timestamp;

public class Lesson {

    private int lessonId;
    private String lessonName;
    private int lessonOrder;
    private String youtubeLink;
    private String htmlContent;
    private int lessonTypeId;
    private int subjectId;
    private int topicId;
    private int packageId;
    private boolean status;
    private String lessonTypeName;
    private String topicName;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private int updatedBy;
    private int quizId;
    private String updatedByName;
    private String quizName;

    public Lesson() {
    }

    public Lesson(int lessonId, String lessonName, int lessonOrder, String youtubeLink, String htmlContent, int lessonTypeId, int subjectId, int topicId, int packageId, boolean status, String lessonTypeName, String topicName, Timestamp createdDate, Timestamp updatedDate, int updatedBy, int quizId, String updatedByName, String quizName) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.lessonOrder = lessonOrder;
        this.youtubeLink = youtubeLink;
        this.htmlContent = htmlContent;
        this.lessonTypeId = lessonTypeId;
        this.subjectId = subjectId;
        this.topicId = topicId;
        this.packageId = packageId;
        this.status = status;
        this.lessonTypeName = lessonTypeName;
        this.topicName = topicName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
        this.quizId = quizId;
        this.updatedByName = updatedByName;
        this.quizName = quizName;
    }



    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public int getLessonOrder() {
        return lessonOrder;
    }

    public void setLessonOrder(int lessonOrder) {
        this.lessonOrder = lessonOrder;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public int getLessonTypeId() {
        return lessonTypeId;
    }

    public void setLessonTypeId(int lessonTypeId) {
        this.lessonTypeId = lessonTypeId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLessonTypeName() {
        return lessonTypeName;
    }

    public void setLessonTypeName(String lessonTypeName) {
        this.lessonTypeName = lessonTypeName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getUpdatedByName() {
        return updatedByName;
    }

    public void setUpdatedByName(String updatedByName) {
        this.updatedByName = updatedByName;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    @Override
    public String toString() {
        return "Lesson{" + "lessonId=" + lessonId + ", lessonName=" + lessonName + ", lessonOrder=" + lessonOrder + ", youtubeLink=" + youtubeLink + ", htmlContent=" + htmlContent + ", lessonTypeId=" + lessonTypeId + ", subjectId=" + subjectId + ", topicId=" + topicId + ", packageId=" + packageId + ", status=" + status + ", lessonTypeName=" + lessonTypeName + ", topicName=" + topicName + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy + ", quizId=" + quizId + ", updatedByName=" + updatedByName + ", quizName=" + quizName + '}';
    }

}
