package lib.driver.mri.driver_class;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.hr.HrEmployee;
import core.classes.mri.MriBundle;
import core.classes.mri.MriHospital;
import core.classes.mri.MriTestRequest;

public class MriBundleDBDriver {



	public MriBundle CreateNewBundle() {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		//System.out.println("Meeee");
		Transaction tx = null;
		try {

			//tx = null;
			MriBundle bundle=new MriBundle();

			tx= session.beginTransaction();

			session.save(bundle);

			tx.commit();
			session.close();
			return bundle;

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

	public Boolean UpdateBundle(MriBundle bundle, MriHospital hospital, int total) {
		Session session=SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx=null;

		int bundleID=bundle.getBundleId();


		try {
			System.out.println("Inside Bundle!");
			System.out.println(hospital.getHospitalName());
			tx= session.beginTransaction();
			MriBundle updateBundle=(MriBundle) session.get(MriBundle.class, bundleID);

			updateBundle.setMriHospital(hospital);
			updateBundle.setNoOfRequests(total);

			session.update(updateBundle);
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
