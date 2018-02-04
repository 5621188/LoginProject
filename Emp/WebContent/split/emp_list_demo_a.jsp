<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<head>
<link  rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" ></link>
<%!
	public static final String URL = "emp_list_demo_a.jsp";
%>
<%!	// 将数据库的连接信息都定义为全局常量
	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver" ;
	public static final String DBURL = "jdbc:oracle:thin:@192.168.108.130:1521:orcl" ;
	public static final String USER = "scott" ;
	public static final String PASSWORD = "tiger" ;
%>
<%	// 数据库的连接查询处理需要以下的变量
	Connection conn = null ;
	PreparedStatement pstmt = null ;
	ResultSet rs = null ;
%>
<%
	int currentPage = 1; //当前所在的页面，默认是在第1页显示的
	int lineSize = 5; //表示每页显示的数据行数
	int allRecorders = 0;//保存总记录的统计结果
	int pageSize = 1;
%>
</head>
<%//cp表示的是currentPage参数的内容，接收的是String,需要转换为int型，需要考虑如果传入参数格式不正确或者为null,
//所以加try...catch,但是catch语句块中不做任何处理
	try{
		currentPage = Integer.parseInt(request.getParameter("cp")); //cp表示currentPage的简写
	}catch(Exception e){}
	try{
		lineSize = Integer.parseInt(request.getParameter("ls")); //ls表示lineSize的简写
	}catch(Exception e){}
%>
<%
	Class.forName(DRIVER) ;
	conn = DriverManager.getConnection(DBURL,USER,PASSWORD) ;
	String sql = "SELECT COUNT(*) FROM emp";
	pstmt = conn.prepareStatement(sql) ;
	rs = pstmt.executeQuery();
	if(rs.next()){
		allRecorders = rs.getInt(1);
	}
	sql = "SELECT * FROM (SELECT empno,ename,job,hiredate,sal,comm,rownum rn FROM emp WHERE rownum <=?)temp WHERE temp.rn >?" ;
	pstmt = conn.prepareStatement(sql) ;
	pstmt.setInt(1,currentPage*lineSize);
	pstmt.setInt(2,(currentPage-1)*lineSize);
	rs = pstmt.executeQuery() ;
%>
<div id="infoContext">
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
</div>
<div>
<%
	if(allRecorders != 0){
		pageSize=(allRecorders+lineSize-1)/lineSize;
	}
%>
</div>
<div id="pageCtl">
	<ul class="pagination"> 
		<li class="<%=currentPage==1?"disabled":""%>">
		<%
			if(currentPage==1){
		%>
				<span>首页</span>
		<%
			}else{
		%>
			<a href="<%=URL%>?cp=1&ls=">首页</a>
		<% 
			}
		%>
		</li>
		<li class="<%=currentPage==1?"disabled":""%>">
		<%
			if(currentPage==1){
		%>
				<span>上一页</span>
		<%
			}else{
		%>
			<a href="<%=URL%>?cp=<%=currentPage-1%>&ls=">上一页</a>
		<% 
			}
		%>
		</li>
		<li class="<%=currentPage==pageSize?"disabled":""%>">
		<%
			if(currentPage==pageSize){
		%>
				<span>下一页</span>
		<%
			}else{
		%>
			<a href="<%=URL%>?cp=<%=currentPage+1%>&ls=">下一页</a>
		<% 
			}
		%>
		</li>
		<li class="<%=currentPage==pageSize?"disabled":""%>">
		<%
			if(currentPage==pageSize){
		%>
				<span>尾页</span>
		<%
			}else{
		%>
			<a href="<%=URL%>?cp=<%=pageSize%>&ls=">尾页</a>
		<% 
			}
		%>
		</li>
	</ul>
</div>