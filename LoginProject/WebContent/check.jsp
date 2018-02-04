<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>>
<html>
<head>
<title>登录程序</title>
</head>
<body>
<%!
	public static final String DRIVER="oracle.jdbc.driver.OracleDriver";
	public static final String URL="jdbc:oracle:thin:@192.168.108.130:1521:orcl";
	public static final String NAME="scott";
	public static final String PASSWORD="tiger";

%>>
<%
	boolean flag = false;
	Class.forName(DRIVER);
	Connection conn = DriverManager.getConnection(URL,NAME,PASSWORD);
	String sql = "select mid from member where mid =? and password = ?";
	PreparedStatement pstmt = conn.prepareStatement(sql);
	pstmt.setString(1,request.getParameter("mid"));
	pstmt.setString(2,request.getParameter("pwd"));
	ResultSet rs = pstmt.executeQuery();
	if(rs.next()){//表示登录的用户名是正确的
		flag = true;
	}
	conn.close();//必须关闭连接
%>
<%
	if(flag){//登录成功，跳转到welcome.jsp
%>
	<jsp:forward page="welcome.jsp"/>
<% 
	}else{
%>
	<jsp:forward page="login.jsp">
		<jsp:param name="error" value="loginError"/>
	</jsp:forward>
<% 		
	}
%>
</body>
</html>