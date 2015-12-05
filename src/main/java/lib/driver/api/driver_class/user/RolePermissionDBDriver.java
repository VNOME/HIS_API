package lib.driver.api.driver_class.user;

import lib.SessionFactoryUtil;
import lib.classes.CasttingMethods.CastList;
import lib.classes.DBDriverBase.DBDriverBase;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import core.classes.api.user.AdminRolePermissions;
import core.classes.api.user.AdminUser;
import core.classes.api.user.AdminUserroles;

public class RolePermissionDBDriver  extends DBDriverBase<AdminRolePermissions> {

	Session session = SessionFactoryUtil.getSessionFactory().openSession();

	public boolean insertUserRolePermission(AdminRolePermissions usRlObj){
		
		Transaction tx=null;
		AdminRolePermissions updateUserObj=new AdminRolePermissions();
		
		try{
			tx=session.beginTransaction();
			updateUserObj.setRoleId(usRlObj.getRoleId());	
			updateUserObj.setPermissoinId(usRlObj.getPermissoinId());
			
			session.save(updateUserObj);
			tx.commit();
			return true;
		}
		
		catch (RuntimeException ex) {
			if(tx != null && tx.isActive()){
				try{
					tx.rollback();
				}catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
		
		
	}
	
public boolean DeleteUserRolePermission(int roleId){
		
		Transaction tx=null;
		
		try{
			tx=session.beginTransaction();
			Query query =  session.createQuery("select u from AdminRolePermissions as u where u.roleId=:roleId");
			query.setParameter("roleId", roleId);
			List<AdminRolePermissions> rolePList =CastList.castList(AdminRolePermissions.class, query.list()); 
			
			for (AdminRolePermissions adminRolePermissions : rolePList) {
				session.delete(adminRolePermissions);
			}
			tx.commit();
			
			return true;
		}
		
		catch (RuntimeException ex) {
			if(tx != null && tx.isActive()){
				try{
					tx.rollback();
				}catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
		
		
	}
	
}
