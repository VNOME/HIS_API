package core.resources.api.user;

import java.sql.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lib.driver.api.driver_class.user.AdminPermissionrequestDBDriver;
import lib.driver.api.driver_class.user.PermissionDBDriver;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import core.classes.api.user.AdminPermission;
import core.classes.api.user.AdminPermissionrequest;
import core.classes.api.user.AdminUser;
import flexjson.JSONSerializer;


@Path("AdminPermissionRequestService")
public class AdminPermissionRequestResource {

	
	AdminPermissionrequestDBDriver permissionRequestDBDriver=new AdminPermissionrequestDBDriver();
	/***
	 * Register a new permission into the system.
	 * @param jsnObj is a JSON object that contains about new permission details
	 * @return returns a string value.True if the permission registration successful else it returns false
	 */
	
	@POST
	@Path("/addNewPermissionRequest")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewPermissionRequest(JSONObject jsnObj){
		String result="false";
		boolean r=false;
		AdminPermissionrequest rpObj=new AdminPermissionrequest();
		
		try{
			rpObj.setReqestPermission(jsnObj.getString("reqestPermission"));
			rpObj.setReason(jsnObj.getString("reason"));

			rpObj.setIsActive(true);
			
			rpObj.setRequestDate(java.sql.Date.valueOf(jsnObj.getString("requestDate")));
			
			AdminUser user = new AdminUser();
			user.setUserId(jsnObj.getInt("requester"));
			rpObj.setAdminUserByRequester(user);
			
			r=permissionRequestDBDriver.inserPermissionRequest(rpObj);
			result=String.valueOf(r);
			return result;
			
		}
		
		catch(JSONException ex){
			ex.printStackTrace();	
			return result;
		}		
		
	}
	
	
	
	
	/***
	 * Update permission details.
	 * @param jsnObj is a JSON object that contains new permission details.
	 * @return returns a string value.True if the permissions updated successfully else it returns false
	 */
		
	@PUT
	@Path("/updatePermissionRequest")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String permissionRequestUpdation(JSONObject jsnObj){
		String result="false";
		boolean r=false;
		AdminPermissionrequest rpObj=new AdminPermissionrequest();
		
		try{
			rpObj.setRequestId(jsnObj.getInt("requestId"));
			
			
			AdminUser user = new AdminUser();
			user.setUserId(jsnObj.getInt("approver"));
			
			rpObj.setAdminUserByApprover(user);
			rpObj.setIsApprove(Boolean.valueOf(jsnObj.getString("isApprove")));
			rpObj.setApproveDate(java.sql.Date.valueOf(jsnObj.getString("approveDate")));
			r=permissionRequestDBDriver.updatePermissionRequest(rpObj);
			result=String.valueOf(r);
			return result;
		}
		catch(JSONException ex){
			ex.printStackTrace();	
			return result;
		}		
		
	}
	
	
	

	/***
	 * Delete a particular permission from the system 
	 * @param jsnObj is JSON object that contains data about permission that going to delete
	 * @return returns a string value.True if the permission deleted successfully else it returns false
	 */	
	
	@DELETE
	@Path("/deletePermissionRequest")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deletePermissionRequest(JSONObject jsnObj){
		String result="false";
		boolean r=false;
		AdminPermissionrequest rpObj=new AdminPermissionrequest();
		
		try{
			rpObj.setRequestId(jsnObj.getInt("requestId"));
			r=permissionRequestDBDriver.delete(rpObj);
			result=String.valueOf(r);
			return result;
		}
		catch(JSONException ex){
			ex.printStackTrace();	
			return result;
		}
		
	}
	
	
	
	/***
	 * Get all the permissions that registered in the system
	 * @return returns a JSON object that contains all the registered permissions
	 */
	
	@GET
	@Path("/getAllPermissionRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllPermissionRequests(){
		String result="";
		List<AdminPermissionrequest> permissionRequestList =permissionRequestDBDriver.getAllPermissionRequests();

		JSONSerializer serializor=new JSONSerializer();
		result = serializor.exclude("*.class").serialize(permissionRequestList);
		 
		return result;
	}
	
	

	@GET
	@Path("/getPermissionRequest/{requestId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPermissionRequest(@PathParam("requestId") int requestId){
		String result="";
		List<AdminPermissionrequest> permissionRequestList =permissionRequestDBDriver.getPermissionRequest(requestId);

		JSONSerializer serializor=new JSONSerializer();
		result = serializor.exclude("*.class").serialize(permissionRequestList);
		 
		return result;
	}
	
	@GET
	@Path("/getApprovedPermissionRequest/{requester}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getApprovedPermissionRequest(@PathParam("requester") int requester){
		String result="";
		List<AdminPermissionrequest> permissionRequestList =permissionRequestDBDriver.getApprovedPermissionRequest(requester);

		JSONSerializer serializor=new JSONSerializer();
		result = serializor.exclude("*.class").serialize(permissionRequestList);
		 
		return result;
	}
	

	@GET
	@Path("/getUnApprovedPermissionRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUnApprovedPermissionRequests(){
		String result="";
		List<AdminPermissionrequest> permissionRequestList =permissionRequestDBDriver.getUnApprovedPermissionRequests();

		JSONSerializer serializor=new JSONSerializer();
		result = serializor.exclude("*.class").serialize(permissionRequestList);
		 
		return result;
	}
	
}
