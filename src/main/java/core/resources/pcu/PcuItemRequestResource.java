package core.resources.pcu;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;





import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;










import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import core.classes.api.user.AdminUser;
import core.classes.pcu.PcuRequesteditems;
import core.classes.pharmacy.MstDrugsNew;
import core.classes.pharmacy.MstPharmDepartment;
import core.classes.pharmacy.TrnRequestDrugs;
import flexjson.JSONSerializer;
import lib.driver.api.driver_class.user.UserDBDriver;
import lib.driver.pcu.driver_class.PcuItemRequestDBDriver;
import lib.driver.pharmacy.driver_class.DrugDBDriver;

@Path("PcuRequest")
public class PcuItemRequestResource {
	
	PcuItemRequestDBDriver pcuItemRequestDBDriver = new PcuItemRequestDBDriver();
	DrugDBDriver drugDbDriver=new DrugDBDriver();
	UserDBDriver userDbDriver=new UserDBDriver();
	
	@GET
	@Path("/getItemDetail/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllItemIDs(@PathParam("id") int SNO) {
		String result="";
		try {
			List<MstDrugsNew> ItemList=pcuItemRequestDBDriver.GetAllItemIDs(SNO);
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(ItemList);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GET
	@Path("/RequestDrug/{qty}/{sno}/{user}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String dispenseDrugManual(@PathParam("qty") float QTY,@PathParam("sno") int SNO,@PathParam("user") int userID) {
		String status="";
		try {
			if (pcuItemRequestDBDriver.RequestDrug(SNO, QTY,userID)) {
				status = "OK";
			} else {
				status = "Items have already been requested.";
			}
			
			return status;
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}
	
	@GET
	@Path("/getRequestedItems")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetPrescriptionDispensedItems() {
		
		String result="";
		try {
			List<PcuRequesteditems> ItemList=pcuItemRequestDBDriver.GetRequestedItems();
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(ItemList);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GET
	@Path("/UpdateItems/{id}/{reqby}/{date}/{stat}")
	@Produces(MediaType.APPLICATION_JSON)
	public String UpdateItems(@PathParam("id") int ID,@PathParam("reqby") int REQBY,@PathParam("date") Date ReqDate, @PathParam("stat") String stat) {
		String status="";
		try {
			if (pcuItemRequestDBDriver.UpdateRequestedItems(ID, REQBY, ReqDate, stat)) {
				status = "Item Updated!!!";
			} else {
				status = "fail";
			}
			
			return status;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GET
	@Path("/DeleteItems/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String DeleteItems(@PathParam("id") int ID) {
		String status="";
		try {
			if (pcuItemRequestDBDriver.DeleteItems(ID)) {
				status = "Item Deleted!!!";
			} else {
				status = "fail";
			}
			
			return status;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	@POST
	@Path("/requestDrug")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String requestDrug(JSONObject json) {
		  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		  java.util.Date date = new java.util.Date();
	      String status="Default";
	     
	      //String dept = "";
	      List<MstDrugsNew> drug=new ArrayList<MstDrugsNew>();
	      
	     // List<MstUser> userNew=new ArrayList<MstUser>();
	      ArrayList<TrnRequestDrugs> requests=new ArrayList<TrnRequestDrugs>();
	      
	      System.out.println(json);
	      
        //MstDrugsNew drugNew = null;
        //MstUser user =null;
        /*UserAction ua = new UserAction();
        DrugAction da = new DrugAction();*/
		try {
			List<AdminUser> user = userDbDriver.getUserDetailsByUserID(json.getInt("user"));
			
			System.out.println(json.getInt("user"));
			System.out.println("asd");
			int count = json.length() - 1;			
			String dnames[] = new String[count];			
			
			String dept = "PCU Pharmacy";
			System.out.println("shermin");
			int qtys[] = new int[count];
			int drugSrNo[]=new int[count];
			int j=0;
			
				
				
				qtys[j] = Integer.parseInt(json.getString("id3"));				
				dnames[j] = json.getString("id1");					
				TrnRequestDrugs req = new TrnRequestDrugs();
				req.setDrugs(drugDbDriver.GetDrugByDrugName(dnames[j]).get(0));					
				req.setQuantity(qtys[j]);
				req.setDepartment(dept);				
				req.setRequestedDate(date);
				req.setProcessed(false);
				
				requests.add(j, req);		
			    j++;
			    
			
			if(drugDbDriver.insertDrugRequest(requests))
			{
					status = "Drug Request Sent!!!";
					
			}
			else
			{
					status = "fail";
					
			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			return e.getMessage()+"requestDrug";
		}
		return status;

	}

}
