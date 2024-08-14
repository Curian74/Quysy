package Model;

public class SubjectDimension {

    private int dimensionId;
    private String dimensionName;
    private String description;
    private String createDate;
    private String updateDate;
    private User updatedBy;

    public SubjectDimension() {
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(int dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "SubjectDimension{" + "dimensionId=" + dimensionId + ", dimensionName=" + dimensionName + ", description=" + description + ", createDate=" + createDate + ", updateDate=" + updateDate + ", updatedBy=" + updatedBy + '}';
    }

    
    
}
