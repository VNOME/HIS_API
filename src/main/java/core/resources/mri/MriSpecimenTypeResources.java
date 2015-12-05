package core.resources.mri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

import lib.driver.mri.driver_class.MriSpecimenTypeDBDriver;
import lib.driver.mri.driver_class.MriTestRequestDBDriver;
import core.classes.lims.Specimen;
import core.classes.mri.MriParentResult;
import core.classes.mri.MriSpecimen;
import core.classes.mri.MriSpecimenType;
import core.classes.mri.MriTestParentFields;
import core.classes.mri.MriTestRequest;
import core.classes.mri.MriTestResult;
import core.classes.mri.MriTestSubFields;
import flexjson.JSONSerializer;

@Path("MriSpecimenType")
public class MriSpecimenTypeResources {

	MriSpecimenTypeDBDriver specimenTypeDBDriver = new MriSpecimenTypeDBDriver();

	@GET
	@Path("/GetAllSpecimenType")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetTestParentFields() {
		try {

			List<MriSpecimenType> specimenList = specimenTypeDBDriver.GetSMriSpecimenType();
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(specimenList);
			// return
			// serializer.include("*.class").exclude("*").serialize(specimenList);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GET
	@Path("/GetAllMriSpecimenTypeDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllSpecimenDetails() {
		try {

			List<MriSpecimenType> specimenList = specimenTypeDBDriver
					.GetSMriSpecimenType();
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("specimenName").exclude("*.class", "")
					.serialize(specimenList);
			// return serializer.exclude("*.class").serialize(specimenList);
			// return
			// serializer.include("specimenId","mriSpecimenType.specimenTypeId","mriTestRequest.testRequestId","specimenBarcode","specimenCollectedPerson","specimenDeliveredDepartmentDate","specimenReceivedDate","storedLocation","storedOrDestroyed").exclude("*").serialize(specimenList);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@POST
	@Path("/addMriSpecimenTypes")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addSpecimenInformation(JSONObject pJson) {
		try {
			JSONArray data = pJson.getJSONArray("NewMriSpecimenType");

			MriSpecimenType specimenType = new MriSpecimenType();		 

			 
			specimenType.setSpecimenName(data.getJSONObject(0).getString("specimenName"));
			System.out.println("________________________"
					+ data.getJSONObject(0).getString("specimenName"));

			specimenTypeDBDriver.addMriSpecimenTypeInformation(specimenType);
 
		} catch (Exception e) {
			System.out.println(e.getMessage());

			return null;
		}
		return "TRUE";
	}
	
}
