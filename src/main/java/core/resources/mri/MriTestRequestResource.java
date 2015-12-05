package core.resources.mri;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.jboss.logging.Param;

import lib.driver.hr.driver_class.HrEmployeeDBDriver;
import lib.driver.mri.driver_class.MriBundleDBDriver;
import lib.driver.mri.driver_class.MriHospitalDBDriver;
import lib.driver.mri.driver_class.MriHospitalPatientDBDriver;
import lib.driver.mri.driver_class.MriLaboratoryTestDBDriver;
import lib.driver.mri.driver_class.MriPatientDBDriver;
import lib.driver.mri.driver_class.MriSpecimenDBDriver;
import lib.driver.mri.driver_class.MriTestRequestDBDriver;
import core.classes.hr.HrEmployeeView;
import core.classes.lims.MainResults;
import core.classes.mri.MriBundle;
import core.classes.mri.MriHospital;
import core.classes.mri.MriHospitalPatient;
import core.classes.mri.MriLaboratoryTest;
import core.classes.mri.MriPatient;
import core.classes.mri.MriSpecimen;
import core.classes.mri.MriTestRequest;
import flexjson.JSONSerializer;


@Path("MriTestRequest")
public class MriTestRequestResource {

	MriTestRequestDBDriver mriTestRequestDBDriver= new MriTestRequestDBDriver();
	MriPatientDBDriver mriPatientDBDriver= new MriPatientDBDriver();
	MriSpecimenDBDriver mriSpecimentDBDriver = new MriSpecimenDBDriver();
	MriLaboratoryTestDBDriver mriLabTestDBDriver = new MriLaboratoryTestDBDriver();
	MriHospitalPatientDBDriver  mriHospitalPatientDBDriver= new MriHospitalPatientDBDriver();
	MriBundleDBDriver mriBundleDBDriver= new MriBundleDBDriver();
	MriHospitalDBDriver mriHospitalDBDriver = new MriHospitalDBDriver();

	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

