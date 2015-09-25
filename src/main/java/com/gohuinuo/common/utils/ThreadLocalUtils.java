package com.gohuinuo.common.utils;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes","unchecked"})
public class ThreadLocalUtils {

	private static final ThreadLocal SESSION_MAP = new ThreadLocal();  
	  
    
    protected ThreadLocalUtils() {  
    }  
  
      
	public static Object get(String attribute) {  
        Map map = (Map) SESSION_MAP.get(); 
        return map.get(attribute);  
    }  
  
      
	public static <T> T get(String attribute, Class<T> clazz) {  
        return (T) get(attribute);  
    }  
  
      
    public static void set(String attribute, Object value) {  
        Map map = (Map) SESSION_MAP.get();  
  
        if (map == null) {  
            map = new HashMap();  
            SESSION_MAP.set(map);  
        }  
  
        map.put(attribute, value);  
    }  
	
}
