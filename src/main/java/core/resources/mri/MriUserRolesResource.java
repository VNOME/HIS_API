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
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import core.classes.hr.HrEmployee;
import core.classes.mri.MriPatient;
import core.classes.mri.MriUserRoles;
import flexjson.JSONException;
import flexjson.JSONSerializer;
import lib.driver.mri.driver_class.MriUserRolesDBDriver;

@Path("MriUserRoles")
public class MriUserRolesResource {
	
	MriUserRolesDBDriver mriUserRolesDBDriver= new MriUserRolesDBDriver();
	
	@GET
	@Path("/GetAllUserRoles")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllUserRoles() {
		String result="";
		try {
			List<MriUserRoles> userRoles = mriUserRolesDBDriver.GetAllUserRoles();

			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(userRoles);

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@POST
	@Path("/InsertNewUserRoles")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String InsertNewUserRoles(JSONObject pJson)
	{
		try {

				JSONArray data = pJson.getJSONArray("NewUserRole");

				MriUserRoles newUserRoles= new MriUserRoles();

				newUserRoles.setRoleName(data.getJSONObject(0).getString("roleName"));
				
				Boolean newUserRolesObject=mriUserRolesDBDriver.InsertNewUserRoles(newUserRoles);

				String UserRolesType=data.getJSONObject(0).getString("UserRolesType");

				System.out.println(UserRolesType);

				
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
	
	/*@POST
	@Path("/UpdateUserRoles")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String UpdateUserRoles(JSONObject pJson)
	{
		
		
		try {
			int roleId =pJson.getInt("roleId");
			String roleName = pJson.getString("roleName");
			
			mriUserRolesDBDriver.UpdateUserRoles(roleId,roleName);
			
			
			return "True";
		}
		catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}


	}*/
	
	@POST
	@Path("/UpdateUserRoles")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String UpdateEmployee(JSONObject pJson) 
	{
		
		try {
			//System.out.println("_____"+pJson );
			
			MriUserRoles ur=new MriUserRoles();
			System.out.println("Json obj"+pJson.getJSONObject("obj").getString("roleId") );
			
			
			ur.setRoleId(Integer.parseInt(pJson.getJSONObject("obj").getString("roleId")));
			ur.setRoleName(pJson.getJSONObject("obj").getString("roleName"));
			
			//System.out.println("______"+Integer.parseInt(pJson.getString("roleId")));
			
			mriUserRolesDBDriver.UpdateUserRoles(ur);
			System.out.println("mriUserRolesDBDriver.UpdateUserRoles(ur)");
			
			return "True";
			
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage().toString();
		}					
	}

	
}
