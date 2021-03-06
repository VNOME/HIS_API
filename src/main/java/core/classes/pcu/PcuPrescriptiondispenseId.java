package core.classes.pcu;

// default package
// Generated Aug 20, 2014 1:40:33 PM by Hibernate Tools 4.0.0

/**
 * PcuPrescriptiondispenseId generated by hbm2java
 */
public class PcuPrescriptiondispenseId implements java.io.Serializable {

	private int prescriptionId;
	private int SNumber;

	public PcuPrescriptiondispenseId() {
	}

	public PcuPrescriptiondispenseId(int prescriptionId, int SNumber) {
		this.prescriptionId = prescriptionId;
		this.SNumber = SNumber;
	}

	public int getPrescriptionId() {
		return this.prescriptionId;
	}

	public void setPrescriptionId(int prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public int getSNumber() {
		return this.SNumber;
	}

	public void setSNumber(int SNumber) {
		this.SNumber = SNumber;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PcuPrescriptiondispenseId))
			return false;
		PcuPrescriptiondispenseId castOther = (PcuPrescriptiondispenseId) other;

		return (this.getPrescriptionId() == castOther.getPrescriptionId())
				&& (this.getSNumber() == castOther.getSNumber());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getPrescriptionId();
		result = 37 * result + this.getSNumber();
		return result;
	}

}
