package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.hr.HrEmployee;

import core.classes.mri.MriHospital;
import core.classes.mri.MriPatient;


public class MriHospitalDBDriver {
	
	Session session=SessionFactoryUtil.getSessionFactory().openSession();
	Transaction tx = null;
	
	



	public List<MriHospital> GetAllHospitals() {
		tx = session.beginTransaction();
		
		Query query = session.createQuery("from MriHospital");
		List<MriHospital> hospitals= query.list();
		tx.commit();
		
		return hospitals;
	}



	public List<MriHospital> GetHospitalById(int hospitalID) {
		tx = session.beginTransaction();

		Query query = session.createQuery("from MriHospital e where e.hospitalId = '"+hospitalID+"'");
		List<MriHospital> hospitalList= query.list();
		tx.commit();
		

		return hospitalList;
	}



	public MriHospital GetHospitalObject(int hospitalID) {
		tx=null;
		
		tx= session.beginTransaction();
		
		MriHospital hospitalO=(MriHospital) session.get(MriHospital.class, hospitalID);
		
		tx.commit();
		
		
		
		return hospitalO;
	}
}
