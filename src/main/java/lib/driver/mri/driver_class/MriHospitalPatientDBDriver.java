package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.hr.HrEmployeeView;

import core.classes.mri.MriHospitalPatient;
import core.classes.mri.MriPatient;

public class MriHospitalPatientDBDriver {
	
	Transaction tx = null;
	
	
	
//	public List<MriExternalPatient> GetExternalPatientById(int patientID) {
//		tx = session.beginTransaction();
//
//		Query query = session.createQuery("from MriExternalPatient e where e.mriPatient.patientId = '"+patientID+"'");
//		List<MriExternalPatient> patientList= query.list();
//		tx.commit();
//		
//
//		return patientList;
//	}

	public List<MriHospitalPatient> GetAllHospitalpatients() {
		
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		
		Query query = session.createQuery("from MriHospitalPatient");
		List<MriHospitalPatient> hospitalPatient= query.list();
		tx.commit();
		
		return hospitalPatient;
	}



	public List<MriHospitalPatient> GetHospitalPatientsByHID(int hospitalID) {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		
		Query query = session.createQuery("from MriHospitalPatient e where e.mriHospital.hospitalId = '"+hospitalID+"'");
		List<MriHospitalPatient> hospitalPatients= query.list();
		tx.commit();
				
		
		return hospitalPatients;
	}



	public MriHospitalPatient GetHospitalPatientByID(int patientID) {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		tx=null;
		
		tx= session.beginTransaction();
		
		MriHospitalPatient patient=(MriHospitalPatient) session.get(MriHospitalPatient.class, patientID);
		
		tx.commit();
		
		session.close();
		
		return patient;
	}



	public Boolean InsertHospitalNewPatient(MriHospitalPatient newHospitalPatient) {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			
			tx= session.beginTransaction();
			
			session.save(newHospitalPatient);
			
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
