package core.classes.inward.charts;

import java.util.Date;

public class Temperaturechart {
	public int rowNo;
	public String bhtNo;
	public float temperature;
	public Date dateTime;
	
	public Temperaturechart(){
		
		
	}
	
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
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

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	

}
