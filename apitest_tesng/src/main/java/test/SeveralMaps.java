package test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class SeveralMaps {
    public static void main(String[] args) {
        Map<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("a6", "6");
        hashMap.put("b1", "1");
        hashMap.put("2", "2");
        hashMap.put("5", "5");
        hashMap.put("3", "3");
        hashMap.put("4", "4");
        System.out.println("*********** HashMap *************");
        System.out.print(hashMap.keySet());
        System.out.println(hashMap.values());
//        for (String key : hashMap.keySet()) {
//			System.out.println(key);
//		}

        Map treeMap = new TreeMap();
        treeMap.put("a6", "6");
        treeMap.put("b1", "1");
        treeMap.put("2", "2");
        treeMap.put("5", "5");
        treeMap.put("3", "3");
        treeMap.put("4", "4");
        System.out.println("*********** TreeMap *************");
        System.out.print(treeMap.keySet());
        System.out.println(treeMap.values());


        Map linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("6", "6");
        linkedHashMap.put("1", "1");
        linkedHashMap.put("2", "2");
        linkedHashMap.put("5", "5");
        linkedHashMap.put("3", "3");
        linkedHashMap.put("4", "4");
        System.out.println("*********** LinkedHashMap *************");
        System.out.print(linkedHashMap.keySet());
        System.out.println(linkedHashMap.values());
    }
}