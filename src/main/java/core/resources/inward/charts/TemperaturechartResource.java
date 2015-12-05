package core.resources.inward.charts;

import java.util.List;
import java.text.DateFormat;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lib.driver.inward.driver_class.charts.TemperaturechartDBDriver;
import core.classes.inward.charts.Temperaturechart;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Path("temperaturechart")
public class TemperaturechartResource {

	TemperaturechartDBDriver temperaturechartdbddiver = new TemperaturechartDBDriver();
	
	@GET
	@Path("/getChart")
	@Produces(MediaType.APPLICATION_JSON)
	public String ChartDetails()
	{
		List<Temperaturechart> chartList = temperaturechartdbddiver.getChartValues();
		JSONSerializer serializer = new JSONSerializer();
		return serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"),
				"checkedDate").serialize(chartList);

	}
}
