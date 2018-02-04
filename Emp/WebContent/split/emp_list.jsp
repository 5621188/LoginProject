<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%!	// 将数据库的连接信息都定义为全局常量
	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver" ;
	public static final String URL = "jdbc:oracle:thin:@192.168.108.130:1521:orcl" ;
	public static final String USER = "scott" ;
	public static final String PASSWORD = "tiger" ;
%>
<%	// 数据库的连接查询处理需要以下的变量
	Connection conn = null ;
	PreparedStatement pstmt = null ;
	ResultSet rs = null ;
%>
<%
	Class.forName(DRIVER) ;
	conn = DriverManager.getConnection(URL,USER,PASSWORD) ;
	String sql = "SELECT empno,ename,job,hiredate,sal,comm FROM emp" ;
	pstmt = conn.prepareStatement(sql) ;
	rs = pstmt.executeQuery() ;
%>
<table border="0" style="width:100%" cellpadding="1" cellspacing="1" bgcolor="#F5F5F5">
	<thead>
		<tr bgcolor="#FFFFFF">
			<td>雇员编号</td>
			<td>姓名</td>
			<td>职位</td>
			<td>雇佣日期</td>
			<td>基本工资</td>
			<td>佣金</td>
		</tr>
	</thead>
	<tbody>
<%
	while (rs.next()) {
		int empno = rs.getInt(1) ;
		String ename = rs.getString(2) ;
		String job = rs.getString(3) ;
		Date hiredate = rs.getDate(4) ;
		double sal = rs.getDouble(5) ;
		double comm = rs.getDouble(6) ;
%>
		<tr bgcolor="#FFFFFF">
			<td><%=empno%></td>
			<td><%=ename%></td>
			<td><%=job%></td>
			<td><%=hiredate%></td>
			<td><%=sal%></td>
			<td><%=comm%></td>
		</tr>
<%
	}
%>		
	</tbody>
</table>
<%
	conn.close() ;
%>