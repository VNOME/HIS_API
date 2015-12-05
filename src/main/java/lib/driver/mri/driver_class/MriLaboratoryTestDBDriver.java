package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.mri.MriLaboratoryTest;
import core.classes.mri.MriSpecimen;
import core.classes.mri.MriTestRequest;

public class MriLaboratoryTestDBDriver {
	
	Session session=SessionFactoryUtil.getSessionFactory().openSession();
	Transaction tx = null;
	
	public List<MriLaboratoryTest> GetAllLabTests() {
		tx = session.beginTransaction();
		
		Query query = session.createQuery("Select t.laboratoryTestId, t.testFullName, t.testShortName from MriLaboratoryTest t");
		List<MriLaboratoryTest> testRequests= query.list();
		tx.commit();
		
		return testRequests;
	}
	
	public MriLaboratoryTest GetLabTestByID(int labTestID) {
			
			tx=null;
			
			tx= session.beginTransaction();
			MriLaboratoryTest labTest=(MriLaboratoryTest) session.get(MriLaboratoryTest.class, labTestID);
			
			tx.commit();
			
			//session.close();
			return labTest;
	}
}
