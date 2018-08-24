package testng;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.testng.TestNG;

public class ScheduledApiTest {

	public static void main(String[] args) {
		
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		// 定时执行
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				TestNG testng = new TestNG();
				List<String> suites = new ArrayList<String>();
				String path = getFile("testng.xml").getPath();
				System.out.println("path " + path);
				suites.add(path);// path to xml..
				testng.setTestSuites(suites);
				testng.run();
			}
		}, 0, 1, TimeUnit.MINUTES);
	}

	private static File getFile(String fileName) {
		ClassLoader classLoader = ScheduledApiTest.class.getClassLoader();
		/**
		 * getResource()方法会去classpath下找这个文件，获取到url resource, 得到这个资源后，调用url.getFile获取到 文件
		 * 的绝对路径
		 */
		URL url = classLoader.getResource(fileName);
		/**
		 * url.getFile() 得到这个文件的绝对路径
		 */
		System.out.println(url.getFile());
		File file = new File(url.getFile());
		System.out.println(file.exists());
		return file;
	}

}
