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

import core.classes.mri.MriDepartment;
import core.classes.mri.MriLaboratory;
import core.classes.mri.MriLaboratoryTest;
import core.classes.mri.MriSpecimenType;
import core.classes.mri.MriTestParentFieldRange;
import core.classes.mri.MriTestParentFields;
import core.classes.mri.MriTestSubFieldRange;
import core.classes.mri.MriTestSubFields;
import flexjson.JSONSerializer;
import lib.SessionFactoryUtil;
import lib.driver.mri.driver_class.MriTestFieldsDBDriver;
import lib.driver.mri.driver_class.MriTestRequestDBDriver;
import lib.driver.mri.driver_class.MriTestResultsDBDriver;

@Path("MriTestFields")
public class MriTestFields {
	MriTestFieldsDBDriver mriTestFieldsDBDriver = new MriTestFieldsDBDriver();
	
	@GET
	@Path("/GetAllDepartments/")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllDepartments() {
		try {	
			List<MriDepartment> deptList =  mriTestFieldsDBDriver.GetAllDepartments();
			
			JSONSerializer serializer = new JSONSerializer();
	
			return serializer.exclude("*.class").serialize(deptList);
		} catch(Exception e) {
			return e.getMessage();
		}		
	}
	
	@GET
	@Path("/GetAllLabs/")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllLabs() {
		try {	
			List<MriLaboratory> deptList =  mriTestFieldsDBDriver.GetAllLabs();
			
			JSONSerializer serializer = new JSONSerializer();
	
			return serializer.exclude("*.class").serialize(deptList);
		} catch(Exception e) {
			return e.getMessage();
		}		
	}
	
	@GET
	@Path("/GetAllSpecimenTypes/")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllSpecimanTypes() {
		try {	
			List<MriSpecimenType> deptList =  mriTestFieldsDBDriver.GetAllSpecimenTypes();
			
			JSONSerializer serializer = new JSONSerializer();
	
			return serializer.exclude("*.class").serialize(deptList);
		} catch(Exception e) {
			return e.getMessage();
		}		
	}
	
	@GET
	@Path("/GetAllLabTestTypes/")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllLabTestTypes() {
		try {	
			List<MriLaboratoryTest> deptList =  mriTestFieldsDBDriver.GetAllLabTests();
			
			JSONSerializer serializer = new JSONSerializer();
	
			return serializer.exclude("*.class","mriLaboratory.*").serialize(deptList);
		} catch(Exception e) {
			return e.getMessage();
		}		
	}
	
