package core.resources.mri;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import lib.driver.mri.driver_class.MriExternalPatientDBDriver;
import lib.driver.mri.driver_class.MriHospitalDBDriver;
import lib.driver.mri.driver_class.MriHospitalPatientDBDriver;
import lib.driver.mri.driver_class.MriPatientDBDriver;
import lib.driver.mri.driver_class.MriTestRequestDBDriver;
import core.classes.mri.MriInternalPatient;
import core.classes.mri.MriHospital;
import core.classes.mri.MriHospitalPatient;
import core.classes.mri.MriLaboratoryTest;
import core.classes.mri.MriPatient;
import core.classes.mri.MriSpecimen;
import core.classes.mri.MriTestRequest;
import flexjson.JSONSerializer;


@Path("MriPatient")
public class MriPatientResource {

	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	MriPatientDBDriver mriPatientDBDriver= new MriPatientDBDriver();
	MriExternalPatientDBDriver mriExternalPatientDBDriver= new MriExternalPatientDBDriver();
	MriHospitalPatientDBDriver mriHospitalPatientDBDriver= new MriHospitalPatientDBDriver();
	MriHospitalDBDriver mriHospitalDBDriver = new MriHospitalDBDriver();

	@GET
	@Path("/GetAllPatients")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllPatients(@Context HttpHeaders headers) {

    	String userAgent = headers.getRequestHeader("Authorization").get(0);
    	System.out.println(userAgent);
    	String ua = userAgent.toString();
    	if( userAgent == ua){
		String result="";
		try {
			List<MriPatient> testRequests= mriPatientDBDriver.GetAllPatients();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
    	}
    	else{
    		System.out.println("Error");

    	}
    	return null;
    	//
	}

	@POST
	@Path("/InsertNewPatient")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String InsertNewPatient(JSONObject pJson)
	{
		try {

				JSONArray data = pJson.getJSONArray("NewPatient");

				MriPatient newPatient= new MriPatient();

				newPatient.setName(data.getJSONObject(0).getString("PatientName"));
				newPatient.setBirthday(dateformat.parse(data.getJSONObject(0).getString("PatientBday")));
				newPatient.setSex(data.getJSONObject(0).getString("PatientGender"));
				newPatient.setNic(data.getJSONObject(0).getString("PatientNic"));
				newPatient.setIsActive(true);
				Boolean newPatientObject=mriPatientDBDriver.InsertNewPatient(newPatient);

				String patientType=data.getJSONObject(0).getString("PatientType");

				System.out.println(patientType);

				if (patientType.equals("Internal Patient")){
					MriInternalPatient newInternalPatient= new MriInternalPatient();

					newInternalPatient.setMriPatient(newPatient);
					newInternalPatient.setAddress(data.getJSONObject(0).getString("PatientAddress"));
					newInternalPatient.setContactNo1(data.getJSONObject(0).getString("PatientContact1"));
					newInternalPatient.setContactNo2(data.getJSONObject(0).getString("PatientContact2"));

					Boolean internalPatient=mriExternalPatientDBDriver.InsertInternalNewPatient(newInternalPatient);
				}
				if (patientType.equals("External Patient")){
					System.out.println("I'm External");
					MriHospitalPatient newHospitalPatient=new MriHospitalPatient();

					int hospitalID=data.getJSONObject(0).getInt("PatientHospital");
					MriHospital hospital= mriHospitalDBDriver.GetHospitalObject(hospitalID);

					newHospitalPatient.setMriHospital(hospital);
					newHospitalPatient.setMriPatient(newPatient);
					newHospitalPatient.setWard(data.getJSONObject(0).getString("PatientWard"));
					newHospitalPatient.setBhtNo(data.getJSONObject(0).getString("PatientBht"));

					Boolean hospitalP=mriHospitalPatientDBDriver.InsertHospitalNewPatient(newHospitalPatient);
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

	@GET
	@Path("/GetPatientCount")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetPatientCount() {
		String result="";
		try {
			int patientCount = mriPatientDBDriver.GetPatientCount();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(patientCount);

		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
