package Model;

public class RegistrationStatus {
    private int registrationStatusId;
    private String registrationStatusName;

    public RegistrationStatus() {
    }

    public RegistrationStatus(int registrationStatusId, String registrationStatusName) {
        this.registrationStatusId = registrationStatusId;
        this.registrationStatusName = registrationStatusName;
    }

    public int getRegistrationStatusId() {
        return registrationStatusId;
    }

    public void setRegistrationStatusId(int registrationStatusId) {
        this.registrationStatusId = registrationStatusId;
    }

    public String getRegistrationStatusName() {
        return registrationStatusName;
    }

    public void setRegistrationStatusName(String registrationStatusName) {
        this.registrationStatusName = registrationStatusName;
    }

    @Override
    public String toString() {
        return "RegistrationStatus{" + "registrationStatusId=" + registrationStatusId + ", registrationStatusName=" + registrationStatusName + '}';
    }

}
