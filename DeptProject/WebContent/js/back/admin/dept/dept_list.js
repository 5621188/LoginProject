window.onload = function(){
	listener("selectall","click",function(){
		checkboxSelectAll("selectall","deptno");
	});
	listener("deleteBtn","click",function(){
		handleDelete("deptno",jsdeleteUrl);
	});
}