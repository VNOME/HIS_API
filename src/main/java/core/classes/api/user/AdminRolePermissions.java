package core.classes.api.user;

import java.util.HashSet;
import java.util.Set;

public class AdminRolePermissions implements java.io.Serializable {

	private Integer roleId;
	private Integer permissoinId;
	
	
	public Integer getRoleId() {
		return roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}


	public Integer getPermissoinId() {
		return permissoinId;
	}


	public void setPermissoinId(Integer permissoinId) {
		this.permissoinId = permissoinId;
	}


	public AdminRolePermissions() {
	}

}
