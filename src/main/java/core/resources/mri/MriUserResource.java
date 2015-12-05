package core.resources.mri;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lib.SessionFactoryUtil;
import lib.classes.securitymodel.encryption.DataHashing;
import lib.driver.mri.driver_class.MriDepartmentDBDriver;
import lib.driver.mri.driver_class.MriUserDBDriver;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

import core.classes.api.user.AdminUser;
import core.classes.mri.MriEmployee;
import core.classes.mri.MriUser;
import core.classes.mri.MriUserRoles;
import flexjson.JSONException;
import flexjson.JSONSerializer;

@Path("MriUser")
public class MriUserResource {
	
	MriUserDBDriver mriUserDBDriver= new MriUserDBDriver();
	DataHashing dataHashing = new DataHashing();
	
	@POST
	@Path("/registerUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String registerUser( JSONObject uJson)
	{
		try {

			JSONArray data = uJson.getJSONArray("NewUser");

			MriUser newUser = new MriUser();
			
			Session session1 = SessionFactoryUtil.getSessionFactory().openSession();
			MriEmployee emp = (MriEmployee) session1.load(MriEmployee.class, data.getJSONObject(0).getInt("employeeId"));
			newUser.setMriEmployee(emp);
			session1.close();
			
			Session session2 = SessionFactoryUtil.getSessionFactory().openSession();
			MriUserRoles uRoles = (MriUserRoles) session2.load(MriUserRoles.class, data.getJSONObject(0).getInt("roleName"));
			newUser.setMriUserRoles(uRoles);
			session2.close();
			
			newUser.setUserName(data.getJSONObject(0).getString("userName"));
			newUser.setPassword(data.getJSONObject(0).getString("Password"));
			newUser.setPassword(dataHashing.createHash(data.getJSONObject(0).getString("Password")));
			
			Boolean newUserObject=mriUserDBDriver.registerUser(newUser);

			String userType=data.getJSONObject(0).getString("userType");

			System.out.println(userType);

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
	
	
	//user validation
	@POST
	@Path("/userValidation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String userValidation(JSONObject jsnObj) throws org.codehaus.jettison.json.JSONException{
		String result="false";
		//boolean r=false;
		System.out.println("_____IM Here");
		System.out.println(jsnObj);
		//JSONArray data = jsnObj.getJSONArray("NewUser");
		 MriUser usr = new MriUser();
		
		try{
			usr.setUserName(jsnObj.getString("user_name"));
			
			usr.setPassword(jsnObj.getString("password"));
			
			if(mriUserDBDriver.validateUserLoginDetails(usr)){
				List<MriUser> validUser  = mriUserDBDriver.getValidUserDetails(usr);
				JSONSerializer serializor=new JSONSerializer();
				result = serializor.include("userId","mriUserRoles.roleName","mriEmployee.name","userName","adminUserroles.adminPermissions.permissionDiscription").exclude("*").serialize(validUser);
				System.out.println(result);
				return result;
			}
			else{
				System.out.println(result);
				return result;
			}			
		}
		catch(JSONException ex){
			ex.printStackTrace();
			return result;
		}
		
	}

}
