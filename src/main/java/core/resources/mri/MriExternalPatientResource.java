package core.resources.mri;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lib.driver.mri.driver_class.MriExternalPatientDBDriver;
import lib.driver.mri.driver_class.MriPatientDBDriver;
import core.classes.hr.HrEmployeeView;
import core.classes.mri.MriInternalPatient;
import core.classes.mri.MriPatient;
import flexjson.JSONSerializer;


@Path("MriExternalPatient")
public class MriExternalPatientResource {

	MriExternalPatientDBDriver mriExternalPatientDBDriver= new MriExternalPatientDBDriver();

	@GET
	@Path("/GetAllExternalPatients")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllExternalPatients() {
		String result="";
		try {
			List<MriInternalPatient> testRequests= mriExternalPatientDBDriver.GetAllExternalPatients();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GET
	@Path("/GetExternalPatientById/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetExternalPatientById(@PathParam("data")  int patientID) { 

		try {
			List<MriInternalPatient> patientList = mriExternalPatientDBDriver.GetExternalPatientById(patientID); // dbaccess

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(patientList);

		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