	@GET
	@Path("/GetAllTestRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllTestRequests() {
		String result="";
		try {
			List<MriTestRequest> testRequests= mriTestRequestDBDriver.GetAllTestRequests();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@POST
	@Path("/InsertNewInternalRequests")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String InsertNewInternalRequests(JSONObject pJson)
	{
		try {
				JSONArray data = pJson.getJSONArray("InternalPatientRequests");

				Set<MriTestRequest> MriTestRequestList = new HashSet<MriTestRequest>();
				for (int curr = 0; curr < data.length(); curr++){

						Date date = new Date();

						String today = dateformat.format(date);
						//String today= date.toString();
						System.out.println(today);
						MriTestRequest testRequest= new MriTestRequest();

						MriPatient requestPatient = mriPatientDBDriver.GetPatientByID(data.getJSONObject(curr).getInt("PatientID"));
						testRequest.setMriPatient(requestPatient);

						testRequest.setIncrement(data.getJSONObject(curr).getInt("Increment"));
						testRequest.setRequestId(data.getJSONObject(curr).getString("RequestID"));
						testRequest.setComments(data.getJSONObject(curr).getString("Comments"));
						testRequest.setTestPriority(data.getJSONObject(curr).getString("Priority"));
						testRequest.setTestRequestDate(dateformat.parse(today));
						testRequest.setTestDueDate(dateformat.parse(data.getJSONObject(curr).getString("DueDate")));
						testRequest.setStatus(0);
//						String specimen = data.getJSONObject(curr).getString("SpecimenID");
//
//						if ( !specimen.isEmpty())
//						{
//							MriSpecimen specimentID= mriSpecimentDBDriver.GetSpecimenByID(data.getJSONObject(curr).getInt("SpecimenID"));
//							testRequest.setMriSpecimen(specimentID);
//						}

						MriLaboratoryTest testType = mriLabTestDBDriver.GetLabTestByID(data.getJSONObject(curr).getInt("TestTypeID"));
						testRequest.setMriLaboratoryTest(testType);

						mriTestRequestDBDriver.InserNewTestRequest(testRequest);
				}

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return "TRUE";
	}

	@POST
	@Path("/InsertNewHospitalRequests")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String InsertNewHospitalRequests(JSONObject pJson)
	{
		try {
				MriBundle bundle=mriBundleDBDriver.CreateNewBundle();

				MriHospital hospital = null;
				MriSpecimen specimentID = null;
				int total=0;

				JSONArray data = pJson.getJSONArray("HospitalPatientRequests");

				Set<MriTestRequest> mriTestRequestList = new HashSet<MriTestRequest>();
				for (int curr = 0; curr < data.length(); curr++){
					Date date = new Date();

					String today = dateformat.format(date);
					//String today= date.toString();
					System.out.println(today);

						MriTestRequest testRequest= new MriTestRequest();

						MriHospitalPatient requestHospitalPatient = mriHospitalPatientDBDriver.GetHospitalPatientByID(data.getJSONObject(curr).getInt("PatientID"));
						MriPatient requestPatient= requestHospitalPatient.getMriPatient();

						testRequest.setMriPatient(requestPatient);
						testRequest.setIsHospitalPatient(true);

						testRequest.setMriHospitalPatient(requestHospitalPatient);
						testRequest.setMriBundle(bundle);

						testRequest.setIncrement(data.getJSONObject(curr).getInt("Increment"));
						testRequest.setRequestId(data.getJSONObject(curr).getString("RequestID"));
						testRequest.setComments(data.getJSONObject(curr).getString("Comments"));
						testRequest.setTestPriority(data.getJSONObject(curr).getString("Priority"));
						testRequest.setTestRequestDate(dateformat.parse(today));
						testRequest.setTestDueDate(dateformat.parse(data.getJSONObject(curr).getString("DueDate")));
						testRequest.setStatus(0);
//						String specimen = data.getJSONObject(curr).getString("SpecimenID");
//
//						if ( !specimen.isEmpty())
//						{
//							specimentID= mriSpecimentDBDriver.GetSpecimenByID(Integer.parseInt(specimen));
//							testRequest.setMriSpecimen(specimentID);
//						}

						MriLaboratoryTest testType = mriLabTestDBDriver.GetLabTestByID(data.getJSONObject(curr).getInt("TestTypeID"));
						testRequest.setMriLaboratoryTest(testType);

						//mriTestRequestList.add(testRequest);


						hospital = mriHospitalDBDriver.GetHospitalObject(data.getJSONObject(curr).getInt("HospitalID"));

						System.out.println(hospital.getHospitalName());
						mriTestRequestDBDriver.InserNewTestRequest(testRequest);
						total=curr;
				}

				mriBundleDBDriver.UpdateBundle(bundle,hospital,total+1);

				return bundle.getBundleId().toString();

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}




	}

	@GET
	@Path("/GetInternalRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetInternalRequests() {
		String result="";
		try {
			List<MriTestRequest> testRequests= mriTestRequestDBDriver.GetInternalRequests();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GET
	@Path("/GetHospitalRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetHospitalRequests() {
		String result="";
		try {
			List<MriTestRequest> testRequests= mriTestRequestDBDriver.GetHospitalRequests();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GET
	@Path("/GetRequestsByDept/{deptID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetRequestsByDept(@PathParam("deptID") int deptID) {
		String result="";
		try {
			List<MriTestRequest> testRequests= mriTestRequestDBDriver.GetRequestsByDept(deptID);

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}


	@GET
	@Path("/GetRequestByRequestID/{requestID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetRequestByRequestID(@PathParam("requestID") int requestID) {
		String result="";
		try {
			List<MriTestRequest> testRequests= mriTestRequestDBDriver.GetRequestByRequestID(requestID);

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GET
	@Path("/GetLastRequestID")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetLastRequestID() {
		String result="";
		try {
			int testRequests= mriTestRequestDBDriver.GetLastRequestID();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GET
	@Path("/GetInternalRequestsBySearch/{data1}/{data2}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetInternalRequestsBySearch(@PathParam("data1")  String searchParam,@PathParam("data2")  String searchID) {
		String result="";
		try {
			List<MriTestRequest> testRequests= mriTestRequestDBDriver.GetInternalRequestsBySearch(searchParam,searchID);

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}


	@GET
	@Path("/GettHospitalRequestsBySearch/{data1}/{data2}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GettHospitalRequestsBySearch(@PathParam("data1")  String searchParam,@PathParam("data2")  String searchID) {
		String result="";
		try {
			List<MriTestRequest> testRequests= mriTestRequestDBDriver.GettHospitalRequestsBySearch(searchParam,searchID);

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GET
	@Path("/GetTestRequestCount")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetTestRequestCount() {
		String result="";
		try {
			int requestCount = mriTestRequestDBDriver.GetTestRequestCount();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(requestCount);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GET
	@Path("/GetUrgentTestRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetUrgentTestRequests() {
		String result="";
		try {
			List<MriTestRequest> testRequests= mriTestRequestDBDriver.GetUrgentTestRequests();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GET
	@Path("/GetRequestCountByLabID/{labID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetRequestCountByLabID(@PathParam("labID") int labID) {
		String result="";
		try {
			int testRequestCount= mriTestRequestDBDriver.GetRequestCountByLabID(labID);

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequestCount);

		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
}
