<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.allthelucky.memo.utils.SqlUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index Page</title>
</head>
<body>
	<%
		final String type = request.getParameter("type");
		if ("mysql".equals(type)) {
			final String infoStr = java.lang.System.getenv("VCAP_SERVICES");
			out.println(infoStr);
		} else if ("init".equals(type)) {
			SqlUtils.executeUpdate(
					"create table if not exists user(id integer primary key auto_increment, username varchar(20) not null, password varchar(32) not null, level integer)")
					.toString();
			SqlUtils.executeUpdate(
					"create table if not exists memo(id integer primary key auto_increment, userid integer not null, title varchar(30) not null, content text not null , tag varchar(120) , createt varchar(16), updatet varchar(13))")
					.toString();
			SqlUtils.executeUpdate(
					"create table if not exists tag(id integer primary key auto_increment, name varchar(20) not null)")
					.toString();
			SqlUtils.executeUpdate(
					"create table if not exists sms(id integer primary key not null auto_increment, mobile varchar(11), code varchar(6), createtime varchar(13))")
					.toString();
			SqlUtils.executeUpdate(
					"create table if not exists sugest(id integer primary key not null auto_increment, userid integer, content text, createtime varchar(13))")
					.toString();
			SqlUtils.executeUpdate(
					"create table if not exists reply(id integer primary key not null auto_increment, userid integer, sugestid integer, content text, createtime varchar(13))")
					.toString();
			SqlUtils.executeUpdate(
					"CREATE TABLE IF NOT EXISTS log(id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, userid INTEGER, createtime VARCHAR(13))")
					.toString();

			out.println("Table init");
		} else if ("clear".equals(type)) {
			SqlUtils.executeUpdate("drop table if exists user").toString();
			SqlUtils.executeUpdate("drop table if exists tag").toString();
			SqlUtils.executeUpdate("drop table if exists memo").toString();
			SqlUtils.executeUpdate("drop table if exists sms").toString();
			SqlUtils.executeUpdate("drop table if exists sugest").toString();
			SqlUtils.executeUpdate("drop table if exists reply").toString();
			SqlUtils.executeUpdate("drop table if exists log").toString();
			out.println("Table clear");
		} else {
			out.println("Hello, Everybody!");
		}
	%>
</body>
</html>