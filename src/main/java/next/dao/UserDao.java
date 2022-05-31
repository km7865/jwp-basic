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

        template.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", setter);
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
        RowMapper<User> mapper = (rs -> {
            List<User> users = new ArrayList<>();
            if (rs.next()) {
                users.add(new User(rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")));
            }
            return (User) users;
        });

        return template.query("SELECT userId, password, name, email FROM USERS", mapper);
    }

    public User findByUserId(String userId) throws SQLException {
        JDBCTemplate<User> template = new JDBCTemplate<>();
        PreparedStatementSetter setter = (pstmt -> {
            pstmt.setString(1, userId);
        });

        RowMapper<User> mapper = (rs -> {
            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
            return user;
        });

        return template.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", setter, mapper);
    }
}
