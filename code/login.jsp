<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import = "java.sql.*" contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.*" %>
<% request.setCharacterEncoding("utf-8"); %>
<%@ page import="java.net.URLDecoder"%>
<% response.setContentType("text/html; charset=utf-8"); %>
<%
 
 String DB_URL = "jdbc:mysql://db12.ctrmjrtgka38.ap-northeast-2.rds.amazonaws.com:3306/db12";
 String DB_USER="youngju";
 String DB_PASSWORD="201202163";

 String query = "";
 Connection conn = null;
 Statement stmt = null;
 ResultSet rs = null;
	
 try {
    String studentId = "";
    String password = "";

    query = "SELECT student.studentId, student.password FROM student ; ";

    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    stmt = conn.createStatement();
    rs = stmt.executeQuery(query);

    JSONObject jsonMain = new JSONObject();
    JSONArray jArray = new JSONArray();
    int i = 0;

    while(rs.next()) {
      JSONObject jObject = new JSONObject();

      studentId = rs.getString("studentId");
      password = rs.getString("password");

      jObject.put("studentId", studentId);
      jObject.put("password", password);

      jArray.add(i, jObject);
      i = i+1;
    }

    jsonMain.put("List", jArray);

	out.println(jsonMain.toJSONString());

    rs.close();
    conn.close();
    stmt.close();
 } catch(Exception e) {
 	out.println(e.getMessage());
 }
%>
