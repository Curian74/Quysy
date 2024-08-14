package Dal;

import Model.Quiz;
import Model.User;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuizDAO extends DBContext {

    private int noOfRecords;

    public List<Quiz> getAllQuizzes() {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Quiz]";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                q.setQuizId(rs.getInt(1));
                q.setQuizName(rs.getString(2));
                q.setNumberOfQuestion(rs.getInt(3));
                q.setDuration(rs.getInt(4));
                q.setPassRate(rs.getFloat(5));
                q.setLevelId(rs.getInt(6));
                q.setSubjectId(rs.getInt(7));
                q.setQuizTypeId(rs.getInt(8));
                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Quiz getQuizById(int quizId) {
        String sql = """
                     SELECT q.*, s.subject_name, qt.quiz_type_name, lv.level_name, u.full_name, u2.full_name FROM 
                       Quiz q JOIN [Subject] s ON q.subject_id = s.subject_id
                       JOIN QuizType qt ON q.quiz_type_id = qt.quiz_type_id
                       JOIN [Level] lv ON q.level_id = lv.level_id
                       JOIN [Users] u ON q.creator_id = u.user_id
                       JOIN [Users] u2 ON q.last_updated_by=u2.user_id
                       WHERE q.quiz_id=?""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, quizId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Quiz q = new Quiz();
                q.setQuizId(rs.getInt(1));
                q.setQuizName(rs.getString(2));
                q.setNumberOfQuestion(rs.getInt(3));
                q.setDuration(rs.getInt(4));
                q.setPassRate(rs.getFloat(5));
                q.setLevelId(rs.getInt(6));
                q.setSubjectId(rs.getInt(7));
                q.setQuizTypeId(rs.getInt(8));
                q.setStatus(rs.getBoolean(9));
                q.setCreatorId(rs.getInt(10));
                q.setLastUpdateBy(rs.getInt(11));
                // Set createDate and updateDate and format them
                LocalDateTime createDate = rs.getTimestamp(12).toLocalDateTime().withNano(0);
                LocalDateTime updateDate = rs.getTimestamp(13).toLocalDateTime().withNano(0);
                q.setCreateDate(createDate);
                q.setUpdateDate(updateDate);

                q.setSubjectName(rs.getString(14));
                q.setTypeName(rs.getString(15));
                q.setLevelName(rs.getString(16));
                q.setCreatorName(rs.getString(17));
                q.setUpdateName(rs.getString(18));
                return q;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Quiz> getQuizzesByUser(User u) {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT q.* FROM Quiz q join Quiz_Record qr on q.[quiz_id] = qr.[quiz_id] join Users u on u.[user_id] = qr.[user_id] where u.[user_id] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, u.getUserId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();

                q.setQuizId(rs.getInt(1));
                q.setQuizName(rs.getString(2));
                q.setNumberOfQuestion(rs.getInt(3));
                q.setDuration(rs.getInt(4));
                q.setPassRate(rs.getFloat(5));
                q.setLevelId(rs.getInt(6));
                q.setSubjectId(rs.getInt(7));
                q.setQuizTypeId(rs.getInt(8));

                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Quiz> getQuizzesByUserAndTypeAndSubject(User user, int quizTypeId, int offset, int noOfRecords) {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = """
                         WITH QuizDetails AS (
                         SELECT DISTINCT q.*, s.subject_name, l.level_name
                         FROM Quiz q
                         JOIN 
                         Registration r ON q.subject_id = r.subject_id
                         JOIN 
                         Users u ON u.user_id = r.user_id
                         JOIN   
                         QuizType qt ON q.quiz_type_id = qt.quiz_type_id  
                         JOIN   
                         Subject s ON s.subject_id = q.subject_id  
                         JOIN   
                         Level l ON l.level_id = q.level_id  
                         WHERE u.user_id = ? AND qt.quiz_type_id = ?  
                         )  
                         SELECT qd.*, COUNT(*) OVER () AS total_records  
                         FROM QuizDetails qd  
                         ORDER BY qd.quiz_id   
                         OFFSET ? ROWS FETCH NEXT ? ROWS ONLY""";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user.getUserId());
            st.setInt(2, quizTypeId);
            st.setInt(3, offset);
            st.setInt(4, noOfRecords);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                q.setQuizId(rs.getInt("quiz_id"));
                q.setQuizName(rs.getString("quiz_name"));
                q.setNumberOfQuestion(rs.getInt("number_of_question"));
                q.setDuration(rs.getInt("duration"));
                q.setPassRate(rs.getFloat("pass_rate"));
                q.setLevelId(rs.getInt("level_id"));
                q.setSubjectId(rs.getInt("subject_id"));
                q.setQuizTypeId(rs.getInt("quiz_type_id"));
                q.setSubjectName(rs.getString("subject_name"));
                q.setLevelName(rs.getString("level_name"));
                list.add(q);
                this.noOfRecords = rs.getInt("total_records");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    public List<Quiz> searchExams(String subject, String examName, int u, int offset, int noOfRecords) {
        List<Quiz> exams = new ArrayList<>();
        try {
            String query = """
                           SELECT DISTINCT q.quiz_id, q.quiz_name, s.subject_name, l.level_name, q.number_of_question, q.duration, q.pass_rate, q.subject_id, q.level_id, 
                           COUNT(*) OVER() AS total_records 
                           FROM Quiz q 
                           JOIN Registration r ON q.subject_id = r.subject_id 
                           JOIN Users u ON u.user_id = r.user_id 
                           JOIN Level l ON q.level_id = l.level_id 
                           JOIN Subject s ON q.subject_id = s.subject_id 
                           WHERE q.quiz_type_id = '1' 
                           AND s.subject_name LIKE ? AND q.quiz_name LIKE ?
                           AND u.user_id = ? 
                           ORDER BY q.quiz_id 
                           OFFSET ? ROWS FETCH NEXT ? ROWS ONLY""";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + subject + "%");
            preparedStatement.setString(2, "%" + examName + "%");
            preparedStatement.setInt(3, u);
            preparedStatement.setInt(4, offset);
            preparedStatement.setInt(5, noOfRecords);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Quiz exam = new Quiz();
                exam.setQuizId(resultSet.getInt("quiz_id"));
                exam.setQuizName(resultSet.getString("quiz_name"));
                exam.setSubjectName(resultSet.getString("subject_name"));
                exam.setLevelName(resultSet.getString("level_name"));
                exam.setNumberOfQuestion(resultSet.getInt("number_of_question"));
                exam.setDuration(resultSet.getInt("duration"));
                exam.setPassRate(resultSet.getFloat("pass_rate"));
                exams.add(exam);

                // Get all records from column "total_records"
                this.noOfRecords = resultSet.getInt("total_records");
            }
            resultSet.close();

        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exams;
    }

    public Quiz getExamById(int examId) {
        Quiz exam = null;
        try {
            String query = "SELECT q.quiz_id, q.quiz_name, s.subject_name, l.level_name, q.number_of_question, q.duration, q.pass_rate, q.subject_id, q.level_id "
                    + "FROM Quiz q "
                    + "JOIN Level l ON q.level_id = l.level_id "
                    + "JOIN Subject s ON q.subject_id = s.subject_id "
                    + "JOIN QuizType qt ON q.quiz_type_id = qt.quiz_type_id "
                    + "WHERE qt.quiz_type_name = 'Simulation Exam' "
                    + "AND q.quiz_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, examId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exam = new Quiz();
                exam.setQuizId(resultSet.getInt("quiz_id"));
                exam.setQuizName(resultSet.getString("quiz_name"));
                exam.setSubjectName(resultSet.getString("subject_name"));
                exam.setLevelName(resultSet.getString("level_name"));
                exam.setNumberOfQuestion(resultSet.getInt("number_of_question"));
                exam.setDuration(resultSet.getInt("duration"));
                exam.setPassRate(resultSet.getFloat("pass_rate"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exam;
    }

    public int createQuiz(Quiz quiz) {
        String sql = "INSERT INTO Quiz (quiz_name, number_of_question, duration, pass_rate, level_id, subject_id, quiz_type_id, creator_id, last_updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, quiz.getQuizName());
            st.setInt(2, quiz.getNumberOfQuestion());
            st.setInt(3, quiz.getDuration());
            st.setFloat(4, quiz.getPassRate());
            st.setInt(5, quiz.getLevelId());
            st.setInt(6, quiz.getSubjectId());
            st.setInt(7, quiz.getQuizTypeId());
            st.setInt(8, quiz.getCreatorId());
            st.setInt(9, quiz.getLastUpdateBy());
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public List<Quiz> getAvailableQuizzes() {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = """
                         SELECT q.*, s.subject_name, qt.quiz_type_name, lv.level_name, u.full_name, u2.full_name FROM 
                           Quiz q JOIN [Subject] s ON q.subject_id = s.subject_id
                           JOIN QuizType qt ON q.quiz_type_id = qt.quiz_type_id
                           JOIN [Level] lv ON q.level_id = lv.level_id
                           JOIN [Users] u ON q.creator_id = u.user_id
                           JOIN [Users] u2 ON q.last_updated_by=u2.user_id
                           WHERE q.[status]=1""";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                q.setQuizId(rs.getInt(1));
                q.setQuizName(rs.getString(2));
                q.setNumberOfQuestion(rs.getInt(3));
                q.setDuration(rs.getInt(4));
                q.setPassRate(rs.getFloat(5));
                q.setLevelId(rs.getInt(6));
                q.setSubjectId(rs.getInt(7));
                q.setQuizTypeId(rs.getInt(8));
                q.setStatus(rs.getBoolean(9));
                q.setCreatorId(rs.getInt(10));
                q.setLastUpdateBy(rs.getInt(11));
                // Set createDate and updateDate and format them
                LocalDateTime createDate = rs.getTimestamp(12).toLocalDateTime().withNano(0);
                LocalDateTime updateDate = rs.getTimestamp(13).toLocalDateTime().withNano(0);
                q.setCreateDate(createDate);
                q.setUpdateDate(updateDate);

                q.setSubjectName(rs.getString(14));
                q.setTypeName(rs.getString(15));
                q.setLevelName(rs.getString(16));
                q.setCreatorName(rs.getString(17));
                q.setUpdateName(rs.getString(18));
                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Quiz> getDeletedQuizzes() {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = """
                           SELECT q.*, s.subject_name, qt.quiz_type_name, lv.level_name, u.full_name, u2.full_name FROM 
                           Quiz q JOIN [Subject] s ON q.subject_id = s.subject_id
                           JOIN QuizType qt ON q.quiz_type_id = qt.quiz_type_id
                           JOIN [Level] lv ON q.level_id = lv.level_id
                           JOIN [Users] u ON q.creator_id = u.user_id
                           JOIN [Users] u2 ON q.last_updated_by=u2.user_id
                           WHERE q.[status]=0""";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                q.setQuizId(rs.getInt(1));
                q.setQuizName(rs.getString(2));
                q.setNumberOfQuestion(rs.getInt(3));
                q.setDuration(rs.getInt(4));
                q.setPassRate(rs.getFloat(5));
                q.setLevelId(rs.getInt(6));
                q.setSubjectId(rs.getInt(7));
                q.setQuizTypeId(rs.getInt(8));
                q.setStatus(rs.getBoolean(9));
                q.setCreatorId(rs.getInt(10));
                q.setLastUpdateBy(rs.getInt(11));
                // Set createDate and updateDate and format them
                LocalDateTime createDate = rs.getTimestamp(12).toLocalDateTime().withNano(0);
                LocalDateTime updateDate = rs.getTimestamp(13).toLocalDateTime().withNano(0);
                q.setCreateDate(createDate);
                q.setUpdateDate(updateDate);

                q.setSubjectName(rs.getString(14));
                q.setTypeName(rs.getString(15));
                q.setLevelName(rs.getString(16));
                q.setCreatorName(rs.getString(17));
                q.setUpdateName(rs.getString(18));
                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<User> getAllCreators() {
        List<User> creators = new ArrayList<>();
        String sql = """
                      SELECT u.*, r.role_name FROM 
                       Users u JOIN Role r ON u.role_id = r.role_id 
                       WHERE u.role_id = 1 OR u.role_id = 2 OR u.role_id = 3""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt(1));
                u.setEmail(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setFullname(rs.getString(4));
                u.setGender(rs.getBoolean(5));
                u.setRoleId(rs.getInt(6));
                u.setStatus(rs.getBoolean(7));
                u.setCreatedDate(rs.getDate(8));
                u.setAvatar(rs.getString(9));
                u.setResetToken(rs.getString(10));
                u.setResetTokenExpiry(rs.getString(11));
                u.setMobile(rs.getString(12));
                u.setRoleName(rs.getString(13));
                creators.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return creators;
    }

    public String getRoleNameByCreatorId(int creatorId) {
        String roleName = null;
        try {
            String query = "SELECT role_name FROM Users u JOIN Role r ON u.role_id = r.role_id WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, creatorId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                roleName = resultSet.getString("role_name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roleName;
    }

    public List<Quiz> filterQuizzes(String searchQuizName, String subjectName, String creatorName, String role, String level, int offset, int limit) {
        List<Quiz> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT q.*, s.subject_name, qt.quiz_type_name, lv.level_name, "
                + "u.full_name AS creator_name, u2.full_name AS updater_name, r.role_name "
                + "FROM Quiz q "
                + "JOIN [Subject] s ON q.subject_id = s.subject_id "
                + "JOIN QuizType qt ON q.quiz_type_id = qt.quiz_type_id "
                + "JOIN [Level] lv ON q.level_id = lv.level_id "
                + "JOIN [Users] u ON q.creator_id = u.user_id "
                + "JOIN [Users] u2 ON q.last_updated_by = u2.user_id "
                + "JOIN [Role] r ON u.role_id = r.role_id "
                + "WHERE q.status = 1"
        );

        List<String> params = new ArrayList<>();

        if (searchQuizName != null && !searchQuizName.isEmpty()) {
            sql.append(" AND q.quiz_name LIKE ?");
            params.add("%" + searchQuizName + "%");
        }
        if (subjectName != null && !subjectName.isEmpty()) {
            sql.append(" AND s.subject_name = ?");
            params.add(subjectName);
        }
        if (creatorName != null && !creatorName.isEmpty()) {
            sql.append(" AND u.full_name = ?");
            params.add(creatorName);
        }
        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.role_name = ?");
            params.add(role);
        }
        if (level != null && !level.isEmpty()) {
            sql.append(" AND lv.level_name = ?");
            params.add(level);
        }

        sql.append(" ORDER BY q.quiz_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            int i;
            for (i = 0; i < params.size(); i++) {
                st.setString(i + 1, params.get(i));
            }
            st.setInt(i + 1, offset);
            st.setInt(i + 2, limit);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Quiz q = new Quiz();
                    // Set quiz properties here...
                    q.setQuizId(rs.getInt("quiz_id"));
                    q.setQuizName(rs.getString("quiz_name"));
                    q.setNumberOfQuestion(rs.getInt("number_of_question"));
                    q.setDuration(rs.getInt("duration"));
                    q.setPassRate(rs.getFloat("pass_rate"));
                    q.setLevelId(rs.getInt("level_id"));
                    q.setSubjectId(rs.getInt("subject_id"));
                    q.setQuizTypeId(rs.getInt("quiz_type_id"));
                    q.setStatus(rs.getBoolean("status"));
                    q.setCreatorId(rs.getInt("creator_id"));
                    q.setLastUpdateBy(rs.getInt("last_updated_by"));

                    LocalDateTime createDate = rs.getTimestamp("create_date").toLocalDateTime().withNano(0);
                    LocalDateTime updateDate = rs.getTimestamp("update_date").toLocalDateTime().withNano(0);
                    q.setCreateDate(createDate);
                    q.setUpdateDate(updateDate);

                    q.setSubjectName(rs.getString("subject_name"));
                    q.setTypeName(rs.getString("quiz_type_name"));
                    q.setLevelName(rs.getString("level_name"));
                    q.setCreatorName(rs.getString("creator_name"));
                    q.setUpdateName(rs.getString("updater_name"));

                    list.add(q);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int countFilteredQuizzes(String searchQuizName, String subjectName, String creatorName, String role, String level) {
        int count = 0;
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) AS total "
                + "FROM Quiz q "
                + "JOIN [Subject] s ON q.subject_id = s.subject_id "
                + "JOIN QuizType qt ON q.quiz_type_id = qt.quiz_type_id "
                + "JOIN [Level] lv ON q.level_id = lv.level_id "
                + "JOIN [Users] u ON q.creator_id = u.user_id "
                + "JOIN [Users] u2 ON q.last_updated_by = u2.user_id "
                + "JOIN [Role] r ON u.role_id = r.role_id "
                + "WHERE q.status = 1"
        );

        List<String> params = new ArrayList<>();

        if (searchQuizName != null && !searchQuizName.isEmpty()) {
            sql.append(" AND q.quiz_name LIKE ?");
            params.add("%" + searchQuizName + "%");
        }
        if (subjectName != null && !subjectName.isEmpty()) {
            sql.append(" AND s.subject_name = ?");
            params.add(subjectName);
        }
        if (creatorName != null && !creatorName.isEmpty()) {
            sql.append(" AND u.full_name = ?");
            params.add(creatorName);
        }
        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.role_name = ?");
            params.add(role);
        }
        if (level != null && !level.isEmpty()) {
            sql.append(" AND lv.level_name = ?");
            params.add(level);
        }

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public List<Quiz> filterDeletedQuizzes(String searchQuizName, String subjectName, String creatorName, String role, String level, int offset, int limit) {
        List<Quiz> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT q.*, s.subject_name, qt.quiz_type_name, lv.level_name, "
                + "u.full_name AS creator_name, u2.full_name AS updater_name, r.role_name "
                + "FROM Quiz q "
                + "JOIN [Subject] s ON q.subject_id = s.subject_id "
                + "JOIN QuizType qt ON q.quiz_type_id = qt.quiz_type_id "
                + "JOIN [Level] lv ON q.level_id = lv.level_id "
                + "JOIN [Users] u ON q.creator_id = u.user_id "
                + "JOIN [Users] u2 ON q.last_updated_by = u2.user_id "
                + "JOIN [Role] r ON u.role_id = r.role_id "
                + "WHERE q.status = 0"
        );

        List<String> params = new ArrayList<>();

        if (searchQuizName != null && !searchQuizName.isEmpty()) {
            sql.append(" AND q.quiz_name LIKE ?");
            params.add("%" + searchQuizName + "%");
        }
        if (subjectName != null && !subjectName.isEmpty()) {
            sql.append(" AND s.subject_name = ?");
            params.add(subjectName);
        }
        if (creatorName != null && !creatorName.isEmpty()) {
            sql.append(" AND u.full_name = ?");
            params.add(creatorName);
        }
        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.role_name = ?");
            params.add(role);
        }
        if (level != null && !level.isEmpty()) {
            sql.append(" AND lv.level_name = ?");
            params.add(level);
        }

        sql.append(" ORDER BY q.quiz_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            int i;
            for (i = 0; i < params.size(); i++) {
                st.setString(i + 1, params.get(i));
            }
            st.setInt(i + 1, offset);
            st.setInt(i + 2, limit);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Quiz q = new Quiz();
                    // Set quiz properties here...
                    q.setQuizId(rs.getInt("quiz_id"));
                    q.setQuizName(rs.getString("quiz_name"));
                    q.setNumberOfQuestion(rs.getInt("number_of_question"));
                    q.setDuration(rs.getInt("duration"));
                    q.setPassRate(rs.getFloat("pass_rate"));
                    q.setLevelId(rs.getInt("level_id"));
                    q.setSubjectId(rs.getInt("subject_id"));
                    q.setQuizTypeId(rs.getInt("quiz_type_id"));
                    q.setStatus(rs.getBoolean("status"));
                    q.setCreatorId(rs.getInt("creator_id"));
                    q.setLastUpdateBy(rs.getInt("last_updated_by"));

                    LocalDateTime createDate = rs.getTimestamp("create_date").toLocalDateTime().withNano(0);
                    LocalDateTime updateDate = rs.getTimestamp("update_date").toLocalDateTime().withNano(0);
                    q.setCreateDate(createDate);
                    q.setUpdateDate(updateDate);

                    q.setSubjectName(rs.getString("subject_name"));
                    q.setTypeName(rs.getString("quiz_type_name"));
                    q.setLevelName(rs.getString("level_name"));
                    q.setCreatorName(rs.getString("creator_name"));
                    q.setUpdateName(rs.getString("updater_name"));

                    list.add(q);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int countFilteredDeletedQuizzes(String searchQuizName, String subjectName, String creatorName, String role, String level) {
        int count = 0;
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) AS total "
                + "FROM Quiz q "
                + "JOIN [Subject] s ON q.subject_id = s.subject_id "
                + "JOIN QuizType qt ON q.quiz_type_id = qt.quiz_type_id "
                + "JOIN [Level] lv ON q.level_id = lv.level_id "
                + "JOIN [Users] u ON q.creator_id = u.user_id "
                + "JOIN [Users] u2 ON q.last_updated_by = u2.user_id "
                + "JOIN [Role] r ON u.role_id = r.role_id "
                + "WHERE q.status = 0"
        );

        List<String> params = new ArrayList<>();

        if (searchQuizName != null && !searchQuizName.isEmpty()) {
            sql.append(" AND q.quiz_name LIKE ?");
            params.add("%" + searchQuizName + "%");
        }
        if (subjectName != null && !subjectName.isEmpty()) {
            sql.append(" AND s.subject_name = ?");
            params.add(subjectName);
        }
        if (creatorName != null && !creatorName.isEmpty()) {
            sql.append(" AND u.full_name = ?");
            params.add(creatorName);
        }
        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.role_name = ?");
            params.add(role);
        }
        if (level != null && !level.isEmpty()) {
            sql.append(" AND lv.level_name = ?");
            params.add(level);
        }

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public void deleteQuiz(int quizId, int updaterId) {
        String sql = "UPDATE Quiz SET status = 0,update_date=GETDATE(), last_updated_by = ? WHERE quiz_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, updaterId);
            st.setInt(2, quizId);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void restoreQuiz(int quizId, int updaterId) {
        String sql = "UPDATE Quiz SET status = 1, last_updated_by = ? WHERE quiz_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, updaterId);
            st.setInt(2, quizId);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int addQuizForTest(String quizName, int numberOfQuestions, int duration, float passRate, int levelId, int subjectId, int creatorId, int lastUpdatedBy) {
        int quizId = 0;
        String sql = """
                     INSERT INTO [dbo].[Quiz]
                                ([quiz_name]
                                ,[number_of_question]
                                ,[duration]
                                ,[pass_rate]
                                ,[level_id]
                                ,[subject_id]
                                ,[quiz_type_id]
                                ,[status]
                                ,[creator_id]
                                ,[last_updated_by])
                          VALUES
                                (?, ?, ?, ?, ?, ?, 1, 1, ?, ?)""";
        try (PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, quizName);
            st.setInt(2, numberOfQuestions);
            st.setInt(3, duration);
            st.setFloat(4, passRate);
            st.setInt(5, levelId);
            st.setInt(6, subjectId);
            st.setInt(7, creatorId);
            st.setInt(8, lastUpdatedBy);
            st.executeUpdate();

            // Retrieve the generated quiz_id
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    quizId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizId;
    }

    public int addQuizWithQuestions(String quizName, int totalQuestions, int duration, float passRate, int levelId, int subjectId, int creatorId, int lastUpdatedBy, int basicQuestions, int intermediateQuestions, int advancedQuestions) {
        int quizId = 0;
        String quizSql = """
                         INSERT INTO [dbo].[Quiz]
                                    ([quiz_name]
                                    ,[number_of_question]
                                    ,[duration]
                                    ,[pass_rate]
                                    ,[level_id]
                                    ,[subject_id]
                                    ,[quiz_type_id]
                                    ,[status]
                                    ,[creator_id]
                                    ,[last_updated_by])
                              VALUES
                                    (?, ?, ?, ?, ?, ?, 1, 1, ?, ?)""";

        String quizQuestionSql = "INSERT INTO [dbo].[QuizQuestion] (quiz_id, question_id) VALUES (?, ?)";

        try (PreparedStatement quizStmt = connection.prepareStatement(quizSql, Statement.RETURN_GENERATED_KEYS)) {
            // Insert the quiz
            quizStmt.setString(1, quizName);
            quizStmt.setInt(2, totalQuestions);
            quizStmt.setInt(3, duration);
            quizStmt.setFloat(4, passRate);
            quizStmt.setInt(5, levelId);
            quizStmt.setInt(6, subjectId);
            quizStmt.setInt(7, creatorId);
            quizStmt.setInt(8, lastUpdatedBy);
            quizStmt.executeUpdate();

            // Retrieve the generated quiz_id
            try (ResultSet generatedKeys = quizStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    quizId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating quiz failed, no ID obtained.");
                }
            }

            // Fetch questions based on levels
            List<Integer> basicQuestionIds = fetchQuestionsByLevel("Basic", basicQuestions);
            List<Integer> intermediateQuestionIds = fetchQuestionsByLevel("Intermediate", intermediateQuestions);
            List<Integer> advancedQuestionIds = fetchQuestionsByLevel("Advanced", advancedQuestions);

            // Insert questions into QuizQuestion table
            try (PreparedStatement quizQuestionStmt = connection.prepareStatement(quizQuestionSql)) {
                for (int questionId : basicQuestionIds) {
                    quizQuestionStmt.setInt(1, quizId);
                    quizQuestionStmt.setInt(2, questionId);
                    quizQuestionStmt.addBatch();
                }
                for (int questionId : intermediateQuestionIds) {
                    quizQuestionStmt.setInt(1, quizId);
                    quizQuestionStmt.setInt(2, questionId);
                    quizQuestionStmt.addBatch();
                }
                for (int questionId : advancedQuestionIds) {
                    quizQuestionStmt.setInt(1, quizId);
                    quizQuestionStmt.setInt(2, questionId);
                    quizQuestionStmt.addBatch();
                }
                quizQuestionStmt.executeBatch();
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizId;
    }

    private List<Integer> fetchQuestionsByLevel(String levelName, int numberOfQuestions) throws SQLException {
        List<Integer> questionIds = new ArrayList<>();
        String sql = "SELECT question_id FROM [dbo].[Question] q JOIN [dbo].[Level] l ON q.level_id = l.level_id WHERE l.level_name = ? ORDER BY NEWID()";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, levelName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next() && questionIds.size() < numberOfQuestions) {
                    questionIds.add(rs.getInt("question_id"));
                }
            }
        }
        return questionIds;
    }

    public void updateQuiz(int id, String name, int level, int typeId, int duration, int lastUpdated) {
        String sql = "UPDATE [dbo].[Quiz] SET "
                + "[quiz_name] = ?,"
                + "[level_id] = ?,"
                + "[quiz_type_id] = ?,"
                + "[duration] = ?,"
                + "[update_date] = getdate(), "
                + "[last_updated_by] = ? "
                + "WHERE quiz_id = ?";  // Note the added space before "WHERE"
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, level);
            ps.setInt(3, typeId);
            ps.setInt(4, duration);
            ps.setInt(5, lastUpdated);
            ps.setInt(6, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int countQuestionsByQuizIdAndLevelId(int quizId, int levelId) {
        int count = 0;
        String sql = "  SELECT COUNT(*) FROM\n"
                + "  Quiz q JOIN QuizQuestion qq ON q.quiz_id=qq.quiz_id\n"
                + "  JOIN Question qt ON qq.question_id = qt.question_id\n"
                + "  WHERE q.quiz_id=?\n"
                + "  GROUP BY qt.level_id\n"
                + "  HAVING qt.level_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, quizId);
            ps.setInt(2, levelId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Quiz> getAllQuizBySubject(int subjectId) {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Quiz] WHERE subject_id = ? ";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, subjectId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                q.setQuizId(rs.getInt("quiz_id"));
                q.setQuizName(rs.getString("quiz_name"));
                q.setNumberOfQuestion(rs.getInt("number_of_question"));
                q.setDuration(rs.getInt("duration"));
                q.setPassRate(rs.getFloat("pass_rate"));
                q.setLevelId(rs.getInt("level_id"));
                q.setSubjectId(rs.getInt("subject_id"));
                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getNoQuestionBySubjectAndLevel(int subjectId, int levelId) {
        int n = 0;
        try {
            String sql = "SELECT COUNT(qs.question_id) AS question_count\n"
                    + "FROM Question qs \n"
                    + "JOIN Lesson ls ON\n"
                    + "qs.lesson_id=ls.lesson_id \n"
                    + "JOIN [Subject] s ON s.subject_id=ls.subject_id\n"
                    + "WHERE s.subject_id=? AND qs.level_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, subjectId);
            ps.setInt(2, levelId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                n = rs.getInt(1);
            }
            return n;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        QuizDAO dao = new QuizDAO();
        Quiz quiz = new Quiz();
        quiz.setQuizName("New Practice Quiz");
        quiz.setNumberOfQuestion(30);
        quiz.setDuration(60);  // Set duration as 60 minutes or any other value
        quiz.setPassRate(70.0f);  // Set pass rate as 70% or any other value
        quiz.setLevelId(1);  // Set levelId based on the logic or selection
        quiz.setSubjectId(1);
        quiz.setQuizTypeId(1);  // Set quizTypeId based on the logic or selection
        quiz.setCreatorId(1);  // Assuming User has a getUserId() method
        quiz.setLastUpdateBy(1);  // Assuming User has a getUserId() method

        int quizId = dao.createQuiz(quiz);
        System.out.println(quizId);
    }

    public int addQuiz(Quiz quiz) throws SQLException {
        String sql = "INSERT INTO Quiz (quiz_name, number_of_question, duration, pass_rate, level_id, subject_id, quiz_type_id, status, creator_id, last_updated_by) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 1, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, quiz.getQuizName());
            ps.setInt(2, quiz.getNumberOfQuestion());
            ps.setInt(3, quiz.getDuration());
            ps.setBigDecimal(4, BigDecimal.valueOf(quiz.getPassRate())); // Use BigDecimal for precision
            ps.setInt(5, quiz.getLevelId());
            ps.setInt(6, quiz.getSubjectId());
            ps.setInt(7, quiz.getQuizTypeId());
            ps.setInt(8, quiz.getCreatorId());
            ps.setInt(9, quiz.getLastUpdateBy()); // Ensure this is included if it's a required field

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated quiz ID
                    } else {
                        throw new SQLException("Creating quiz failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating quiz failed, no rows affected.");
            }
        }
    }

    public void addQuestionToQuiz(int quizId, int questionId) throws SQLException {
        String sql = "INSERT INTO QuizQuestion (quiz_id, question_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizId);
            ps.setInt(2, questionId);
            ps.executeUpdate();
        }
    }

    public boolean isQuizNameExists(String quizName) {
        String sql = "SELECT COUNT(*) FROM Quiz WHERE quiz_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, quizName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
