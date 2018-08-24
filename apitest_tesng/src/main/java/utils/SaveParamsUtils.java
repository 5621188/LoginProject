package utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jayway.jsonpath.JsonPath;

public class SaveParamsUtils {
	
	//public static Map<String, Object> saveMap = new LinkedHashMap<String, Object>();
	
	
	  private static ThreadLocal<Map<String, Object>> saveMap = new ThreadLocal<Map<String, Object>>() {  
	        public Map<String, Object> initialValue() {  
	        	return new LinkedHashMap<String,Object>();
	        }  
	    }; 
	
	   //基于线程的map,保证并发时候数据隔离
//		  private static ThreadLocal<Map<String,Object>> saveMap = new ThreadLocal<Map<String,Object>>() {  
//		        public Map<String,Object> initialValue() {  
//		            return new LinkedHashMap<String,Object>();  
//		        }  
//		    };  
	
	
//	public static void saveMap(String json, String save){
//		Map<String,Object> map = MapUtils.covertStringToMp(save);
//		if(map!=null&&!map.isEmpty()){
//		for (Entry<String, Object> entry : map.entrySet()) {
//			System.out.println(entry.getKey() + " ---  "+entry.getValue().toString());
//			String key = entry.getKey();
//			saveMap.put(key, JsonPath.read(json, entry.getValue().toString()));
//		  }
//		}
//	}
	
	public static void saveMap(String json, String save){
		Map<String,Object> map = MapUtils.covertStringToMp(save);
		if(map!=null&&!map.isEmpty()){
		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			//多关联
			if(key.endsWith("*")) {
				String[] arrayKey = key.split("_");
				String before =arrayKey[0];
				List<Object> values =JsonPath.read(json, entry.getValue().toString());
				int i=1;
				for (Object value : values) {
					saveMap.get().put(before+"_"+(i++), value);
				}
				
			}else {
				//单个
				try {
					saveMap.get().put(key, JsonPath.read(json, entry.getValue().toString()));
				} catch (Exception e) {
				   	
				}
			}
		  }
		System.out.println(Thread.currentThread().getName()+" saveMap---  "+ saveMap.get());
		}
	}
	
	/**
	 * 反射对象属性放到map
	 * @param object
	 */
	public static void addBeanToMp(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			String name = f.getName();
			Object fvalue;
			try {
				fvalue = f.get(object);
				if(fvalue!=null) {
					saveMap.get().put(name, fvalue);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+" addBeanToMp---  "+ saveMap.get());
	}
	
	public static void addtoMp(String key, String value) {
		saveMap.get().put(key, value);
	}
	
	public static Object get(String key) {
		// 下划线开头变量代表函数
		if(key.startsWith("_")) {
			return FunctionUtils.getFuncion(key);
		}
		//非下划线的代表变量
		if(saveMap.get().get(key)==null) {
			return "";
		}
		return saveMap.get().get(key);
	}
	
	public static void clear() {
		saveMap.get().clear();
	}
	
	public static void main(String[] args) {
//		  String mString="{\"msg\":\"登录成功\",\"uid\":\"E2BBEDC09FAA4406B8D85C96DF6281CF\",\"code\":\"1\"}";
//		  String save="id=$.uid;mymsg=$.msg";
//		  saveMap(mString, save);
		  String json3="{\"code\":\"1\",\"data\":[{\"name\":\"testfan0\",\"pwd\":\"pwd0\"},{\"name\":\"testfan1\",\"pwd\":\"pwd1\"},{\"name\":\"testfan2\",\"pwd\":\"pwd2\"},{\"name\":\"testfan3\",\"pwd\":\"pwd3\"},{\"name\":\"testfan4\",\"pwd\":\"pwd4\"},{\"name\":\"testfan5\",\"pwd\":\"pwd5\"},{\"name\":\"testfan6\",\"pwd\":\"pwd6\"},{\"name\":\"testfan7\",\"pwd\":\"pwd7\"},{\"name\":\"testfan8\",\"pwd\":\"pwd8\"},{\"name\":\"testfan9\",\"pwd\":\"pwd9\"}]}";
		  String  save2="name_*=$..name";
		  saveMap(json3, save2);
	}

}
