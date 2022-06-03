package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.Answer;
import next.model.Question;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AnswerDao {
    public void insert(Answer answer) {
        JdbcTemplate template = new JdbcTemplate();
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setObject(1, answer.getWriter());
                pstmt.setObject(2, answer.getContents());
                pstmt.setObject(3, Date.valueOf(LocalDateTime.now().toLocalDate()));
                pstmt.setObject(4, answer.getQuestionId());
            }
        };

        template.update("INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)", pss);
    }

    public void update(Answer answer) {
        JdbcTemplate template = new JdbcTemplate();
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setObject(1, answer.getContents());
            }
        };

        template.update("UPDATE ANSWERS set contents=? WHERE answerId=?", pss);
    }

    public List<Answer> findAll() {
        JdbcTemplate template = new JdbcTemplate();
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
            }
        };
        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(
                        rs.getLong("answerId"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate").toLocalDateTime(),
                        rs.getLong("questionId"));
            }
        };

        return template.query("SELECT * FROM ANSWERS", rm, pss);
    }

    public List<Answer> findByQuestionId(Long questionId) {
        JdbcTemplate template = new JdbcTemplate();
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setObject(1, questionId);
            }
        };
        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(
                        rs.getLong("answerId"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate").toLocalDateTime(),
                        rs.getLong("questionId"));
            }
        };

        return template.query("SELECT * FROM ANSWERS WHERE questionId=?", rm, pss);
    }

    public void remove(Long answerId) {
        JdbcTemplate template = new JdbcTemplate();
        PreparedStatementSetter pss = (pstmt -> {pstmt.setObject(1, answerId);});

        template.update("DELETE FROM ANSWERS WHERE answerId = ?", pss);
    }
}
