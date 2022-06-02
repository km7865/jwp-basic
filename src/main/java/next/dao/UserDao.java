package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;


/*
* Connection, PreparedStatement 관리 중복
* try 구문 중복
* (User) 캐스트 구문 제거 -> 제네릭 사용 -> List<Object> 와 Object 구분 ?
* 
*/
public class UserDao {

    public void insert(User user) throws SQLException {
        JDBCTemplate<User> template = new JDBCTemplate<>();
        PreparedStatementSetter setter = (pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        });

        // pss, 가변인자
        // template.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", setter);
        template.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) throws SQLException {
        JDBCTemplate<User> template = new JDBCTemplate<>();
        PreparedStatementSetter setter = pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        };

        template.update("UPDATE USERS SET password=?, name=?, email=? WHERE userId=?", setter);
    }

    public List<User> findAll() throws SQLException {
        JDBCTemplate<User> template = new JDBCTemplate<>();
        PreparedStatementSetter pss = (pstmt -> {});

        RowMapper<User> mapper = (rs ->
                new User(rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")));

        return template.query("SELECT userId, password, name, email FROM USERS", pss, mapper);
    }

    public User findByUserId(String userId) throws SQLException {
        JDBCTemplate<User> template = new JDBCTemplate<>();
        PreparedStatementSetter setter = (pstmt -> {
            pstmt.setString(1, userId);
        });

        RowMapper<User> mapper = (rs ->
                new User(rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")));

        return template.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", setter, mapper);
    }
}
