package core.resources.lims;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import core.classes.lims.Category;
import core.classes.lims.SubTestFields;
import core.classes.lims.TestFieldsRange;
import flexjson.JSONSerializer;
import lib.driver.lims.driver_class.CategoryDBDriver;
import lib.driver.lims.driver_class.ParentTestFieldsDBDriver;
import lib.driver.lims.driver_class.SubTestFieldsDBDriver;
import lib.driver.lims.driver_class.TestFieldsRangeDBDriver;

@Path("TestFieldsRange")
public class TestFieldsRangeResource {

TestFieldsRangeDBDriver  rangeDBDriver= new TestFieldsRangeDBDriver();
ParentTestFieldsDBDriver parentfieldDBDriver = new ParentTestFieldsDBDriver();
SubTestFieldsDBDriver subtestfieldsDBDriver = new SubTestFieldsDBDriver();
	
	@POST
	@Path("/addNewRange")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addRanges(JSONObject obj)
	{
		try {
			
				TestFieldsRange ra = new TestFieldsRange();
				ra.setGender(obj.getString("gender").toString());
				ra.setMinage(Integer.parseInt(obj.getString("minage").toString()));
				ra.setMaxage(Integer.parseInt(obj.getString("maxage").toString()));
				ra.setUnit(obj.getString("unit").toString());
				ra.setMinVal(Double.parseDouble(obj.getString("minVal").toString()));
				ra.setMaxVal(Double.parseDouble(obj.getString("maxVal").toString()));
				ra.setFparentfield_ID(parentfieldDBDriver.getParentFieldByID(Integer.parseInt(obj.getString("fparentfield_ID").toString())));
				//ra.setFsubfield_ID(subtestfieldsDBDriver.getSubTestFieldByID(Integer.parseInt(obj.getString("fsubfield_ID").toString())));
			
				rangeDBDriver.addNewRange(ra);
				JSONSerializer jsonSerializer = new JSONSerializer();
				return jsonSerializer.include("range_ID").serialize(ra);
				
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null; 
		}     	        
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null; 
		}
		
	}
	
	@GET
	@Path("/getAllRanges")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCategories()
	{
		List<TestFieldsRange> rangeList =   rangeDBDriver.getRangeList();
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.include("fparentfield_ID.parent_FieldName","fsubfield_ID.subtest_FieldName").exclude("*.class","fparentfield_ID.*","fsubfield_ID.*").serialize(rangeList);
	}
	
	
}
