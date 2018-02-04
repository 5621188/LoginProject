window.onload=function(){
	listener(ele("mid"),"blur",validateMid);
	listener(ele("pwd"),"blur",validatePassword);
	listener(ele("loginForm"),"submit",function(){
		if(validateForm()){
			this.submit();
		}else{
			formStop(e);
		}
	});
}
function validateForm(){
	console.log("********validateForm**********");
	return validateMid() && validatePassword();
}
function validateMid(){
	return validateEmpty("mid");
}
function validatePassword(){
	return validateEmpty("pwd");
}