package core.classes.hr;

// default package
// Generated Aug 25, 2014 2:45:14 PM by Hibernate Tools 4.0.0

/**
 * HrIdentityId generated by hbm2java
 */
public class HrIdentityId implements java.io.Serializable {

	private int identityTypeId;
	private int empId;

	public HrIdentityId() {
	}

	public HrIdentityId(int identityTypeId, int empId) {
		this.identityTypeId = identityTypeId;
		this.empId = empId;
	}

	public int getIdentityTypeId() {
		return this.identityTypeId;
	}

	public void setIdentityTypeId(int identityTypeId) {
		this.identityTypeId = identityTypeId;
	}

	public int getEmpId() {
		return this.empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HrIdentityId))
			return false;
		HrIdentityId castOther = (HrIdentityId) other;

		return (this.getIdentityTypeId() == castOther.getIdentityTypeId())
				&& (this.getEmpId() == castOther.getEmpId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdentityTypeId();
		result = 37 * result + this.getEmpId();
		return result;
	}

}