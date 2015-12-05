package core.resources.opd;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import core.classes.api.user.AdminUser;
import core.classes.opd.Visit;
import lib.driver.opd.driver_class.VisitDBDriver;

/**
 * This class define all the generic REST Services necessary for handling patient's visits
 * @author 
 * @version 1.0
 */
@Path("Visit")
public class VisitResource {
	
	VisitDBDriver visitDBDriver=new VisitDBDriver();
	DateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Add Visit Details Of Patient
	 * @param vJson JSON Object that contains all the Visit Details
	 * @return A String and If Data inserted Successfully return is True else False.
	 */
	@POST
	@Path("/addVisit")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addVisit(JSONObject vJson) {
		
	
		try{
			int doctorid = vJson.getInt("Doctor");
			int patientID = vJson.getInt("pid");
			
			Visit visit=new Visit();
			visit.setVisitType(vJson.getString("VisitType"));
			visit.setVisitDate(dateformat1.parse(vJson.getString("DateandTime")));
			visit.setVisitComplaint(vJson.getString("Injury"));
			visit.setVisitRemarks(vJson.getString("Remarks"));
		
			visit.setVisitLastUpdate(dateformat1.parse(vJson.getString("DateandTime")));
		
			visitDBDriver.saveVisit(visit, doctorid, patientID);
			return "True";
		}
		catch (JSONException e) {
			e.printStackTrace();
			return "False";
		} catch (ParseException e) {
			e.printStackTrace();
			return "False"; 
		}
		
	}
	
	/**
	 * Update Visit Details
	 * @param vJson JSON Object that contains all the Visit Details
	 * @return A String and If Data Updated Successfully return is True else False.
	 */
	@PUT
	@Path("/updateVisit")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateVisit(JSONObject vJson){
		
		Visit visit=new Visit();
		
		try{
		 
			visit.setVisitID(vJson.getInt("visitid"));
			visit.setVisitType(vJson.getString("VisitType"));
			visit.setVisitComplaint(vJson.getString("Injury"));
			visit.setVisitRemarks(vJson.getString("Remarks"));
			visit.setVisitLastUpdate(new Date());
			int doctor = vJson.getInt("Doctor");
			
			visitDBDriver.updateVisit(visit,doctor);
			return "True";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "False";
		} 
		
		
	}
	
	
	/**
	 * Get Visit Details by visit ID
	 * @param visitID is an integer value
	 * @return JSON String that contains all the Visit Details
	 */
	@GET
	@Path("/getVisitsByVisitID/{visitID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVisitsByVisitID(@PathParam("visitID")int visitID){
		 
		Visit  visitRecord = visitDBDriver.retrieveVisistByVisitID(visitID);
		JSONSerializer serializer = new JSONSerializer(); 
  
		return  serializer.exclude("*.class","prescriptions.prescribeItems.drugID.categories",
				"prescriptions.prescribeItems.drugID.supp","prescriptions.prescribeItems.drugID.dispense",
				"prescriptions.prescribeItems.drugID.request","prescriptions.prescribeItems.drugID.frequencies", 
				"prescriptions.prescribeItems.drugID.dosages","prescriptions.visit","patient",
				 
				"exams.visit",
				"exams.examLastUpdateUser","exams.examCreateUser", 
				"exams.examCreateDate",
				"exams.examLastUpdate",
				"exams.examActive", 
				"exams.examID", 
				
				"visitLastUpDateUser.specialPermissions","visitLastUpDateUser.userRoles","visitLastUpDateUser.employees.department","visitLastUpDateUser.employees.leaves",
				"visitCreateUser.specialPermissions", "visitCreateUser.userRoles","visitCreateUser.employees.department","visitCreateUser.employees.leaves",
				"visitDoctor.specialPermissions","visitDoctor.userRoles","visitDoctor.employees.department","visitDoctor.employees.leaves").
				
				include("*").serialize(visitRecord);
	}
	
	
	
	/**
	 * Get visit Details By Patient ID
	 * @param patientID is an Integer value
	 * @return JSON String that contains all the Visit Details
	 */
	@GET
	@Path("/getVisitsByPatientID/{patientID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVisitsByPatientID(@PathParam("patientID")int patientID){
		List<Visit> visitRecord = visitDBDriver.retrieveVisitsByPatientID(patientID);
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.include("patient.patientID").exclude("*.class","patient.*").transform(new DateTransformer("yyyy-MM-dd"),"visitLastUpdate","visitDate").serialize(visitRecord);
	}
	
	
	/**
	 * Get Recent Visit details of a Patient
	 * @param patientID is an Integer Values
	 * @return JSON String that contains all the Visit Details
	 */
	@GET
	@Path("/getRecentVisit/{patientID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRecentVisit(@PathParam("patientID")int patientID){
		List<Visit> visitRecord = visitDBDriver.retrieveRecent(patientID);
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.include("patient.patientID").exclude("*.class","patient.*").transform(new DateTransformer("yyyy-MM-dd"),"visitLastUpdate","visitDate").serialize(visitRecord);
	}
	
	/***
	 * Get Visit details By Visit Date 
	 * @param visitDate Is a Date Type Value
	 * @return JSON String that contains all the Visit Details
	 */
	@GET
	@Path("/getVisitsByVisitDate/{visitDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVisitsByVisitDate(@PathParam("visitDate")String visitDate){
		List<Visit> visitRecords;
		try {
			visitRecords = visitDBDriver.getVisitsByVisitDate( dateformat2.parse(visitDate ));
			JSONSerializer serializer = new JSONSerializer();
			return  serializer.include("patient.patientID","patient.patientFullName").exclude("*.class","patient.*").transform(new DateTransformer("yyyy-MM-dd"),"visitLastUpdate","visitDate").serialize(visitRecords);
		
		} catch (Exception e) {
			return "Error" + e.getMessage();
		}
		
	}

	@GET
	@Path("/getVisitsForReport/{fromDate}/{toDate}/{doctor}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVisitsForReport(@PathParam("fromDate")String fromDate,@PathParam("toDate")String toDate,@PathParam("doctor")int doctor){		
		try {		
		
			List<Visit> visitRecords = visitDBDriver.getVisitsForReport( dateformat2.parse(fromDate ), dateformat2.parse(toDate ), doctor);
			JSONSerializer serializer = new JSONSerializer();
			return  serializer.include("patient.patientID","patient.patientTitle","patient.patientFullName").exclude("*.class","patient.*").transform(new DateTransformer("yyyy-MM-dd"),"visitLastUpdate","visitDate").serialize(visitRecords);
		
		} catch (Exception e) {
			return "Error" + e.getMessage();
		}
		
	}

}
