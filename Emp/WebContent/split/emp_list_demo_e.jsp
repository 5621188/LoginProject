<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<head>
<%!
	public static final String URL = "emp_list_demo_e.jsp";
%>
<%!	// 将数据库的连接信息都定义为全局常量
	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver" ;
	public static final String DBURL = "jdbc:oracle:thin:@192.168.108.130:1521:orcl" ;
	public static final String USER = "scott" ;
	public static final String PASSWORD = "tiger" ;
%>
<link  rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" ></link>
<%	// 数据库的连接查询处理需要以下的变量
	Connection conn = null ;
	PreparedStatement pstmt = null ;
	ResultSet rs = null ;
	ResultSetMetaData rsMetaData = null ;
%>
<%
	request.setCharacterEncoding("UTF-8");
	int currentPage = 1; //当前所在的页面，默认是在第1页显示的
	int lineSize = 1; //表示每页显示的数据行数
	int allRecorders = 0;//保存总记录的统计结果
	int pageSize = 1;//保存总页数
	String columnData = "雇员编号:empno|雇员姓名:ename|雇员职位:job|";
	String column = request.getParameter("col");
	String keyWord = request.getParameter("kw");
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
	Map<String,String> colNames = new  HashMap<>();
	colNames.put("empno","雇员编号");
	colNames.put("ename","雇员姓名");
	colNames.put("job","雇员职位");
	colNames.put("hiredate","雇佣日期");
	colNames.put("sal","雇员工资");
	colNames.put("comm","雇员佣金");
%>
<%
	Class.forName(DRIVER) ;
	conn = DriverManager.getConnection(DBURL,USER,PASSWORD) ;
	String sql = null;
	if(column == null || keyWord == null || "".equals(column) || "".equals(keyWord)){
		sql = "SELECT COUNT(*) FROM emp";
		pstmt = conn.prepareStatement(sql) ;
	}else{
		sql = "SELECT COUNT(*) FROM emp WHERE "+column+" LIKE ?";
		pstmt = conn.prepareStatement(sql) ;
		pstmt.setString(1,"%"+keyWord+"%");
	}
	rs = pstmt.executeQuery();
	if(rs.next()){
		allRecorders = rs.getInt(1);
	}
	if(column == null || keyWord == null || "".equals(column) || "".equals(keyWord)){
		sql = "SELECT * FROM (SELECT empno,ename,job,hiredate,sal,comm,rownum rn FROM emp WHERE rownum <=?)temp WHERE temp.rn >?" ;
		pstmt = conn.prepareStatement(sql) ;
		pstmt.setInt(1,currentPage*lineSize);
		pstmt.setInt(2,(currentPage-1)*lineSize);
	}else{
		sql = "SELECT * FROM (SELECT empno,ename,job,hiredate,sal,comm,rownum rn FROM emp WHERE "+column+" LIKE ? AND rownum <=? )temp WHERE temp.rn >? ";
		pstmt = conn.prepareStatement(sql) ;
		pstmt.setString(1,"%"+keyWord+"%");
		pstmt.setInt(2,currentPage*lineSize);
		pstmt.setInt(3,(currentPage-1)*lineSize);
	}
	rsMetaData = pstmt.getMetaData();
	rs = pstmt.executeQuery() ;
%>
<%
	column = column==null ? "":column;
	keyWord = keyWord==null ? "":keyWord;
%>
<jsp:include page="split_plugin_search_bar.jsp">
	<jsp:param value="<%=URL%>" name="url"/>
	<jsp:param value="<%=allRecorders%>" name="allRecorders"/>
	<jsp:param value="<%=columnData%>" name="columnData"/>
	<jsp:param value="<%=keyWord%>" name="keyWord"/>
	<jsp:param value="<%=column%>" name="column"/>
</jsp:include>
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
		java.sql.Date hiredate = rs.getDate(4) ;
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
<jsp:include page="split_plugin_page_bar.jsp">
	<jsp:param value="<%=currentPage%>" name="currentPage"/>
	<jsp:param value="<%=lineSize%>" name="lineSize"/>
	<jsp:param value="<%=allRecorders%>" name="allRecorders"/>
	<jsp:param value="<%=column%>" name="column"/>
	<jsp:param value="<%=keyWord%>" name="keyWord"/>
	<jsp:param value="<%=URL%>" name="url"/>
</jsp:include>