	@POST
	@Path("/addLabTestName")
	@Produces("text/plain")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addLabTestName(JSONObject obj)
	{
		MriLaboratoryTest test = new MriLaboratoryTest();
		try {
			test.setTestFullName(obj.getString("test_name"));
			test.setTestShortName(obj.getString("test_short_name"));
			test.setIsBinary(obj.getInt("is_binary"));
			Session session = SessionFactoryUtil.getSessionFactory().openSession();
			MriLaboratory lab = (MriLaboratory) session.load(MriLaboratory.class, obj.getInt("lab"));
			test.setMriLaboratory(lab);
			session.close();
			if(mriTestFieldsDBDriver.addLabTest(test)){
				return "true";
			} else {
				return "false";
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "false";
	}
	
	MriTestResultsDBDriver mriTestResultsDBDriver = new MriTestResultsDBDriver();
	
	@GET
	@Path("/GetParentFeilds/{labTestId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetTestParentFields(@PathParam("labTestId")int labTestId) {
		try {			
			List<MriTestParentFields> testFields= mriTestResultsDBDriver.GetResultParentTypes(labTestId);
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testFields);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@POST
	@Path("/addTestParentField")
	@Produces("text/plain")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addTestParentField(JSONObject obj)
	{
		MriTestParentFields testParentFields = new MriTestParentFields();
		try {
			Session session = SessionFactoryUtil.getSessionFactory().openSession();
			MriLaboratoryTest tst = (MriLaboratoryTest) session.load(MriLaboratoryTest.class, obj.getInt("lab_test_id"));
			session.close();
			testParentFields.setMriLaboratoryTest(tst);
			testParentFields.setTestParentFieldName(obj.getString("test_field_name"));
			boolean res=mriTestFieldsDBDriver.addParentTestField(testParentFields);
			if(res)
				return "true";
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "false";
	}
	
	@GET
	@Path("/CheckForExistenceParents/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String CheckForExistenceParents(JSONObject obj) {
		try {
			String gender = obj.getString("gender");
			int parentId = obj.getInt("parentId");
			if(mriTestFieldsDBDriver.checkForExistenceParent(gender, parentId)==true){
				return "true";
			} else {
				return "false";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "false";
	}
	
	@POST
	@Path("/addParentFieldData")
	@Produces("text/plain")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addParentFieldData(JSONObject obj)
	{
		MriTestParentFieldRange data = new MriTestParentFieldRange();
		try {
			data.setGender(obj.getString("gender"));
			data.setMaxAge(obj.getInt("maxAge"));
			data.setMinage(obj.getInt("minAge"));
			data.setMinVal(obj.getInt("minValue"));
			data.setMaxVal(obj.getInt("maxValue"));
			data.setUnit(obj.getString("unit"));
			data.setFtestParentFieldId(obj.getInt("parentID"));
			boolean res=mriTestFieldsDBDriver.addParentFieldData(data);
			if(res)
				return "true";
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "false";
	}
	
	@GET
	@Path("/GetTestParentData/{labTestId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetTestParentData(@PathParam("labTestId")int labTestId) {
		try {	
			List<MriTestParentFieldRange> pRange =  mriTestFieldsDBDriver.GetTestParentData(labTestId);
			
			JSONSerializer serializer = new JSONSerializer();
	
			return serializer.exclude("*.class").serialize(pRange);
		} catch(Exception e) {
			return e.getMessage();
		}		
	}
	
	@POST
	@Path("/addTestSubField")
	@Produces("text/plain")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addTestSubField(JSONObject obj)
	{
		MriTestSubFields subField = new MriTestSubFields();
		try {
			subField.setTestSubFieldName(obj.getString("testName"));
			Session session = SessionFactoryUtil.getSessionFactory().openSession();
			MriTestParentFields par = (MriTestParentFields) session.load(MriTestParentFields.class, obj.getInt("parentField"));
			subField.setMriTestParentFields(par);
			session.close();
			boolean res=mriTestFieldsDBDriver.addSubTestField(subField);
			if(res)
				return "true";
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "false";
	}
	
	@GET
	@Path("/GetSubFeilds/{parentField}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetTestSubFields(@PathParam("parentField")int parentField) {
		try {			
			List<MriTestSubFields> testFields= mriTestResultsDBDriver.GetResultSubTypes(parentField);
			
			JSONSerializer serializer = new JSONSerializer();

			return serializer.exclude("*.class").serialize(testFields);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@POST
	@Path("/addSubFieldData")
	@Produces("text/plain")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addSubFieldData(JSONObject obj)
	{
		MriTestSubFieldRange data = new MriTestSubFieldRange();
		try {
			data.setGender(obj.getString("gender"));
			data.setMaxAge(obj.getInt("maxAge"));
			data.setMinage(obj.getInt("minAge"));
			data.setMinVal(obj.getInt("minValue"));
			data.setMaxVal(obj.getInt("maxValue"));
			data.setUnit(obj.getString("unit"));
			Session session = SessionFactoryUtil.getSessionFactory().openSession();
			MriTestSubFields sub = (MriTestSubFields) session.load(MriTestSubFields.class, obj.getInt("parentID"));
			data.setMriTestSubFields(sub);
			session.close();
			boolean res=mriTestFieldsDBDriver.addSubFieldData(data);
			if(res)
				return "true";
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "false";
	}
}