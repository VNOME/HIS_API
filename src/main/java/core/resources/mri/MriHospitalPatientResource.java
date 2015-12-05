package core.resources.mri;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lib.driver.mri.driver_class.MriExternalPatientDBDriver;
import lib.driver.mri.driver_class.MriHospitalPatientDBDriver;
import lib.driver.mri.driver_class.MriPatientDBDriver;
import core.classes.hr.HrEmployeeView;
import core.classes.mri.MriHospitalPatient;
import core.classes.mri.MriPatient;
import flexjson.JSONSerializer;


@Path("MriHospitalPatient")
public class MriHospitalPatientResource {
	
	MriHospitalPatientDBDriver mriHospitalPatientDBDriver= new MriHospitalPatientDBDriver();
	
	@GET
	@Path("/GetAllHospitalpatients")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllExternalPatients() {
		String result="";
		try {
			List<MriHospitalPatient> hospitalPatients= mriHospitalPatientDBDriver.GetAllHospitalpatients();
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(hospitalPatients);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GET
	@Path("/GetHospitalPatientsByHID/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetExternalPatientById(@PathParam("data")  int hospitalID) { // eka parameter ekak ena eka
				
		try {
			List<MriHospitalPatient> hospitalPatients= mriHospitalPatientDBDriver.GetHospitalPatientsByHID(hospitalID); // dbaccess
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(hospitalPatients);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
