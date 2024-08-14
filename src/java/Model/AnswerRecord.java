package Model;

public class AnswerRecord {

    private int answerRecordId;
    private boolean isMarked;
    private String answerContent;
    private int selectedAnswerId;
    private int quizRecordId;
    private int questionId;

    public AnswerRecord() {
    }

    public AnswerRecord(int answerRecordId, boolean isMarked, String answerContent, int selectedAnswerId, int quizRecordId, int questionId) {
        this.answerRecordId = answerRecordId;
        this.isMarked = isMarked;
        this.answerContent = answerContent;
        this.selectedAnswerId = selectedAnswerId;
        this.quizRecordId = quizRecordId;
        this.questionId = questionId;
    }

    public int getAnswerRecordId() {
        return answerRecordId;
    }

    public void setAnswerRecordId(int answerRecordId) {
        this.answerRecordId = answerRecordId;
    }

    public boolean isIsMarked() {
        return isMarked;
    }

    public void setIsMarked(boolean isMarked) {
        this.isMarked = isMarked;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public int getSelectedAnswerId() {
        return selectedAnswerId;
    }

    public void setSelectedAnswerId(int selectedAnswerId) {
        this.selectedAnswerId = selectedAnswerId;
    }

    public int getQuizRecordId() {
        return quizRecordId;
    }

    public void setQuizRecordId(int quizRecordId) {
        this.quizRecordId = quizRecordId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "AnswerRecord{" + "answerRecordId=" + answerRecordId + ", isMarked=" + isMarked + ", answerContent=" + answerContent + ", selectedAnswerId=" + selectedAnswerId + ", quizRecordId=" + quizRecordId + ", questionId=" + questionId + '}';
    }

}
