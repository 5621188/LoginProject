<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<head>
<%!
	public static final String URL = "emp_list_demo_c.jsp";
%>
<%!	// 将数据库的连接信息都定义为全局常量
	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver" ;
	public static final String DBURL = "jdbc:oracle:thin:@192.168.108.130:1521:orcl" ;
	public static final String USER = "scott" ;
	public static final String PASSWORD = "tiger" ;
%>
<script type="text/javascript">
	window.onload = function(){
		document.getElementById("goBtn").addEventListener("click",function(){
			window.location = "<%=URL%>?cp="+document.getElementById("inputcp").value;
		},false);
	}
</script>
<link  rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" ></link>
<%	// 数据库的连接查询处理需要以下的变量
	Connection conn = null ;
	PreparedStatement pstmt = null ;
	ResultSet rs = null ;
	ResultSetMetaData rsMetaData = null ;
%>
<%
	int currentPage = 1; //当前所在的页面，默认是在第1页显示的
	int lineSize = 2; //表示每页显示的数据行数
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
	System.out.println(column+"------------"+keyWord);
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
			<a href="<%=URL%>?cp=1&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>">首页</a>
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
			<a href="<%=URL%>?cp=<%=currentPage-1%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>">上一页</a>
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
			<a href="<%=URL%>?cp=<%=currentPage+1%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>">下一页</a>
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
			<a href="<%=URL%>?cp=<%=pageSize%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>">尾页</a>
		<% 
			}
		%>
		</li>
		<li>
			<span>共<%=allRecorders%>条记录</span>
		</li>
		<li>
			<span>
				<input type="text" name="inputcp" id="inputcp" maxlength="5" size="5">
				<input type="button" id="goBtn" value="跳转" >
			</span>
		</li>
	</ul>
</div>