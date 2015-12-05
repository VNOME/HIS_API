package core.resources.mri;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import core.classes.mri.MriEmployee;
import core.classes.mri.MriUserRoles;
import flexjson.JSONException;
import flexjson.JSONSerializer;
import lib.driver.mri.driver_class.MriEmployeeDBDriver;

@Path("MriEmployee")
public class MriEmployeeResource {
	
	MriEmployeeDBDriver mriEmployeeDBDriver= new MriEmployeeDBDriver();

	@GET
	@Path("/GetAllEmployees")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllEmployees() {
		String result="";
		try {
			List<MriEmployee> employees= mriEmployeeDBDriver.GetAllEmployees();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(employees);

		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	//insert employee
	@POST
	@Path("/InsertNewEmployee")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String InsertNewEmployee(JSONObject pJson)
	{
		try {

				JSONArray data = pJson.getJSONArray("NewEmployee");

				MriEmployee newEmployee= new MriEmployee();

				newEmployee.setName(data.getJSONObject(0).getString("EmployeeName"));
				newEmployee.setAge(data.getJSONObject(0).getInt("EmployeeAge"));
				newEmployee.setSex(data.getJSONObject(0).getString("EmployeeSex"));
				newEmployee.setAddress(data.getJSONObject(0).getString("EmployeeAddress"));
				newEmployee.setContactNo1(data.getJSONObject(0).getString("EmployeeContactNo1"));
				newEmployee.setContactNo2(data.getJSONObject(0).getString("EmployeeContactNo2"));
				newEmployee.setExtension(data.getJSONObject(0).getString("EmployeeExtension"));
				newEmployee.setEmail(data.getJSONObject(0).getString("EmployeeEmail"));
				newEmployee.setNic(data.getJSONObject(0).getString("EmployeeNic"));
				
				Boolean newEmployeeObject=mriEmployeeDBDriver.InsertNewEmployee(newEmployee);

				String employeeType=data.getJSONObject(0).getString("employeeType");

				System.out.println(employeeType);

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
	
	
	
	//update employee
	@POST
	@Path("/UpdateEmployee")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String UpdateEmployee(JSONObject pJson) 
	{
		
		try {
			//System.out.println("_____"+pJson );
			
			MriEmployee emp=new MriEmployee();
			//System.out.println("Json obj"+pJson.getJSONObject("obj").getString("roleId") );
			
			
			emp.setEmployeeId(Integer.parseInt(pJson.getJSONObject("obj").getString("employeeId")));
			emp.setName(pJson.getJSONObject("obj").getString("employeeName"));
			emp.setAge(pJson.getJSONObject("obj").getInt("age"));
			emp.setSex(pJson.getJSONObject("obj").getString("sex"));
			emp.setAddress(pJson.getJSONObject("obj").getString("address"));
			emp.setContactNo1(pJson.getJSONObject("obj").getString("ctc1"));
			emp.setContactNo2(pJson.getJSONObject("obj").getString("ctc2"));
			emp.setExtension(pJson.getJSONObject("obj").getString("ext"));
			emp.setEmail(pJson.getJSONObject("obj").getString("email"));
			emp.setNic(pJson.getJSONObject("obj").getString("nic"));
			
			
			//System.out.println("______"+Integer.parseInt(pJson.getString("roleId")));
			
			mriEmployeeDBDriver.UpdateEmployee(emp);
			//System.out.println("mriUserRolesDBDriver.UpdateUserRoles(ur)");
			
			return "True";
			
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage().toString();
		}					
	}
	
	
	
	
	}
	
	


