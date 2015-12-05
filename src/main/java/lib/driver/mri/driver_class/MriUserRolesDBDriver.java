package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.hr.HrEmployee;
import core.classes.mri.MriUserRoles;

public class MriUserRolesDBDriver {
	
	Session session=SessionFactoryUtil.getSessionFactory().openSession();
	Transaction tx = null;

	public List<MriUserRoles> GetAllUserRoles() {
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriUserRoles");
		List<MriUserRoles> userRoles= query.list();
		tx.commit();

		return userRoles;
	}

	public MriUserRoles GetUserRolesByID(int roleId) {

		tx=null;

		tx= session.beginTransaction();

		MriUserRoles userRoles =(MriUserRoles) session.get(MriUserRoles.class, roleId);

		tx.commit();

		return userRoles;


	}
	
	public Boolean InsertNewUserRoles(MriUserRoles newUserRoles) {
		Transaction tx = null;
		try {
			
			//tx = null;
			
			tx= session.beginTransaction();
			
			session.save(newUserRoles);
			
			
			
			tx.commit();
			//session.close();
			return true;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				
				throw ex;
			}
			
			return false;
			
		} 
		
		
	}
	
	/*public Boolean UpdateUserRoles(int roleId, String roleName) {
		Transaction tx = null;
		try {
			
			//tx = null;
			
			tx= session.beginTransaction();
			MriUserRoles uRoles = (MriUserRoles) session.get(MriUserRoles.class,roleId);
			uRoles.setRoleName(roleName);
			
			session.update(uRoles);
			tx.commit();
			//session.close();
			return true;
			
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				
				throw ex;
			}
			
			return false;
			
		} 
		
		
	}*/
	
	public boolean UpdateUserRoles(MriUserRoles ur) {
		System.out.println("Hello___________________________");
		Transaction tx=null;
		
		int roleId= ur.getRoleId();
		System.out.println("Role id"+roleId );
		
		try {
			//System.out.println(emp.getTitle());
			tx= session.beginTransaction();
			MriUserRoles updateUr=(MriUserRoles) session.get(MriUserRoles.class, roleId);
			
			updateUr.setRoleName(ur.getRoleName());
			System.out.println("Role name"+ ur.getRoleName());
			
			session.update(updateUr);
			tx.commit();
			return true;
			
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
		
	}

}
