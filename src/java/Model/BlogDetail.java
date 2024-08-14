package Model;

public class BlogDetail {

    private int blogDetailId;
    private int blogId;
    private String content;

    public BlogDetail() {
    }

    public BlogDetail(int blogDetailId, int blogId, String content) {
        this.blogDetailId = blogDetailId;
        this.blogId = blogId;
        this.content = content;
    }

    public int getBlogDetailId() {
        return blogDetailId;
    }

    public void setBlogDetailId(int blogDetailId) {
        this.blogDetailId = blogDetailId;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BlogDetail{" + "blogDetailId=" + blogDetailId + ", blogId=" + blogId + ", content=" + content + '}';
    }
    

}   
