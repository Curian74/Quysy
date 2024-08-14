package Model;

import java.util.Date;

public class Question {

    private int questionId;
    private String questionDetail;
    private String media;
    private Date createDate;
    private Date updateDate;
    private Boolean status;
    private int questionTypeId;
    private int dimensionId;
    private int levelId;
    private int lessonId;
    private int topicId;
    private int creatorId;
    private int lastUpdatedBy;
    private String subjectName;
    private String lessonName;
    private String dimensionName;
    private String levelName;

    public Question() {
    }

    public Question(int questionId, String questionDetail, String media, Date createDate, Date updateDate, Boolean status, int questionTypeId, int dimensionId, int levelId, int lessonId, int topicId, int creatorId, int lastUpdatedBy) {
        this.questionId = questionId;
        this.questionDetail = questionDetail;
        this.media = media;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.questionTypeId = questionTypeId;
        this.dimensionId = dimensionId;
        this.levelId = levelId;
        this.lessonId = lessonId;
        this.topicId = topicId;
        this.creatorId = creatorId;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionDetail() {
        return questionDetail;
    }

    public void setQuestionDetail(String questionDetail) {
        this.questionDetail = questionDetail;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(int dimensionId) {
        this.dimensionId = dimensionId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(int lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
    
    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public String toString() {
        return "Question{" + "questionId=" + questionId + ", questionDetail=" + questionDetail + ", media=" + media + ", createDate=" + createDate + ", updateDate=" + updateDate + ", status=" + status + ", questionTypeId=" + questionTypeId + ", dimensionId=" + dimensionId + ", levelId=" + levelId + ", lessonId=" + lessonId + ", topicId=" + topicId + ", creatorId=" + creatorId + ", lastUpdatedBy=" + lastUpdatedBy + ", subjectName=" + subjectName + ", lessonName=" + lessonName + ", dimensionName=" + dimensionName + ", levelName=" + levelName + '}';
    }

}
