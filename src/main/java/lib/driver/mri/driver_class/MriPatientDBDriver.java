package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.hr.HrEmployee;
import core.classes.mri.MriHospital;
import core.classes.mri.MriPatient;


public class MriPatientDBDriver {
	
	Session session=SessionFactoryUtil.getSessionFactory().openSession();
	Transaction tx = null;
	
	public List<MriPatient> GetAllPatients() {
		tx = session.beginTransaction();
		
		Query query = session.createQuery("from MriPatient");
		List<MriPatient> testRequests= query.list();
		tx.commit();
		
		return testRequests;
	}

	public MriPatient GetPatientByID(int patientID) {
		
		tx=null;
		
		tx= session.beginTransaction();
		
		MriPatient patient=(MriPatient) session.get(MriPatient.class, patientID);
		
		tx.commit();
		
		return patient;
		
		
	}

	public Boolean InsertNewPatient(MriPatient newPatient) {
		Transaction tx = null;
		try {
			
			//tx = null;
			
			tx= session.beginTransaction();
			
			session.save(newPatient);
			
			
			
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

	public int GetPatientCount() {
		tx = session.beginTransaction();

		Query query = session.createQuery("select COUNT(p.patientId) from MriPatient p where p.isActive='1'");
		
		
		List listRes = query.list();
		
		int patientCount = (int) ((Long) listRes.get(0)).longValue();
		tx.commit();
		
		return patientCount;
	}

	
}
