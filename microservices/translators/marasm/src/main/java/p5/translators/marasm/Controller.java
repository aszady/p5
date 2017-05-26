package p5.translators.marasm;

import org.p5.commons.crodis.Crodis;
import org.p5.commons.crodis.Item;
import org.springframework.web.bind.annotation.RequestMapping;
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
        Crodis crodis = new Crodis("marasm");
        Map<String, Float> conditions = new HashMap<>();

        // AGH UST
        conditions.put(Item.CONDITION_TEMPERATURE, 44f);
        crodis.addItem(50.068f ,19.912f, 10f, conditions);

        // Ruczaj
        conditions.put(Item.CONDITION_TEMPERATURE, -42f);
        crodis.addItem(50.030f, 19.908f, 10f, conditions);

        // Nowa Huta
        conditions.put(Item.CONDITION_TEMPERATURE, 0f);
        crodis.addItem(50.072f, 20.038f, 10f, conditions);

        return crodis;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Marasm!";
    }

	@RequestMapping("/get_area")
	public @ResponseBody Crodis area() {
		return this.crodis;
	}

}
