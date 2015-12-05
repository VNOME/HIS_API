package core.resources.mri;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import lib.driver.mri.driver_class.MriSpecimenDBDriver;
import lib.driver.mri.driver_class.MriSpecimenTypeDBDriver;
import lib.driver.mri.driver_class.MriTestRequestDBDriver;
import core.classes.lims.Specimen;
import core.classes.mri.MriLaboratoryTest;
import core.classes.mri.MriParentResult;
import core.classes.mri.MriPatient;
import core.classes.mri.MriSpecimen;
import core.classes.mri.MriSpecimenType;
import core.classes.mri.MriTestParentFields;
import core.classes.mri.MriTestRequest;
import core.classes.mri.MriTestResult;
import core.classes.mri.MriTestSubFields;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Path("MriSpecimen")
public class MriSpecimenResources {

	MriSpecimenDBDriver specimenDBDriver = new MriSpecimenDBDriver();
	MriSpecimenTypeDBDriver specimenTypeDBDriver = new MriSpecimenTypeDBDriver();
	MriTestRequestDBDriver mriTestRequestDBDriver = new MriTestRequestDBDriver();

	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

	@GET
	@Path("/GetAllSpecimen")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetTestParentFields() {
		try {

			List<MriSpecimen> specimenList = specimenDBDriver.GetSpecimens();
			JSONSerializer serializer = new JSONSerializer();

			// return serializer.exclude("*.class").serialize(specimenList);
			return serializer
					.include("specimenId", "mriSpecimenType.specimenTypeId",
							"specimenBarcode", "specimenCollectedPerson",
							"specimenDeliveredDepartmentDate",
							"specimenReceivedDate", "storedLocation",
							"storedOrDestroyed").exclude("*")
					.serialize(specimenList);

		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GET
	@Path("/GetAllSpecimens")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetTestParentFieldss() {
		try {

			List<MriSpecimen> specimenList = specimenDBDriver.GetSpecimens();
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(specimenList);
			 

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/*
	 * @POST
	 * 
	 * @Path("/addSpecimenInformation")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON) public String
	 * addSpecimenInformation(JSONObject pJson) { SimpleDateFormat dateFormatter
	 * = new SimpleDateFormat ( "dd/MM/yyyy" );
	 * 
	 * java.util.Date specimenReceivedDate = null; java.util.Date
	 * specimenDeliveredDepartmentDate = null;
	 * 
	 * try{ specimenReceivedDate =
	 * dateFormatter.parse(pJson.getString("specimenReceivedDate"));
	 * specimenDeliveredDepartmentDate =
	 * dateFormatter.parse(pJson.getString("specimenDeliveredDepartmentDate"));
	 * 
	 * }catch(ParseException | java.text.ParseException | JSONException ex){}
	 * try {
	 * 
	 * MriSpecimen specimen = new MriSpecimen();
	 * 
	 * int specimenID = pJson.getInt("specimenId"); int mriSpecimenType =
	 * pJson.getInt("mriSpecimenType"); int mriTestRequests =
	 * pJson.getInt("mriTestRequests"); String specimenCollectedPerson
	 * =pJson.getString("specimenCollectedPerson");
	 * 
	 * specimen.setSpecimenReceivedDate(specimenReceivedDate);
	 * specimen.setSpecimenDeliveredDepartmentDate
	 * (specimenDeliveredDepartmentDate);
	 * specimen.setSpecimenBarcode(pJson.getString
	 * ("specimenBarcode").toString());
	 * specimen.setRemarks(pJson.getString("remarks").toString());
	 * specimen.setStoredLocation(pJson.getString("storedLocation").toString());
	 * specimen.setStoredOrDestroyed(pJson.getString("storedOrDestroyed"));
	 * 
	 * 
	 * 
	 * specimenDBDriver.addSpecimenInformation(specimen, specimenID,
	 * mriSpecimenType, mriTestRequests, specimenCollectedPerson);
	 * 
	 * 
	 * JSONSerializer jsonSerializer = new JSONSerializer(); return
	 * jsonSerializer.include("specimen_ID").serialize(specimen); } catch
	 * (Exception e) { System.out.println(e.getMessage());
	 * 
	 * return null; }
	 * 
	 * }
	 */

	@POST
	@Path("/InsertNewSpecimen")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String InsertNewSpecimen(JSONObject pJson) {
		try {
			JSONArray data = pJson.getJSONArray("MriSpecimenDetails");

			MriSpecimen newSpecimen = new MriSpecimen();
			MriSpecimenType specimenType = specimenTypeDBDriver.GetSpecimenTypeByID(data.getJSONObject(0).getInt("specimenTypeId"));
			newSpecimen.setMriSpecimenType(specimenType);
			newSpecimen.setSpecimenBarcode(data.getJSONObject(0).getString("specimenBarcode"));
			newSpecimen.setStoredOrDestroyed(data.getJSONObject(0).getString("storedOrDestroyed"));
			newSpecimen.setRemarks(data.getJSONObject(0).getString("remarks"));
			newSpecimen.setSpecimenCollectedPerson(data.getJSONObject(0).getString("specimenCollectedPerson"));
			newSpecimen.setSpecimenReceivedDate(dateformat.parse(data.getJSONObject(0).getString("specimenReceivedDate")));
			newSpecimen.setSpecimenDeliveredDepartmentDate(dateformat.parse(data.getJSONObject(0).getString("specimenDeliveredDepartmentDate")));
			newSpecimen.setStoredLocation(data.getJSONObject(0).getString("storedLocation"));
			

			
			String requestIDs=data.getJSONObject(0).getString("requestID");
			
			MriSpecimen addedSpecimen= specimenDBDriver.InsertNewSpecimen(newSpecimen);
			int specimentID=addedSpecimen.getSpecimenId();
			String success="";
			if (specimentID > 0){
				String requests[]=requestIDs.split(",");
				for(int i=0; i<requests.length;i++){
					
					String requestID=requests[i].replaceAll("\\p{Z}","");
					int requestPrimaryKey=mriTestRequestDBDriver.GetRequestPrimaryKeyByRequestID(requestID);
					
					System.out.println(requestID);
					
					success=mriTestRequestDBDriver.InsertSpecimentForRequest(requestPrimaryKey,addedSpecimen);
				}
				
			}
			//System.out.println(specimenType.getSpecimenName());


		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("E1");
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("E2");
			return null;
		}
		System.out.println("TRUE");
		return "TRUE";
	}

	@GET
	@Path("/getSpecimenByRequestID/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTestRequestByRequestID(@PathParam("data") int testRequestId) {

		List<MriSpecimen> specimenList = specimenDBDriver
				.getSpecimenDetailsByRequestID(testRequestId);
		JSONSerializer serializer = new JSONSerializer();

		return serializer.exclude("*.class").serialize(specimenList);
		// return serializer.include("*").

		// exclude("mrispecime0_.fspecimen_type_name").serialize(specimen);
	}

	@GET
	@Path("/getSpecimenByDepartmentName/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecimenByDepartmentName(
			@PathParam("data") int testRequestId) {

		List<MriSpecimen> specimenList = specimenDBDriver
				.getSpecimenDetailsByRequestID(testRequestId);
		JSONSerializer serializer = new JSONSerializer();

		return serializer
				.include(
						"mriTestRequest.mriLaboratoryTest.mriLaboratory.mriDepartment.departmentName")
				.exclude("*").serialize(specimenList);
		// return serializer.exclude("*.class").serialize(specimenList);
		// return serializer.include("*").

		// exclude("mrispecime0_.fspecimen_type_name").serialize(specimen);
	}

	@GET
	@Path("/GetAllSpecimenDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllSpecimenDetails() {
		try {

			List<MriSpecimen> specimenList = specimenDBDriver.GetSpecimens();
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(specimenList);
			// return
			// serializer.include("specimenId","mriSpecimenType.specimenTypeId","mriTestRequest.testRequestId","specimenBarcode","specimenCollectedPerson","specimenDeliveredDepartmentDate","specimenReceivedDate","storedLocation","storedOrDestroyed").exclude("*").serialize(specimenList);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GET
	@Path("/GetAllSpecimenDetailsByRequestID/{testRequestId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllSpecimenDetailsByRequestID(
			@PathParam("testRequestId") int testRequestId) {
		try {

			List<MriSpecimen> specimenList = specimenDBDriver
					.GetSpecimensByReqestID(testRequestId);
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(specimenList);
			// return
			// serializer.include("specimenId","mriSpecimenType.specimenTypeId","mriTestRequest.testRequestId","specimenBarcode","specimenCollectedPerson","specimenDeliveredDepartmentDate","specimenReceivedDate","storedLocation","storedOrDestroyed").exclude("*").serialize(specimenList);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	// Get ID From MriTestRequest Table
	@GET
	@Path("/getSpecimenIDsByRequestID/{testRequestId1}/{testRequestId2}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecimenIDsByRequestID(
			@PathParam("testRequestId1") int testRequestId1,
			@PathParam("testRequestId2") int testRequestId2) {

		List<MriTestRequest> requestRec = specimenDBDriver
				.retrieveTestRequestDetailByRequestIDs(testRequestId1,
						testRequestId2);
		JSONSerializer serializer = new JSONSerializer();

		/*
		 * String stockList ; stockList =
		 * serializer.include("requestId").exclude("*").serialize(requestRec);
		 * 
		 * System.out.println("________________________"+ stockList) ;
		 * 
		 * List<MriTestRequest> ls = new
		 * JSONDeserializer<List<MriTestRequest>>().deserialize(stockList);
		 * System.out.println("________________________"+ ls) ;
		 */

		// mriSpecimen.specimenId ,requestId,testRequestId
		return serializer.include("mriSpecimen.specimenId", "mriPatient.name")
				.exclude("*").serialize(requestRec);
		// return
		// serializer.include("*.class").exclude("requestId").serialize(requestRec);
	}

	// Get ID From MriTestRequest Table
	@GET
	@Path("/getSpecimenIDsByRequestIDs/{testRequestId1}/{testRequestId2}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecimenIDsByRequestIDs(
			@PathParam("testRequestId1") int testRequestId1,
			@PathParam("testRequestId2") int testRequestId2) {

		List<MriSpecimen> requestRec = specimenDBDriver
				.retrieveSpecimenDetailByRequestIDs(testRequestId1,
						testRequestId2);
		JSONSerializer serializer = new JSONSerializer();

		// mriSpecimen.specimenId ,requestId
		// return serializer
		// .include("specimenId", "mriTestRequest.mriPatient.name")
		// .exclude("*").serialize(requestRec);
		return serializer.exclude("*.class").serialize(requestRec);
	}

	@GET
	@Path("/GetRequestByRequestID/{requestID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetRequestByRequestID(@PathParam("requestID") int requestID) {
		String result = "";
		try {
			List<MriTestRequest> testRequests = mriTestRequestDBDriver
					.GetRequestByRequestID(requestID);

			JSONSerializer serializer = new JSONSerializer();

			return serializer.include("requestId").exclude("*.class")
					.serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GET
	@Path("/getMaxSpecimenID")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetMaxSpecimenID() {
		String result = "";
		try {
			List<MriSpecimen> testRequests = mriTestRequestDBDriver.getMaxSpecimenIDs();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.include("requestId").exclude("*.class")
					.serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	// *2 time Get ID From MriTestRequest Table
	@GET
	@Path("/getMriTestReqDetailsByRequestID/{testRequestId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecimenIDsByRequestID(
			@PathParam("testRequestId") int testRequestId1) {
		try {
			List<MriTestRequest> requestRec = specimenDBDriver
					.getTestRequestDetailByRequestIDs(testRequestId1);

			JSONSerializer serializer = new JSONSerializer();
			return serializer.include("testRequestId").exclude("*.class")
					.serialize(requestRec);
			// return
			// serializer.include("mriPatient.name","mriPatient.sex","mriPatient.birthday","testRequestId").exclude("*").serialize(requestRec);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	
	@POST
	@Path("/updateTRSpecimentID")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateTRSpecimentID(JSONObject pJson) {
		try {
			JSONArray data = pJson.getJSONArray("MriTestRequestDetails");

			MriTestRequest testRequest = new MriTestRequest();
			MriSpecimen mriSpecimen = new MriSpecimen();			
			
			mriSpecimen = specimenDBDriver.GetSpecimenByID(data.getJSONObject(0).getInt("mriSpecimenId"));
			testRequest.setMriSpecimen(mriSpecimen); 
			testRequest.setRequestId(data.getJSONObject(0).getString("requestId"));
			
			System.out.println("currSpecIDs  :"+ data.getJSONObject(0).getString("mriSpecimenId"));
			specimenDBDriver.updateTestRequestSpecimenID(testRequest);

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return "TRUE";
	}

	
	
	
	
	
	
	
}
