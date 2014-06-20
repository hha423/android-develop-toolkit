<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>进入主页 －－JSP页面。</title>
</head>
<body>
	当前时间：
	<%
	out.println(new Date().toString());
%>
	<a href="setheader.jsp">set header</a>
	<br />
	<a href="showparams.html">get para</a>
	<br />
	<a href="showparamself.jsp">show it self</a>
	<br />
	<a href="student.html">student.html</a>
	<br />
	<a href="showuser.jsp">showuser.jsp</a>
	<br />
	<a href="showusers.jsp">showusers.jsp</a>
	<br />
	<br /> 在from的响应页的useBean之前加上:
	<br /> request.setCharacterEncoding("utf-8");
	<br />中文才不会乱码。
</body>
</html>