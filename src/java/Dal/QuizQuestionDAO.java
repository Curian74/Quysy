package Dal;

import Model.QuizQuestion;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuizQuestionDAO extends DBContext {

    public void addQuestionToQuiz(QuizQuestion quizQuestion) {
        String sql = "INSERT INTO QuizQuestion (quiz_id, question_id) VALUES (?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, quizQuestion.getQuizId());
            st.setInt(2, quizQuestion.getQuestionId());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizQuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
