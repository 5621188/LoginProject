package test;

import java.util.HashMap;
import java.util.Map;


import com.googlecode.aviator.AviatorEvaluator;
import com.jayway.jsonpath.JsonPath;

import utils.CheckPointUtils;

public class TestAviator {
	
	public static void main(String[] args) {
		
		    Long result = (Long) AviatorEvaluator.execute("1+2+3+6");
	        System.out.println(result);
	      
	        Map<String, Object> env = new HashMap<String, Object>();
	        env.put("data", 2);
	        //env.put("data3", 2);
	        Boolean result2 = (Boolean) AviatorEvaluator.execute("data==1", env);
	        System.out.println(result2);
	        
	        String mString="{\"msg\":\"登录成功\",\"uid\":\"E2BBEDC09FAA4406B8D85C96DF6281CF\",\"code\":\"1\"}";
//	        env = new HashMap<String, Object>();
//	        env.put("$.code", JsonPath.read(mString, "$.code"));
//	        Boolean result3 = (Boolean) AviatorEvaluator.execute("$.code=1", env);
//	        System.out.println(result3);
	        
	     System.out.println(CheckPointUtils.checkbyJsonPath(mString, "$.code=1"));
	}

}
