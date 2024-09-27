package com.example.itec3860project1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.html.HTMLElement;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class vehicleController {

    private final ObjectMapper mapper= new ObjectMapper();
    private ApplicationHome home = new ApplicationHome(Itec3860Project1Application.class);

    @RequestMapping(value="/addVehicle", method= RequestMethod.POST)
    public vehicle addvehicle(@RequestBody vehicle vehicle) throws IOException {
        // we will keep track of vehicles in the file  "vehicles.txt"
        ObjectMapper mapper = new ObjectMapper();
        // serialized the vehicle
       String json = mapper.writeValueAsString(vehicle);
        FileUtils.writeStringToFile(new File("./vehicles.txt"), json +"\n", "UTF-8",true);
       return vehicle;

    }

    @RequestMapping(value="/getvehicle/{id}", method=RequestMethod.GET)
    public vehicle getvehicle(@PathVariable int id) throws IOException {
        File file = new File( home.getDir() + "/vehicle.txt");
        List<String> lines = FileUtils.readLines( file,"UTF-8");
        for (String line : lines) {
            vehicle v = mapper.readValue(line, vehicle.class);
            if(v.getId() == id) {
                return v;
            }
        }
        return new vehicle(0,"",0,0);
    }


    @RequestMapping(value="/updateVehicle", method=RequestMethod.PUT)
    public vehicle updatevehicle( @RequestBody vehicle vehicle) throws IOException {
        //read tne file
        File file = new File( home.getDir() + "/vehicle.txt");
       List<String>lines = FileUtils.readLines(file,"UTF-8");
       for(String line : lines) {
           vehicle v = mapper.readValue(line, vehicle.class);
           if(v.getId()== vehicle.getId()) {
               v.setMakeModel(vehicle.getMakeModel());
               v.setYear(vehicle.getYear());
               v.setRetailPrice(vehicle.getRetailPrice());

               //don't forget to update file!
               FileUtils.writeLines(file,lines,"UTF-8");
               return v;
           }
       }
        return vehicle;
    }

    @RequestMapping(value="/printVars", method=RequestMethod.GET)
    public void printVars() throws IOException {
  // home.getDir(); // returns the folder where the jar is.This is what i wanted.
  // home.getSource(); // returns the jar absolute path.
  // System.out.println(home.getDir());
  // System.out.println(home.getSource());
    }

}
