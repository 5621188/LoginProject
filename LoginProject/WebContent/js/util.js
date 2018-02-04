/**
 * 验证数据是否为空
 * @param {Object} eleId 要操作元素的id名称
 */
function validateEmpty(eleId){
	var obj = ele(eleId);//取得指定的对象
	if(obj != null){
		if(obj.value == ""){//数据为空
			setFailure(obj);
			return false;
		}else{
			setSuccess(obj);
			return true;
		}
	}
}
/**
 * 使用正则进行数据的验证
 * @param {Object} eleId 组件的元素ID
 * @param {Object} regex 正则验证规则
 */
function validateRegex(eleId,regex){
	var obj = ele(eleId);
	if(validateEmpty(eleId)){
		if(!regex.test(obj.value)){
			setFailure(obj);
			return false;
		}else{
			setSuccess(obj);
			return true;
		}
	}
	setFailure(obj);
	return false;
}
/**
 * 验证输入的内容是否是数字
 * @param {Object} eleId
 */
function validateNumber(eleId){
	return validateRegex(eleId,/^\d+(\.\d+)?$/);
}
/**
 * 根据id取得一个具体的对象，是简化调用的难度
 * @param {Object} eleId
 */
function ele(eleId){
	return document.getElementById(eleId);
}
/**
 * 设置错误时的显示信息
 * @param {Object} obj 要进行错误样式设置的元素
 * @param {Object} className 样式名称
 * @param {Object} text 文本信息
 */
function setValidateStyle(obj,className,text){
//	console.log(obj);
	var objSpan = ele(obj.id+"Span");//根据对象的id属性找到Span
	obj.className=className;
//	console.log(objSpan);
	if(objSpan != null){
		objSpan.innerHTML=text;
	}
	
}
function setSuccess(obj){
	setValidateStyle(obj,"success","<font color='green'>√</font>");
}
function setFailure(obj){
	setValidateStyle(obj,"failure","<font color='red'>×</font>");
}
function blind(obj,eventType,func){
	obj.addEventListener(eventType,func,false);
}
function round(num,scale){
	return Math.round(num*Math.pow(10,scale))/Math.pow(10,scale);
}
function listener(obj,eventType,func){
	obj.addEventListener(eventType,func,false);
}
function formStop(e) {
	if (e && e.preventDefault) {	// 现在是在W3C标准下执行
		e.preventDefault() ;	// 阻止浏览器的动作
	} else {	// 专门针对于IE浏览器的处理
		window.event.returnValue = false ;
	}
}
