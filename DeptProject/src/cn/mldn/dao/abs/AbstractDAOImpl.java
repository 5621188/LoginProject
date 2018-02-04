package cn.mldn.dao.abs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import cn.mldn.util.dbc.DatabaseConnection;

/**
 * 这个类可以实现接口与子类之间的过滤，同时该类还可以完成一些被重复使用的操作。
 * @author lz
 *
 */
public abstract class AbstractDAOImpl {
	protected PreparedStatement pstmt = null;
	/**
	 * 定义一个统一的批量删除的处理操作方法，该操作方法只需要传入不同的数据表、主键列的名称和要删除的主键数据
	 * 本方法只适合于主键是int的
	 * @param tableName 数据表名称
	 * @param pkColumnName 数据表的主键列名称
	 * @param ids 要删除的主键数据
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	public  boolean handleDeleteForInt(String tableName,String pkColumnName,Set<Integer> ids) throws SQLException{
		StringBuffer buf = new StringBuffer();
		buf.append("DELETE FROM ").append(tableName).append(" WHERE ")
		.append(pkColumnName).append(" IN (");
		Iterator<Integer> iter = ids.iterator();
		while(iter.hasNext()) {
			buf.append(iter.next()).append(",");
		}
		buf.delete(buf.length()-1, buf.length()).append(")");
		PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(buf.toString());
		return pstmt.executeUpdate() > 0;
	}
	
}
