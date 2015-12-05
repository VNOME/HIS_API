package lib.driver.mri.driver_class;

import java.util.List;

import lib.SessionFactoryUtil;
import lib.classes.CasttingMethods.CastList;
import lib.classes.securitymodel.encryption.DataHashing;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.api.user.AdminUser;
import core.classes.mri.MriDepartment;
import core.classes.mri.MriUser;

public class MriUserDBDriver {

	Session session=SessionFactoryUtil.getSessionFactory().openSession();
	DataHashing dataHashing=new DataHashing();
	Transaction tx = null;
	
	//register user
			public Boolean registerUser(MriUser newUser) {
				Transaction tx = null;
				try {
					
					//tx = null;
					
					tx= session.beginTransaction();
					
					session.save(newUser);
					
					
					
					tx.commit();
					//session.close();
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
			
			//validate password
			public boolean validateUserLoginDetails(MriUser usrObj){
				
				boolean result=false;
				String password_Hash=getUserPassword_Hash(usrObj.getUserName());
				String chk_Password=usrObj.getPassword();
					//System.out.println("Password Me :"+ password_Hash);
				try
				{
					//if(chk_Password.matches(password_Hash)){
					if(dataHashing.validatePassword(chk_Password, password_Hash)){
						return result=true;
					}
					else{
						return false;
					}
				}
				
				catch(Exception ex){
					ex.printStackTrace();
					return result;
				}
				
			}
			
			//password hash
			private String getUserPassword_Hash(String usrName) {
				
				Transaction tx = null;
				String password="";
				
				try{
					tx = session.beginTransaction();
					String hql="select uc from MriUser as uc where uc.userName=:usrName";
					Query query =  session.createQuery(hql);
					query.setParameter("usrName", usrName);
					List<MriUser> userCList = CastList.castList(MriUser.class,query.list());

					tx.commit();
					
					for(int i = 0; i < userCList.size(); i++) {
				            password=userCList.get(i).getPassword();
				    }
					
					
					return password;
					
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
			
			
			//validate user
			public List<MriUser> getValidUserDetails(MriUser usr){
				
				Transaction tx = null;
				String userName=usr.getUserName();
				String password=getUserPassword_Hash(usr.getUserName());
				
				try{
					tx = session.getTransaction();
					String hql="select uc from MriUser as uc where uc.userName=:usrName AND password=:password";
					Query query =  session.createQuery(hql);
					query.setParameter("usrName", userName);
					query.setParameter("password", password);
					
					List<MriUser> userList =CastList.castList(MriUser.class, query.list()); 
					
					//tx.commit();
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
					
					System.out.println(ex.getMessage());
					
					
					return null;
				}
				
			}
}
