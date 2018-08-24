package api;

import com.github.crab2died.converter.WriteConvertible;

public class RunResult implements WriteConvertible{

	@Override
	public Object execWrite(Object object) {
		if (object != null) {
			boolean run = (Boolean) object;
			if (run) {
				return "是";
			} else {
				return "否";
			}
		} else {
			return "否";
		}
	}

}
