package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import next.model.Answer;
import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import core.jdbc.RowMapper;

@Slf4j
public class AnswerDao {
    private static AnswerDao answerDao;

    private AnswerDao() {}

    public static AnswerDao getInstance() {
        if (answerDao == null) {
            answerDao = new AnswerDao();
        }

        return answerDao;
    }

    public Answer insert(Answer answer) {
        String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, answer.getWriter());
                pstmt.setString(2, answer.getContents());
                pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreateDate()));
                pstmt.setLong(4, answer.getQuestionId());
                return pstmt;
            }
        };

        KeyHolder keyHolder = new KeyHolder();
        JdbcTemplate.getInstance().update(psc, keyHolder);
        return findById(keyHolder.getId());
    }

    public Answer findById(long answerId) {

        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";

        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                        rs.getTimestamp("createdDate"), rs.getLong("questionId"));
            }
        };

        return JdbcTemplate.getInstance().queryForObject(sql, rm, answerId);
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? "
                + "order by answerId desc";

        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                        rs.getTimestamp("createdDate"), questionId);
            }
        };

        return JdbcTemplate.getInstance().query(sql, rm, questionId);
    }

    public void delete(Long answerId) {
        String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
        log.debug("answerId: {}", answerId);

        JdbcTemplate.getInstance().update(sql, answerId);
    }
}
