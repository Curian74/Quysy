package Model;

public class Answer {

    private int answerId;
    private int questionId;
    private String answerDetail;
    private boolean isCorrect;
    private Explanation explanation;

    public Answer() {
    }

    public Answer(int answerId, int questionId, String answerDetail, boolean isCorrect) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.answerDetail = answerDetail;
        this.isCorrect = isCorrect;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswerDetail() {
        return answerDetail;
    }

    public void setAnswerDetail(String answerDetail) {
        this.answerDetail = answerDetail;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Explanation getExplanation() {
        return explanation;
    }

    public void setExplanation(Explanation explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "Answer{" + "answerId=" + answerId + ", questionId=" + questionId + ", answerDetail=" + answerDetail + ", isCorrect=" + isCorrect + ", explanation=" + explanation + '}';
    }

}
