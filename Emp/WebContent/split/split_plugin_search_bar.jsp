<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%---
	<jsp:include page="split_plugin_search_bar.jsp">
	<jsp:param value="<%=URL%>" name="url"/>
	<jsp:param value="<%=allRecorders%>" name="allRecorders"/>
	<jsp:param value="<%=columnData%>" name="columnData"/>
	<jsp:param value="<%=keyWord%>" name="keyWord"/>
	<jsp:param value="<%=column%>" name="column"/>
	</jsp:include>
 --%>
<%
	request.setCharacterEncoding("UTF-8");
	String url = request.getParameter("url");//提交路径
	String columnData = request.getParameter("columnData");//查询列
	String keyWord = request.getParameter("keyWord");
	String column = request.getParameter("column");
	int allRecorders = 0;
	if("null".equals(column) || column == null || "".equals(column)){
		column = "";
	}
	if("null".equals(keyWord) || keyWord == null || "".equals(keyWord)){
		keyWord = "";
	}
%>
<%
	try{
		allRecorders = Integer.parseInt(request.getParameter("allRecorders"));
	}catch(Exception e){
		
	}
%>
	
<div id="searchDiv">
	<form action="<%=url%>" method="post">
<%
	if(!(columnData == null || "".equals(columnData))){
%>	
		<select id="col" name="col">
<%
	String[] result = columnData.split("\\|");
	System.out.print(Arrays.toString(result));
	for(int x=0;x<result.length;x++){
		String temp[] = result[x].split(":");
%>
		<option value="<%=temp[1]%>" <%=temp[1].equals(column)?"selected":""%>><%=temp[0]%></option>
<%

	}
%>	
		</select>
<%
	}
%>
		<input type="text" name="kw" id="kw" value="<%=keyWord%>"/>
		<input type="submit" value="查询" />
		<p>共<%=allRecorders%>条记录</p>
	</form>
</div>