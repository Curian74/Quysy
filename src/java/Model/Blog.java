package Model;

import java.util.Date;

public class Blog {

    private int blogID;
    private String blogTitle;
    private String blogThumbnail;
    private int authorId;
    private Date blogCreatedDate;
    private Date blogUpdatedDate;
    private int categoryId;
    private String briefInfo;
    private int view;
    private int status;
    private int isFeatured;

    public Blog() {
    }

    public Blog(int blogID, String blogTitle, String blogThumbnail, int authorId, Date blogCreatedDate, Date blogUpdatedDate, int categoryId, String briefInfo, int view) {
        this.blogID = blogID;
        this.blogTitle = blogTitle;
        this.blogThumbnail = blogThumbnail;
        this.authorId = authorId;
        this.blogCreatedDate = blogCreatedDate;
        this.blogUpdatedDate = blogUpdatedDate;
        this.categoryId = categoryId;
        this.briefInfo = briefInfo;
        this.view = view;
    }

    public Blog(int blogID, String blogTitle, String blogThumbnail, int authorId, Date blogCreatedDate, Date blogUpdatedDate, int categoryId, String briefInfo, int view, int status, int isFeatured) {
        this.blogID = blogID;
        this.blogTitle = blogTitle;
        this.blogThumbnail = blogThumbnail;
        this.authorId = authorId;
        this.blogCreatedDate = blogCreatedDate;
        this.blogUpdatedDate = blogUpdatedDate;
        this.categoryId = categoryId;
        this.briefInfo = briefInfo;
        this.view = view;
        this.status = status;
        this.isFeatured = isFeatured;
    }

    // Getter và Setter
    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public String getBlogThumbnail() {
        return blogThumbnail;
    }

    public void setBlogThumbnail(String blogThumbnail) {
        this.blogThumbnail = blogThumbnail;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBriefInfo() {
        return briefInfo;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    public Date getBlogUpdatedDate() {
        return blogUpdatedDate;
    }

    public void setBlogUpdatedDate(Date blogUpdatedDate) {
        this.blogUpdatedDate = blogUpdatedDate;
    }

    public Date getBlogCreatedDate() {
        return blogCreatedDate;
    }

    public void setBlogCreatedDate(Date blogCreatedDate) {
        this.blogCreatedDate = blogCreatedDate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(int isFeatured) {
        this.isFeatured = isFeatured;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Blog{"
                + "blogID=" + blogID
                + ", blogTitle='" + blogTitle + '\''
                + ", blogThumbnail='" + blogThumbnail + '\''
                + ", authorId=" + authorId
                + ", blogCreatedDate=" + blogCreatedDate
                + ", blogUpdatedDate=" + blogUpdatedDate
                + ", categoryId=" + categoryId
                + ", briefInfo='" + briefInfo + '\''
                + ", view=" + view
                + ", status=" + status
                + ", isFeatured=" + isFeatured
                + '}';
    }

}
