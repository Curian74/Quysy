package Model;

public class Settings {

    private int settingId;
    private String value;
    private int order;
    private boolean status;
    private int settingTypeId;
    private String description;
    private String typeName;

    public Settings() {
    }

    public int getSettingId() {
        return settingId;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getSettingTypeId() {
        return settingTypeId;
    }

    public void setSettingTypeId(int settingTypeId) {
        this.settingTypeId = settingTypeId;
    }

    @Override
    public String toString() {
        return "Settings{" + "settingId=" + settingId + ", value=" + value + ", order=" + order + ", status=" + status + ", settingTypeId=" + settingTypeId + ", description=" + description + ", typeName=" + typeName + '}';
    }

}
