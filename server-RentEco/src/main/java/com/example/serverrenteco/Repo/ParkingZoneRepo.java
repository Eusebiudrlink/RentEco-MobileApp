package com.example.serverrenteco.Repo;

import com.example.serverrenteco.Domain.ParkingZone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingZoneRepo extends JpaRepository<ParkingZone, Long> {
}
