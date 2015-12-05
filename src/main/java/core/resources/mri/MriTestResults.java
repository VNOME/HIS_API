package core.resources.mri;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

import lib.SessionFactoryUtil;
import lib.driver.mri.driver_class.MriTestRequestDBDriver;
import lib.driver.mri.driver_class.MriTestResultsDBDriver;
import core.classes.mri.MriLaboratory;
import core.classes.mri.MriParentResult;
import core.classes.mri.MriTestParentFields;
import core.classes.mri.MriTestRequest;
import core.classes.mri.MriTestSubFields;
import flexjson.JSONSerializer;

@Path("MriTestResults")
public class MriTestResults {
	
	MriTestResultsDBDriver mriTestRequestDBDriver = new MriTestResultsDBDriver();
	@GET
	@Path("/GetParentFeilds/{testRequesId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetTestParentFields(@PathParam("testRequesId")String testRequesId) {
		try {
			int labTestId = mriTestRequestDBDriver.GetLabTestId(testRequesId);
			
			List<MriTestParentFields> testFields= mriTestRequestDBDriver.GetResultParentTypes(labTestId);
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testFields);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GET
	@Path("/GetSubFeilds/{parentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetSubFeilds(@PathParam("parentId")int parentId) {
		try {			
			List<MriTestSubFields> subFields = mriTestRequestDBDriver.GetResultSubTypes(parentId);
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(subFields);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@POST
	@Path("/addTestParentFeildResults")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addTestParentFeildResults(JSONObject obj)
	{
		boolean temp=false;
		boolean res=true;
		try {
			JSONArray arr = obj.getJSONArray("fields");
			Session session = SessionFactoryUtil.getSessionFactory().openSession();
			MriTestRequest req = (MriTestRequest) session.load(MriTestRequest.class, obj.getInt("reqId"));	
			session.close();
			for (int i = 0; i < arr.length(); i++) {
				JSONObject field = arr.getJSONObject(i);
				session = SessionFactoryUtil.getSessionFactory().openSession();
				MriTestParentFields par = (MriTestParentFields) session.load(MriTestParentFields.class, field.getInt("id"));
				session.close();
				temp =mriTestRequestDBDriver.AddTestParentResult(req,par, field.getInt("test"), field.getString("value"));
				if(temp==false)
					res=false;
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(res==true)
			return "{'success':'true'}";
		else
			return "{'success':'false'}";
	}
	
	@POST
	@Path("/addTestSubFeildResults")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addTestSubFeildResults(JSONObject obj) {
		boolean temp=false;
		boolean res=true;
		try {
			JSONArray arr = obj.getJSONArray("fields");
			Session session = SessionFactoryUtil.getSessionFactory().openSession();
			MriTestRequest req = (MriTestRequest) session.load(MriTestRequest.class, obj.getInt("reqId"));
			session.close();
			for (int i = 0; i < arr.length(); i++) {
				JSONObject field = arr.getJSONObject(i);
				session = SessionFactoryUtil.getSessionFactory().openSession();
				MriTestSubFields sub = (MriTestSubFields) session.load(MriTestSubFields.class, field.getInt("id"));
				session.close();
				temp = mriTestRequestDBDriver.AddTestSubResult(req,sub, field.getInt("parent"), field.getString("value"));
				if(temp==false)
					res=false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(res==true)
			return "{'success':'true'}";
		else
			return "{'success':'false'}";	
	}
	
	@GET
	@Path("/updateResultTable/{reqId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateResultTable(@PathParam("reqId")int reqId) {
		try {			
			boolean stat = mriTestRequestDBDriver.updateRequestTable(reqId);
			if(stat)
				return "true";
			else
				return "false";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
