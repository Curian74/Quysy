package Dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Blog;
import Model.BlogDetail;

public class BlogDAO extends DBContext {

    // Get all blogs from the database
    public List<Blog> getAllBlogs() {
        List<Blog> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Blog]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blog b = new Blog(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5),
                        rs.getDate(6), rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getInt(10), rs.getInt(11));
                list.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // Get the blog by id
    public Blog getBlogByID(int id) {
        String sql = "SELECT * FROM [Blog] WHERE [blog_id] = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Blog b = new Blog(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11)
                );
                return b;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Get three latest blogs
    public List<Blog> getLatestUploadedBlogs() {
        List<Blog> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Blog] ORDER BY [created_date] DESC";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blog b = new Blog(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5),
                        rs.getDate(6), rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getInt(10), rs.getInt(11));
                list.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // Get blogs sorted by updated date
    public List<Blog> getLatestUpdatedBlogs() {
        List<Blog> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Blog] ORDER BY [update_date] DESC";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blog b = new Blog(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5),
                        rs.getDate(6), rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getInt(10), rs.getInt(11));
                list.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // Get top blogs
    public List<Blog> getTopBlogs() {
        List<Blog> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Blog] ORDER BY [view] DESC";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blog b = new Blog(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5),
                        rs.getDate(6), rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getInt(10), rs.getInt(11));
                list.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // Method to get blogs by filter and status
    public List<Blog> getBlogsByFilterAndStatus(String searchTerm, int categoryId, int status) {
        List<Blog> list = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM [Blog] WHERE [status] = ?");

            // Append filters for searchTerm and categoryId if provided
            if (searchTerm != null && !searchTerm.isEmpty()) {
                sql.append(" AND [title] LIKE ?");
            }
            if (categoryId > 0) {
                sql.append(" AND [category_id] = ?");
            }

            PreparedStatement st = connection.prepareStatement(sql.toString());

            int paramIndex = 1;
            st.setInt(paramIndex++, status);
            if (searchTerm != null && !searchTerm.isEmpty()) {
                st.setString(paramIndex++, "%" + searchTerm + "%");
            }
            if (categoryId > 0) {
                st.setInt(paramIndex++, categoryId);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blog b = new Blog(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11)
                );
                list.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Blog> getFeaturedBlogs() {
        List<Blog> list = new ArrayList<>();
        String sql = "SELECT * FROM [Blog] WHERE [is_featured] = 1 AND [status] = 1";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blog b = new Blog(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6), rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getInt(10), rs.getInt(11));
                list.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void updateBlog(Blog blog) {
        String sql = "UPDATE [Blog] SET [title] = ?, [thumbnail] = ?, [brief_info] = ?, "
                + "[category_id] = ?, [update_date] = ?, [status] = ?, [is_featured] = ? WHERE [blog_id] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, blog.getBlogTitle());
            st.setString(2, blog.getBlogThumbnail());
            st.setString(3, blog.getBriefInfo());
            st.setInt(4, blog.getCategoryId());
            st.setDate(5, new java.sql.Date(blog.getBlogUpdatedDate().getTime()));
            st.setInt(6, blog.getStatus());
            st.setInt(7, blog.getIsFeatured());
            st.setInt(8, blog.getBlogID());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteBlog(int blogID) {
        String sql = "DELETE FROM [Blog] WHERE [blog_id] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blogID);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateBlogDetail(BlogDetail blogDetail) {
        String sql = "UPDATE [BlogDetail] SET [content] = ? WHERE [blog_detail_id] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, blogDetail.getContent());
            st.setInt(2, blogDetail.getBlogDetailId());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<BlogDetail> getBlogDetailsByBlogId(int blogId) {
        List<BlogDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM [BlogDetail] WHERE [blog_id] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blogId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                BlogDetail bd = new BlogDetail(rs.getInt("blog_detail_id"), rs.getInt("blog_id"), rs.getString("content"));
                list.add(bd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void deleteBlogDetailsByBlogId(int blogId) {
        String sql = "DELETE FROM [BlogDetail] WHERE [blog_id] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blogId);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertBlogDetail(BlogDetail blogDetail) {
        String sql = "INSERT INTO [BlogDetail] ([blog_id], [content]) VALUES (?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blogDetail.getBlogId());
            st.setString(2, blogDetail.getContent());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteBlogDetail(int blogDetailId) {
        String sql = "DELETE FROM [BlogDetail] WHERE [blog_detail_id] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blogDetailId);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
