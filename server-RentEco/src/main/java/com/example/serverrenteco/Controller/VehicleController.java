package com.example.serverrenteco.Controller;

import com.example.serverrenteco.Domain.AutoVehicle;
import com.example.serverrenteco.Service.VehicleService;
import com.example.serverrenteco.Validators.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private TokenValidator tokenValidator;

    @GetMapping("/")
    public ResponseEntity<List<AutoVehicle>> getAllItems(@RequestHeader("Authorization") String authorization) {
        System.out.println("Get all items");
        String jwtToken = authorization.substring(7);
        if(tokenValidator.validateToken(jwtToken)==true){
            System.out.println("Token is valid");
            List<AutoVehicle> vehicles = vehicleService.findAll();
            return ResponseEntity.ok(vehicles);
        }
        else{
            System.out.println("Token is invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<AutoVehicle> update(@PathVariable("id") int id, @RequestHeader("Authorization") String authorization,@RequestBody AutoVehicle receivedVehicle) {
        System.out.println("Update item with id = " + id);
        String jwtToken = authorization.substring(7);
        if(tokenValidator.validateToken(jwtToken)==true){
            System.out.println("Token is valid");
            try {
                AutoVehicle vehicle = vehicleService.update(id, receivedVehicle);
                return ResponseEntity.ok(vehicle);
            } catch (Exception e) {
                System.out.println("Error on updateVehicle");
                return ResponseEntity.notFound().build();
            }
        }
        else{
            System.out.println("Token is invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PostMapping("/")
    public ResponseEntity<?> createItem(@RequestBody AutoVehicle vehicle) {
    //public ResponseEntity<?> createItem(@RequestBody Vehicle vehicle, @RequestAttribute("userId") String userId) {
       // vehicle.setId(userId);
        vehicleService.save(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
