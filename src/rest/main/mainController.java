package rest.main;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rest.ships.ships;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class mainController {
    @GetMapping("/schedule/{amount}")
    public void getSchedule(@PathVariable("amount") int amount) throws URISyntaxException, JsonProcessingException {
        URI url = new URI("http://localhost:8080/schedule/"+amount);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<rest.main.ships> sched = objectMapper.readValue(response.getBody(), new TypeReference<List<rest.main.ships>>(){});
        System.out.println();
        main.printSchedule(sched);
        main.anotherShip(sched);
        main.createJson(sched);
    }

    @GetMapping("/readJson/{name}")
    public List<rest.main.ships> readJson(@PathVariable("name") String name) {
        ObjectMapper mapper = new ObjectMapper();
        List<rest.main.ships> ship = new ArrayList<>();
        try {
            ship = Arrays.asList(mapper.readValue(Paths.get("target/"+name).toFile(), rest.main.ships[].class));
        } catch(Exception e){
            System.out.println("This file does not exist");
            return null;
        }
        return ship;
    }

    @GetMapping("/ship")
    public ships getShips() {
        ships ship = ships.generateNewShip();
        ship.getShipInfo();
        return ship;
    }

    @PostMapping("/results/{results}")
    public String results(@PathVariable("results") List<String> results) throws JSONException {
        Map<String, Object> report = new HashMap<>();
        report.put("Total ships unloaded", results.get(0));
        report.put("Average queue size", results.get(1));
        report.put("Average queue wait time", results.get(2));
        report.put("Average unload delay", results.get(3));
        report.put("Longest unload delay from Dry", results.get(4));
        report.put("Longest unload delay from Fluid", results.get(5));
        report.put("Longest unload delay from Container", results.get(6));
        report.put("Total Bill", results.get(7));
        report.put("Fluid cranes", results.get(8));
        report.put("Dry cranes", results.get(9));
        report.put("Container cranes", results.get(10));
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        File reportFile = new File ("target/report.json");
        try {
            writer.writeValue(reportFile, report);
            System.out.println("Successfully Copied JSON Object to File...");
        } catch(Exception e){
            System.out.println(e);
        }
        return "Report was created successfully";
    }
}
