package lib.driver.mri.driver_class;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.lims.Laboratories;
import core.classes.mri.MriParentResult;
import core.classes.mri.MriSubFieldResult;
import core.classes.mri.MriTestParentFields;
import core.classes.mri.MriTestRequest;
import core.classes.mri.MriTestSubFields;

public class MriTestResultsDBDriver {
	public List<MriTestSubFields> GetResultSubTypes(int parentField) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session
				.createQuery(
						"select m.testSubFieldId,m.testSubFieldName,m.mriTestParentFields.testParentFieldId from MriTestSubFields m where m.mriTestParentFields.testParentFieldId=:parentField")
				.setParameter("parentField", parentField);
		List<MriTestSubFields> testFeilds = query.list();
		tx.commit();

		return testFeilds;
	}
	
	public List<MriTestParentFields> GetResultParentTypes(int labTestId) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session
				.createQuery(
						"from MriTestParentFields m where m.mriLaboratoryTest.laboratoryTestId=:laboratoryTestId")
				.setParameter("laboratoryTestId", labTestId);
		List<MriTestParentFields> testFeilds = query.list();
		tx.commit();

		return testFeilds;
	}

	public int GetLabTestId(String requestId) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session.createQuery(
				"from MriTestRequest m where m.requestId=:requestId")
				.setParameter("requestId", requestId);
		MriTestRequest test = (MriTestRequest) query.list().get(0);
		tx.commit();

		return test.getMriLaboratoryTest().getLaboratoryTestId();
	}

	public Boolean AddTestParentResult(MriTestRequest req,MriTestParentFields par,int testTypeId,String value) {
		MriParentResult res =new MriParentResult();
		res.setMriTestRequest(req);
		res.setMriTestParentFields(par);
		res.setParentResultValue(value);
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		try{
			MriTestRequest reqs = (MriTestRequest) session.load(MriTestRequest.class, res.getMriTestRequest().getTestRequestId());
			req.setStatus(1);
			session.update(req);
			
			session.save(res);			
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
	
	public Boolean AddTestSubResult(MriTestRequest req,MriTestSubFields sub,int testParentId,String value) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		MriParentResult pRes = (MriParentResult) session.get(MriParentResult.class, testParentId);
		MriSubFieldResult res = new MriSubFieldResult();
		res.setMriTestSubFields(sub);
		res.setMriTestRequest(req);
		res.setSubResultValue(value);
			
		try{
			session.save(res);			
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
	
	public boolean updateRequestTable(int reqId) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		String hql = "UPDATE MriTestRequest set status = :status "  + 
	             "WHERE testRequestId = :testRequestId";
		Query query = session.createQuery(hql);
		query.setParameter("status", 1);
		query.setParameter("testRequestId", reqId);
		int result = query.executeUpdate();
		return true;
	}
}
