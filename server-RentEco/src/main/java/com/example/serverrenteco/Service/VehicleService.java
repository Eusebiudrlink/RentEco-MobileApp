package com.example.serverrenteco.Service;

import com.example.serverrenteco.Domain.AutoVehicle;
import com.example.serverrenteco.Repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class VehicleService {

    @Autowired
    VehicleRepo vehicleRepo;

    public List<AutoVehicle> findAllByUserId(String userId) {
        return null;
    }

    public void save(AutoVehicle vehicle) {
        vehicleRepo.save(vehicle);
    }

    public List<AutoVehicle> findAll() {
        return vehicleRepo.findAll();
    }

    public AutoVehicle update(int id, AutoVehicle receivedVehicle) {
        AutoVehicle autoVehicle = vehicleRepo.findById(id);
        if (autoVehicle.getRented() == false && receivedVehicle.getRented() == true)//daca nu e deja inchiriat si vreau sa il inchiriez
        {
            autoVehicle.setRented(true);
            return vehicleRepo.save(autoVehicle);
        } else if (autoVehicle.getRented() == true && receivedVehicle.getRented() == false) {//daca sa terminat inchirierea
                autoVehicle.setRented(false);
                return vehicleRepo.save(autoVehicle);
            }
          else if (autoVehicle.getRented() == false && receivedVehicle.getRented() == false ) {//daca vreau sa modific doar locatia{
                    autoVehicle.setAddress(receivedVehicle.getAddress());
                    autoVehicle.setLatitude((receivedVehicle.getLatitude()));
                    autoVehicle.setLongitude(receivedVehicle.getLongitude());
                    return vehicleRepo.save(autoVehicle);
                }
             else
                return null;
        }
    }
