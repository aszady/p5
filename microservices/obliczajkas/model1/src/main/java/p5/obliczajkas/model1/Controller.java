package p5.obliczajkas.model1;


import org.p5.commons.crodis.Crodis;
import org.p5.commons.crodis.Item;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    private Crodis crodis;

    public Controller() {
        this.crodis = this.generateCrodis();
    }

    private Crodis generateCrodis() {
        Crodis crodis = new Crodis("mock");
        Map<String, Float> conditions = new HashMap<>();

        // AGH UST
        conditions.put(Item.CONDITION_TEMPERATURE, 44f);
        crodis.addItem(50.068f, 19.912f, 10f, conditions);

        return crodis;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Obliczajka1!";
    }

    @RequestMapping("/point")
    public
    @ResponseBody
    Crodis calculate(@RequestParam(value = "latitude") Float latitude,
                     @RequestParam(value = "longitude") Float longitude) {
        return this.crodis;
    }

}
