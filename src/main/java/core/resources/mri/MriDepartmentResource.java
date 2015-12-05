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
import org.hibernate.Session;

import lib.SessionFactoryUtil;
import lib.driver.mri.driver_class.MriDepartmentDBDriver;
import lib.driver.mri.driver_class.MriEmployeeDBDriver;
import core.classes.mri.MriDepartment;
import core.classes.mri.MriEmployee;
import core.classes.mri.MriInternalPatient;
import core.classes.mri.MriLaboratory;
import core.classes.mri.MriUserRoles;
import flexjson.JSONException;
import flexjson.JSONSerializer;

@Path("MriDepartment")
public class MriDepartmentResource {

	MriDepartmentDBDriver mriDepartmentDBDriver= new MriDepartmentDBDriver();

	@GET
	@Path("/GetAllDepartments")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllDepartments() {
		String result="";
		try {
			List<MriDepartment> departments= mriDepartmentDBDriver.GetAllDepartments();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(departments);

		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	//insert department
		@POST
		@Path("/InsertNewDepartment")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public String InsertNewEmployee(JSONObject pJson)
		{
			try {

					JSONArray data = pJson.getJSONArray("NewDepartment");

					MriDepartment newDepartment= new MriDepartment();

					newDepartment.setDepartmentName(data.getJSONObject(0).getString("DepartmentName"));
					newDepartment.setLocation(data.getJSONObject(0).getString("DepartmentLocation"));
					newDepartment.setLaboratoryCounts(data.getJSONObject(0).getInt("DepartmentLabCount"));
					newDepartment.setNumberOfMlt(data.getJSONObject(0).getInt("DepartmentNoOfMlt"));
					newDepartment.setNumberOfConsultant(data.getJSONObject(0).getInt("DepartmentNoOfConsultant"));
			
					Session session = SessionFactoryUtil.getSessionFactory().openSession();
					MriEmployee emp = (MriEmployee) session.load(MriEmployee.class, data.getJSONObject(0).getInt("employeeId"));
					newDepartment.setMriEmployee(emp);
					session.close();

					Boolean newDepartmentObject=mriDepartmentDBDriver.InsertNewDepartment(newDepartment);

					String departmentType=data.getJSONObject(0).getString("departmentType");

					System.out.println(departmentType);

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
		@Path("/UpdateDepartment")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public String UpdateEmployee(JSONObject pJson) 
		{
			
			try {
				MriDepartment dept =new MriDepartment();
				
				//emp.setEmployeeId(Integer.parseInt(pJson.getJSONObject("obj").getString("employeeId")));
				
				dept.setDepartmentId(Integer.parseInt(pJson.getJSONObject("obj").getString("deptId")));
				dept.setDepartmentName(pJson.getJSONObject("obj").getString("deptName"));
				dept.setLocation(pJson.getJSONObject("obj").getString("deptLocation"));
				dept.setLaboratoryCounts(pJson.getJSONObject("obj").getInt("deptLabCount"));
				dept.setNumberOfMlt(pJson.getJSONObject("obj").getInt("deptNoOfMLT"));
				dept.setNumberOfConsultant(pJson.getJSONObject("obj").getInt("deptNoOfConsultant"));
				
				
				mriDepartmentDBDriver.UpdateDepartment(dept);
				
				return "True";
				
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage().toString();
			}					
		}
		
		@GET
		@Path("/GetAllLabs")
		@Produces(MediaType.APPLICATION_JSON)
		public String GetAllLabs() {
			String result="";
			try {
				List<MriLaboratory> labs= mriDepartmentDBDriver.GetAllLabs();

				JSONSerializer serializer = new JSONSerializer();

				return serializer.exclude("*.class").serialize(labs);

			} catch (Exception e) {
				return e.getMessage();
			}
		}
		
		
		
		
		
}
