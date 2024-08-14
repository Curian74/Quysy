package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Quiz {

    private int quizId;
    private String quizName;
    private int numberOfQuestion;
    private int duration;
    private float passRate;
    private int levelId;
    private int subjectId;
    private int quizTypeId;
    private String subjectName;
    private String levelName;
    private int creatorId;
    private boolean status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String typeName;
    private String creatorName;
    private int lastUpdateBy;
    private String updateName;

    // fields for formatted date and time
    private String formattedCreateDate;
    private String formattedCreateTime;
    private String formattedUpdateDate;
    private String formattedUpdateTime;


    public Quiz() {
    }

    public Quiz(int quizId, String quizName, int numberOfQuestion, int duration, float passRate, int levelId, int subjectId, int quizTypeId, String subjectName, String levelName) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.numberOfQuestion = numberOfQuestion;
        this.duration = duration;
        this.passRate = passRate;
        this.levelId = levelId;
        this.subjectId = subjectId;
        this.quizTypeId = quizTypeId;
        this.subjectName = subjectName;
        this.levelName = levelName;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getPassRate() {
        return passRate;
    }

    public void setPassRate(float passRate) {
        this.passRate = passRate;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getQuizTypeId() {
        return quizTypeId;
    }

    public void setQuizTypeId(int quizTypeId) {
        this.quizTypeId = quizTypeId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        setFormattedCreateDate(createDate);
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        setFormattedUpdateDate(updateDate);
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFormattedCreateDate() {
        return formattedCreateDate;
    }

    public String getFormattedCreateTime() {
        return formattedCreateTime;
    }

    public String getFormattedUpdateDate() {
        return formattedUpdateDate;
    }

    public String getFormattedUpdateTime() {
        return formattedUpdateTime;
    }

    private void setFormattedCreateDate(LocalDateTime createDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.formattedCreateDate = createDate.format(dateFormatter);
        this.formattedCreateTime = createDate.format(timeFormatter);
    }

    private void setFormattedUpdateDate(LocalDateTime updateDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.formattedUpdateDate = updateDate.format(dateFormatter);
        this.formattedUpdateTime = updateDate.format(timeFormatter);
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    
    @Override
    public String toString() {
        return "Quiz{" + "quizId=" + quizId + ", quizName=" + quizName + ", numberOfQuestion=" + numberOfQuestion + ", duration=" + duration + ", passRate=" + passRate + ", levelId=" + levelId + ", subjectId=" + subjectId + ", quizTypeId=" + quizTypeId + ", subjectName=" + subjectName + ", levelName=" + levelName + ", creatorId=" + creatorId + ", status=" + status + ", createDate=" + createDate + ", updateDate=" + updateDate + ", typeName=" + typeName + ", creatorName=" + creatorName + ", formattedCreateDate=" + formattedCreateDate + ", formattedCreateTime=" + formattedCreateTime + ", formattedUpdateDate=" + formattedUpdateDate + ", formattedUpdateTime=" + formattedUpdateTime + '}';
    }

}
