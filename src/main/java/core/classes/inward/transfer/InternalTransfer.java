package core.classes.inward.transfer;

import java.util.Date;

import core.classes.api.user.AdminUser;
import core.classes.inward.WardAdmission.Admission;
import core.classes.inward.admin.Ward;

public class InternalTransfer {
	public int transferId;
	

	public Admission bhtNo;
	public Ward transferFromWard;
	public Ward transferWard;
	public String resonForTrasnsfer;
	public String reportOfSpacialExamination;
	public String treatmentSuggested;
	public Date  transferCreatedDate;
	public AdminUser transferCreatedUser;
	public int read;
	
	public InternalTransfer(){
		
		
	}
	
	public int getTransferId() {
		return transferId;
	}

	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}
	
	
	public Admission getBhtNo() {
		return bhtNo;
	}

	public void setBhtNo(Admission bhtNo) {
		this.bhtNo = bhtNo;
	}

	public Ward getTransferFromWard() {
		return transferFromWard;
	}

	public void setTransferFromWard(Ward transferFromWard) {
		this.transferFromWard = transferFromWard;
	}

	public Ward getTransferWard() {
		return transferWard;
	}

	public void setTransferWard(Ward transferWard) {
		this.transferWard = transferWard;
	}

	public String getResonForTrasnsfer() {
		return resonForTrasnsfer;
	}

	public void setResonForTrasnsfer(String resonForTrasnsfer) {
		this.resonForTrasnsfer = resonForTrasnsfer;
	}

	public String getReportOfSpacialExamination() {
		return reportOfSpacialExamination;
	}

	public void setReportOfSpacialExamination(String reportOfSpacialExamination) {
		this.reportOfSpacialExamination = reportOfSpacialExamination;
	}

	public String getTreatmentSuggested() {
		return treatmentSuggested;
	}

	public void setTreatmentSuggested(String treatmentSuggested) {
		this.treatmentSuggested = treatmentSuggested;
	}

	public Date getTransferCreatedDate() {
		return transferCreatedDate;
	}

	public void setTransferCreatedDate(Date transferCreatedDate) {
		this.transferCreatedDate = transferCreatedDate;
	}



	public AdminUser getTransferCreatedUser() {
		return transferCreatedUser;
	}

	public void setTransferCreatedUser(AdminUser transferCreatedUser) {
		this.transferCreatedUser = transferCreatedUser;
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}
	
	

}
