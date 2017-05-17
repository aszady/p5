package p5.translators.marasm;

import p5.Crodis;
import p5.translators.Bbox;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class Controller {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Marasm!";
    }

	@RequestMapping("/get_area")
	public @ResponseBody Crodis area() {
		return new Crodis();
	}

}
