package utils;

import java.io.File;
import java.util.List;

import com.github.crab2died.ExcelUtils;

import api.Coverage;

/**
 * 提供参数覆盖
 * @author wsl
 *
 */
public class CoverageUtils {
	public static List<Coverage> getCoverageList(){
		String path =System.getProperty("user.dir")+File.separator+"data"+File.separator+"apitest.xlsx";
		List<Coverage> list=null;
		try {
			 list =ExcelUtils.getInstance().readExcel2Objects(path, Coverage.class,1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
