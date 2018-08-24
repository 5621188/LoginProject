package api;

import com.github.crab2died.annotation.ExcelField;

public class Coverage {
	@ExcelField(title = "loginname")
	private String loginname;
	
	@ExcelField(title = "loginpass")
	private String loginpass;

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLoginpass() {
		return loginpass;
	}

	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}

	@Override
	public String toString() {
		return "Coverage [loginname=" + loginname + ", loginpass=" + loginpass + "]";
	}

}
