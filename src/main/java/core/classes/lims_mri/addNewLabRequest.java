package core.classes.lims_mri;

import java.sql.Date;

public class addNewLabRequest implements java.io.Serializable {
	
	private int test_request_id;
	private String test_name;
	private String hospital_name;
	private int patient_bht;
	private int patient_ward_no;
	private String comments;
	private String test_priority;
	private String test_request_type;
	private Date test_request_date;
	private Date test_due_date;
	

	public int getTest_request_id() {
		return test_request_id;
	}
	public void setTest_request_id(int test_request_id) {
		this.test_request_id = test_request_id;
	}
	public String getTest_name() {
		return test_name;
	}
	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}
	public String getHospital_name() {
		return hospital_name;
	}
	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}
	public int getPatient_bht() {
		return patient_bht;
	}
	public void setPatient_bht(int patient_bht) {
		this.patient_bht = patient_bht;
	}
	public int getPatient_ward_no() {
		return patient_ward_no;
	}
	public void setPatient_ward_no(int patient_ward_no) {
		this.patient_ward_no = patient_ward_no;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getTest_priority() {
		return test_priority;
	}
	public void setTest_priority(String test_priority) {
		this.test_priority = test_priority;
	}
	public String getTest_request_type() {
		return test_request_type;
	}
	public void setTest_request_type(String test_request_type) {
		this.test_request_type = test_request_type;
	}
	public Date getTest_request_date() {
		return test_request_date;
	}
	public void setTest_request_date(Date test_request_date) {
		this.test_request_date = test_request_date;
	}
	public Date getTest_due_date() {
		return test_due_date;
	}
	public void setTest_due_date(Date test_due_date) {
		this.test_due_date = test_due_date;
	}

	
	
}
