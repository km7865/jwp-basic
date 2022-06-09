package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import core.jdbc.*;
import next.model.Question;

public class QuestionDao {
    private static QuestionDao questionDao;

    private QuestionDao() {}

    public static QuestionDao getInstance() {
        if (questionDao == null) {
            questionDao = new QuestionDao();
        }

        return questionDao;
    }

    public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS " +
                "(writer, title, contents, createdDate) " + 
                " VALUES (?, ?, ?, ?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, question.getWriter());
                pstmt.setString(2, question.getTitle());
                pstmt.setString(3, question.getContents());
                pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
                return pstmt;
            }
        };

        KeyHolder keyHolder = new KeyHolder();
        JdbcTemplate.getInstance().update(psc, keyHolder);
        return findById(keyHolder.getId());
    }

    public void update(Question question) {
        String sql = "UPDATE QUESTIONS set title = ?, contents = ? WHERE questionId = ?";
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setObject(1, question.getTitle());
                pstmt.setObject(2, question.getContents());
                pstmt.setObject(3, question.getQuestionId());
            }
        };

        JdbcTemplate.getInstance().update(sql, pss);
    }

    public int addCountOfComment(Long questionId) {
        String sql = "UPDATE QUESTIONS set countOfAnswer = countOfAnswer+1 WHERE questionId=?";
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setObject(1, questionId);
            }
        };

        JdbcTemplate.getInstance().update(sql, pss);
        return findById(questionId).getCountOfComment();
    }
    
    public List<Question> findAll() {
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc";

        RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"), null,
                        rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }

        };

        return JdbcTemplate.getInstance().query(sql, rm);
    }

    public Question findById(long questionId) {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "WHERE questionId = ?";

        RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                        rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }
        };

        return JdbcTemplate.getInstance().queryForObject(sql, rm, questionId);
    }

    public void delete(Long questionId) {
        String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";
        PreparedStatementSetter pss = (pstmt -> {
            pstmt.setObject(1, questionId);
        });

        JdbcTemplate.getInstance().update(sql, pss);
    }
}
