package core.classes.inward.charts;

import java.util.Date;

public class DiabeticChart {
	public int rowNo;
	public String bhtNo;
	public Date dateTime;
	public float bloodSuger;
	
	public DiabeticChart(){
		
		
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public String getBhtNo() {
		return bhtNo;
	}

	public void setBhtNo(String bhtNo) {
		this.bhtNo = bhtNo;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public float getBloodSuger() {
		return bloodSuger;
	}

	public void setBloodSuger(float bloodSuger) {
		this.bloodSuger = bloodSuger;
	}
	
	

}
