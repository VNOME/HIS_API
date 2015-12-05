package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.mri.MriDepartment;
import core.classes.mri.MriLaboratoryTest;
import core.classes.mri.MriSpecimen;
import core.classes.mri.MriTestRequest;

public class MriSpecimenDBDriver {

	Session session = SessionFactoryUtil.getSessionFactory().openSession();
	Transaction tx = null;

	public List<MriSpecimen> GetSpecimens() {
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriSpecimen");
		List<MriSpecimen> specimensList = query.list();
		tx.commit();

		return specimensList;
	}

	public List<MriSpecimen> GetSpecimensByReqestID(int mriTestRequestid) {
		tx = session.beginTransaction();

		String hql = "select v from MriSpecimen as v where v.mriTestRequests.testRequestId=:mriTestRequestID";
		Query query = session.createQuery(hql);
		query.setParameter("mriTestRequestID", mriTestRequestid);
		List<MriSpecimen> specimensList = query.list();
		tx.commit();

		return specimensList;
	}

	public boolean addSpecimenInformation(MriSpecimen specimen) {

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			System.out.println("beginTransaction MriSpecimen");
			System.out.println(specimen.getMriSpecimenType().getSpecimenName());
			session.save(specimen);

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

	public MriSpecimen GetSpecimenByID(int specimentID) {

		tx = null;
		tx = session.beginTransaction();

		MriSpecimen speciment = (MriSpecimen) session.get(MriSpecimen.class,
				specimentID);
		System.out.println("Speciment Mee : " + specimentID);

		tx.commit();

		// session.close();
		return speciment;
	}

	public List<MriSpecimen> getSpecimenDetailsByRequestID(int mriTestRequestid) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// String hql =
			// "select v from MriSpecimen as v where v.mriTestRequests.testRequestId=:mriTestRequestID";
			String hql = "select v from MriSpecimen as v where v.requestId=:mriTestRequestID";
			Query query = session.createQuery(hql);
			query.setParameter("mriTestRequestID", mriTestRequestid);

			List<MriSpecimen> specimensList = query.list();
			tx.commit();
			return specimensList;

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

	public List<MriTestRequest> retrieveTestRequestDetailByRequestIDs(int sID,
			int eID) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "select v from MriTestRequest as v where  v.mriSpecimen.specimenId BETWEEN :SID AND :EID";
			// String
			// hql="select v from MriTestRequest as v where v.mriSpecimen.specimenId BETWEEN :SID AND :EID";
			// String
			// hql="select v from Specimen as v where v.flabtestrequest_ID.labTestRequest_ID BETWEEN 26 AND 30";
			Query query = session.createQuery(hql);
			query.setParameter("SID", sID);
			query.setParameter("EID", eID);
			// Specimen requestRec = (Specimen) query.list().get(0);
			List<MriTestRequest> specimentIDList = query.list();
			tx.commit();
			return specimentIDList;

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

	public List<MriSpecimen> retrieveSpecimenDetailByRequestIDs(int sID, int eID) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// String hql =
			// "select v from MriSpecimen as v where v.mriTestRequests.testRequestId BETWEEN :SID AND :EID";
			String hql = "select v from MriSpecimen as v where v.requestId BETWEEN :SID AND :EID";
			// String
			// hql="select v from MriTestRequest as v where v.mriSpecimen.specimenId BETWEEN :SID AND :EID";
			// String
			// hql="select v from Specimen as v where v.flabtestrequest_ID.labTestRequest_ID BETWEEN 26 AND 30";
			Query query = session.createQuery(hql);
			query.setParameter("SID", sID);
			query.setParameter("EID", eID);
			// Specimen requestRec = (Specimen) query.list().get(0);
			List<MriSpecimen> specimentIDList = query.list();
			tx.commit();
			return specimentIDList;

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

	public List<MriTestRequest> getTestRequestDetailByRequestIDs(int sID) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "select v from MriTestRequest as v where  v.mriSpecimen.specimenId=:SID ";
			// String
			// hql="select v from MriTestRequest as v where v.mriSpecimen.specimenId BETWEEN :SID AND :EID";
			// String
			// hql="select v from Specimen as v where v.flabtestrequest_ID.labTestRequest_ID BETWEEN 26 AND 30";
			Query query = session.createQuery(hql);
			query.setParameter("SID", sID);

			// Specimen requestRec = (Specimen) query.list().get(0);
			List<MriTestRequest> specimentIDList = query.list();
			tx.commit();
			return specimentIDList;

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
	
	public boolean updateTestRequestSpecimenID(MriTestRequest testRequest) {

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			int fspecimenID = testRequest.getMriSpecimen().getSpecimenId();
			String requestID = testRequest.getRequestId();
			
			Query query = session.createQuery("update mri_test_request set fspecimen_id = :fspecimenID" +
    				" where request_id = :test_requestID");
			query.setParameter("fspecimen_id", fspecimenID);
			query.setParameter("request_id", requestID);
			int result = query.executeUpdate();
						
			System.out.println("result");
			 

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

	public MriSpecimen InsertNewSpecimen(MriSpecimen newSpecimen) {
		Transaction tx = null;
		int specimenID=0;
		try {
			tx = session.beginTransaction();
			System.out.println("beginTransaction MriSpecimen");
			//System.out.println(specimen.getMriSpecimenType().getSpecimenName());
			session.save(newSpecimen);
			specimenID=newSpecimen.getSpecimenId();
			//System.out.println("specimenID: "+specimenID);
			tx.commit();
			return newSpecimen;
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
	
	
	
	
	
}
