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
    String seatId = "";
    String time9 = "";
    String time10 = "";
    String time11 = "";
    String time12 = "";
    String time13 = "";
    String time14 = "";
    String time15 = "";
    String time16 = "";
    String time17 = "";

    query = "SELECT seat.seatId, seat.time9, seat.time10, seat.time11, seat.time12, seat.time13, seat.time14, seat.time15, seat.time16, seat.time17 FROM seat ; ";

    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    stmt = conn.createStatement();
    rs = stmt.executeQuery(query);

    JSONObject jsonMain = new JSONObject();
    JSONArray jArray = new JSONArray();
    int i = 0;

    while(rs.next()) {
      JSONObject jObject = new JSONObject();

      seatId = rs.getString("seatId");
      time9 = rs.getString("time9");
      time10 = rs.getString("time10");
      time11 = rs.getString("time11");
      time12 = rs.getString("time12");
      time13 = rs.getString("time13");
      time14 = rs.getString("time14");
      time15 = rs.getString("time15");
      time16 = rs.getString("time16");
      time17 = rs.getString("time17");

      jObject.put("seatId", seatId);
      jObject.put("time9", time9);
      jObject.put("time10", time10);
      jObject.put("time11", time11);
      jObject.put("time12", time12);
      jObject.put("time13", time13);
      jObject.put("time14", time14);
      jObject.put("time15", time15);
      jObject.put("time16", time16);
      jObject.put("time17", time17);

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
