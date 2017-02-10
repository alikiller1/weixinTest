package per.liuqh.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtil {
	public static void main(String[] args) {
		try {
			Connection con = DBUtil.getConnection(
					"jdbc:mysql://localhost:3306/testdb", "root", "123");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from stu");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "-->" + rs.getString(2));
			}
			DBUtil.closeCon(rs, st, null, con);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Connection getConnection(String url, String user, String pw)
			throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pw);
			return con;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void closeCon(ResultSet rs, Statement st,
			PreparedStatement pst, Connection con) throws Exception {
		try {
			if (st != null) {
				st.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
