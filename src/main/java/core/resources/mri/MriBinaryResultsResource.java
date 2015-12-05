package core.resources.mri;

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
import org.hibernate.Session;

import core.classes.mri.MriBinaryResults;
import core.classes.mri.MriLaboratory;
import core.classes.mri.MriTestParentFields;
import core.classes.mri.MriTestRequest;
import flexjson.JSONSerializer;
import lib.SessionFactoryUtil;
import lib.driver.mri.driver_class.MriBinaryResultsDBDriver;

@Path("MriBinaryResult")
public class MriBinaryResultsResource {
	MriBinaryResultsDBDriver binDbDriver = new MriBinaryResultsDBDriver();
	
	@POST
	@Path("/getTestRequests/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getTestRequests(JSONObject obj)
	{		
		JSONSerializer serializer = new JSONSerializer();
		if(obj.has("start_date") && obj.has("end_date")) {
			try {
				return serializer.serialize(binDbDriver.GetTestRequestForPeriod(obj.getString("start_date"), obj.getString("end_date")));
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return serializer.serialize(binDbDriver.GetTestRequests());
		}
	}
	
	@POST
	@Path("/addSingleResult/")
	@Produces("text/plain")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addSingleResult(JSONObject obj)
	{		
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		MriTestRequest req;
		MriBinaryResults bin = new MriBinaryResults(); 
		try {
			req = (MriTestRequest) session.load(MriTestRequest.class, obj.getInt("id"));
			bin.setMriTestRequest(req);
			bin.setResultValue(obj.getInt("value"));
			boolean res = binDbDriver.addSingleResult(bin);
			if(res)
				return "true";
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "false";
	}
	
	@POST
	@Path("/getCompletedTestReqests/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getCompletedTestReqests(JSONObject obj)
	{		
		JSONSerializer serializer = new JSONSerializer();
		if(obj.has("start_date") && obj.has("end_date")) {
			try {
				return serializer.serialize(binDbDriver.GetCompletedTestRequestsForPeriod(obj.getString("start_date"), obj.getString("end_date")));
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return serializer.serialize(binDbDriver.GetCompletedTestRequests());
		}
	}
	
	@GET
	@Path("/GetSingleTestRequestData/{reqid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetSingleTestRequestData(@PathParam("reqid")int reqid) {
		try {			
			List data= binDbDriver.GetSingleTestRequestData(reqid);
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.serialize(data);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
