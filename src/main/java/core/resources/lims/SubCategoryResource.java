package core.resources.lims;

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

import core.classes.lims.Category;
import core.classes.lims.SubCategory;
import flexjson.JSONSerializer;
import lib.driver.lims.driver_class.CategoryDBDriver;
import lib.driver.lims.driver_class.SubCategoryDBDriver;

@Path("SubCategory")
public class SubCategoryResource {

SubCategoryDBDriver sDBDriver= new SubCategoryDBDriver();
	
	@POST
	@Path("/addSubCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addSubCategory(JSONObject pJson)
	{	

		try {
			SubCategory scat  =  new SubCategory();
			
			int categoryID = pJson.getInt("fCategory_ID");
			
			scat.setSubCategory_IDName(pJson.get("subCategory_IDName").toString());
			scat.setSub_CategoryName(pJson.get("sub_CategoryName").toString());
			
			
			sDBDriver.insertSubCategory(scat, categoryID);
			 
			JSONSerializer jsonSerializer = new JSONSerializer();
			return jsonSerializer.include("sub_CategoryID").serialize(scat);
			
		} catch (Exception e) {
			 System.out.println(e.getMessage());
			 
			return null; 
		}

	}
	
	@GET
	@Path("/getAllSubCategories")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllSubCategories()
	{
		List<SubCategory> subCatList =   sDBDriver.getSubCategoryList();
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.include("fCategory_ID.category_Name","Specimentypes").exclude("*.class","fCategory_ID.*").serialize(subCatList);
	}
	
	@GET
	@Path("/getSubCategoriesByCategoryID/{catID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllSubCategories(@PathParam("catID")int catID)
	{
		List<SubCategory> subCatList =   sDBDriver.getSubCategoryListByCatID(catID);
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.include("sub_CategoryID","subCategory_IDName","sub_CategoryName").exclude("*.class","fCategory_ID.*").serialize(subCatList);
	}
	
	
	
}
