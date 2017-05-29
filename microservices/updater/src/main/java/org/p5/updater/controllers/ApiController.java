package org.p5.updater.controllers;

import org.p5.commons.crodis.Crodis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    @Value("${p5.yacs}")
    private String yacsUrl;

    @Value("${p5.translators}")
    private List<String> translatorUrls;


    @RequestMapping(value = "/location", method = RequestMethod.POST)
    public void calculateLocation(@RequestParam double latitude,
                                  @RequestParam double longitude) {
        Map<String, Double> coordinates = new HashMap<>(2);
        coordinates.put("latitude", latitude);
        coordinates.put("longitude", longitude);
        for (String url : translatorUrls) {
            RestTemplate template = new RestTemplate();
            saveCrodis(template.getForObject(url + "/location", Crodis.class, coordinates));
        }
    }

    private void saveCrodis(Crodis crodis) {
        RestTemplate template = new RestTemplate();
        template.put(yacsUrl, crodis);
    }
}
