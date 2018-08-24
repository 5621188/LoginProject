package testng;

import java.io.File;
import java.util.List;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import com.github.crab2died.ExcelUtils;

import api.TestResult;
import utils.DateUtils;

public class TestngLister implements IReporter {

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
	   System.out.println(ApiTestTestNg.allResult.size());
	   String path2 =System.getProperty("user.dir")+File.separator+"data"+File.separator+"apitest_result"+DateUtils.getCurrentTime()+".xlsx";
		System.out.println("测试测试最终结果 "+ApiTestTestNg.allResult.size());
		try {
			ExcelUtils.getInstance().exportObjects2Excel(ApiTestTestNg.allResult, TestResult.class, path2);
			ApiTestTestNg.allResult.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
