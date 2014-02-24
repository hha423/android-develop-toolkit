<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.allthelucky.memo.domain.User"%>
<%
	User user = new User();
	user.setId(1);
	user.setUsername("admin");
	user.setPassword("123456");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>javaBean access by JSP statments</title>
</head>
<body>
	<h3>first, we have set value by JSP statments</h3>
	<h2>then, we get value by JSP statments</h2>
	Bean's username value:
	<%=user.getUsername()%><br />
	<%=user.getPassword()%>
</body>
</html>