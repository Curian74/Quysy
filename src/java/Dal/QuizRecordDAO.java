package Dal;

import Model.QuizRecord;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuizRecordDAO extends DBContext {

    public List<QuizRecord> getAllQuizRecord() {
        List<QuizRecord> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [QuizRecord]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                QuizRecord qr = new QuizRecord(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getInt(4), rs.getFloat(5), rs.getInt(6), rs.getInt(7));
                list.add(qr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizRecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public QuizRecord getQuizRecordById(int quizRecordId) {
        try {
            String sql = "SELECT * FROM [QuizRecord] WHERE [quiz_record_id] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, quizRecordId);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                QuizRecord qr = new QuizRecord(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getInt(4), rs.getFloat(5), rs.getInt(6), rs.getInt(7));
                return qr;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizRecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<QuizRecord> getPracticesList(User u, int subjectId) {
        List<QuizRecord> list = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder(
                    """
                    SELECT
                    \tqr.*,
                    \tq.quiz_name,
                    \tq.number_of_question,
                    \ts.subject_id,
                    \ts.subject_name,
                    \tqt.quiz_type_name 
                    FROM
                    \tQuizRecord qr
                    \tJOIN Quiz q ON qr.quiz_id = q.quiz_id
                    \tJOIN Subject s ON q.subject_id = s.subject_id
                    \tJOIN QuizType qt ON q.quiz_type_id = qt.quiz_type_id
                    WHERE qr.user_id = ?""");

            // Append filter for subjectId if provided
            if (subjectId > 0) {
                sql.append(" AND s.subject_id = ?");
            }

            PreparedStatement st = connection.prepareStatement(sql.toString());
            st.setInt(1, u.getUserId());

            if (subjectId > 0) {
                st.setInt(2, subjectId);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                QuizRecord qr = new QuizRecord(
                        rs.getInt(1),
                        rs.getDate(2),
                        rs.getDate(3),
                        rs.getInt(4),
                        rs.getFloat(5),
                        rs.getInt(6),
                        rs.getInt(7)
                );
                qr.setQuizName(rs.getString(8));
                qr.setNumberOfQuestion(rs.getInt(9));
                qr.setSubjectId(rs.getInt(10));
                qr.setSubjectName(rs.getString(11));
                qr.setQuizTypeName(rs.getString(12));
                list.add(qr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizRecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public QuizRecord getPracticeById(int practiceId) {
        try {
            String sql = """
                         SELECT
                         \tqr.*,
                         \tq.quiz_name,
                         \tq.number_of_question,
                         \ts.subject_id,
                         \ts.subject_name,
                         \tqt.quiz_type_name 
                         FROM
                         \tQuizRecord qr
                         \tJOIN Quiz q ON qr.quiz_id = q.quiz_id
                         \tJOIN Subject s ON q.subject_id = s.subject_id
                         \tJOIN QuizType qt ON q.quiz_type_id = qt.quiz_type_id
                         WHERE qr.quiz_record_id = ?""";

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, practiceId);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                QuizRecord qr = new QuizRecord(
                        rs.getInt(1),
                        rs.getDate(2),
                        rs.getDate(3),
                        rs.getInt(4),
                        rs.getFloat(5),
                        rs.getInt(6),
                        rs.getInt(7)
                );
                qr.setQuizName(rs.getString(8));
                qr.setNumberOfQuestion(rs.getInt(9));
                qr.setSubjectId(rs.getInt(10));
                qr.setSubjectName(rs.getString(11));
                qr.setQuizTypeName(rs.getString(12));
                return qr;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizRecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
