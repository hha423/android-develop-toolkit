<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.allthelucky.memo.utils.SqlUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sql Console</title>
<style type="text/css">
body {
	font-size: 14px;
}
h1 {
	font-size: 18px;
}
#main {
	background: #eeeeee;
	margin-left: auto;
	margin-right: auto;
	width: 500px;
	margin-left: auto;
}
</style>
<%
	request.setCharacterEncoding("UTF-8");
	response.setHeader("Content-type", "text/html;charset=UTF-8");
	response.setCharacterEncoding("UTF-8");
	String result = "";
	String sql = request.getParameter("data");
	if (sql != null) {
		if (sql.startsWith("select")) {
			result = SqlUtils.executeQuery(sql, null).toString();
		} else if (sql.startsWith("insert") || sql.startsWith("update") || sql.startsWith("delete")) {
			result = SqlUtils.executeUpdate(sql).toString();
		} else {
			result = SqlUtils.executeSql(sql).toString();
		}
	} else {
		result = "";
	}
%>
</head>
<body>
	<div id="main">
		<form method="post" action="./sql_console.jsp">
			<table width="500px">
				<tr>
					<td colspan="2" align="center"><h1>Sql Console for test!</h1></td>
				</tr>
				<tr>
					<td width="120px">Sql语句</td>
					<td width="380px"><textarea style="width: 360px; height: 60px"
							name="data"><%if (sql != null && !sql.equals("")) {out.print(sql);}%></textarea></td>
				</tr>
				<tr>
					<td width="140px"></td>
					<td width="380px"><input type="submit"
						style="width: 120px; height: 24px" value="开始请求">
				</tr>
			</table>
		</form>
	</div>
	<br />
	<div id="main">
		<table width="500px">
			<tr>
				<td width="120px">响应结果</td>
				<td width="380px"><textarea style="width: 360px; height: 100px"><%if (result != null && !result.equals("")) {out.print(result);}%></textarea></td>
			</tr>
		</table>
	</div>
</body>
</html>
