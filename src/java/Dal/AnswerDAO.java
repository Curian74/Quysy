package Dal;

import Model.Answer;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnswerDAO extends DBContext {

    public List<Answer> getAllAnswers() {
        List<Answer> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Answer]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Answer a = new Answer();
                a.setAnswerId(rs.getInt(1));
                a.setQuestionId(rs.getInt(2));
                a.setAnswerDetail(rs.getString(3));
                a.setIsCorrect(rs.getBoolean(4));
                list.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Answer> getAnswersByQuestionId(int questionId) {
        List<Answer> answers = new ArrayList<>();
        String sql = "SELECT * FROM Answer WHERE question_id = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Answer answer = new Answer();
                    answer.setAnswerId(rs.getInt("answer_id"));
                    answer.setQuestionId(rs.getInt("question_id"));
                    answer.setAnswerDetail(rs.getString("answer_detail"));
                    answer.setIsCorrect(rs.getBoolean("is_correct"));
                    answers.add(answer);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answers;
    }

    public Answer getCorrectAnswersByQuestionId(int questionId) {
        String sql = "SELECT * FROM Answer WHERE question_id = ? AND is_correct=1";
        Answer answer = new Answer();
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    answer.setAnswerId(rs.getInt("answer_id"));
                    answer.setQuestionId(rs.getInt("question_id"));
                    answer.setAnswerDetail(rs.getString("answer_detail"));
                    answer.setIsCorrect(rs.getBoolean("is_correct"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answer;
    }

}
