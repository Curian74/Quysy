package Dal;

import Model.Lesson;
import Model.LessonTopic;
import Model.LessonType;
import Model.User;
import Model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;

public class LessonDAO extends DBContext {

    public List<Lesson> getAllLessons() {
        List<Lesson> list = new ArrayList<>();
        String sql = "SELECT l.*, lt.lesson_type_name, ltp.lesson_topic_name, u.full_name, q.quiz_name "
                + "FROM Lesson l "
                + "JOIN LessonType lt ON l.lesson_type_id = lt.lesson_type_id "
                + "JOIN LessonTopic ltp ON l.topic_id = ltp.lesson_topic_id "
                + "LEFT JOIN Users u ON l.updated_by = u.user_id "
                + "LEFT JOIN Quiz q ON l.quiz_id = q.quiz_id";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Lesson lesson = extractLessonFromResultSet(rs);
                list.add(lesson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Lesson> getLessonsBySubjectID(int subjectId) {
        List<Lesson> list = new ArrayList<>();
        String sql = "SELECT l.*, lt.lesson_type_name, ltp.lesson_topic_name, u.full_name AS full_name, q.quiz_name "
                + "FROM Lesson l "
                + "JOIN LessonType lt ON l.lesson_type_id = lt.lesson_type_id "
                + "JOIN LessonTopic ltp ON l.topic_id = ltp.lesson_topic_id "
                + "LEFT JOIN Users u ON l.updated_by = u.user_id "
                + "LEFT JOIN Quiz q ON l.quiz_id = q.quiz_id "
                + "WHERE l.subject_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, subjectId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = extractLessonFromResultSet(rs);
                    list.add(lesson);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Lesson getLessonById(int lessonId) {
        String sql = "SELECT l.*, lt.lesson_type_name, ltp.lesson_topic_name, u.full_name AS full_name, q.quiz_name "
                + "FROM Lesson l "
                + "JOIN LessonType lt ON l.lesson_type_id = lt.lesson_type_id "
                + "JOIN LessonTopic ltp ON l.topic_id = ltp.lesson_topic_id "
                + "LEFT JOIN Users u ON l.updated_by = u.user_id "
                + "LEFT JOIN Quiz q ON l.quiz_id = q.quiz_id "
                + "WHERE l.lesson_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, lessonId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return extractLessonFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Lesson extractLessonFromResultSet(ResultSet rs) throws SQLException {
        Lesson lesson = new Lesson();
        lesson.setLessonId(rs.getInt("lesson_id"));
        lesson.setLessonName(rs.getString("lesson_name"));
        lesson.setLessonOrder(rs.getInt("lesson_order"));
        lesson.setYoutubeLink(rs.getString("youtube_link"));
        lesson.setHtmlContent(rs.getString("html_content"));
        lesson.setLessonTypeId(rs.getInt("lesson_type_id"));
        lesson.setLessonTypeName(rs.getString("lesson_type_name"));
        lesson.setSubjectId(rs.getInt("subject_id"));
        lesson.setTopicId(rs.getInt("topic_id"));
        lesson.setTopicName(rs.getString("lesson_topic_name"));
        lesson.setPackageId(rs.getInt("package_id"));
        lesson.setStatus(rs.getBoolean("status"));
        lesson.setCreatedDate(rs.getTimestamp("created_date"));
        lesson.setUpdatedDate(rs.getTimestamp("updated_date"));

        lesson.setUpdatedBy(rs.getInt("updated_by"));
        lesson.setUpdatedByName(rs.getString("full_name"));

        lesson.setQuizName(rs.getString("quiz_name"));

        return lesson;
    }

    // Method to retrieve lessons by both subject ID and package ID
    public int getNumberOfLessons(int subjectId) {
        String sql = "SELECT COUNT(*) AS lessonCount FROM Lesson WHERE subject_id = ?";
        int lessonCount = 0;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, subjectId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    lessonCount = rs.getInt("lessonCount");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lessonCount;
    }

    public boolean updateLesson(Lesson lesson) {
        String sql = "UPDATE Lesson SET "
                + "lesson_name = ?, "
                + "lesson_order = ?, "
                + "youtube_link = ?, "
                + "html_content = ?, "
                + "lesson_type_id = ?, "
                + "subject_id = ?, "
                + "topic_id = ?, "
                + "package_id = ?, "
                + "status = ?, "
                + "quiz_id = ?, "
                + "updated_by = ?, "
                + "updated_date = CURRENT_TIMESTAMP "
                + "WHERE lesson_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, lesson.getLessonName());
            st.setInt(2, lesson.getLessonOrder());
            st.setString(3, lesson.getYoutubeLink());
            st.setString(4, lesson.getHtmlContent());
            st.setInt(5, lesson.getLessonTypeId());
            st.setInt(6, lesson.getSubjectId());
            st.setInt(7, lesson.getTopicId());
            st.setInt(8, lesson.getPackageId());
            st.setBoolean(9, lesson.getStatus());

            if (lesson.getQuizId() != 0) {
                st.setInt(10, lesson.getQuizId());
            } else {
                st.setNull(10, java.sql.Types.INTEGER);
            }

            if (lesson.getUpdatedBy() != 0) {
                st.setInt(11, lesson.getUpdatedBy());
            } else {
                st.setNull(11, java.sql.Types.INTEGER);
            }

            st.setInt(12, lesson.getLessonId());

            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, "Error updating lesson", ex);
            return false;
        }
    }

    public boolean insertLesson(Lesson lesson) {
        String sql = "INSERT INTO Lesson (lesson_name, lesson_order, youtube_link, html_content, lesson_type_id, subject_id, topic_id, package_id, status, quiz_id, updated_by, created_date, updated_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, lesson.getLessonName());
            st.setInt(2, lesson.getLessonOrder());
            st.setString(3, lesson.getYoutubeLink());
            st.setString(4, lesson.getHtmlContent());
            st.setInt(5, lesson.getLessonTypeId());
            st.setInt(6, lesson.getSubjectId());
            st.setInt(7, lesson.getTopicId());
            st.setInt(8, lesson.getPackageId());
            st.setBoolean(9, lesson.getStatus());

            if (lesson.getQuizId() != 0) {
                st.setInt(10, lesson.getQuizId());
            } else {
                st.setNull(10, java.sql.Types.INTEGER);
            }

            if (lesson.getUpdatedBy() != 0) {
                st.setInt(11, lesson.getUpdatedBy());
            } else {
                st.setNull(11, java.sql.Types.INTEGER);
            }

            st.setTimestamp(12, lesson.getCreatedDate());
            st.setTimestamp(13, lesson.getUpdatedDate());

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating lesson failed, no rows affected.");
            }

            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    lesson.setLessonId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating lesson failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, "Error inserting lesson", ex);
            return false;
        }
    }

    public boolean deleteLesson(int lessonId) {
        String sql = "DELETE FROM Lesson WHERE lesson_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, lessonId);

            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, "Error deleting lesson", ex);
            return false;
        }
    }

    public List<LessonTopic> getAllLessonTopics() {
        List<LessonTopic> list = new ArrayList<>();
        String sql = "SELECT * FROM LessonTopic";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                LessonTopic topic = new LessonTopic();
                topic.setLessonTopicId(rs.getInt("lesson_topic_id"));
                topic.setLessonTopicName(rs.getString("lesson_topic_name"));
                list.add(topic);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<LessonType> getAllLessonTypes() {
        List<LessonType> lessonTypes = new ArrayList<>();
        String sql = "SELECT * FROM LessonType ORDER BY lesson_type_id";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                LessonType lessonType = new LessonType(
                        rs.getInt("lesson_type_id"),
                        rs.getString("lesson_type_name")
                );
                lessonTypes.add(lessonType);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lessonTypes;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullname(rs.getString("full_name"));
                list.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Quiz> getAllQuizzes() {
        List<Quiz> list = new ArrayList<>();
        String sql = "SELECT * FROM Quiz";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getInt("quiz_id"));
                quiz.setQuizName(rs.getString("quiz_name"));
                list.add(quiz);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getLatestLessonOrder(int subjectId, int packageId) {
        String sql = "SELECT MAX(lesson_order) FROM Lesson WHERE subject_id = ? AND package_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, subjectId);
            st.setInt(2, packageId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public List<Integer> getExistingLessonOrders(int subjectId, int packageId) {
        List<Integer> orders = new ArrayList<>();
        String sql = "SELECT DISTINCT lesson_order FROM Lesson WHERE subject_id = ? AND package_id = ? ORDER BY lesson_order";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, subjectId);
            st.setInt(2, packageId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    orders.add(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    public List<Lesson> getLessonsBySubjectAndPackage(int subjectId, int packageId) {
        List<Lesson> list = new ArrayList<>();
        String sql = "SELECT l.*, lt.lesson_type_name, ltp.lesson_topic_name, u.full_name AS full_name, q.quiz_name "
                + "FROM Lesson l "
                + "JOIN LessonType lt ON l.lesson_type_id = lt.lesson_type_id "
                + "JOIN LessonTopic ltp ON l.topic_id = ltp.lesson_topic_id "
                + "LEFT JOIN Users u ON l.updated_by = u.user_id "
                + "LEFT JOIN Quiz q ON l.quiz_id = q.quiz_id "
                + "WHERE l.subject_id = ? AND l.package_id = ? "
                + "ORDER BY l.lesson_order, CASE WHEN l.lesson_type_id = 1 THEN 0 ELSE 1 END";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, subjectId);
            st.setInt(2, packageId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = extractLessonFromResultSet(rs);
                    list.add(lesson);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Lesson> getLessonsByTypeAndSubject(int lessonTypeId, int subjectId) {
        List<Lesson> list = new ArrayList<>();
        String sql = "SELECT l.*, lt.lesson_type_name, ltp.lesson_topic_name, u.full_name AS full_name, q.quiz_name "
                + "FROM Lesson l "
                + "JOIN LessonType lt ON l.lesson_type_id = lt.lesson_type_id "
                + "JOIN LessonTopic ltp ON l.topic_id = ltp.lesson_topic_id "
                + "LEFT JOIN Users u ON l.updated_by = u.user_id "
                + "LEFT JOIN Quiz q ON l.quiz_id = q.quiz_id "
                + "WHERE l.lesson_type_id = ? AND l.subject_id = ? "
                + "ORDER BY l.lesson_order";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, lessonTypeId);
            st.setInt(2, subjectId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = extractLessonFromResultSet(rs);
                    list.add(lesson);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Lesson> getFilteredSortedLessons(int subjectId, Integer packageId, String lessonType,
            String lessonTopic, String lessonName, String updatedBy, String sortBy, String sortOrder,
            String dateFrom, String dateTo, String lessonOrder) {
        List<Lesson> lessons = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT l.*, lt.lesson_type_name, ltp.lesson_topic_name, u.full_name AS full_name, q.quiz_name "
                + "FROM Lesson l "
                + "JOIN LessonType lt ON l.lesson_type_id = lt.lesson_type_id "
                + "JOIN LessonTopic ltp ON l.topic_id = ltp.lesson_topic_id "
                + "LEFT JOIN Users u ON l.updated_by = u.user_id "
                + "LEFT JOIN Quiz q ON l.quiz_id = q.quiz_id "
                + "WHERE l.subject_id = ?");

        List<Object> params = new ArrayList<>();
        params.add(subjectId);

        if (packageId != null) {
            sql.append(" AND l.package_id = ?");
            params.add(packageId);
        }
        if (lessonType != null && !lessonType.isEmpty()) {
            sql.append(" AND l.lesson_type_id = ?");
            params.add(Integer.valueOf(lessonType));
        }
        if (lessonTopic != null && !lessonTopic.isEmpty()) {
            sql.append(" AND l.topic_id = ?");
            params.add(Integer.valueOf(lessonTopic));
        }
        if (lessonName != null && !lessonName.isEmpty()) {
            sql.append(" AND l.lesson_name LIKE ?");
            params.add("%" + lessonName + "%");
        }
        if (updatedBy != null && !updatedBy.isEmpty()) {
            sql.append(" AND u.full_name LIKE ?");
            params.add("%" + updatedBy + "%");
        }
        if (dateFrom != null && !dateFrom.isEmpty()) {
            sql.append(" AND l.updated_date >= ?");
            params.add(java.sql.Timestamp.valueOf(dateFrom + " 00:00:00"));
        }
        if (dateTo != null && !dateTo.isEmpty()) {
            sql.append(" AND l.updated_date <= ?");
            params.add(java.sql.Timestamp.valueOf(dateTo + " 23:59:59"));
        }
        if (lessonOrder != null && !lessonOrder.isEmpty()) {
            sql.append(" AND l.lesson_order = ?");
            params.add(Integer.valueOf(lessonOrder));
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            if (sortBy.equals("created_date") || sortBy.equals("updated_date")) {
                sql.append(" ORDER BY l.").append(sortBy);
                if (sortOrder != null && !sortOrder.isEmpty()) {
                    sql.append(" ").append(sortOrder);
                } else {
                    sql.append(" DESC");
                }
            }
        } else {
            sql.append(" ORDER BY l.lesson_order, CASE WHEN l.lesson_type_id = 1 THEN 0 ELSE 1 END");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = extractLessonFromResultSet(rs);
                    lesson.setUpdatedByName(rs.getString("full_name"));
                    lessons.add(lesson);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lessons;
    }

    public List<Lesson> getLessonGroupsBySubject(int subjectId) {
        List<Lesson> list = new ArrayList<>();
        String sql = "SELECT l.*, lt.lesson_type_name, ltp.lesson_topic_name, u.full_name AS full_name, q.quiz_name "
                + "FROM Lesson l "
                + "JOIN LessonType lt ON l.lesson_type_id = lt.lesson_type_id "
                + "JOIN LessonTopic ltp ON l.topic_id = ltp.lesson_topic_id "
                + "LEFT JOIN Users u ON l.updated_by = u.user_id "
                + "LEFT JOIN Quiz q ON l.quiz_id = q.quiz_id "
                + "WHERE l.subject_id = ? AND l.lesson_type_id = 1 "
                + "ORDER BY l.lesson_order";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, subjectId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = extractLessonFromResultSet(rs);
                    list.add(lesson);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Lesson> getLessonsByOrderSubjectAndPackage(int lessonOrder, int subjectId, int packageId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT l.lesson_id, l.lesson_name, lt.lesson_type_name, ltp.lesson_topic_name "
                + "FROM Lesson l "
                + "JOIN LessonType lt ON l.lesson_type_id = lt.lesson_type_id "
                + "JOIN LessonTopic ltp ON l.topic_id = ltp.lesson_topic_id "
                + "WHERE l.lesson_order = ? AND l.subject_id = ? AND l.package_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, lessonOrder);
            st.setInt(2, subjectId);
            st.setInt(3, packageId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setLessonId(rs.getInt("lesson_id"));
                    lesson.setLessonName(rs.getString("lesson_name"));
                    lesson.setLessonTypeName(rs.getString("lesson_type_name"));
                    lesson.setTopicName(rs.getString("lesson_topic_name"));
                    lessons.add(lesson);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lessons;
    }

    public boolean isLessonNameDuplicate(String lessonName, int subjectId, int lessonId) {
        String sql = "SELECT COUNT(*) FROM Lesson WHERE lesson_name = ? AND subject_id = ? AND lesson_id != ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, lessonName);
            st.setInt(2, subjectId);
            st.setInt(3, lessonId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
