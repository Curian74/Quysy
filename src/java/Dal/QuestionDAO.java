package Dal;

import Model.Question;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionDAO extends DBContext {

    public List<Question> getAllQuestions() {
        List<Question> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Question]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Question q = new Question(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getDate(5),
                        rs.getBoolean(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12),
                        rs.getInt(13)
                );

                q.setStatus(rs.getBoolean(10));
                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Question> getQuestionsByQuizRecordId(int quizRecordId) {
        List<Question> list = new ArrayList<>();
        String sql = """
                      SELECT qt.* FROM Quiz q 
                       JOIN QuizQuestion qq ON q.quiz_id = qq.quiz_id
                       JOIN Question qt ON qq.question_id = qt.question_id
                       JOIN QuizRecord qr ON qr.quiz_id = q.quiz_id  where qr.quiz_record_id = ?""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, quizRecordId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Question q = new Question(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getDate(5),
                        rs.getBoolean(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12),
                        rs.getInt(13)
                );
                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Question> getQuestionsByTopicId(int topicId, int limit) {
        String sql = "SELECT TOP (?) * FROM Question WHERE topic_id = ? ORDER BY NEWID()";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, limit);
            st.setInt(2, topicId);
            ResultSet rs = st.executeQuery();

            List<Question> questions = new ArrayList<>();
            while (rs.next()) {
                questions.add(mapResultSetToQuestion(rs));
            }
            return questions;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Question> getQuestionsByDimensionId(int dimensionId, int limit) {
        String sql = "SELECT TOP (?) * FROM Question WHERE dimension_id = ? ORDER BY NEWID()";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, limit);
            st.setInt(2, dimensionId);
            ResultSet rs = st.executeQuery();

            List<Question> questions = new ArrayList<>();
            while (rs.next()) {
                questions.add(mapResultSetToQuestion(rs));
            }
            return questions;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Question mapResultSetToQuestion(ResultSet rs) {
        try {
            return new Question(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDate(4),
                    rs.getDate(5),
                    rs.getBoolean(6),
                    rs.getInt(7),
                    rs.getInt(8),
                    rs.getInt(9),
                    rs.getInt(10),
                    rs.getInt(11),
                    rs.getInt(12),
                    rs.getInt(13)
            );
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Question> getQuestionsList(int subjectId, int lessonId, int dimensionId, int levelId, Boolean status) {
        List<Question> list = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("""
                                                  SELECT q.*, s.subject_name, l.lesson_name, d.dimension_name, lv.level_name 
                                                  FROM [Question] q 
                                                  JOIN [Lesson] l ON q.lesson_id = l.lesson_id 
                                                  JOIN [Subject] s ON l.subject_id = s.subject_id 
                                                  JOIN [SubjectDimension] d ON q.dimension_id = d.dimension_id 
                                                  JOIN [Level] lv ON q.level_id = lv.level_id 
                                                  WHERE 1=1""");

            List<Object> params = new ArrayList<>();

            if (subjectId != 0) {
                sql.append(" AND s.subject_id = ?");
                params.add(subjectId);
            }
            if (lessonId != 0) {
                sql.append(" AND q.lesson_id = ?");
                params.add(lessonId);
            }
            if (dimensionId != 0) {
                sql.append(" AND q.dimension_id = ?");
                params.add(dimensionId);
            }
            if (levelId != 0) {
                sql.append(" AND q.level_id = ?");
                params.add(levelId);
            }
            if (status != null) {
                sql.append(" AND q.status = ?");
                params.add(status);
            }

            PreparedStatement st = connection.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("question_id"));
                q.setQuestionDetail(rs.getString("question_detail"));
                q.setMedia(rs.getString("media"));
                q.setCreateDate(rs.getDate("create_date"));
                q.setUpdateDate(rs.getDate("update_date"));
                q.setQuestionTypeId(rs.getInt("question_type_id"));
                q.setDimensionId(rs.getInt("dimension_id"));
                q.setLevelId(rs.getInt("level_id"));
                q.setLessonId(rs.getInt("lesson_id"));
                q.setStatus(rs.getBoolean("status"));
                q.setSubjectName(rs.getString("subject_name"));
                q.setLessonName(rs.getString("lesson_name"));
                q.setDimensionName(rs.getString("dimension_name"));
                q.setLevelName(rs.getString("level_name"));
                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean updateQuestionStatus(int questionId, boolean status) {
        String sql = "UPDATE [Question] SET status = ? WHERE question_id = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setBoolean(1, status);
            st.setInt(2, questionId);

            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getNoQuestionsBySubjectId(int subjectId) {
        int n = 0;
        try {
            String sql = "SELECT COUNT(qs.question_id) AS question_count\n"
                    + "FROM Question qs \n"
                    + "JOIN Lesson ls ON\n"
                    + "qs.lesson_id=ls.lesson_id \n"
                    + "JOIN [Subject] s ON s.subject_id=ls.subject_id\n"
                    + "WHERE s.subject_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                n = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    public List<Integer> getQuestionIdsBySubjectAndLevel(int subjectId, int levelId) {
        List<Integer> questionIds = new ArrayList<>();
        String sql = "SELECT question_id FROM Question qs JOIN Lesson ls ON qs.lesson_id=ls.lesson_id WHERE ls.subject_id = ? AND qs.level_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            ps.setInt(2, levelId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                questionIds.add(rs.getInt("question_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questionIds;
    }

    public static void main(String[] args) {
        QuestionDAO dao = new QuestionDAO();
//        dao.updateQuestionStatus(1, false);
//        List<Question> list = dao.getQuestionsList(0, 0, 0, 0, null);
//        for (Question question : list) {
//            System.out.println(question);
//        }
        List<Integer> n = dao.getQuestionIdsBySubjectAndLevel(1, 1);
        for (Integer integer : n) {
            System.out.println(integer);
        }
    }

}
