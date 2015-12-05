package core.resources.lims;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import core.classes.lims.Category;
import flexjson.JSONSerializer;
import lib.driver.lims.driver_class.CategoryDBDriver;

@Path("Category")
public class CategoryResource {

CategoryDBDriver cDBDriver= new CategoryDBDriver();
	
	@POST
	@Path("/addCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addCategory(JSONObject pJson)
	{
		
		try {
			Category cat  =  new Category();
			cat.setCategory_IDName(pJson.get("category_IDName").toString());
			cat.setCategory_Name(pJson.get("category_Name").toString());	
			cDBDriver.insertCategory(cat);
			 
			JSONSerializer jsonSerializer = new JSONSerializer();
			return jsonSerializer.include("category_ID").serialize(cat);
		} catch (Exception e) {
			 System.out.println(e.getMessage());
			return null; 
		}

	}
	
	@GET
	@Path("/getAllCategories")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCategories()
	{
		List<Category> testsList =   cDBDriver.getCategoryList();
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.exclude("*.class").serialize(testsList);
	}
	

}
