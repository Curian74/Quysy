/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author asus
 */
public class SettingType {
    private int settingTypeId;
    private String settingTypeName;

    public SettingType() {
    }

    public SettingType(int settingTypeId, String settingTypeName) {
        this.settingTypeId = settingTypeId;
        this.settingTypeName = settingTypeName;
    }

    public int getSettingTypeId() {
        return settingTypeId;
    }

    public void setSettingTypeId(int settingTypeId) {
        this.settingTypeId = settingTypeId;
    }

    public String getSettingTypeName() {
        return settingTypeName;
    }

    public void setSettingTypeName(String settingTypeName) {
        this.settingTypeName = settingTypeName;
    }

    @Override
    public String toString() {
        return "SettingType{" + "settingTypeId=" + settingTypeId + ", settingTypeName=" + settingTypeName + '}';
    }
    
    
}
