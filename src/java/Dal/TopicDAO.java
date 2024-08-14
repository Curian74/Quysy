package Dal;

import Model.Topic;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TopicDAO extends DBContext {

    public List<Topic> getTopicsBySubjectId(int subjectId) {
        List<Topic> list = new ArrayList<>();
        try {
            String sql = "SELECT topic_id, topic_name, description FROM SubjectTopic WHERE subject_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, subjectId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Topic t = new Topic();
                t.setTopicId(rs.getInt("topic_id"));
                t.setTopicName(rs.getString("topic_name"));
                t.setDescription(rs.getString("description"));
                list.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
