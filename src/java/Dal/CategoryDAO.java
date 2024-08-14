package Dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Category;

public class CategoryDAO extends DBContext {
    
    //Get all category from the database
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Category]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(1), rs.getString(2));
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    //Get the category by id
    public Category getCategoryByID(int id) {
        String sql = "SELECT * FROM [Category] WHERE [category_id] = ?";
        
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Category c = new Category(rs.getInt(1), rs.getString(2));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }   
    
}
