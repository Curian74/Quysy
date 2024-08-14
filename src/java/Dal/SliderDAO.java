package Dal;

import Model.Slider;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SliderDAO extends DBContext {

    // Get all sliders from the database
    public List<Slider> getAllSliders() {
        List<Slider> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Slider]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Slider s = new Slider(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getString(6));
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // Get all active sliders
    public List<Slider> getAllActiveSliders() {
        List<Slider> sliders = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Slider] WHERE [status] = 1";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Slider s = new Slider(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getString(6));
                sliders.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sliders;
    }

    public Slider getSliderByID(int id) {
        String sql = "SELECT * FROM [Slider] WHERE [slider_id] = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Slider s = new Slider(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getString(6));
                return s;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean updateSlider(Slider slide) {
        String sql = "UPDATE [Slider] SET [slider_image] = ?, [title] = ?, [backlink] = ?, [status] = ?, [notes] = ? WHERE [slider_id] = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, slide.getSliderThumbnail());
            st.setString(2, slide.getTitle());
            st.setString(3, slide.getBacklink());
            st.setBoolean(4, slide.isStatus());
            st.setString(5, slide.getNotes());
            st.setInt(6, slide.getSliderId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Slider> searchSliders(String title, String link, Boolean status) {
        List<Slider> sliders = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Slider WHERE 1=1");
        
        if (title != null && !title.trim().isEmpty()) {
            sql.append(" AND title LIKE ?");
        }
        if (link != null && !link.trim().isEmpty()) {
            sql.append(" AND backlink LIKE ?");
        }
        if (status != null) {
            sql.append(" AND status = ?");
        }

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (title != null && !title.trim().isEmpty()) {
                st.setString(paramIndex++, "%" + title + "%");
            }
            if (link != null && !link.trim().isEmpty()) {
                st.setString(paramIndex++, "%" + link + "%");
            }
            if (status != null) {
                st.setBoolean(paramIndex++, status);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Slider s = new Slider(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getString(6));
                sliders.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sliders;
    }

    public boolean insertSlider(Slider slide) {
        String sql = "INSERT INTO [Slider] ([slider_image], [title], [backlink], [status], [notes]) VALUES (?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement st = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                st.setString(1, slide.getSliderThumbnail());
                st.setString(2, slide.getTitle());
                st.setString(3, slide.getBacklink());
                st.setBoolean(4, slide.isStatus());
                st.setString(5, slide.getNotes());
                int affectedRows = st.executeUpdate();
                
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            slide.setSliderId(generatedKeys.getInt(1));
                        }
                    }
                }
                
                connection.commit();
                return affectedRows > 0;
            }
        } catch (SQLException ex) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, "Error during rollback", rollbackEx);
            }
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, "Error inserting slider", ex);
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException autoCommitEx) {
                Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, "Error setting auto-commit", autoCommitEx);
            }
        }
    }

    public boolean updateSliderStatus(int sliderId, boolean newStatus) {
        String sql = "UPDATE slider SET status = ? WHERE slider_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, newStatus);
            stmt.setInt(2, sliderId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteSlider(int id) {
        String sql = "DELETE FROM [Slider] WHERE [slider_id] = ?";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setInt(1, id);
                int affectedRows = st.executeUpdate();
                connection.commit();
                return affectedRows > 0;
            }
        } catch (SQLException ex) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, "Error during rollback", rollbackEx);
            }
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, "Error deleting slider", ex);
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException autoCommitEx) {
                Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, "Error setting auto-commit", autoCommitEx);
            }
        }
    }

    public int getMaxSliderId() {
        String sql = "SELECT MAX(slider_id) FROM [Slider]";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}