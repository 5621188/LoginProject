package cn.mldn.vo;

import java.util.Date;

public class Emp {
	private int empno;
	private String ename;
	private String job;
	private Emp mgr;
	private Date hiredate;
	private double sal;
	private double comm;
	private Dept dept;
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public Emp getMgr() {
		return this.mgr;
	}
	public void setMgr(Emp mgr) {
		this.mgr = mgr;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public double getSal() {
		return sal;
	}
	public void setSal(double sal) {
		this.sal = sal;
	}
	public double getComm() {
		return comm;
	}
	public void setComm(double comm) {
		this.comm = comm;
	}
	public Dept getDept() {
		return this.dept;
	}
	public void setComm(Dept dept) {
		this.dept = dept;
	}
	@Override
	public String toString() {
		return "Emp [empno=" + empno + ", ename=" + ename + ", job=" + job + ", mgr=" + mgr + ", hiredate=" + hiredate
				+ ", sal=" + sal + ", comm=" + comm + ", dept=" + dept + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(comm);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + empno;
		result = prime * result + ((ename == null) ? 0 : ename.hashCode());
		result = prime * result + ((hiredate == null) ? 0 : hiredate.hashCode());
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		result = prime * result + ((mgr == null) ? 0 : mgr.hashCode());
		temp = Double.doubleToLongBits(sal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emp other = (Emp) obj;
		if (Double.doubleToLongBits(comm) != Double.doubleToLongBits(other.comm))
			return false;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (empno != other.empno)
			return false;
		if (ename == null) {
			if (other.ename != null)
				return false;
		} else if (!ename.equals(other.ename))
			return false;
		if (hiredate == null) {
			if (other.hiredate != null)
				return false;
		} else if (!hiredate.equals(other.hiredate))
			return false;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;
		if (mgr == null) {
			if (other.mgr != null)
				return false;
		} else if (!mgr.equals(other.mgr))
			return false;
		if (Double.doubleToLongBits(sal) != Double.doubleToLongBits(other.sal))
			return false;
		return true;
	}
	
}
