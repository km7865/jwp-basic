package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

abstract public class SelectJDBCTemplate {
    abstract void setValues(PreparedStatement pstmt) throws SQLException;
    abstract Object mapRow(ResultSet rs) throws SQLException;


}
