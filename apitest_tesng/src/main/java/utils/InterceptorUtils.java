package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import api.TestCase;

/**
 * 前置
 * @author wsl
 *
 */
public class InterceptorUtils {

	private static Pattern replaceParamPattern = Pattern.compile("\\$\\{(.+?)\\}");

	public static void doBefore(TestCase bean) {
		String before = bean.getBefore();
		if (before != null) {
			Matcher matcher = replaceParamPattern.matcher(before);
			while (matcher.find()) {
				String value = SaveParamsUtils.get(matcher.group(1)).toString();
				// 替換
				before = before.replace(matcher.group(), value);
			}
			String key = before.split("=")[0];
			String value = before.split("=")[1];
			if (key.contains("md5")) {
				doMd5(key, value);
			} else if (key.contains("rsa")) {
               //支持其他算法
			}
		}
	}

	private static void doMd5(String key, String value) {
		String md5Hex = DigestUtils.md5Hex(value);
		SaveParamsUtils.addtoMp(key, md5Hex);
	}

}
