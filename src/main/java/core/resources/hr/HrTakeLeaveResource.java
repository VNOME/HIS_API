package core.resources.hr;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lib.driver.hr.driver_class.HrHasLeaveDBDriver;
import lib.driver.hr.driver_class.HrTakeLeaveDBDriver;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import core.classes.api.user.AdminUser;
import core.classes.hr.HrEmployee;
import core.classes.hr.HrHasleaves;
import core.classes.hr.HrLeavetype;
import core.classes.hr.HrTakeleaves;
import flexjson.JSONSerializer;

@Path("HrTakeLeave")
public class HrTakeLeaveResource {

	HrTakeLeaveDBDriver hrTakeLeaveDBDriver= new HrTakeLeaveDBDriver();

	@POST
	@Path("/RequestLeave")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String RequestLeave( JSONObject uJson){
		
		System.out.println(uJson.toString());
		
		String result="false";
		boolean r=false;
		 HrTakeleaves hasLeaves=new HrTakeleaves();

		
		try{
			
			
			 HrEmployee emp = new HrEmployee();
			 emp.setEmpId(uJson.getInt("userId"));
			 HrLeavetype leaveType = new HrLeavetype();
			 leaveType.setLeaveTypeId(uJson.getInt("LeaveTypeId"));
			 
			 
			 hasLeaves.setHrEmployee(emp);
			 hasLeaves.setHrLeavetype(leaveType);
			 hasLeaves.setStartDatetime(uJson.getString("startDate"));
			 hasLeaves.setEndDatetime(uJson.getString("endDate"));
			 hasLeaves.setApproveStatus("0");
			 hasLeaves.setReason(uJson.getString("reason"));
			 
			r=hrTakeLeaveDBDriver.requestLeave(hasLeaves);
			result=String.valueOf(r);
			
			return result;
			 
		}
		
		catch( JSONException ex){
			ex.printStackTrace();	
			return result;
		}
		
		catch( Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}
		
		
	}
	
	
	@GET
	@Path("/getRequestDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllActiveUserDetails(){
		String result="";
		List<HrTakeleaves> usrList =hrTakeLeaveDBDriver.getRequestLeaveDetails();

		JSONSerializer serializor=new JSONSerializer();
		result = serializor.exclude("*.class").serialize(usrList);
		 
		return result;
		
	}
	
	
}
