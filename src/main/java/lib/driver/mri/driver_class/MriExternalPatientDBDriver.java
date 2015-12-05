package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.mri.MriInternalPatient;

public class MriExternalPatientDBDriver {
	Session session=SessionFactoryUtil.getSessionFactory().openSession();
	Transaction tx = null;
	
	public List<MriInternalPatient> GetAllExternalPatients() {
		tx = session.beginTransaction();
		
		Query query = session.createQuery("from MriInternalPatient");
		List<MriInternalPatient> externalPatient= query.list();
		tx.commit();
		
		return externalPatient;
	}
	
	public List<MriInternalPatient> GetExternalPatientById(int patientID) {
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriInternalPatient e where e.mriPatient.patientId = '"+patientID+"'");
		List<MriInternalPatient> patientList= query.list();
		tx.commit();
		

		return patientList;
	}

	public Boolean InsertInternalNewPatient(MriInternalPatient newInternalPatient) {
		Transaction tx = null;
		try {
			
			
			
			tx= session.beginTransaction();
			
			session.save(newInternalPatient);
			
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
