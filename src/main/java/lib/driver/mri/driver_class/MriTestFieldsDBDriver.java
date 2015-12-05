package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.mri.MriDepartment;
import core.classes.mri.MriLaboratory;
import core.classes.mri.MriLaboratoryTest;
import core.classes.mri.MriSpecimenType;
import core.classes.mri.MriTestParentFieldRange;
import core.classes.mri.MriTestParentFields;
import core.classes.mri.MriTestRequest;
import core.classes.mri.MriTestSubFieldRange;
import core.classes.mri.MriTestSubFields;

public class MriTestFieldsDBDriver {
	public List<MriDepartment> GetAllDepartments() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from MriDepartment");
		List<MriDepartment> testFeilds = query.list();
		tx.commit();

		return testFeilds;
	}
	
	public List<MriLaboratory> GetAllLabs() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from MriLaboratory");
		List<MriLaboratory> testFeilds = query.list();
		tx.commit();

		return testFeilds;
	}
	
	public List<MriSpecimenType> GetAllSpecimenTypes() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from MriSpecimenType");
		List<MriSpecimenType> testFeilds = query.list();
		tx.commit();

		return testFeilds;
	}
	
	public boolean addLabTest(MriLaboratoryTest labTest) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		try{
			session.save(labTest);			
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
	
	public List<MriLaboratoryTest> GetAllLabTests() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session
				.createQuery("select t from MriLaboratoryTest t");
		List<MriLaboratoryTest> testFeilds = query.list();
		tx.commit();

		return testFeilds;
	}
	
	public boolean addParentTestField(MriTestParentFields field) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		try{
			session.save(field);			
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
	
	public boolean checkForExistenceParent(String gender, int parentId) {	
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		Query q = session.createQuery("from MriTestParentFieldRange m where m.gender=:gender and m.mriTestParentFields.testParentFieldId=:testParentFieldId");
		q.setParameter("gender", gender);
		q.setParameter("testParentFieldId", parentId);
		List<MriTestParentFieldRange> list = q.list();
		tx.commit();
		if(list.size()>0) {
			return true;
		} return false;
	}
	
	public boolean addParentFieldData(MriTestParentFieldRange field) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		try{
			session.save(field);			
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
	
	public List<MriTestParentFieldRange> GetTestParentData(int labTestId) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();

		Query query = session.createQuery(
				"from MriTestParentFieldRange m where m.ftestParentFieldId.mriLaboratoryTest.laboratoryTestId=:labTestId")
				.setParameter("labTestId", labTestId);
		List<MriTestParentFieldRange> list = query.list();
		tx.commit();

		return list;
	}
	
	public boolean addSubTestField(MriTestSubFields field) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		try{
			session.save(field);			
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
	
	public boolean addSubFieldData(MriTestSubFieldRange field) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		try{
			session.save(field);			
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
