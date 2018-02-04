package cn.mldn.service.impl;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mldn.dao.IDeptDAO;
import cn.mldn.dao.IEmpDAO;
import cn.mldn.service.IDeptService;
import cn.mldn.util.factory.Factory;
import cn.mldn.vo.Dept;
public class DeptServiceImpl implements IDeptService{
	@Override
	public boolean add(Dept vo) throws Exception {
		IDeptDAO deptDAO = Factory.getDAOInstance("dept.dao");
		if(deptDAO.findById(vo.getDeptno()) == null) {
			return deptDAO.doCreate(vo);
		}
		return false;
	}
	@Override
	public List<Dept> list() throws Exception {
		IDeptDAO deptDAO = Factory.getDAOInstance("dept.dao");
		return deptDAO.findAll();
	}
	@Override
	public Map<String, Object> listDetails() throws Exception {
		IDeptDAO deptDAO = Factory.getDAOInstance("dept.dao");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("allDepts", deptDAO.findAll());
		map.put("deptStats", deptDAO.findAllStat());
		return map;
	}
	@Override
	public Dept getEditPre(int id) throws Exception {
		IDeptDAO deptDAO = Factory.getDAOInstance("dept.dao");
		return deptDAO.findById(id);
	}
	@Override
	public boolean edit(Dept vo) throws Exception {
		IDeptDAO deptDAO = Factory.getDAOInstance("dept.dao");
		return deptDAO.doUpdate(vo);
	}
	@Override
	public boolean remove(Set<Integer> ids) throws Exception {
		if(ids == null || ids.size() == 0) return false;
		IEmpDAO empDAO = Factory.getDAOInstance("emp.dao");
		Iterator<Integer> iter = ids.iterator();
		while(iter.hasNext()) {
			empDAO.doRemoveByDeptno(iter.next());
		}
		IDeptDAO deptDAO = Factory.getDAOInstance("dept.dao");
		return deptDAO.doRemoveBatch(ids);
	}
}
