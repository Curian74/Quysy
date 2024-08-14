package Model;

public class Dimensions {

    private int id;
    private int dimensionId;
    private int dimensionTypeId;
    private int subjectId;
    private String dimensionName;
    private String dimensionType;

    public Dimensions() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(int dimensionId) {
        this.dimensionId = dimensionId;
    }

    public int getDimensionTypeId() {
        return dimensionTypeId;
    }

    public void setDimensionTypeId(int dimensionTypeId) {
        this.dimensionTypeId = dimensionTypeId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(String dimensionType) {
        this.dimensionType = dimensionType;
    }

    @Override
    public String toString() {
        return "Dimensions{" + "id=" + id + ", dimensionId=" + dimensionId + ", dimensionTypeId=" + dimensionTypeId + ", subjectId=" + subjectId + ", dimensionName=" + dimensionName + ", dimensionType=" + dimensionType + '}';
    }

}
