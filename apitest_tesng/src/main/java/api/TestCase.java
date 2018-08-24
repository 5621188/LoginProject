package api;

import com.github.crab2died.annotation.ExcelField;


public class TestCase {
	
	@ExcelField(title = "开启", readConverter=IsRunCovert.class, writeConverter=RunResult.class)
	private boolean run;
	
	@ExcelField(title = "用例名称")
	private String casename;
	
	@ExcelField(title = "类型")
	private String type;
	
	@ExcelField(title = "地址")
	private String url;
	
	@ExcelField(title = "参数")
	private String params;
	
	@ExcelField(title = "头部")
	private String header;
	
	@ExcelField(title = "检查点")
	private String check;
	
	@ExcelField(title = "关联")
	private String correlation;
	
	@ExcelField(title = "测试结果", writeConverter=WriteResult.class)
	private Boolean result =null;
	
	@ExcelField(title = "前置")
	private String before;
	
	
	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getCorrelation() {
		return correlation;
	}

	public void setCorrelation(String correlation) {
		this.correlation = correlation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getCasename() {
		return casename;
	}

	public void setCasename(String casename) {
		this.casename = casename;
	}

	@Override
	public String toString() {
		return "TestCase [run=" + run + ", type=" + type + ", url=" + url + ", params=" + params + ", header=" + header
				+ ", check=" + check + ", correlation=" + correlation + ", casename=" + casename + "]";
	}

}
