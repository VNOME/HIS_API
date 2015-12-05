package core.classes.api.user;
import java.util.HashSet;
import java.util.Set;

import core.classes.hr.*;;

/**
 * AdminUser generated by hbm2java
 */
public class AdminUser implements java.io.Serializable {

	private Integer userId;
	private AdminUserroles adminUserroles;
	private HrEmployee hrEmployee;
	private String userName;
	private String password;
	private boolean isActive;
	private Set adminPermissionrequestsForRequester = new HashSet(0);
	private Set adminPermissionrequestsForApprover = new HashSet(0);

	public AdminUser() {
	}

	public AdminUser(AdminUserroles adminUserroles, HrEmployee hrEmployee,
			String userName, String password, boolean isActive) {
		this.adminUserroles = adminUserroles;
		this.hrEmployee = hrEmployee;
		this.userName = userName;
		this.password = password;
		this.isActive = isActive;
	}

	public AdminUser(AdminUserroles adminUserroles, HrEmployee hrEmployee,
			String userName, String password, boolean isActive,
			Set adminPermissionrequestsForRequester,
			Set adminPermissionrequestsForApprover) {
		this.adminUserroles = adminUserroles;
		this.hrEmployee = hrEmployee;
		this.userName = userName;
		this.password = password;
		this.isActive = isActive;
		this.adminPermissionrequestsForRequester = adminPermissionrequestsForRequester;
		this.adminPermissionrequestsForApprover = adminPermissionrequestsForApprover;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public AdminUserroles getAdminUserroles() {
		return this.adminUserroles;
	}

	public void setAdminUserroles(AdminUserroles adminUserroles) {
		this.adminUserroles = adminUserroles;
	}

	public HrEmployee getHrEmployee() {
		return this.hrEmployee;
	}

	public void setHrEmployee(HrEmployee hrEmployee) {
		this.hrEmployee = hrEmployee;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Set getAdminPermissionrequestsForRequester() {
		return this.adminPermissionrequestsForRequester;
	}

	public void setAdminPermissionrequestsForRequester(
			Set adminPermissionrequestsForRequester) {
		this.adminPermissionrequestsForRequester = adminPermissionrequestsForRequester;
	}

	public Set getAdminPermissionrequestsForApprover() {
		return this.adminPermissionrequestsForApprover;
	}

	public void setAdminPermissionrequestsForApprover(
			Set adminPermissionrequestsForApprover) {
		this.adminPermissionrequestsForApprover = adminPermissionrequestsForApprover;
	}

}
