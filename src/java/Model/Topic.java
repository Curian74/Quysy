package Model;

public class Topic {

    private int topicId;
    private String topicName;
    private String description;
    private int subjectId;

    public Topic() {
    }

    public Topic(int topicId, String topicName, String description, int subjectId) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.description = description;
        this.subjectId = subjectId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Topic{" + "topicId=" + topicId + ", topicName=" + topicName + ", description=" + description + ", subjectId=" + subjectId + '}';
    }

}
