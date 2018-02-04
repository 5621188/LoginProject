package cn.mldn.dao;
import java.util.Map;
import cn.mldn.util.dao.IBaseDAO;
import cn.mldn.vo.Dept;
public interface IDeptDAO extends IBaseDAO<Integer, Dept> {
	/**
	 * 进行部门统计信息的查询，统计信息以emp表为主，那么返回如下的内容：部门编号、统计信息；<br>
	 * @return 由于会返回多行的统计结果，所以使用Map集合描述，同时该map集合的信息组成如下：<br>
	 * key=部门编号、value=部门的统计信息；<br>
	 * 部门的统计信息由于有很多，所以继续设置一个Map集合，该Map集合的组成如下：<br>
	 * key=统计信息标记、value=统计信息结果<br>
	 * 统计信息标记：count、avg、max、min、avgyear
	 * @throws Exception
	 */
	public Map<Integer,Map<String,Object>> findAllStat() throws Exception;
} 
