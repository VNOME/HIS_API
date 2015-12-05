package core.resources.lims;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import core.classes.lims.Category;
import core.classes.lims.LabDepartments;
import core.classes.lims.LabTypes;
import core.classes.lims.SampleCenterTypes;
import flexjson.JSONSerializer;
import lib.driver.lims.driver_class.CategoryDBDriver;
import lib.driver.lims.driver_class.LabDepartmentDBDriver;
import lib.driver.lims.driver_class.LabTypeDBDriver;
import lib.driver.lims.driver_class.SampleCenterTypeDBDriver;

@Path("LabDepartments")
public class LabDepartmentsResource {

LabDepartmentDBDriver ldDBDriver= new LabDepartmentDBDriver();
	
	@POST
	@Path("/addLabDepartment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addLabDepartment(JSONObject pJson)
	{
		
		try {
			LabDepartments dType  =  new LabDepartments();
			
			
			dType.setLabDept_Name(pJson.getString("labDept_Name").toString());
			ldDBDriver.insertNewLabDepartment(dType);
			
			
			
			
			JSONSerializer jsonSerializer = new JSONSerializer();
			return jsonSerializer.include("labDept_ID").serialize(dType);
		} catch (Exception e) {
			 System.out.println(e.getMessage());
			return null; 
		}

	}
	
	@GET
	@Path("/getAllLabDepartments")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllLabDepartments()
	{
		List<LabDepartments> labDepartmentsList =  ldDBDriver.getLabDepartmentsList();
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.exclude("*.class").serialize(labDepartmentsList);
	}
	
}
