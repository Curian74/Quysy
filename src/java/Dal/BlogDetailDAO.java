package Dal;

import Model.BlogDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlogDetailDAO extends DBContext {
    
    // Get all blog content from the database
    public List<BlogDetail> getAllBlogDetails() {
        List<BlogDetail> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [BlogDetail]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                BlogDetail bd = new BlogDetail(rs.getInt(1), rs.getInt(2), rs.getString(3));
                list.add(bd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    // Get blog details by blog id
    public List<BlogDetail> getBlogDetailsByBlogId(int blogId) {
        List<BlogDetail> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [BlogDetail] WHERE [blog_id] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blogId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                BlogDetail bd = new BlogDetail(rs.getInt(1), rs.getInt(2), rs.getString(3));
                list.add(bd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
