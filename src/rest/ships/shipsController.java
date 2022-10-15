package rest.ships;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class shipsController {

    @RequestMapping("/ships")
    public ships getShips() {
        ships ship = ships.generateNewShip();
        ship.getShipInfo();
        return ship;
    }

    @GetMapping("/schedule")
    public String generateSchedule() {
        List<ships> sched = new ArrayList<ships>();
        schedule.generateSchedule(20, sched);
//                ships[] sched = schedule.generateSchedule(num);
        schedule.sortSchedule(sched);
        schedule.printSchedule(sched);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        StringWriter sw =new StringWriter();
        try {
            writer.writeValue(sw, sched);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sw.toString();
    }

    @RequestMapping("/schedule/{amount}")
    public String generateNewSchedule(@PathVariable("amount") int amount) {
        List<ships> sched = new ArrayList<ships>();
        schedule.generateSchedule(amount, sched);
//                ships[] sched = schedule.generateSchedule(num);
        schedule.sortSchedule(sched);
        schedule.printSchedule(sched);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        StringWriter sw = new StringWriter();
        try {
            writer.writeValue(sw, sched);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sw.toString();
    }

}
