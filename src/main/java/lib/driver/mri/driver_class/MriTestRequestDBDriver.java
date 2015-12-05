package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.hr.HrEmployee;
import core.classes.mri.MriSpecimen;
import core.classes.mri.MriTestRequest;

public class MriTestRequestDBDriver {




	public List<MriTestRequest> GetAllTestRequests() {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriTestRequest");
		List<MriTestRequest> testRequests= query.list();
		tx.commit();

		return testRequests;
	}

	public void InserNewTestRequest(MriTestRequest testRequest) {
		Session session=null;
		session=SessionFactoryUtil.getSessionFactory().openSession();


		Transaction tx = null;
		try {

			tx= session.beginTransaction();
			System.out.println(testRequest.getMriLaboratoryTest().getTestFullName());
			session.save(testRequest);
			tx.commit();

		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}

				throw ex;
			}

		}

	}

	public List<MriTestRequest> GetInternalRequests() {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='false' ORDER BY t.testRequestId DESC");
		List<MriTestRequest> testRequests= query.list();
		tx.commit();

		return testRequests;
	}

	public List<MriTestRequest> GetHospitalRequests() {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient !='false' ORDER BY t.testRequestId DESC");
		List<MriTestRequest> testRequests= query.list();
		tx.commit();

		return testRequests;
	}

	public List<MriTestRequest> GetRequestsByDept(int deptID) {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriTestRequest t Where t.mriLaboratoryTest.mriLaboratory.laboratoryId = '"+deptID+"' ORDER BY t.testRequestId DESC");
		List<MriTestRequest> testRequests= query.list();
		tx.commit();

		return testRequests;
	}

	public List<MriTestRequest> GetRequestByRequestID(int requestID) {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		System.out.println(requestID);
		System.out.println("Hiiii");

		Query query = session.createQuery("from MriTestRequest t where t.testRequestId= '"+requestID+"'");
		List<MriTestRequest> testRequests= query.list();
		tx.commit();

		return testRequests;
	}

	public Integer GetLastRequestID() {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session.createQuery("select MAX(r.increment) from MriTestRequest r");
		List listRes = query.list();

		int testRequests = ((Integer) listRes.get(0)).intValue();
		tx.commit();

		return testRequests;
	}

	public List<MriTestRequest> GetInternalRequestsBySearch(String searchParam,String searchID) {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		Query query=null;

		System.out.println("Hiiii : "+searchParam);

		if((searchParam.equals("by_name"))){
			System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='false' and t.mriPatient.patientId='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_nic"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='false' and t.mriPatient.patientId='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_id"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='false' and t.mriPatient.patientId='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_requestID"))){
			System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='false' and t.requestId='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_Priority"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='false' and t.testPriority='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_TestType"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='false' and t.mriLaboratoryTest.laboratoryTestId='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_status"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='false' and t.status='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_dueDate"))){
			System.out.println("Hiiii "+searchID);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='false' and t.testDueDate='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_requestDate"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='false' and t.testRequestDate='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else{
			System.out.println("Hiiii ! "+searchParam);
		}

		List<MriTestRequest> testRequests= query.list();
		tx.commit();

		return testRequests;
	}

	public int GetTestRequestCount() {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session.createQuery("select COUNT(r.testRequestId) from MriTestRequest r where r.status='0'");
		
		
		List listRes = query.list();
		
		int patientCount = (int) ((Long) listRes.get(0)).longValue();
		tx.commit();
		
		return patientCount;
	}


	public List<MriTestRequest> GetUrgentTestRequests() {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriTestRequest t where t.status='Pending' order by t.testDueDate");
		query.setMaxResults(10);
		List<MriTestRequest> testRequests= query.list();
		tx.commit();

		return testRequests;
	}

	public List<MriSpecimen> getMaxSpecimenIDs() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session
				.createQuery("SELECT max(specimenId)  from MriSpecimen");
		List<MriSpecimen> testRequests = query.list();
		tx.commit();

		return testRequests;
	}

	public String InsertSpecimentForRequest(int requestID, MriSpecimen specimen) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx=null;


		try {


			System.out.println("Request ID: "+requestID);
			tx= session.beginTransaction();
			MriTestRequest updateReq=(MriTestRequest) session.get(MriTestRequest.class, requestID);
			//MriSpecimen specimen=(MriSpecimen) session.get(MriSpecimen.class, specimenID);
			updateReq.setMriSpecimen(specimen);


			session.update(updateReq);
			tx.commit();
			return "True";

		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return "False";
		}
	}

	public int GetRequestPrimaryKeyByRequestID(String requestID) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session
				.createQuery("Select t.testRequestId from MriTestRequest t where t.requestId='"+requestID+"'");
		//List<MriSpecimen> testRequests = query.list();
		List listRes = query.list();
		int reqID = (int) ((Integer) listRes.get(0)).intValue();
		tx.commit();

		return reqID;
	}

	public List<MriTestRequest> GettHospitalRequestsBySearch(
			String searchParam, String searchID) {
		// TODO Auto-generated method stub
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		Query query=null;

		System.out.println("Hiiii : "+searchParam);

		if((searchParam.equals("by_name"))){
			System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='true' and t.mriPatient.patientId='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_nic"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='true' and t.mriPatient.patientId='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_id"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='true' and t.mriPatient.patientId='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_requestID"))){
			System.out.println("Hiiii I'm search param"+searchParam);
			System.out.println("Hiiii I'm search ID:"+searchID);
			 query = session.createQuery("from MriTestRequest t Where  t.requestId='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_Priority"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='true' and t.testPriority='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_TestType"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='true' and t.mriLaboratoryTest.laboratoryTestId='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_status"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='true' and t.status='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_dueDate"))){
			System.out.println("Hiiii "+searchID);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='true' and t.testDueDate='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else if((searchParam.equals("by_requestDate"))){
			//System.out.println("Hiiii "+searchParam);
			 query = session.createQuery("from MriTestRequest t Where t.isHospitalPatient='true' and t.testRequestDate='"+searchID+"' ORDER BY t.testRequestId DESC");
		}
		else{
			System.out.println("Hiiii ! "+searchParam);
		}

		List<MriTestRequest> testRequests= query.list();
		tx.commit();

		return testRequests;
		//return null;
	}

	
	public int GetRequestCountByLabID(int labID) {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session.createQuery("select COUNT(r.testRequestId) from MriTestRequest r where r.mriLaboratoryTest.mriLaboratory.laboratoryId='"+labID+"' AND r.status='0'");
		
		
		List listRes = query.list();
		
		int patientCount = (int) ((Long) listRes.get(0)).longValue();
		tx.commit();
		
		return patientCount;
	}
}
