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

import core.classes.lims.ParentTestFields;
import core.classes.lims.SubTestFields;
import flexjson.JSONSerializer;
import lib.driver.lims.driver_class.ParentTestFieldsDBDriver;
import lib.driver.lims.driver_class.SubTestFieldsDBDriver;
import lib.driver.lims.driver_class.TestFieldsRangeDBDriver;
import lib.driver.lims.driver_class.TestNamesDBDriver;

@Path("SubTestFields")
public class SubTestFieldsResource {

ParentTestFieldsDBDriver parentfieldDBDriver= new ParentTestFieldsDBDriver();
TestFieldsRangeDBDriver testFieldsRangeDBDriver= new TestFieldsRangeDBDriver();
SubTestFieldsDBDriver subtestfieldsDBDriver= new SubTestFieldsDBDriver();
	
@POST
@Path("/addNewSubTestField")
@Produces("text/plain")
@Consumes(MediaType.APPLICATION_JSON)
public String addNewParentField(JSONObject obj)
{
	try {
		JSONArray data = obj.getJSONArray("subfields");
		Set<SubTestFields> SubFieldList = new HashSet<SubTestFields>();
		for (int curr = 0; curr < data.length(); curr++){
			SubTestFields sf = new SubTestFields();
			sf.setSubField_IDName("SF");
			sf.setSubtest_FieldName(data.getJSONObject(curr).getString("subtest_FieldName"));
			//sf.setfTest_RangeID(testFieldsRangeDBDriver.getTestFieldRangeByID(Integer.parseInt(data.getJSONObject(curr).getString("fTest_RangeID"))));
			sf.setfPar_Test_FieldID(parentfieldDBDriver.getParentFieldByID(Integer.parseInt(data.getJSONObject(curr).getString("fPar_Test_FieldID"))));			
			SubFieldList.add(sf);
		
        } 
		
		for (SubTestFields sf : SubFieldList) {
			subtestfieldsDBDriver.addNewSubTestField(sf);
		}
		
	} catch (JSONException e) {
		e.printStackTrace();
		return null; 
	}     	        
	catch (Exception e) {
		System.out.println(e.getMessage());
		return null; 
	}
	return "TRUE";
}
	
	@GET
	@Path("/getAllSubTestFields")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllParenTestFields()
	{
		List<SubTestFields> subTestFIeldsList =   subtestfieldsDBDriver.getSubTestFieldsList();
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.include("fPar_Test_FieldID.parent_FieldName").exclude("*.class","fPar_Test_FieldID.*").serialize(subTestFIeldsList);
	}
	
	@GET
	@Path("/getMaxSubTestFieldID")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetMAxSubTestField()
	{
		String MaxID =   subtestfieldsDBDriver.getsubtestfieldID();
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.exclude("*.class").serialize(MaxID);
	}
	
	
}
