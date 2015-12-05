package core.resources.api.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lib.driver.api.driver_class.user.RolePermissionDBDriver;
import core.classes.api.user.AdminPermission;
import core.classes.api.user.AdminRolePermissions;

@Path("UserRolePermissionService")
public class RolePermissionResource {

	RolePermissionDBDriver rolePermissionDBDriver = new RolePermissionDBDriver();
			

	
	@Path("/InsertRolePermission/{roleId}/{permissionId}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String InsertRolePermission(@PathParam("roleId") int roleId, @PathParam("permissionId") int permissionId){
		String result="false";
		boolean r=false;
		AdminRolePermissions rpObj=new AdminRolePermissions();
		rpObj.setRoleId(roleId);
		rpObj.setPermissoinId(permissionId);
		
		try{
			r=rolePermissionDBDriver.insertUserRolePermission(rpObj);
			result=String.valueOf(r);
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();	
			return result;
		}
		
	}
	
	
	@Path("/DeleteRolePermission/{roleId}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String DeleteRolePermission(@PathParam("roleId") int roleId){
		String result="false";
		boolean r=false;
		
		try{
			r=rolePermissionDBDriver.DeleteUserRolePermission(roleId);
			result=String.valueOf(r);
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();	
			return result;
		}
		
	}
}
