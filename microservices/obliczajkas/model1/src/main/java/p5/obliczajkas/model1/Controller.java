package p5.obliczajkas.model1;


import org.p5.commons.crodis.Crodis;
import org.p5.commons.crodis.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    private CalculatorFactory calculatorFactory;

    @Value("${p5.yacs}")
    private String yacsUrl;

    public Controller() throws GeoprocessingException {
        DistanceCalculatorFactory dcf = new DistanceCalculatorFactory();
        Interpolator defaultInterpolator = new LinearInterpolator(dcf.buildDefault());
        this.calculatorFactory = new CalculatorFactory(dcf, defaultInterpolator);
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Obliczajka1!";
    }

    private TargetDescription constructTargetDescription(float latitude, float longitude) {
        TargetDescription targetDescription = new TargetDescription();
        targetDescription.setLatitude(latitude);
        targetDescription.setLongitude(longitude);
        return targetDescription;
    }

    private Crodis fetchCrodis(float latitude, float longitude) {
		String url = "http://" + yacsUrl + "/?latitude=" + latitude + "&longitude=" + longitude;
        return new RestTemplate().getForObject(url, Crodis.class);
    }

	@RequestMapping("/point")
	public @ResponseBody Crodis calculate(@RequestParam(value = "latitude") Float latitude,
                                          @RequestParam(value = "longitude") Float longitude) throws GeoprocessingException {
        Calculator calculator = this.calculatorFactory.getCalculator(
                "haversine", this.constructTargetDescription(latitude, longitude));
        calculator.feedCrodis(fetchCrodis(latitude, longitude));
        return calculator.interpolate();
    }
}
