package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// InsertJDBCTemplate, UpdateJDBCTemplate 의 구현코드가 같기 때문에 하나의 클래스로 통합
// 추상 메서드로 정의해두었기 때문에 insert, update 에 맞춰 구현 가능
// 하지만 여전히 User 와 의존관계가 존재하기 때문에 다른 Dao 에 맞춰 사용할 수 없음 -> 리팩토링 필요
// User 객체를 넘겨받을 필요가 없기에 제거, sql 문은 인자로 넘겨받아 사용 -> User 클래스에 종속적이지 않음
// SelectJDBCTemplate 과 통합하면 Update 에서 필요 없는 mapRow 도 구현을 해야하므로 리팩토링 필요

public class JDBCTemplate<T> {

    public void update(String sql, PreparedStatementSetter setter) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);){
            setter.setValues(pstmt);

            pstmt.executeUpdate();
        }
    }

    public void update(String sql, Object... values) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);){
            // TODO

            pstmt.executeUpdate();
        }
    }

    public List<T> query(String sql, RowMapper<T> mapper) throws SQLException {

        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);) {
            return (List<T>) mapper.mapRow(pstmt.executeQuery());
        }
    }

    public T queryForObject(String sql, PreparedStatementSetter setter, RowMapper<T> mapper) throws SQLException {

        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);){
            setter.setValues(pstmt);

            return mapper.mapRow(pstmt.executeQuery());
        }
    }
}
