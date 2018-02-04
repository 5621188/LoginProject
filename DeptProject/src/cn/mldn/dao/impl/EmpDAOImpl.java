package cn.mldn.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import cn.mldn.dao.IEmpDAO;
import cn.mldn.dao.abs.AbstractDAOImpl;
import cn.mldn.util.dbc.DatabaseConnection;
import cn.mldn.vo.Emp;

public class EmpDAOImpl extends AbstractDAOImpl  implements IEmpDAO {
	@Override
	public boolean doCreate(Emp vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doUpdate(Emp vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemove(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Emp findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> findAllSplit(Integer currentPage, Integer lineSize) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAllCount() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doRemoveByDeptno(Integer deptno) throws SQLException {
		String  sql = "DELETE FROM emp WHERE deptno=?";
		super.pstmt = DatabaseConnection.getConnection().prepareStatement(sql);
		super.pstmt.setInt(1, deptno);
		return pstmt.executeUpdate() > 0;
	}

}
