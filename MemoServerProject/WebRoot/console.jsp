<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.allthelucky.memo.utils.Utils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Request Console</title>
<style type="text/css">
body{
	font-size:14px;
}
h1{font-size:18px;}
#main {
	background:#eeeeee;
	margin-left: auto;
	margin-right: auto;
	width: 500px;
	margin-left: auto;
}
</style>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	response.setHeader("Content-type", "text/html;charset=UTF-8");
	response.setCharacterEncoding("UTF-8");
	final String url=request.getParameter("url");
	final String postData = request.getParameter("data");

	String result="";
	if(url==null|| url.equals("")|| !url.startsWith("http")) {
		result = "error url";
	} else {
		result = Utils.postData(url, postData);
	}
%>
	<div id="main">
		<form method="post" action="./console.jsp">
			<table width="500px" >
				<tr> <td colspan="2" align="center"><h1>Request Console for test!</h1></td></tr>
				<tr>
					<td width="120px">请求地址</td>
					<td width="380px"><input type="text" name="url"   style="width:360px;height:24px" value="<%if(url!=null&&!url.equals("")) {out.print(url);}%>"></td>
				</tr>
				<tr>
					<td width="120px">提交数据</td>
					<td width="380px"><textarea style="width:360px;height:160px" name="data"><%if(postData!=null&&!postData.equals("")){out.print(postData);}%></textarea></td>
				</tr>
				<tr>
					<td width="140px"></td>
					<td width="380px"><input type="submit" style="width:120px;height:24px" value="开始请求">
				</tr>
			</table>
		</form>
		</div>
		<br/>
	<div id="main">
		<table  width="500px">
			<tr>
				<td width="120px">响应结果</td>
				<td width="380px"><textarea style="width:360px;height:100px"><%if(result!=null&&!result.equals("")){out.print(result);}%></textarea></td>
			</tr>
		</table>
	</div>
</body>
</html>
