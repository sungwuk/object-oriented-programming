<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import = "java.sql.*" contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.*" %>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>
<%
  String user = request.getParameter("user");
  String score = request.getParameter("score");


 String DB_URL = "jdbc:mysql://db12.ctrmjrtgka38.ap-northeast-2.rds.amazonaws.com:3306/db12";
 String DB_USER="youngju";
 String DB_PASSWORD="201202163";

 String query = "";
 Connection conn = null;
 Statement stmt = null;
  ResultSet rs = null;
	String preUser = "";

  try {
	Class.forName("com.mysql.jdbc.Driver");
	conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	stmt = conn.createStatement();

	for( int i = 9; i < 17; i++) {
		out.println(i);
		query="SELECT seat.time" + i + " FROM seat WHERE seat.time" + (i+1) + " = " + user + " ; ";
		rs = stmt.executeQuery(query);

 		if ( rs.getString("time"+i).isEmpty() ) {
			preUser = rs.getString("time"+i);
			out.println(preUser);
		}
   	}
	conn.close();
	stmt.close();
 } catch(Exception e) {
	out.println(e.getMessage());
 }

%>