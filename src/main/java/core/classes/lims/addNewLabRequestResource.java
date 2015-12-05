package core.classes.lims;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import core.classes.lims.Category;
import flexjson.JSONSerializer;
import lib.driver.hr.driver_class.HrEmployeeDBDriver;
import lib.driver.lims.driver_class.CategoryDBDriver;





import lib.driver.lims.driver_class.LabTestRequestFiltersDBDriver;

import java.sql.Date;

@Path("addNewLabRequest")
public class addNewLabRequestResource {
	
	LabTestRequestFiltersDBDriver LabTestRequestFiltersDBDriver= new LabTestRequestFiltersDBDriver();
	DateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd");
	
	@POST
	@Path("/addNewTest")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String AddNewTest(JSONObject wJson)  
	{
		
		//System.out.println(dateformat2.parse(wJson.getString("empBDay").toString()));

		try {
			addNewLabRequest req=new addNewLabRequest();
			
			
			req.setLab_test_request_id(Integer.parseInt(wJson.getString("ftest_id")));
			req.setFlab_id(Integer.parseInt(wJson.getString("fpatient_id"))); 
			req.setFpatient_id(Integer.parseInt(wJson.getString("flab_id"))); 
			req.setFlab_id(Integer.parseInt(wJson.getString("comments")));
			req.setComments(wJson.getString("priority"));
			req.setPriority(wJson.getString("status"));
			req.setStatus(wJson.getString("empImage"));

			
			req.setTest_request_date (dateformat2.parse(wJson.getString("test_request_date")));
			req.setTest_due_date(dateformat2.parse(wJson.getString("test_due_date")));
			req.setTest_due_date(dateformat2.parse(wJson.getString("ftest_request_person")));
			
			int empID = Integer.parseInt(LabTestRequestFiltersDBDriver.addNewLabRequest(req));
			

			
			
			return req.getLab_test_request_id().toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage().toString();
		}
	}
	
	
}