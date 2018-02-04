<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<head>
<%!
	public static final String URL = "emp_list_demo_d.jsp";
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
	int currentPage = 1; //当前所在的页面，默认是在第1页显示的
	int lineSize = 3; //表示每页显示的数据行数
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
	keyWord = keyWord==null ? "":keyWord;
%>
<%
	column = column==null ? "":column;
	keyWord = keyWord==null ? "":keyWord;
%>
<div id="searchDiv">
	<form action="<%=URL%>" method="post">
		<select id="col" name="col">
<%
	//if(rs.next()){
	//	for(int i=0;i<rsMetaData.getColumnCount()-1;i++){
	//		String col = rsMetaData.getColumnName(i+1).toLowerCase();
	//		String columnName = colNames.get(col);
	String[] result = columnData.split("\\|");
	System.out.print(Arrays.toString(result));
	for(int x=0;x<result.length;x++){
		String temp[] = result[x].split(":");
%>
		<option value="<%=temp[1]%>" <%=temp[1].equals(column)?"selected":""%>><%=temp[0]%></option>
<%
//}
	}
%>	
		</select>
		<input type="text" name="kw" id="kw" value="<%=keyWord%>"/>
		<input type="submit" value="查询" />
	</form>
</div>
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
<div>
<%
	if(allRecorders != 0){
		pageSize=(allRecorders+lineSize-1)/lineSize;
	}
%>
</div>
<div id="testDiv">
	<p>column = <%=column %></p>
	<p>keyWord = <%=keyWord %></p>
	<p>总记录数：<%=allRecorders%></p>
	<p>总页数：<%=pageSize %></p>
</div>
<%
	int seed = 2;//定义一个种子数，用于判断是否会有“.”
%>
<div id="pageCtl">
	<ul class="pagination"> 
<%
	if(pageSize==1){
%>
	<li class="<%=currentPage==1?"active":""%>">
		<a>1</a>
	</li>
<%
	}
	else{
%>
		<li class="<%=currentPage==1?"active":""%>">
<%
			if(currentPage==1){
%>
				<span>1</span>
<%
			}else{
%>
			<a href="<%=URL%>?cp=<%=1%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>">1</a>		
<%				
			}
%>
		</li>
<%
		if(pageSize > seed*2){//数据量很大，需要省略号的出现
			if(currentPage <= seed){
				int startPage = 2;
				int endPage = currentPage + seed;
				for(int i=startPage;i<endPage;i++){
%>
				<li class="<%=currentPage==i?"active":""%>"><a href="<%=URL%>?cp=<%=i%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>"><%=i%></a></li>
<%		
			    }
				if((currentPage + seed) <= pageSize){
%>
				<li class="disabled"><span>...</span></li>
<%
				}
			}else{//需要分两段显示
%>
				<li  class="disabled"><span>...</span></li>
<%
				int startPage = currentPage - seed + 1;
				int endPage = currentPage + seed>=pageSize?pageSize:currentPage + seed;
				for(int i=startPage;i<endPage;i++){
%>
				<li class="<%=currentPage==i?"active":""%>"><a href="<%=URL%>?cp=<%=i%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>"><%=i%></a></li>
<%		
			    }
				if((currentPage + seed) < pageSize){
%>
					<li class="disabled"><span>...</span></li>
<%
				}
			}
		}else{
			for(int i=2;i<pageSize;i++){
%>
				<li class="<%=currentPage==i?"active":""%>"><a href="<%=URL%>?cp=<%=i%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>"><%=i%></a></li>
<%		
			}
		}
%>
		<li class="<%=currentPage==pageSize?"active":""%>"><a href="<%=URL%>?cp=<%=pageSize%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>"><%=pageSize%></a></li>
<%
	}
%>
	</ul>
</div>