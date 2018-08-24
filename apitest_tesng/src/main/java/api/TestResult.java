package api;

import com.github.crab2died.annotation.ExcelField;

public class TestResult {
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
	
	@ExcelField(title = "测试结果", writeConverter=WriteResult.class)
	private Boolean result =null;

	public String getCasename() {
		return casename;
	}

	public void setCasename(String casename) {
		this.casename = casename;
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

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "TestResult [casename=" + casename + ", type=" + type + ", url=" + url + ", params=" + params
				+ ", header=" + header + ", result=" + result + "]";
	}
}
