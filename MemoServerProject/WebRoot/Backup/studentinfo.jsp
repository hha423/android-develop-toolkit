<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.allthelucky.memo.domain.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学生信息显示</title>
</head>
<body>
	<%
		request.setCharacterEncoding("utf-8");
	%>
	<jsp:useBean id="user" class="com.allthelucky.memo.domain.User" scope="page">
		<jsp:setProperty name="user" property="username" param="name" />
		<jsp:setProperty name="user" property="depart" param="depart" />
		<jsp:setProperty name="user" property="age" param="age" />
	</jsp:useBean>

	学生姓名:<jsp:getProperty name="user" property="username" />
	<br /> 所在学院:<jsp:getProperty name="user" property="password" /><br />
	学生年龄:<jsp:getProperty name="user" property="age" />
	<br />
	${user.username}
	${user.password}
	${user.age}
</body>
</html>