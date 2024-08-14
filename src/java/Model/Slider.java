package Model;

public class Slider {

    private int sliderId;
    private String sliderThumbnail;
    private String title;
    private String backlink;
    private boolean status;
    private String notes;

    public Slider() {
    }

    public Slider(int sliderId, String sliderThumbnail, String title, String backlink, boolean status, String notes) {
        this.sliderId = sliderId;
        this.sliderThumbnail = sliderThumbnail;
        this.title = title;
        this.backlink = backlink;
        this.status = status;
        this.notes = notes;
    }

    public int getSliderId() {
        return sliderId;
    }

    public void setSliderId(int sliderId) {
        this.sliderId = sliderId;
    }

    public String getSliderThumbnail() {
        return sliderThumbnail;
    }

    public void setSliderThumbnail(String sliderThumbnail) {
        this.sliderThumbnail = sliderThumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBacklink() {
        return backlink;
    }

    public void setBacklink(String backlink) {
        this.backlink = backlink;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Slider{" + "sliderId=" + sliderId + ", sliderThumbnail=" + sliderThumbnail + ", title=" + title + ", backlink=" + backlink + ", status=" + status + ", notes=" + notes + '}';
    }

}
