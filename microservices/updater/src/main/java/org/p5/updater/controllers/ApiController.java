package org.p5.updater.controllers;

import org.p5.commons.crodis.Crodis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    private final DiscoveryClient discoveryClient;

    @Value("${p5.crodis.radius}")
    private double radius;

    @Value("${p5.yacs}")
    private String yacsName;

    @Value("${p5.translators}")
    private List<String> translatorNames;

    @Autowired
    public ApiController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @RequestMapping(value = "/location", method = RequestMethod.POST)
    public void calculateLocation(@RequestParam double latitude,
                                  @RequestParam double longitude) {
        Map<String, Double> coordinates = new HashMap<>(2);
        coordinates.put("latitude", latitude);
        coordinates.put("longitude", longitude);
        coordinates.put("radius", radius);
        for (String name : translatorNames) {
            RestTemplate template = new RestTemplate();
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(name);
            if (serviceInstances.size() != 0) {
                String url = serviceInstances.get(0).getUri() + "/area";
//                String url = "http://localhost:4412/area";
                Crodis crodis = template.getForObject(url, Crodis.class, coordinates);
                saveCrodis(crodis);
            }
        }
    }

    private void saveCrodis(Crodis crodis) {
        RestTemplate template = new RestTemplate();
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(yacsName);
        if (serviceInstances.size() != 0) {
            template.put(serviceInstances.get(0).getUri(), crodis);
        }
    }
}
