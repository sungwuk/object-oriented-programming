<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import = "java.sql.*" contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.*" %>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>
<%
  String user = request.getParameter("user");
  String tableNum = request.getParameter("tableNum");
  String startHour = request.getParameter("startHour");
  String endHour = request.getParameter("endHour");

 String DB_URL = "jdbc:mysql://db12.ctrmjrtgka38.ap-northeast-2.rds.amazonaws.com:3306/db12";
 String DB_USER="youngju";
 String DB_PASSWORD="201202163";

 String query = "";
 Connection conn = null;
 Statement stmt = null;

  try {
	int start = Integer.parseInt(startHour);
	int end = Integer.parseInt(endHour);

	for( int i = start; i < end; i++) {
		String hour = Integer.toString(i);
		query="UPDATE seat SET time" + hour + " = '" + user + "' WHERE seatId = '" + tableNum + "' ; ";
 
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		stmt = conn.createStatement();
		stmt.executeUpdate(query); 
   	}
	conn.close();
	stmt.close();
 } catch(Exception e) {
	out.println(e.getMessage());
 }

%>