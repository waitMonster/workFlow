package com.facishare.openapi.util;

import java.sql.*;

public class JdbcUtil {

	private JdbcUtil() {

	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection(String jdbcUrl,
			   String userName,String password) throws SQLException {
		return DriverManager.getConnection(jdbcUrl,userName,password);
	}

	public static void free(ResultSet rs, PreparedStatement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}

}
