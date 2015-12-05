package lib.driver.inward.driver_class.charts;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.inward.charts.Temperaturechart;

public class TemperaturechartDBDriver {
Session session = SessionFactoryUtil.getSessionFactory().openSession();
	
	public List<Temperaturechart> getChartValues() {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select t from Temperaturechart as t");
			@SuppressWarnings("unchecked")
			List<Temperaturechart> chartvalues = query.list();
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
