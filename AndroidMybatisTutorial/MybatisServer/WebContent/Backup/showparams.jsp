<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>show getted data</title>
</head>
<body>
	you have just input:
	<hr />
	姓名：<%=request.getParameter("name")%><br /> 年龄：<%=request.getParameter("age")%><br />
	性别：<%=request.getParameter("gender")%><br /> 说明：<%=request.getParameter("note")%><br />
	<%
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String paraName = (String) e.nextElement();
			String paraValue = (String) request.getParameter(paraName);
			out.println("参数名称：" + paraName);
			out.println("参数内容：" + paraValue);
			out.print("<br/>");
		}
	%>

</body>
</html>