package core.resources.mri;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import core.classes.mri.MriLaboratoryTest;
import lib.driver.mri.driver_class.MriLaboratoryTestDBDriver;
import flexjson.JSONSerializer;


@Path("MriLaboratoryTest")
public class MriLaboratoryTestsResource {
	
MriLaboratoryTestDBDriver mriLaboratoryTestDBDriver= new MriLaboratoryTestDBDriver();
	
	@GET
	@Path("/GetAllLaboratoryTest")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllLaboratoryTest() {
		String result="";
		try {
			List<MriLaboratoryTest> testRequests= mriLaboratoryTestDBDriver.GetAllLabTests();
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testRequests);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
