package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.mri.MriDepartment;
import core.classes.mri.MriEmployee;
import core.classes.mri.MriLaboratory;
import core.classes.mri.MriUserRoles;

public class MriDepartmentDBDriver {

	Session session=SessionFactoryUtil.getSessionFactory().openSession();
	Transaction tx = null;

	public List<MriDepartment> GetAllDepartments() {
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriDepartment");
		List<MriDepartment> departments= query.list();
		tx.commit();

		return departments;
	}

	public MriDepartment GetDepartmentByID(int departmentID) {

		tx=null;

		tx= session.beginTransaction();

		MriDepartment department =(MriDepartment) session.get(MriDepartment.class, departmentID);

		tx.commit();

		return department;


	}
	
	//insert department
		public Boolean InsertNewDepartment(MriDepartment newDepartment) {
			Transaction tx = null;
			try {
				
				//tx = null;
				
				tx= session.beginTransaction();
				
				session.save(newDepartment);
				
				
				
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
		
		//update department
		public boolean UpdateDepartment(MriDepartment dept) {
			Transaction tx=null;
			
			int deptId= dept.getDepartmentId();
			
			
			try {
				//System.out.println(emp.getTitle());
				tx= session.beginTransaction();
				MriDepartment updateDept =(MriDepartment) session.get(MriDepartment.class, deptId);
				
				updateDept.setDepartmentName(dept.getDepartmentName());
				updateDept.setLocation(dept.getLocation());
				updateDept.setLaboratoryCounts(dept.getLaboratoryCounts());
				updateDept.setNumberOfMlt(dept.getNumberOfMlt());
				updateDept.setNumberOfConsultant(dept.getNumberOfConsultant());
				
				session.update(updateDept);
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
		
		public List<MriLaboratory> GetAllLabs() {
			tx = session.beginTransaction();

			Query query = session.createQuery("from MriLaboratory");
			List<MriLaboratory> labs= query.list();
			tx.commit();

			return labs;
		}
		
		
}
