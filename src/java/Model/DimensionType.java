package Model;

public class DimensionType {

    private int dimensionTypeId;
    private String dimensionTypeName;
    private String description;
    private String createDate;
    private String updateDate;
    private User updatedBy;

    public DimensionType() {
    }

    public int getDimensionTypeId() {
        return dimensionTypeId;
    }
 
    public void setDimensionTypeId(int dimensionTypeId) {
        this.dimensionTypeId = dimensionTypeId;
    }

    public String getDimensionTypeName() {
        return dimensionTypeName;
    }

    public void setDimensionTypeName(String dimensionTypeName) {
        this.dimensionTypeName = dimensionTypeName;
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
        return "DimensionType{" + "dimensionTypeId=" + dimensionTypeId + ", dimensionTypeName=" + dimensionTypeName + ", description=" + description + ", createDate=" + createDate + ", updateDate=" + updateDate + ", updatedBy=" + updatedBy + '}';
    }

}
