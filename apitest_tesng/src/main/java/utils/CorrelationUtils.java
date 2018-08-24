package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.TestCase;

public class CorrelationUtils {
	
	//${id}  \\$\\{(.+?)\\}
	private static Pattern replaceParamPattern = Pattern.compile("\\$\\{(.+?)\\}");
	
	public static void check(TestCase bean){
		    Matcher matcher = replaceParamPattern.matcher(bean.getUrl());
	        while (matcher.find()) {
	        	String value = SaveParamsUtils.get(matcher.group(1)).toString();
	        	//替換
	        	String newUrl = bean.getUrl().replace(matcher.group(), value);
	        	bean.setUrl(newUrl);
	        }
	        
	        if(bean.getHeader()!=null) {
	        	matcher = replaceParamPattern.matcher(bean.getHeader());
	 	        while (matcher.find()) {
	 	        	String value = SaveParamsUtils.get(matcher.group(1)).toString();
	 	        	//替換
	 	        	String header = bean.getHeader().replace(matcher.group(), value);
	 	        	bean.setHeader(header);
	 	        }
	        }
	        
	        if(bean.getParams()!=null) {
	        	matcher = replaceParamPattern.matcher(bean.getParams());
	 	        while (matcher.find()) {
	 	        	String value = SaveParamsUtils.get(matcher.group(1)).toString();
	 	        	//替換
	 	        	String params = bean.getParams().replace(matcher.group(), value);
	 	        	bean.setParams(params);
	 	        }
	        }
	}
	
	public static void main(String[] args) {
		  String mString="{\"msg\":\"登录成功\",\"uid\":\"E2BBEDC09FAA4406B8D85C96DF6281CF\",\"code\":\"1\"}";
		  String save="id=$.uid;mymsg=$.msg";
		  SaveParamsUtils.saveMap(mString, save);
		  
		  TestCase testCase = new TestCase();
		  testCase.setUrl("http://www.baidu.com?pid=${id}&tid=${mymsg}");
		  CorrelationUtils.check(testCase);
		  
		  System.out.println(testCase);
		
	}
}
