package com.example.serverrenteco.Service;

import com.example.serverrenteco.Domain.Ride;
import com.example.serverrenteco.Repo.RideRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class RideService {
    @Autowired
    private RideRepo rideRepo;;

    public void save(Ride ride) {
        rideRepo.save(ride);
    }

    public List<Ride> findAll() {
       return rideRepo.findAll();
    }
}
