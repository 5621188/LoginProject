package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

public class JsonPathTest {

	public static void main(String[] args) {
		String json = readtxt();
		List<String> authors = JsonPath.read(json, "$.store.book[*].author");
		for (String str : authors) {
			System.out.println(str);
		}
		
		ConcurrentHashMap hash = new ConcurrentHashMap();
		  String json2 ="{\"msg\":\"登录成功\",\"uid\":\"E2BBEDC09FAA4406B8D85C96DF6281CF\",\"code\":\"1\"}";
		
		  JSONObject object = (JSONObject) JSON.parse(json2);
		  Object code= object.get("code");
		  
		  Map map2 = (Map) JSON.parseObject(json2, LinkedHashMap.class);
		  System.out.println(map2.getClass() +" map2 " +map2);
		  
		  ObjectMapper mapper = new ObjectMapper();  
		  try {
			Map map= mapper.readValue(json2, TreeMap.class);
		    System.out.println(map.getClass() +" map1 " +map);
			code= map.get("code");
		} catch (IOException e) {
			e.printStackTrace();
		}
		 code= JsonPath.read(json2, "$.code");
		 System.out.println(code.getClass() +"  "  +code);
		 String json3="{\"code\":\"1\",\"data\":[{\"name\":\"testfan0\",\"pwd\":\"pwd0\"},{\"name\":\"testfan1\",\"pwd\":\"pwd1\"},{\"name\":\"testfan2\",\"pwd\":\"pwd2\"},{\"name\":\"testfan3\",\"pwd\":\"pwd3\"},{\"name\":\"testfan4\",\"pwd\":\"pwd4\"},{\"name\":\"testfan5\",\"pwd\":\"pwd5\"},{\"name\":\"testfan6\",\"pwd\":\"pwd6\"},{\"name\":\"testfan7\",\"pwd\":\"pwd7\"},{\"name\":\"testfan8\",\"pwd\":\"pwd8\"},{\"name\":\"testfan9\",\"pwd\":\"pwd9\"}]}";
		 List<Object> o = JsonPath.read(json3, "$..name");
		 System.out.println(o.getClass().getSuperclass()+": "+o);
	}
	
	private static String readtxt() {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader("D:\\test.json");
            BufferedReader bfd = new BufferedReader(fr);
            String s = "";
            while((s=bfd.readLine())!=null) {
                sb.append(s);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

}
