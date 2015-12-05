package core.classes.lims;

import java.sql.Date;

public class addNewLabRequest {
	
	private Integer lab_test_request_id;
	private Integer ftest_id;
	private Integer fpatient_id;
	private Integer flab_id;
	private String comments;
	private String priority;
	private String status;
	private Date test_request_date;
	private Date test_due_date;
	private String ftest_request_person;

	/**
	 * @return the lab_test_request_id
	 */
	public Integer getLab_test_request_id() {
		return lab_test_request_id;
	}
	/**
	 * @param lab_test_request_id the lab_test_request_id to set
	 */
	public void setLab_test_request_id(int lab_test_request_id) {
		this.lab_test_request_id = lab_test_request_id;
	}
	/**
	 * @return the ftest_id
	 */
	public int getFtest_id() {
		return ftest_id;
	}
	/**
	 * @param ftest_id the ftest_id to set
	 */
	public void setFtest_id(int ftest_id) {
		this.ftest_id = ftest_id;
	}
	/**
	 * @return the fpatient_id
	 */
	public int getFpatient_id() {
		return fpatient_id;
	}
	/**
	 * @param fpatient_id the fpatient_id to set
	 */
	public void setFpatient_id(int fpatient_id) {
		this.fpatient_id = fpatient_id;
	}
	/**
	 * @return the flab_id
	 */
	public int getFlab_id() {
		return flab_id;
	}
	/**
	 * @param flab_id the flab_id to set
	 */
	public void setFlab_id(int flab_id) {
		this.flab_id = flab_id;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the test_request_date
	 */
	public Date getTest_request_date() {
		return test_request_date;
	}
	/**
	 * @param date the test_request_date to set
	 */
	public void setTest_request_date(java.util.Date date) {
		this.test_request_date = (Date) date;
	}
	
//	public void setTest_request_date(Date test_request_date) {
//		this.test_request_date = test_request_date;
//	}
	/**
	 * @return the test_due_date
	 */
	public Date getTest_due_date() {
		return test_due_date;
	}
	/**
	 * @param date the test_due_date to set
	 */
	public void setTest_due_date(java.util.Date date) {
		this.test_due_date = (Date) date;
	}
	/**
	 * @return the ftest_request_person
	 */
	public String getFtest_request_person() {
		return ftest_request_person;
	}
	/**
	 * @param ftest_request_person the ftest_request_person to set
	 */
	public void setFtest_request_person(String ftest_request_person) {
		this.ftest_request_person = ftest_request_person;
	}
	
	



	
	
}
