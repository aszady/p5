package org.p5.cas;

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
import java.util.Map;

@RestController
@RibbonClient(name = "cas", configuration = CasConfiguration.class)
public class CasController {

    private final RestTemplate restTemplate;

    @Value("${p5.yacs}")
    private String yacsName;

    @Value("${p5.updater}")
    private String updaterName;


    @Autowired
    public CasController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/location", method = RequestMethod.GET)
    public Crodis getLocation(@RequestParam double latitude,
                              @RequestParam double longitude) {
        Map<String, Double> coordinates = new HashMap<>(2);
        coordinates.put("latitude", latitude);
        coordinates.put("longitude", longitude);

        String yacsUrl = "http://" + yacsName + "/";
        Crodis crodis = restTemplate.getForObject(yacsUrl, Crodis.class, coordinates);
        if (crodis.isEmpty()) {
            restTemplate.postForLocation("http://" + updaterName + "location", null, coordinates);
            crodis = restTemplate.getForObject(yacsUrl, Crodis.class, coordinates);
        }
        return crodis;
    }

}
