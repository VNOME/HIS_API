package core.resources.lims;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import core.classes.lims.Category;
import core.classes.lims.LabTestRequest;
import core.classes.lims.MainResults;
import core.classes.lims.Reports;
import core.classes.lims.SubCategory;
import core.classes.opd.OutPatient;
import core.classes.opd.Patient;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import lib.driver.lims.driver_class.CategoryDBDriver;
import lib.driver.lims.driver_class.LabTestRequestDBDriver;
import lib.driver.lims.driver_class.SubCategoryDBDriver;

@Path("LabTestRequest")
public class LabTestRequestResource {

LabTestRequestDBDriver requestDBDriver= new LabTestRequestDBDriver();
	
	@POST
	@Path("/addLabTestRequest")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public String addNewLabTest(JSONObject pJson)
	{
		

		

		try {
			LabTestRequest testRequest = new LabTestRequest();
			
			int testID = pJson.getInt("ftest_ID");
			int patientID = pJson.getInt("fpatient_ID");
			int labID = pJson.getInt("flab_ID");
			int userid = pJson.getInt("ftest_RequestPerson");
			
			testRequest.setComment(pJson.getString("comment").toString());
			testRequest.setPriority(pJson.getString("priority").toString());
			testRequest.setStatus(pJson.getString("status").toString());
			testRequest.setTest_RequestDate(new Date());
			testRequest.setTest_DueDate(new Date());
			
			
			requestDBDriver.addNewLabTestRequest(testRequest, testID, patientID, labID, userid);
			
			 
			JSONSerializer jsonSerializer = new JSONSerializer();
			return jsonSerializer.include("labTestRequest_ID").serialize(testRequest);
		} catch (Exception e) {
			 System.out.println(e.getMessage());
			 
			return null; 
		}

	}
	
	@GET
	@Path("/getAllLabTestRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTestRequests()
	{
		
		List<LabTestRequest> testRequestsList =   requestDBDriver.getTestRequestsList();
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.include("ftest_ID.test_ID","priority","status","ftest_ID.test_IDName","ftest_ID.test_Name","fpatient_ID.patientID","fpatient_ID.patientFullName","fspecimen_ID.specimen_ID.*","flab_ID.lab_ID.*","flab_ID.lab_Name.*","ftest_RequestPerson.userID.*","ftest_RequestPerson.userName.*"
				,"fsample_CenterID.sampleCenter_ID.*","fsample_CenterID.sampleCenter_Name.*").exclude("*.class","fspecimen_ID.*","flab_ID.*","ftest_RequestPerson.*","fsample_CenterID.*","fpatient_ID.*","ftest_ID.*","ftest_RequestPerson.*").transform(new DateTransformer("yyyy-MM-dd"),"test_RequestDate","test_DueDate").serialize(testRequestsList);
	}
	
	@GET
	@Path("/getRequestsByPatientID/{patientID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllSubCategories(@PathParam("patientID")int patientID)
	{
		List<LabTestRequest> testRequestsList =   requestDBDriver.getLabTestRequestsByPid(patientID);
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.include("ftest_ID.test_ID","ftest_ID.test_IDName","status","ftest_ID.test_Name","fpatient_ID.patientID","fpatient_ID.patientFullName","fpatient_ID.patientGender","fpatient_ID.patientDateOfBirth","fspecimen_ID.specimen_ID.*","flab_ID.lab_ID.*","flab_ID.lab_Name.*","ftest_RequestPerson.userID.*","ftest_RequestPerson.userName.*"
				,"fsample_CenterID.sampleCenter_ID.*","fsample_CenterID.sampleCenter_Name.*").exclude("*.class","fspecimen_ID.*","flab_ID.*","ftest_RequestPerson.*","fsample_CenterID.*","fpatient_ID.*","ftest_ID.*","ftest_RequestPerson.*").transform(new DateTransformer("yyyy-MM-dd"),"test_RequestDate","test_DueDate").serialize(testRequestsList);
	}
	
}
