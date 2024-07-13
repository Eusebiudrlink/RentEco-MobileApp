package com.example.serverrenteco.Repo;

import com.example.serverrenteco.Domain.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepo extends JpaRepository<Ride, Long> {
}
