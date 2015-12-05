package core.resources.inward.charts;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import core.classes.inward.charts.DiabeticChart;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import lib.driver.inward.driver_class.charts.DiabeticChartDBDriver;

@Path("DiabeticChart")
public class DiabeticChartResource {
	DiabeticChartDBDriver diabeticchartdbdriver = new DiabeticChartDBDriver();
	
	@GET
	@Path("/getChart")
	@Produces(MediaType.APPLICATION_JSON)
	public String ChartDetails()
	{
		List<DiabeticChart> chartList = diabeticchartdbdriver.getChartValues();
		JSONSerializer serializer = new JSONSerializer();
		return serializer.transform(new DateTransformer("yyyy-MM-dd"),
				"checkedDate").serialize(chartList);

	}
}
