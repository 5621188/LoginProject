window.onload = function() {
	listener("deptno","blur",validateDeptno) ;
	listener("dname","blur",validateDname) ;
	listener("loc","blur",validateLoc) ;
	listener("deptForm","submit",function(e){
		if (validateDeptno() & validateDname() & validateLoc()) {
			this.submit() ;
		} else {
			e.preventDefault() ;
		}
	}) ;
}
function validateDeptno() {
	return validateRegex("deptno",/\d{2}/) ;
}
function validateDname() {
	return validateEmpty("dname") ;
}
function validateLoc() {
	return validateEmpty("loc") ;
}