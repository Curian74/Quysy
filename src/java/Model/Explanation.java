package Model;

public class Explanation {

    private int explanationId;
    private String explanationDetail;
    private String source;
    private String page;
    private int questionId;

    public Explanation() {
    }
  
    public Explanation(int explanationId, String explanationDetail, String source, String page, int questionId) {
        this.explanationId = explanationId;
        this.explanationDetail = explanationDetail;
        this.source = source;
        this.page = page;
        this.questionId = questionId;
    }

    public int getExplanationId() {
        return explanationId;
    }

    public void setExplanationId(int explanationId) {
        this.explanationId = explanationId;
    }

    public String getExplanationDetail() {
        return explanationDetail;
    }

    public void setExplanationDetail(String explanationDetail) {
        this.explanationDetail = explanationDetail;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Explanation{" + "explanationId=" + explanationId + ", explanationDetail=" + explanationDetail + ", source=" + source + ", page=" + page + ", questionId=" + questionId + '}';
    }
    

}
