package cn.code.accessdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestAccess {
	public static void main(String args[]) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:test", "",
					"");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from 5U_LizhiComment");
			while (rs.next()) {
				System.out.print(rs.getString(3)); // print comment id
				System.out.print("\t" + rs.getString(4)); // print user name
				System.out.print("\t" + rs.getString(5)); // print comment
															// content
				System.out.println();
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
