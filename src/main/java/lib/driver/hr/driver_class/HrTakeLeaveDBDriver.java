package lib.driver.hr.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;
import lib.classes.CasttingMethods.CastList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.api.user.AdminUser;
import core.classes.hr.HrEmployee;
import core.classes.hr.HrHasleaves;
import core.classes.hr.HrHasleavesId;
import core.classes.hr.HrLeavetype;
import core.classes.hr.HrTakeleaves;

public class HrTakeLeaveDBDriver {
Session session=SessionFactoryUtil.getSessionFactory().openSession();

	
	public boolean requestLeave(HrTakeleaves takeLeave){
		Transaction tx = null;
		try{	
			tx = session.beginTransaction();
			
			AdminUser user = (AdminUser) session.get(AdminUser.class, takeLeave.getHrEmployee().getEmpId());
			
			System.out.println(user.getHrEmployee().getEmpId());
			
			HrEmployee emp = (HrEmployee) session.get(HrEmployee.class, user.getHrEmployee().getEmpId());
			
			HrLeavetype hrLeavetype=(HrLeavetype) session.get(HrLeavetype.class, takeLeave.getHrLeavetype().getLeaveTypeId());
			
			System.out.println(hrLeavetype.getLeaveTypeId());
			
			takeLeave.setHrLeavetype(hrLeavetype);
			takeLeave.setHrEmployee(emp);
			takeLeave.setApprover(null);
			//takeLeave.setReason(hasLeave.getRemain());
			//leave.setTotal(hasLeave.getTotal());
			
		 
			session.save(takeLeave);
			
			
			tx.commit();
			return true;
		}
		catch(RuntimeException ex){
			if(tx != null && tx.isActive())
			{
				try{
					tx.rollback();
				}
				catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
	}
	
	
	public List<HrTakeleaves> getRequestLeaveDetails(){
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			Query query =  session.createQuery("select u from HrTakeleaves as u where u.approver = NULL");
			
			List<HrTakeleaves> userList =CastList.castList(HrTakeleaves.class, query.list()); 
			tx.commit();
			return userList;
			
		}
		catch(RuntimeException ex){
			if(tx != null && tx.isActive())
			{
				try{
					tx.rollback();
				}catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return null;
		}
	}
}
