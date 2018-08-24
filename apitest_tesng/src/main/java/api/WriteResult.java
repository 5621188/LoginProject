package api;

import com.github.crab2died.converter.WriteConvertible;

public class WriteResult implements WriteConvertible {

	@Override
	public Object execWrite(Object object) {
		if (object != null) {
			boolean ok = (Boolean) object;
			if (ok) {
				return "测试通过";
			} else {
				return "未试通过";
			}
		} else {
			return "未测试";
		}
	}

}
