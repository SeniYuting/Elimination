package model.DataBaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseHelper {
	private static Connection conn;
	Statement st;

	public DataBaseHelper() {
		if (conn == null) {
			conn = getConnection();
		}
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/user", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public String insert(String sqlInfo) {
		try {
			st = (Statement) conn.createStatement();
			int res = st.executeUpdate(sqlInfo);

			if (res != 0) {
				return "Ìí¼Ó³É¹¦£¡";
			} else {
				return "Ìí¼ÓÊ§°Ü£¡";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Ìí¼ÓÊ§°Ü£¡";
		}
	}

	public String delete(String sqlInfo) {
		try {
			st = (Statement) conn.createStatement();
			int res = st.executeUpdate(sqlInfo);

			if (res != 0) {
				return "É¾³ý³É¹¦£¡";
			} else {
				return "É¾³ýÊ§°Ü£¡";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "É¾³ýÊ§°Ü£¡";
		}
	}

	public String update(String sqlInfo) {
		try {
			st = (Statement) conn.createStatement();
			int res = st.executeUpdate(sqlInfo);

			if (res != 0) {
				return "¸üÐÂ³É¹¦£¡";
			} else {
				return "¸üÐÂÊ§°Ü£¡";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "¸üÐÂÊ§°Ü£¡";
		}
	}

	public ResultSet find(String info) {
		try {
			st = conn.createStatement();

			ResultSet result = st.executeQuery(info);

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
