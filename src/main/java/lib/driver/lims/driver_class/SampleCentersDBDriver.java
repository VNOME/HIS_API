package lib.driver.lims.driver_class;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;

import core.classes.api.user.AdminUser;
import core.classes.lims.Category;
import core.classes.lims.LabTypes;
import core.classes.lims.Laboratories;

import core.classes.lims.SampleCenterTypes;
import core.classes.lims.SampleCenters;
import core.classes.lims.SubCategory;
import core.classes.lims.TestFieldsRange;
import core.classes.opd.OutPatient;

public class SampleCentersDBDriver {

	Session session = SessionFactoryUtil.getSessionFactory().openSession();
	
	public boolean insertNewSampleCenter(SampleCenters samplecenter, int sampleCenterTypeID, int userid) {

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			SampleCenterTypes sCenterType = (SampleCenterTypes)session.get(SampleCenterTypes.class, sampleCenterTypeID);
	
			AdminUser user = (AdminUser) session.get(AdminUser.class, userid);
			
			samplecenter.setfSampleCenterType_ID(sCenterType);
		
			samplecenter.setfSampleCenter_AddedUserID(user);
			samplecenter.setfSampleCenterLast_UpdatedUserID(user);
		
			session.save(samplecenter);
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
	public List<SampleCenters> getNewSampleCenterList() {
		Transaction tx = null;
		try {
			//catID = 2;
			tx = session.beginTransaction();
			Query query = session.createQuery("select s from SampleCenters s" );
			//query.setParameter("catID", catID);
			List<SampleCenters> sampleCenterList = query.list();
			tx.commit();
			return sampleCenterList;
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
	
	public SampleCenters getSampleCenterID(int id) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select s from SampleCenters s where s.sampleCenter_ID="+id);
			List<SampleCenters> sampleCenterList = query.list();
			if (sampleCenterList.size() == 0)
				return null;
			tx.commit();
			return (SampleCenters)sampleCenterList.get(0);
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

	public List<SampleCenters> getSamplecentersByLabType(int typeID) {
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			Query query = session.createQuery("select t from SampleCenterTypes as t where t.sampleCenterType_ID=:typeID");
			query.setParameter("typeID", typeID);
			List<SampleCenterTypes> testList = query.list();
			SampleCenterTypes catObject=testList.get(0);
			tx.commit();

			tx = session.beginTransaction();
			Query query1 = session.createQuery("select s from SampleCenters as s where s.fSampleCenterType_ID=:catObj");
			query1.setParameter("catObj", catObject);
			List<SampleCenters> testList1 = query1.list();
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
}
