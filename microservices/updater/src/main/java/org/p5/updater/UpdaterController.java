package org.p5.updater;

import org.p5.commons.crodis.Crodis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RibbonClient(name = "updater", configuration = UpdaterConfiguration.class)
public class UpdaterController {

    private final RestTemplate restTemplate;

    @Value("${p5.crodis.radius}")
    private double radius;

    @Value("${p5.yacs}")
    private String yacsName;

    @Value("${p5.translators}")
    private List<String> translatorNames;


    @Autowired
    public UpdaterController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/location", method = RequestMethod.POST)
    public void calculateLocation(@RequestParam double latitude,
                                  @RequestParam double longitude) {
        Map<String, Double> coordinates = new HashMap<>(3);
        coordinates.put("latitude", latitude);
        coordinates.put("longitude", longitude);
        coordinates.put("radius", radius);
        for (String name : translatorNames) {
            String url = "http://" + name + "/area";
            Crodis crodis = restTemplate.getForObject(url, Crodis.class, coordinates);
            saveCrodis(crodis);
        }
    }

    private void saveCrodis(Crodis crodis) {
        restTemplate.put("http://" + yacsName, crodis);
    }
}
