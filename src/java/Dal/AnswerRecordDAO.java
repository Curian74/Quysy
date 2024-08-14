package Dal;

import Model.AnswerRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnswerRecordDAO extends DBContext {

    public List<AnswerRecord> getAllAnswerRecords() {
        List<AnswerRecord> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [AnswerRecord]";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                AnswerRecord a = new AnswerRecord();
                a.setAnswerRecordId(rs.getInt("answer_record_id"));
                a.setIsMarked(rs.getBoolean("is_marked"));
                a.setAnswerContent(rs.getString("answer_content"));
                a.setSelectedAnswerId(rs.getInt("selected_answer_id"));
                a.setQuizRecordId(rs.getInt("quiz_record_id"));
                a.setQuestionId(rs.getInt("question_id"));
                list.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerRecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<AnswerRecord> getCorrectAnswerRecords() {
        List<AnswerRecord> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [AnswerRecord] ar JOIN [Answer] a "
                    + "ON ar.selected_answer_id = a.answer_id "
                    + "WHERE a.is_correct=1";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                AnswerRecord a = new AnswerRecord();
                a.setAnswerRecordId(rs.getInt("answer_record_id"));
                a.setIsMarked(rs.getBoolean("is_marked"));
                a.setAnswerContent(rs.getString("answer_content"));
                a.setSelectedAnswerId(rs.getInt("selected_answer_id"));
                a.setQuizRecordId(rs.getInt("quiz_record_id"));
                a.setQuestionId(rs.getInt("question_id"));
                list.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerRecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public AnswerRecord getAnswerRecordByQuestionIdAndQuizRecordId(int questionId, int quizRecordId) {
        AnswerRecord ar = new AnswerRecord(); // Initialize as null and instantiate only if a record is found
        String sql = "SELECT * FROM AnswerRecord WHERE quiz_record_id = ? AND question_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizRecordId);
            ps.setInt(2, questionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ar = new AnswerRecord();
                    ar.setAnswerRecordId(rs.getInt("answer_record_id")); // Use column names for clarity
                    ar.setIsMarked(rs.getBoolean("is_marked"));
                    ar.setAnswerContent(rs.getString("answer_content"));
                    ar.setSelectedAnswerId(rs.getInt("selected_answer_id"));
                    ar.setQuizRecordId(rs.getInt("quiz_record_id"));
                    ar.setQuestionId(rs.getInt("question_id"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerRecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ar;
    }

    public List<AnswerRecord> getMarkedAnswerRecords(int quizRecordId) {
        List<AnswerRecord> markedRecords = new ArrayList<>();
        String sql = "SELECT * FROM [AnswerRecord] WHERE quiz_record_id = ? AND is_marked=1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizRecordId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AnswerRecord answerRecord = new AnswerRecord();
                    answerRecord.setAnswerRecordId(rs.getInt("answer_record_id"));
                    answerRecord.setIsMarked(rs.getBoolean("is_marked"));
                    answerRecord.setAnswerContent(rs.getString("answer_content"));
                    answerRecord.setSelectedAnswerId(rs.getInt("selected_answer_id"));
                    answerRecord.setQuizRecordId(rs.getInt("quiz_record_id"));
                    answerRecord.setQuestionId(rs.getInt("question_id"));
                    markedRecords.add(answerRecord);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerRecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return markedRecords;
    }

    public List<AnswerRecord> getAnsweredAnswerRecords(int quizRecordId) {
        List<AnswerRecord> answeredRecords = new ArrayList<>();
        String sql = "SELECT * FROM AnswerRecord WHERE quiz_record_id = ? AND (selected_answer_id IS NOT NULL OR answer_content IS NOT NULL)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizRecordId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AnswerRecord answerRecord = new AnswerRecord();
                    answerRecord.setAnswerRecordId(rs.getInt("answer_record_id"));
                    answerRecord.setIsMarked(rs.getBoolean("is_marked"));
                    answerRecord.setAnswerContent(rs.getString("answer_content"));
                    answerRecord.setSelectedAnswerId(rs.getInt("selected_answer_id"));
                    answerRecord.setQuizRecordId(rs.getInt("quiz_record_id"));
                    answerRecord.setQuestionId(rs.getInt("question_id"));
                    answeredRecords.add(answerRecord);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerRecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answeredRecords;
    }

    public List<AnswerRecord> getUnansweredAnswerRecords(int quizRecordId) {
        List<AnswerRecord> unansweredRecords = new ArrayList<>();
        String sql = "SELECT * FROM AnswerRecord WHERE quiz_record_id = ? AND selected_answer_id IS NULL AND answer_content IS NULL";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizRecordId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AnswerRecord answerRecord = new AnswerRecord();
                    answerRecord.setAnswerRecordId(rs.getInt("answer_record_id"));
                    answerRecord.setIsMarked(rs.getBoolean("is_marked"));
                    answerRecord.setAnswerContent(rs.getString("answer_content"));
                    answerRecord.setSelectedAnswerId(rs.getInt("selected_answer_id"));
                    answerRecord.setQuizRecordId(rs.getInt("quiz_record_id"));
                    answerRecord.setQuestionId(rs.getInt("question_id"));
                    unansweredRecords.add(answerRecord);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerRecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return unansweredRecords;
    }

    public List<AnswerRecord> getAnswerRecordByQuizRecordId(int quizRecordId) {
        List<AnswerRecord> answerRecords = new ArrayList<>();
        String sql = "SELECT * FROM AnswerRecord WHERE quiz_record_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizRecordId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AnswerRecord answerRecord = new AnswerRecord();
                    answerRecord.setAnswerRecordId(rs.getInt("answer_record_id"));
                    answerRecord.setIsMarked(rs.getBoolean("is_marked"));
                    answerRecord.setAnswerContent(rs.getString("answer_content"));
                    answerRecord.setSelectedAnswerId(rs.getInt("selected_answer_id"));
                    answerRecord.setQuizRecordId(rs.getInt("quiz_record_id"));
                    answerRecord.setQuestionId(rs.getInt("question_id"));
                    answerRecords.add(answerRecord);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerRecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answerRecords;
    }

}
