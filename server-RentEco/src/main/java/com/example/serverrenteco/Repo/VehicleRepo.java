package com.example.serverrenteco.Repo;

import com.example.serverrenteco.Domain.AutoVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepo extends JpaRepository<AutoVehicle, Long> {

     List<AutoVehicle> findAll();

     AutoVehicle findById(int id);

     AutoVehicle save(AutoVehicle autoVehicle);

}
