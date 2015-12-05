package lib.driver.mri.driver_class;

import java.util.List;

import javax.ws.rs.Path;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.mri.MriEmployee;
import core.classes.mri.MriUserRoles;

public class MriEmployeeDBDriver {
	
	Session session=SessionFactoryUtil.getSessionFactory().openSession();
	Transaction tx = null;

	public List<MriEmployee> GetAllEmployees() {
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriEmployee");
		List<MriEmployee> employees= query.list();
		tx.commit();

		return employees;
	}
	
	//insert employee
	public Boolean InsertNewEmployee(MriEmployee newEmployee) {
		Transaction tx = null;
		try {
			
			//tx = null;
			
			tx= session.beginTransaction();
			
			session.save(newEmployee);
			
			
			
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
	

	
	//update employee
	public boolean UpdateEmployee(MriEmployee emp) {
		System.out.println("Hello___________________________");
		Transaction tx=null;
		
		int employeeId= emp.getEmployeeId();
		//System.out.println("Role id"+roleId );
		
		try {
			//System.out.println(emp.getTitle());
			tx= session.beginTransaction();
			MriEmployee uEmp=(MriEmployee) session.get(MriEmployee.class, employeeId);
			
			uEmp.setName(emp.getName());
			uEmp.setAge(emp.getAge());
			uEmp.setSex(emp.getSex());
			uEmp.setAddress(emp.getAddress());
			uEmp.setContactNo1(emp.getContactNo1());
			uEmp.setContactNo2(emp.getContactNo2());
			uEmp.setExtension(emp.getExtension());
			uEmp.setEmail(emp.getEmail());
			uEmp.setNic(emp.getNic());
			
			//System.out.println("Role name"+ ur.getRoleName());
			
			session.update(uEmp);
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
