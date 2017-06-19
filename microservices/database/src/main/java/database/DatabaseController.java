package database;

import org.p5.commons.crodis.Crodis;
import org.p5.commons.crodis.Item;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Mefju on 25.05.2017.
 */
@RestController
public class DatabaseController {

    ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfiguration.class);
    MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

    public void prepareIndex(){
        mongoOperation.indexOps(CrodisItem.class).ensureIndex(new GeospatialIndex("location"));
        mongoOperation.indexOps(CrodisItem.class).ensureIndex(new Index().on("timeOfDeliver", Sort.Direction.ASC).expire(3600));
    }


    @RequestMapping(method=RequestMethod.PUT)  //dodawanie nowych crodisów
    public int putCrodis(@RequestBody Crodis crodis){
        for (Item item: crodis.getItems()) {
            CrodisItem crodisItem = new CrodisItem(crodis.getSource(), item);
            crodisItem.setTimeOfDeliver(new Date());
            mongoOperation.save(crodisItem);
        }
        return 10;
    }

    @RequestMapping(method=RequestMethod.GET)  //wysyłanie crodisów
    public Crodis getCrodis(@RequestParam(value = "latitude") Float latitude, @RequestParam(value = "longitude") Float longitude){
        BasicQuery query1 = new BasicQuery("{ location : { $near : [ "+latitude.toString()+", "+longitude.toString()+"], $maxDistance: 1000 } }");
        List<CrodisItem> crodisItems = mongoOperation.find(query1, CrodisItem.class);
        Crodis tmp = new Crodis();
        for (CrodisItem item : crodisItems) {
            tmp.addItem(item.location[0], item.location[1], item.radius, item.conditions);
        }

        return tmp;
    }
}
