package api;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;

import com.github.crab2died.ExcelUtils;

import utils.CheckPointUtils;
import utils.CorrelationUtils;
import utils.CoverageUtils;
import utils.DateUtils;
import utils.HttpClientUtils;
import utils.InterceptorUtils;
import utils.MapUtils;
import utils.SaveParamsUtils;

/**
 * 覆盖率测试主线程
 *
 */
class CoverageTask implements Callable<List<TestResult>>{
	
	public List<TestCase> totestList;
	
	public Coverage coverage;

	public CoverageTask(List<TestCase> totestList, Coverage coverage) {
		super();
		this.totestList = totestList;
		this.coverage = coverage;
	}

	@Override
	public List<TestResult> call() throws Exception {
		System.out.println("覆盖测试" + Thread.currentThread().getName());
		List<TestResult> testResultList = new ArrayList<TestResult>();
		String rsString = null;
		//添加当前组对象
		SaveParamsUtils.addBeanToMp(coverage);
	
		for (TestCase testCase : totestList) {
			//是否开启
			if(testCase.isRun()) {
				System.out.println(testCase);
				//前置
				InterceptorUtils.doBefore(testCase);
				//关联替换
				CorrelationUtils.check(testCase);
				//get请求
				if("get".equalsIgnoreCase(testCase.getType())) {
					 rsString = HttpClientUtils.doGet(testCase.getUrl(),testCase.getHeader());
				}else if("post".equalsIgnoreCase(testCase.getType())) {
					 rsString = HttpClientUtils.doPost(testCase.getUrl(), MapUtils.covertStringToMp(testCase.getHeader()), MapUtils.covertStringToMp(testCase.getParams(), "&"));
				}else if("postjson".equalsIgnoreCase(testCase.getType())) {
					rsString =HttpClientUtils.doPostJson(testCase.getUrl(), testCase.getParams(), MapUtils.covertStringToMp(testCase.getHeader()));
				}
				System.out.println(rsString);
				SaveParamsUtils.saveMap(rsString, testCase.getCorrelation());
				boolean check = CheckPointUtils.checkbyJsonPath(rsString, testCase.getCheck());
				System.out.println("check---"+check);
				testCase.setResult(check);
				TestResult result = new TestResult();
				BeanUtils.copyProperties(result, testCase);
				testResultList.add(result);
			}
		}
		SaveParamsUtils.clear();
		totestList.clear();
		return  testResultList;
	}
}

public class ApiTest {
	
	public static void main(String[] args) {
		//testCoverage();
		
      ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
      //定时执行
	  scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				testCoverage();
			}
		}, 0, 3, TimeUnit.MINUTES);
	}
	
	private static void testCoverage() {
		String path = System.getProperty("user.dir") + File.separator + "data" + File.separator + "apitest.xlsx";
		String path2 =System.getProperty("user.dir")+File.separator+"data"+File.separator+"apitest_result"+DateUtils.getCurrentTime()+".xlsx";
		try {
			//覆盖率测试并发支持及线程池控制
			ExecutorService cachedThreadPool = Executors.newFixedThreadPool(50);
			//线程返回对象
			List<Future<List<TestResult>>> futures = new ArrayList<Future<List<TestResult>>>();
			//最终结果
			List<TestResult> allResult = new ArrayList<>();
			
			//初始化需要用来覆盖的参数
			List<Coverage> coveragesList = CoverageUtils.getCoverageList();
			for (Coverage coverage : coveragesList) {
				List<TestCase> list = ExcelUtils.getInstance().readExcel2Objects(path, TestCase.class);
				Future<List<TestResult>> future = cachedThreadPool.submit(new CoverageTask(list, coverage));
				futures.add(future);
			}
			//等线程处理完
			for (Future<List<TestResult>> future : futures) {
				List<TestResult> result = future.get();
				allResult.addAll(result);
			}
			
			System.out.println("测试测试最终结果 "+allResult.size());
			ExcelUtils.getInstance().exportObjects2Excel(allResult, TestResult.class, path2);
			allResult.clear();
			futures.clear();
			cachedThreadPool.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	private static void testcase() {
//		String path =System.getProperty("user.dir")+File.separator+"data"+File.separator+"apitest.xlsx";
//		String path2 =System.getProperty("user.dir")+File.separator+"data"+File.separator+"apitest_result"+DateUtils.getCurrentTime()+".xlsx";
//		try {
//			List<TestCase> list= ExcelUtils.getInstance().readExcel2Objects(path, TestCase.class);
//			String rsString = null;
//			for (TestCase testCase : list) {
//				//是否开启
//				if(testCase.isRun()) {
//					System.out.println(testCase);
//					CorrelationUtils.check(testCase);
//					//get请求
//					if("get".equalsIgnoreCase(testCase.getType())) {
//						 rsString = HttpClientUtils.doGet(testCase.getUrl(),testCase.getHeader());
//					}else if("post".equalsIgnoreCase(testCase.getType())) {
//						 rsString = HttpClientUtils.doPost(testCase.getUrl(), MapUtils.covertStringToMp(testCase.getHeader()), MapUtils.covertStringToMp(testCase.getParams(), "&"));
//					}else if("postjson".equalsIgnoreCase(testCase.getType())) {
//						rsString =HttpClientUtils.doPostJson(testCase.getUrl(), testCase.getParams(), MapUtils.covertStringToMp(testCase.getHeader()));
//					}
//					System.out.println(rsString);
//					SaveParamsUtils.saveMap(rsString, testCase.getCorrelation());
//					boolean check = CheckPointUtils.checkbyJsonPath(rsString, testCase.getCheck());
//					System.out.println("check---"+check);
//					testCase.setResult(check);
//				}
//			}
//			ExcelUtils.getInstance().exportObjects2Excel(list, TestCase.class, path2);
//			//SaveParamsUtils.clear();
//			//EmailUtils.sendEmailsWithAttachments("api测试", "测试结果", path2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
