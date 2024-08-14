package Model;

import java.util.Date;

public class QuizRecord {

    private int quizRecordId;
    private Date createDate;
    private Date finishDate;
    private int timeSpent;
    private float score;
    private int userId;
    private int quizId;
    private String quizName;
    private int numberOfQuestion;
    private int subjectId;
    private String subjectName;
    private String quizTypeName;

    public QuizRecord() {
    }

    public QuizRecord(int quizRecordId, Date createDate, Date finishDate, int timeSpent, float score, int userId, int quizId) {
        this.quizRecordId = quizRecordId;
        this.createDate = createDate;
        this.finishDate = finishDate;
        this.timeSpent = timeSpent;
        this.score = score;
        this.userId = userId;
        this.quizId = quizId;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getQuizRecordId() {
        return quizRecordId;
    }

    public void setQuizRecordId(int quizRecordId) {
        this.quizRecordId = quizRecordId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getQuizTypeName() {
        return quizTypeName;
    }

    public void setQuizTypeName(String quizTypeName) {
        this.quizTypeName = quizTypeName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "QuizRecord{" + "quizRecordId=" + quizRecordId + ", createDate=" + createDate + ", finishDate=" + finishDate + ", timeSpent=" + timeSpent + ", score=" + score + ", userId=" + userId + ", quizId=" + quizId + ", quizName=" + quizName + ", numberOfQuestion=" + numberOfQuestion + ", subjectName=" + subjectName + ", quizTypeName=" + quizTypeName + '}';
    }

}
