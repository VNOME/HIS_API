package lib.driver.inward.driver_class.charts;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.inward.charts.DiabeticChart;

public class DiabeticChartDBDriver {

Session session = SessionFactoryUtil.getSessionFactory().openSession();
	
	public List<DiabeticChart> getChartValues() {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select d from DiabeticChart as d");
			@SuppressWarnings("unchecked")
			List<DiabeticChart> chartvalues = query.list();
			tx.commit();
			return chartvalues;
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
		// TODO Auto-generated method stub
	}
}
