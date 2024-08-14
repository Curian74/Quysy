package Dal;

import Model.Explanation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExplanationDAO extends DBContext {

    public List<Explanation> getAllExplanation() {
        List<Explanation> list = new ArrayList<>();
        String sql = "SELECT * FROM [Explanation]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Explanation exp = new Explanation();
                exp.setExplanationId(rs.getInt(1));
                exp.setExplanationDetail(rs.getString(2));
                exp.setSource(rs.getString(3));
                exp.setPage(rs.getString(4));
                exp.setQuestionId(rs.getInt(5));
                list.add(exp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExplanationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Explanation getExplanationByExpId(int expId) {
        String sql = "SELECT * FROM Explanation WHERE explanation_id=?";
        Explanation exp = new Explanation();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, expId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exp.setExplanationId(rs.getInt("explanation_id"));
                exp.setExplanationDetail(rs.getString("explanation_detail"));
                exp.setSource(rs.getString("source"));
                exp.setPage(rs.getString("page"));
                exp.setQuestionId(rs.getInt("question_id"));
            }
            return exp;
        } catch (SQLException ex) {
            Logger.getLogger(ExplanationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Explanation getExplanationByQuestionId(int questionId) {
        Explanation exp = new Explanation();
        String sql = "SELECT e.* "
                + "FROM Explanation e "
                + "JOIN Question q ON e.question_id = q.question_id "
                + "JOIN SubjectDimension d ON q.dimension_id = d.dimension_id "
                + "WHERE q.question_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exp.setExplanationId(rs.getInt("explanation_id"));
                exp.setExplanationDetail(rs.getString("explanation_detail"));
                exp.setSource(rs.getString("source"));
                exp.setPage(rs.getString("page"));
                exp.setQuestionId(rs.getInt("question_id"));
            }
            return exp;
        } catch (SQLException ex) {
            Logger.getLogger(ExplanationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
