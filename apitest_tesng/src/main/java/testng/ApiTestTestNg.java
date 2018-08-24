package testng;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.crab2died.ExcelUtils;

import api.Coverage;
import api.TestCase;
import api.TestResult;
import utils.CheckPointUtils;
import utils.CorrelationUtils;
import utils.CoverageUtils;
import utils.HttpClientUtils;
import utils.InterceptorUtils;
import utils.MapUtils;
import utils.SaveParamsUtils;

public class ApiTestTestNg {

	String path = System.getProperty("user.dir") + File.separator + "data" + File.separator + "apitest.xlsx";
	
	
	//最终结果
	public static List<TestResult> allResult = new ArrayList<>();

	@Test(dataProvider = "parameter", threadPoolSize = 0)
	public void parameterIntTest(Coverage coverage) {
		System.out.println(Thread.currentThread().getName() + " " + coverage);
		List<TestResult> testResultList = new ArrayList<TestResult>();
		String rsString = null;
		// 添加当前组对象
		SaveParamsUtils.addBeanToMp(coverage);
		List<TestCase> totestList = null;
		 List<Boolean> expected_resulet = new ArrayList<Boolean>();
         List<Boolean> actual_resulet = new ArrayList<Boolean>();
		try {
			totestList = ExcelUtils.getInstance().readExcel2Objects(path, TestCase.class);
			for (TestCase testCase : totestList) {
				// 是否开启
				if (testCase.isRun()) {
					System.out.println(testCase);
					// 前置
					InterceptorUtils.doBefore(testCase);
					// 关联替换
					CorrelationUtils.check(testCase);
					// get请求
					if ("get".equalsIgnoreCase(testCase.getType())) {
						rsString = HttpClientUtils.doGet(testCase.getUrl(), testCase.getHeader());
					} else if ("post".equalsIgnoreCase(testCase.getType())) {
						rsString = HttpClientUtils.doPost(testCase.getUrl(),
								MapUtils.covertStringToMp(testCase.getHeader()),
								MapUtils.covertStringToMp(testCase.getParams(), "&"));
					} else if ("postjson".equalsIgnoreCase(testCase.getType())) {
						rsString = HttpClientUtils.doPostJson(testCase.getUrl(), testCase.getParams(),
								MapUtils.covertStringToMp(testCase.getHeader()));
					}
					System.out.println(rsString);
					SaveParamsUtils.saveMap(rsString, testCase.getCorrelation());
					boolean check = CheckPointUtils.checkbyJsonPath(rsString, testCase.getCheck());
					System.out.println("check---" + check);
					testCase.setResult(check);
					actual_resulet.add(check);
					expected_resulet.add(true);
					TestResult result = new TestResult();
					BeanUtils.copyProperties(result, testCase);
					testResultList.add(result);
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			Assert.fail();
		}
		SaveParamsUtils.clear();
		allResult.addAll(testResultList);
		totestList.clear();
		//testResultList.clear();
		System.out.println("---------"+Thread.currentThread().getName() + allResult.size());
		Assert.assertEquals(actual_resulet, expected_resulet);
	}

	@DataProvider(name = "parameter", parallel = true)
	public Iterator<Object[]> parameterIntTestProvider() {
		List<Object[]> dataProvider = new ArrayList<Object[]>();

		List<Coverage> coveragesList = CoverageUtils.getCoverageList();
		for (Coverage coverage : coveragesList) {
			dataProvider.add(new Object[] { coverage });
		}
		return dataProvider.iterator();
	}

}
