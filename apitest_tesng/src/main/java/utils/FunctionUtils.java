package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 方法扩展
 * @author wsl
 *
 */
public class FunctionUtils {
	
	public static Object getFuncion(String key) {
		if("_time".equals(key)) {
			return _time();
		}else if("_uuid".equals(key)) {
			return _uuid();
		}else if("_time2".equals(key)) {
			return _time2();
		}
		return "";
	}
	
	private static String _time() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String time = formatter.format(date);
		return time;
	}
	
	private static String _uuid() {
		return UUID.randomUUID().toString();
	}
	
	private static String _time2() {
		return ""+System.currentTimeMillis();
	}

}
