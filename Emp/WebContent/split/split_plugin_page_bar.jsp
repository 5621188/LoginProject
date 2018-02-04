<%@page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String url = request.getParameter("url");
	int currentPage = 1;
	int lineSize = 1;
	int allRecorders = 0;
	int pageSize = 1;
	String column = request.getParameter("column");
	String keyWord = request.getParameter("keyWord");
%>
<%
	try{
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}catch(Exception e){}
	try{
		lineSize = Integer.parseInt(request.getParameter("lineSize"));
	}catch(Exception e){}
	try{
		allRecorders = Integer.parseInt(request.getParameter("allRecorders"));
	}catch(Exception e){}
	if("null".equals(column) || column == null || "".equals(column)){
		column = "";
	}
	if("null".equals(keyWord) || keyWord == null || "".equals(keyWord)){
		keyWord = "";
	}
%>
<%
	int seed = 3;//定义一个种子数，用于判断是否会有“...”
	if(allRecorders != 0){
		pageSize=(allRecorders+lineSize-1)/lineSize;
	}
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
			<a href="<%=url%>?cp=<%=1%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>">1</a>		
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
				<li class="<%=currentPage==i?"active":""%>"><a href="<%=url%>?cp=<%=i%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>"><%=i%></a></li>
<%		
			    }
				if((currentPage + seed) <= pageSize){
%>
				<li class="disabled"><span>...</span></li>
<%
				}
			}else{//需要分两段显示
				int startPage = currentPage - seed + 1;
				int endPage = currentPage + seed>=pageSize?pageSize:currentPage + seed;
				if(startPage>2){
%>
					<li  class="disabled"><span>...</span></li>
<% 
				}
				for(int i=startPage;i<endPage;i++){
%>
				<li class="<%=currentPage==i?"active":""%>"><a href="<%=url%>?cp=<%=i%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>"><%=i%></a></li>
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
				<li class="<%=currentPage==i?"active":""%>"><a href="<%=url%>?cp=<%=i%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>"><%=i%></a></li>
<%		
			}
		}
%>
		<li class="<%=currentPage==pageSize?"active":""%>"><a href="<%=url%>?cp=<%=pageSize%>&ls=<%=lineSize%>&col=<%=column%>&kw=<%=keyWord%>"><%=pageSize%></a></li>
<%
	}
%>
	</ul>
</div>