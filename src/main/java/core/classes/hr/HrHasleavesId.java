package core.classes.hr;

// default package
// Generated Aug 25, 2014 2:45:14 PM by Hibernate Tools 4.0.0

/**
 * HrHasleavesId generated by hbm2java
 */
public class HrHasleavesId implements java.io.Serializable {

	private int empId;
	private int leaveTypeId;

	public HrHasleavesId() {
	}

	public HrHasleavesId(int empId, int leaveTypeId) {
		this.empId = empId;
		this.leaveTypeId = leaveTypeId;
	}

	public int getEmpId() {
		return this.empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getLeaveTypeId() {
		return this.leaveTypeId;
	}

	public void setLeaveTypeId(int leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HrHasleavesId))
			return false;
		HrHasleavesId castOther = (HrHasleavesId) other;

		return (this.getEmpId() == castOther.getEmpId())
				&& (this.getLeaveTypeId() == castOther.getLeaveTypeId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getEmpId();
		result = 37 * result + this.getLeaveTypeId();
		return result;
	}

}