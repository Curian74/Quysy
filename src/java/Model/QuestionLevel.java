package Model;

public class QuestionLevel {

    private int levelId;
    private String levelName;

    public QuestionLevel() {
    }

    public QuestionLevel(int levelId, String levelName) {
        this.levelId = levelId;
        this.levelName = levelName;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public String toString() {
        return "QuestionLevel{" + "levelId=" + levelId + ", levelName=" + levelName + '}';
    }

}
