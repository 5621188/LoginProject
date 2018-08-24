package utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {
	
	public static Map<String, Object> covertStringToMp(String params, String regx) {
	    if(params==null)  return null;
		String [] params_array =params.split(regx);
		Map<String, Object> paramsmp = new HashMap<String, Object>();
		if(params!=null&&!"".equals(params)) {
		for (String header : params_array) {
			String[] keys = header.split("=");
			if(keys.length>1) {
				paramsmp.put(keys[0], keys[1]);
			}
		}
		}
		return paramsmp;
	}
	
	public static Map<String, Object> covertStringToMp(String params) {
		return covertStringToMp(params, ";");
	}
	
	public static void main(String[] args) {
		String teString="method=loginMobile&loginname=test1&loginpass=test1";
		Map<String, Object> mp1 = covertStringToMp(teString, "&");
		System.out.println(mp1);
		String test1="token=11;user=123;";
		Map<String, Object> mp2 = covertStringToMp(test1);
		System.out.println(mp2);
	}

}
