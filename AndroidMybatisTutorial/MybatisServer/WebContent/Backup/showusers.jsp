<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.allthelucky.memo.domain.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>javaBean access by jsp:tags</title>
</head>
<body>
	<jsp:useBean id="user" class="com.allthelucky.memo.domain.User" scope="page">
		<jsp:setProperty name="user" property="username" value="Savant Pan" />
		<jsp:setProperty name="user" property="password" value="12345" />
	</jsp:useBean>
	<h3>first, we have set value by jsp:setProperty tags in useBean</h3>
	<h2>then, we get value by jsp:getProperty tags</h2>
	username:<jsp:getProperty name="user" property="username" />
	<br /> password:<jsp:getProperty name="user" property="password" />
</body>
</html>