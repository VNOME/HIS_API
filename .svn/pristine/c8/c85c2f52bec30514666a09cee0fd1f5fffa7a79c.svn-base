/**
 * 
 */
package lib.driver.opd.driver_class;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.opd.*;

/**
 * @author Prabhath
 * 
 */
public class PrescriptionDBDriver {

	Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
	
	public boolean insertPrescription(Prescription prescription,int visitID) {

		Transaction tx = null;
		try {
			if(!session.isOpen())
				session = SessionFactoryUtil.getSessionFactory().openSession();

			tx = session.beginTransaction();
			Visit visit=(Visit) session.get(Visit.class, visitID);
			prescription.setVisit(visit);
			
			session.save(prescription);
		 
			for (PrescribeItem item : prescription.prescribeItems) {
				item.setPrescription(prescription);
				session.save(item);
			}
			 
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

	
	
	public boolean updatePrescription(Prescription pres,int presid) {

		Transaction tx = null;
		try {
			if(!session.isOpen())
				session = SessionFactoryUtil.getSessionFactory().openSession();

			tx = session.beginTransaction();
			Prescription prescription=(Prescription) session.get(Prescription.class, presid);
		   
			Query query = session.createQuery("delete PrescribeItem where prescription=:p");
			query.setParameter("p", prescription);
			query.executeUpdate();

		 	for (PrescribeItem item : pres.prescribeItems) {
				item.setPrescription(prescription);
				session.merge(item);
			} 
		 	
			tx.commit();
			return true;
		} catch (Exception ex) {
			System.out.println("DB " + ex.getMessage());
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

	
	
	public Prescription getPrescription(int PRES_ID) {

		Transaction tx = null;
		try {
			
			tx = session.beginTransaction();

			Query query = session.createQuery("from Prescription where prescriptionID = :PRES_ID");
			query.setParameter("PRES_ID", PRES_ID);

			Prescription prescription = (Prescription) query.list().get(0);

			tx.commit();
			return prescription;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return null;
		}

	}

	public List<Prescription> getPrescriptionsByPatientID(int patientID) {

		Transaction tx = null;
		try {
			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			 
			tx = session.beginTransaction();

			OutPatient patient = (OutPatient) session.get(OutPatient.class, patientID);
 
			Query query = session
					.createQuery("from Prescription as p where (p.visit.patient = :patient AND p.prescriptionStatus=0 AND p.prescriptionDate='" + dateformat.format(new Date()) +"')");

			query.setParameter("patient", patient);
			
			List<Prescription> prescription = query.list();
			System.out.println(prescription.size());
			
			tx.commit();
			return prescription;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return null;
		}

	}
	
	public boolean updatePrescription(Prescription pres){
		
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Prescription prescription = (Prescription) session.get(Prescription.class, pres.getPrescriptionID());
			prescription.setPrescriptionStatus(pres.getPrescriptionStatus());
			session.update(prescription);
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
