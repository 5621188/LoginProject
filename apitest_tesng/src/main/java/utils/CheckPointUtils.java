package utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;

public class CheckPointUtils {
	
	public static boolean checkbyJsonPath(String json, String params) {
		   if(params==null||"".equals(params)) {
			   //检查参数为空,不需要检查，返回结果true
			   return true;
		   }
		   System.out.println("json "+json);
		   System.out.println("check "+params);
		   String[] checks =params.split("\\|\\||&&");
		   Map<String, Object> env = new LinkedHashMap<String, Object>();
		   int count=0;
		   for (String check : checks) {
			   String[] values =  check.split(">|<|>=|<=|=|==");
			   Object value = JsonPath.read(json, values[0]);
			   String newcheck = check;
			    // 字符串特殊处理
			   if(value instanceof String) {
					 newcheck = check.replace(values[values.length-1], covertToAviatorString(values[values.length-1]));
				}
			   // = 替换成 ==
			   if(newcheck.contains("=")&&!newcheck.contains(">=")&&!newcheck.contains("<=")&&!newcheck.contains("==")) {
				   newcheck = newcheck.replace("=", "==");
				}
			   //替换值
			   params = params.replace(check, newcheck);
			   
			    //替换$. 成data
			   String mapkey = "data"+count++;
			   env.put(mapkey, value);
			    //防止替换多个问题 
			   String regex =makeQueryStringAllRegExp(values[0]); 
			   params = params.replaceFirst(regex, mapkey);
			   System.out.println("中间转换----"+params +" map " +env);
		   }
		       System.out.println("最终转换----"+params +" map " +env);
			   Boolean result = (Boolean) AviatorEvaluator.execute(params, env);
			   System.out.println("比较结果 "+result);
			   return result;
	}
	
//	public static boolean checkbyJsonPath(String json, String params) {
//		if (params != null && !"".equals(params)) {
//			    //提取表达式 给jsonpath 提取数据使用
//				String[] values = params.split("=|>|<|>=|<=|==");
//				Object value = JsonPath.read(json, values[0]);
//				//提取数据如果是特殊类型
//				if(value instanceof String) {
//					System.out.println("----------"+ values[1]);
//					params = params.replace(values[values.length-1], covertToAviatorString(values[values.length-1]));
//				}
//				
//				if(params.contains("=")&&!params.contains(">=")&&!params.contains("<=")&&!params.contains("==")) {
//					params = params.replace("=", "==");
//				}
//				
//				//替换$. 成data
//				params = params.replace(values[0], "data");
//				//构造aviator参数
//				Map<String, Object> env = new HashMap<String, Object>();
//				env.put("data", value);
//				System.out.println(" totest " + params + " value " + value);
//				Boolean result = (Boolean) AviatorEvaluator.execute(params, env);
//				return result;
//		}
//		return true;
//	}
	
	
	
	private static String makeQueryStringAllRegExp(String str) {
	        if(StringUtils.isBlank(str)){
	            return str;
	        }
	        return str.replace("\\", "\\\\").replace("*", "\\*")
	                .replace("+", "\\+").replace("|", "\\|")
	                .replace("{", "\\{").replace("}", "\\}")
	                .replace("(", "\\(").replace(")", "\\)")
	                .replace("^", "\\^").replace("$", "\\$")
	                .replace("[", "\\[").replace("]", "\\]")
	                .replace("?", "\\?").replace(",", "\\,")
	                .replace(".", "\\.").replace("&", "\\&");
	    }
	
	
	
	
	
	/**
	 * 字符串特殊处理
	 * @param value
	 * @return
	 */
	private static String covertToAviatorString(String value) {
		return "'"+value+"'";
	}
	
	public static void main(String[] args) {
		String test ="{\"msg\":\"登录成功\",\"uid\":\"DAD3483647A94DBDB174C4C036CA8A80\",\"code\":\"1\",\"code2\":\"2\",\"code3\":\"3\"}";
		String expression2="$.code=1";
		checkbyJsonPath(test,expression2);
		    
		//复杂检查点问题
		String expression="$.code=1&&$.code2>=4||$.code3>2";
	    checkbyJsonPath(test,expression);
	}

}
