package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.mri.MriBinaryResults;
import core.classes.mri.MriTestRequest;

public class MriBinaryResultsDBDriver {
	
	public List GetTestRequests() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		String sql = "SELECT * FROM mri_test_request r, mri_laboratory_test l, mri_patient p where r.flaboratory_test_id=l.laboratory_test_id and"
				+ " r.fpatient_id=p.patient_id and l.is_binary=1 and r.status=0";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List results = query.list();
		
		tx.commit();

		return results;
	}
	
	public List GetTestRequestForPeriod(String start,String end) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		String sql = "SELECT * FROM mri_test_request r, mri_laboratory_test l, mri_patient p where r.status=0 and r.flaboratory_test_id=l.laboratory_test_id and"
				+ " r.fpatient_id=p.patient_id and l.is_binary=1 and test_request_date>='"+start+"' and test_request_date<='"+end+"'";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List results = query.list();
		
		tx.commit();

		return results;
	}
	
	public boolean addSingleResult(MriBinaryResults result) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		try{
			session.save(result);			
			
			MriTestRequest req = (MriTestRequest) session.load(MriTestRequest.class, result.getMriTestRequest().getTestRequestId());
			req.setStatus(1);
			session.update(req);
			
			tx.commit();
			System.out.println("testing\n\n"+result.getMriTestRequest().getTestRequestId());
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
	
	public void updateRequestTable(int reqId) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		MriTestRequest req = (MriTestRequest) session.load(MriTestRequest.class, reqId);
		req.setStatus(1);
		session.update(req);
		//return true;
	}
	
	public List GetCompletedTestRequestsForPeriod(String start,String end) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		String sql = "SELECT * FROM mri_test_request r, mri_laboratory_test l, mri_patient p where r.flaboratory_test_id=l.laboratory_test_id and"
				+ " r.fpatient_id=p.patient_id and l.is_binary=1 and r.status=1 and test_request_date>='"+start+"' and test_request_date<='"+end+"'";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List results = query.list();
		
		tx.commit();

		return results;
	}
	
	public List GetCompletedTestRequests() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		String sql = "SELECT * FROM mri_test_request r, mri_laboratory_test l, mri_patient p where r.flaboratory_test_id=l.laboratory_test_id and"
				+ " r.fpatient_id=p.patient_id and l.is_binary=1 and r.status=1";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List results = query.list();
		
		tx.commit();

		return results;
	}
	
	public List GetSingleTestRequestData(int reqid) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		String sql = "SELECT * FROM mri_test_request r, mri_laboratory_test l, mri_patient p, mri_laboratory lb, mri_binary_results re where r.flaboratory_test_id=l.laboratory_test_id and"
				+ " r.fpatient_id=p.patient_id and lb.laboratory_id=l.flaboratory_id and re.ftest_request_id=r.test_request_id and  l.is_binary=1 and r.status=1";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List results = query.list();
		
		tx.commit();

		return results;
	}
}
