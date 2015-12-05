package lib.driver.lims.driver_class;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import core.classes.api.user.AdminUser;
import core.classes.lims.Category;
import core.classes.lims.LabTestRequest;
import core.classes.lims.Laboratories;
import core.classes.lims.MainResults;
import core.classes.lims.Reports;
import core.classes.lims.SampleCenters;
import core.classes.lims.Specimen;
import core.classes.lims.SubCategory;
import core.classes.lims.TestNames;
import core.classes.opd.OutPatient;
import core.classes.opd.Patient;

public class LabTestRequestDBDriver {

	Session session = SessionFactoryUtil.getSessionFactory().openSession();
	MainResultsDBDriver mDriver = new MainResultsDBDriver();
	
	public boolean addNewLabTestRequest(LabTestRequest testRequest, int testID, int patientID, int labID, int userid) {

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			SampleCenters sctype ;
			Laboratories ltype;
			TestNames tstype = (TestNames) session.get(TestNames.class, testID);
			OutPatient ptype = (OutPatient) session.get(OutPatient.class, patientID);
			 ltype = (Laboratories) session.get(Laboratories.class, labID);
			
			
			AdminUser user = (AdminUser) session.get(AdminUser.class, userid);
			
			testRequest.setFtest_ID(tstype);
			testRequest.setFpatient_ID(ptype);
			testRequest.setFlab_ID(ltype);
			
			testRequest.setFtest_RequestPerson(user);
		
			session.save(testRequest);			
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
	
	/**
	 * This method retrieve a list of laboratory tests currently available in the system
	 * 
	 * @return lab test list List<TestNames>
	 * @throws Method
	 *             throws a {@link RuntimeException} in failing the return,
	 *             throws a {@link HibernateException} on error rolling back
	 *             transaction.
	 */
	public List<LabTestRequest> getTestRequestsList() {
		Transaction tx = null;
		try {
			//catID = 2;
			tx = session.beginTransaction();
			Query query = session.createQuery("select r from LabTestRequest r" );
			//query.setParameter("catID", catID);
			List<LabTestRequest> testrequestsList = query.list();
			tx.commit();
			return testrequestsList;
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

	
	public LabTestRequest getTestRequestByID(int id) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select r from LabTestRequest r where r.labTestRequest_ID="+id);
			List<LabTestRequest> testrequestsList = query.list();
			if (testrequestsList.size() == 0)
				return null;
			tx.commit();
			return (LabTestRequest)testrequestsList.get(0);
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
	public List<LabTestRequest> getLabTestRequestsByPid(int patientID) {
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			Query query = session.createQuery("select c from OutPatient as c where c.patientID=:patientID");
			query.setParameter("patientID", patientID);
			List<OutPatient> testList = query.list();
			OutPatient catObject=testList.get(0);
			tx.commit();

			tx = session.beginTransaction();
			Query query1 = session.createQuery("select m from LabTestRequest as m where m.fpatient_ID=:catObj");
			query1.setParameter("catObj", catObject);
			List<LabTestRequest> testList1 = query1.list();
			tx.commit();
			return testList1;
			
			
			
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
		/*public List<LabTestRequest> getLabtestrequestListByPatientID(int pID) {
			Transaction tx = null;
			try {

				tx = session.beginTransaction();
				Query query = session.createQuery("select l from LabTestRequest as l where l.fpatient_ID=:pID");
				query.setParameter("pID", pID);
				List<LabTestRequest> testrequestsList = query.list();
				tx.commit();
				return testrequestsList;
				
				
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
		}*/
		
		/*public LabTestRequest getTestRequestByPID(int id) {
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				Query query = session.createQuery("select r from LabTestRequest r where r.fpatient_ID="+id);
				List<LabTestRequest> testrequestsList = query.list();
				if (testrequestsList.size() == 0)
					return null;
				tx.commit();
				return (LabTestRequest)testrequestsList.get(0);
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
		}*/
		
		/*public LabTestRequest getMainResultsByRequestID(int id) {
			
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				Query query = session.createQuery("select r from LabTestRequest r where r.labTestRequest_ID="+id);
				List<LabTestRequest> testreportsList = query.list();
				if (testreportsList.size() == 0)
					return null;
				
				LabTestRequest req = testreportsList.get(0);
				Set<MainResults> labMainresultset = new HashSet<MainResults>();
				List <MainResults> resultsList = mDriver.getMainResultsByRequestID(req.getLabTestRequest_ID());
				
				for (MainResults as : resultsList) {
					labMainresultset.add(as);
				}
				
				req.setLabMainresultses(labMainresultset);
				
				
				tx.commit();
				return req;
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
		}*/
	
}
