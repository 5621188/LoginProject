package api;

import com.github.crab2died.converter.ReadConvertible;

public class IsRunCovert implements ReadConvertible{

	@Override
	public Object execRead(String object) {
		return "是".equals(object);
	}

}
