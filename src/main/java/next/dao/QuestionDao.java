package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import lombok.extern.slf4j.Slf4j;
import next.model.Question;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
public class QuestionDao {
    public void insert(Question question) {
        JdbcTemplate template = new JdbcTemplate();
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setObject(1, question.getWriter());
            pstmt.setObject(2, question.getTitle());
            pstmt.setObject(3, question.getContents());
            pstmt.setObject(4, Date.valueOf(question.getCreatedDate().toLocalDate()));
        };
        //template.update("INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, 0)", pss);
        template.update("INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, 0)",
                question.getWriter(), question.getTitle(), question.getContents(), Date.valueOf(question.getCreatedDate().toLocalDate()));
    }

    public void update(Question question) {
        JdbcTemplate template = new JdbcTemplate();
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setObject(1, question.getTitle());
                pstmt.setObject(2, question.getContents());
                pstmt.setObject(3, question.getQuestionId());
            }
        };
        template.update("UPDATE QUESTIONS set title=?, contents=? WHERE questionId=?", pss);
    }

    public List<Question> findAll() {
        JdbcTemplate template = new JdbcTemplate();

        RowMapper<Question> rm = (rs -> {
            return new Question(rs.getLong("questionId"),
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"),
                    rs.getTimestamp("createdDate").toLocalDateTime(),
                    rs.getInt("countOfAnswer"));
        });

        return template.query("SELECT * FROM QUESTIONS", rm);
    }

    public Question findByQuestionId(Long questionId) {
        JdbcTemplate template = new JdbcTemplate();

        PreparedStatementSetter pss = (pstmt -> {
            pstmt.setObject(1, questionId);
        });

        RowMapper<Question> rm = (rs -> {
            return new Question(rs.getLong("questionId"),
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"),
                    rs.getTimestamp("createdDate").toLocalDateTime(),
                    rs.getInt("countOfAnswer"));
        });

        return template.queryForObject("SELECT * FROM QUESTIONS WHERE questionId = ?", rm, pss);
    }
}
