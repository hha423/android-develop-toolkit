<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*" %>
<%request.setCharacterEncoding("utf-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试主页 －－JSP页面。</title>
</head>
<body>
当前时间：
<% 
	out.println(new Date().toString());
%>
<br/>
表单测试：
<form method="post" action="showparamself.jsp">
姓名：<input type="text" name="name"><br/>
性别：<input type="text" name="gender"><br/>
年龄：<input type="text" name="age"><br/>
<input type="submit" value="提交">
<input type="reset" value="清空"><br/>
</form>
<br/>
<%
	Enumeration<String> e = request.getParameterNames();
	while (e.hasMoreElements()) {
		String paraName = (String)e.nextElement();
		String paraValue = (String)request.getParameter(paraName);
		out.println(paraName);
		out.println(":");
		out.println(paraValue);
		out.print("<br/>");
	}
%>
</body>
</html>
