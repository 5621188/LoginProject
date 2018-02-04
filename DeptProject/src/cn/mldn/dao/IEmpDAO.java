package cn.mldn.dao;
import java.sql.SQLException;
import cn.mldn.util.dao.IBaseDAO;
import cn.mldn.vo.Emp;
public interface IEmpDAO extends IBaseDAO<Integer, Emp> {
	/**
	 * 根据部门编号删除对应的雇员信息
	 * @param deptno
	 * @return
	 * @throws SQLException
	 */
	public boolean doRemoveByDeptno(Integer deptno) throws SQLException;
}
