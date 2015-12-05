package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.mri.MriParentResult;
import core.classes.mri.MriSubFieldResult;
import core.classes.mri.MriTestParentFieldRange;

public class MriTestReportDBDriver {
	
	public List GetParentResultData(String reqID) {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		// **Issue : If range is not added field will not select
		Query query = session.createQuery("select r,t from MriParentResult r,MriTestParentFieldRange t Where r.mriTestRequest.requestId=:requestId and t.ftestParentFieldId=r.mriTestParentFields.testParentFieldId");
		query.setParameter("requestId", reqID);
		List<MriParentResult> res= query.list();
		tx.commit();

		return res;
	}
	public List<MriSubFieldResult> GetSubFieldResultData(String reqID,int parId) {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriSubFieldResult r Where r.mriTestRequest.requestId=:requestId and r.mriTestSubFields.mriTestParentFields.testParentFieldId=:parentId");
		query.setParameter("requestId", reqID);
		query.setParameter("parentId", parId);
		List<MriSubFieldResult> res= query.list();
		tx.commit();

		return res;
	}

}
