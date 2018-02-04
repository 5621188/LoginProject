package cn.mldn.dao.impl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mldn.dao.IDeptDAO;
import cn.mldn.dao.abs.AbstractDAOImpl;
import cn.mldn.util.dbc.DatabaseConnection;
import cn.mldn.vo.Dept;
public class DeptDAOImpl extends AbstractDAOImpl implements IDeptDAO{
	private PreparedStatement pstmt = null;
	@Override
	public boolean doCreate(Dept vo) throws SQLException {
		String sql = "INSERT INTO dept (deptno,dname,loc) VALUES (?,?,?)";
		this.pstmt = DatabaseConnection.getConnection().prepareStatement(sql);
		this.pstmt.setInt(1,vo.getDeptno());
		this.pstmt.setString(2, vo.getDname());
		this.pstmt.setString(3, vo.getLoc());
		return this.pstmt.executeUpdate() > 0;
	}

	@Override
	public boolean doUpdate(Dept vo) throws SQLException {
		String sql = "UPDATE dept SET dname = ?,loc = ? where  deptno = ?";
		this.pstmt = DatabaseConnection.getConnection().prepareStatement(sql);
		this.pstmt.setInt(3,vo.getDeptno());
		this.pstmt.setString(1, vo.getDname());
		this.pstmt.setString(2, vo.getLoc());
		return this.pstmt.executeUpdate() > 0;
	}

	@Override
	public boolean doRemove(Integer id) throws SQLException {
		throw new SQLException("该SQL操作方法未实现！");
	}

	@Override
	public Dept findById(Integer id) throws SQLException {
		String sql = "SELECT deptno,dname,loc FROM dept WHERE deptno=?";
		this.pstmt = DatabaseConnection.getConnection().prepareStatement(sql);
		this.pstmt.setInt(1, id);
		ResultSet rs = this.pstmt.executeQuery();
		if(rs.next()){
			Dept vo = new Dept();
			vo.setDeptno(rs.getInt(1));
			vo.setDname(rs.getString(2));
			vo.setLoc(rs.getString(3));
			return vo;
		}
		return null;
	}

	@Override
	public List<Dept> findAll() throws SQLException {
		String sql = "SELECT deptno,dname,loc FROM dept";
		this.pstmt = DatabaseConnection.getConnection().prepareStatement(sql);
		ResultSet rs = this.pstmt.executeQuery();
		List<Dept> all = new ArrayList<Dept>();
		while(rs.next()) {
			Dept vo = new Dept();
			vo.setDeptno(rs.getInt(1));
			vo.setDname(rs.getString(2));
			vo.setLoc(rs.getString(3));
			all.add(vo);
		}
		return all;
	}

	@Override
	public List<Dept> findAllSplit(Integer currentPage, Integer lineSize) throws SQLException {
		throw new SQLException("该SQL操作方法未实现！");
	}

	@Override
	public List<Dept> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord)
			throws SQLException {
		throw new SQLException("该SQL操作方法未实现！");
	}

	@Override
	public Integer getAllCount() throws SQLException {
		throw new SQLException("该SQL操作方法未实现！");
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws SQLException {
		throw new SQLException("该SQL操作方法未实现！");
	}

	@Override
	public Map<Integer, Map<String, Object>> findAllStat() throws Exception {
		Map<Integer, Map<String, Object>> allStat = new HashMap<>();
		String sql = "	SELECT deptno,NVL(COUNT(*),0) count,NVL(ROUND(AVG(sal),2),0.00) avg,NVL(MAX(sal),0.00) max,NVL(MIN(sal),0.00) min, "
				+ " NVL(TRUNC(AVG(MONTHS_BETWEEN(SYSDATE,hiredate)/12),2),0.00) avgyear " + 
				  " FROM emp GROUP BY deptno " ;
		this.pstmt = DatabaseConnection.getConnection().prepareStatement(sql);
		ResultSet rs = this.pstmt.executeQuery();
		while(rs.next()) {
			Map<String,Object> deptStat =new HashMap<>();
			deptStat.put("count", rs.getInt(2));
			deptStat.put("avg", rs.getDouble(3));
			deptStat.put("max", rs.getDouble(4));
			deptStat.put("min", rs.getDouble(5));
			deptStat.put("avgyear", rs.getDouble(6));
			allStat.put(rs.getInt(1), deptStat);
		}
		return allStat;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		return super.handleDeleteForInt("dept", "deptno", ids);
	}

	
}
