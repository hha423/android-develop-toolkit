package com.allthelucky.memo.utils;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SqlUtils {
	private static String hostName = "localhost";
	private static String portValue = "3306";
	private static String databaseName = "test";
	private static String userName = "root";
	private static String passWord = "root";

	private static String driverName = "com.mysql.jdbc.Driver";
	private static String connName = "jdbc:mysql://" + hostName + ":" + portValue + "/" + databaseName;

	static {
		final String infoStr = java.lang.System.getenv("VCAP_SERVICES");
		if (infoStr != null && !"".equals(infoStr)) {
			JSONObject obj = new JSONObject(infoStr);
			if (obj != null) {
				JSONArray array = obj.optJSONArray("mysql-5.1");
				obj = array.optJSONObject(0);
				if (obj != null) {
					obj = obj.optJSONObject("credentials");
					if (obj != null) {
						databaseName = obj.optString("name");
						hostName = obj.optString("host");
						portValue = obj.optString("port");
						userName = obj.optString("username");
						passWord = obj.optString("password");
						connName = "jdbc:mysql://" + hostName + ":" + portValue + "/" + databaseName;
					}
				}
			}
		}
	}

	/**
	 * read input JSONObject
	 * 
	 * @param request
	 * @return
	 */
	public static JSONObject readJSONObject(HttpServletRequest request) {
		try {
			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			final String json = sb.toString();
			if (json != null && !"".equals(json)) {
				return new JSONObject(json.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}

	/**
	 * 取数据库连接
	 */
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(connName, userName, passWord);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 普通操作如CREATE, DROP, RENAME
	 * 
	 * @param sql
	 * @return
	 */
	public static JSONObject executeSql(String sql) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			return setResult(((!st.execute(sql)) ? "00" : "01"), null);
		} catch (SQLException e) {
			e.printStackTrace();
			return setResult("01", null);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 执行INSERT, UPDATE, DELETE
	 * 
	 * @param sql
	 * @return
	 */
	public static JSONObject executeUpdate(String sql) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			return setResult(((st.executeUpdate(sql) != 0) ? "00" : "01"), null);
		} catch (SQLException e) {
			e.printStackTrace();
			return setResult("01", null);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 执行查询 QUERY
	 * 
	 * @param sql
	 * @return
	 */
	public static JSONObject executeQuery(String sql, String[] names) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			String tnames[] = names;
			if (tnames == null || tnames.length == 0) {
				ResultSetMetaData rsmd = rs.getMetaData(); // 表的字段属性变量
				int len = rsmd.getColumnCount();
				if (len > 0) {
					tnames = new String[len];
					for (int i = 1; i <= len; i++) // 按字段属性输出表的数据名
					{
						tnames[i - 1] = rsmd.getColumnName(i);
					}
				}
			}
			final JSONArray data = new JSONArray();
			boolean enter = false;
			while (rs.next()) {
				if (!enter)
					enter = true;
				if (tnames.length > 0) {// 按字段名查询
					JSONObject item = new JSONObject();
					int len = tnames.length;
					for (int i = 0; i < len; i++) {// 将查询值输出到JSONObject
						final String key = tnames[i];
						try {
							item.put(key, rs.getString(key));// 放入JSONArray
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					data.put(item);
				}
			}
			return setResult(enter ? "00" : "01", data);
		} catch (SQLException e) {
			e.printStackTrace();
			return setResult("01", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				conn = null;
			}
		}
	}

	public static JSONObject setResult(String code, Object object) {
		final JSONObject result = new JSONObject();
		result.put("code", code);
		if (object != null) {
			result.put("data", object);
		}
		return result;
	}

}
