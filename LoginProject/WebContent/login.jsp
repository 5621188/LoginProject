<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录程序</title>
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
	<script type="text/javascript" src="js/util.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
</head>
<%
	String error = request.getParameter("error");
%>
<h2><%= error==null?"":"登录失败，错误的用户名或密码"%></h2>
<body>
	<form action="check.jsp" method="post" id="loginForm">
		<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" id="mid" name="mid" class="init"/></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="password" id="pwd" name="pwd" class="init"/></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="登录" id="subButton" name="subButton"/>
					<input type="reset" value="重置" id="resButton" name="resButton"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>