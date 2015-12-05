package core.resources.mri;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lib.driver.mri.driver_class.MriHospitalDBDriver;
import lib.driver.mri.driver_class.MriPatientDBDriver;
import lib.driver.mri.driver_class.MriTestRequestDBDriver;

import core.classes.mri.MriHospital;
import core.classes.mri.MriPatient;
import flexjson.JSONSerializer;


@Path("MriHospital")
public class MriHospitalResource {
	
	
	MriHospitalDBDriver mriHospitalDBDriver= new MriHospitalDBDriver();
	
	@GET
	@Path("/GetAllHospitals")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllHospitals() {
		String result="";
		try {
			List<MriHospital> testRequests= mriHospitalDBDriver.GetAllHospitals();
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GET
	@Path("/GetHospitalById/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetHospitalById(@PathParam("data")  int hospitalID) { // eka parameter ekak ena eka
				
		try {
			List<MriHospital> hospitalData = mriHospitalDBDriver.GetHospitalById(hospitalID); // dbaccess
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(hospitalData);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
