package com.example.serverrenteco.Service;

import com.example.serverrenteco.Domain.ParkingZone;
import com.example.serverrenteco.GPSUtils;
import com.example.serverrenteco.Repo.ParkingZoneRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyDouble;

@ExtendWith(MockitoExtension.class)
public class ParkingZoneServiceTest {

    @Mock
    private ParkingZoneRepo parkingZoneRepo;

    @InjectMocks
    private ParkingZoneService parkingZoneService;

    @Test
    public void testFindParkingZoneWithinRadius() {
        // Arrange
        ParkingZone vehicleParkingZone = new ParkingZone();
        vehicleParkingZone.setLatitude(45.0);
        vehicleParkingZone.setLongitude(25.0);

        ParkingZone matchingParkingZone = new ParkingZone();
        matchingParkingZone.setLatitude(45.0);
        matchingParkingZone.setLongitude(25.0);

        List<ParkingZone> parkingZones = Arrays.asList(matchingParkingZone);
        when(parkingZoneRepo.findAll()).thenReturn(parkingZones);
        // Mock GPSUtils static method
        try (MockedStatic<GPSUtils> gpsUtilsMockedStatic = Mockito.mockStatic(GPSUtils.class)) {
            gpsUtilsMockedStatic.when(() -> GPSUtils.isWithinRadius(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                    .thenReturn(true);

            // Act
            ParkingZone result = parkingZoneService.findParkingZone(vehicleParkingZone);

            // Assert
            assertThat(result).isEqualTo(matchingParkingZone);
        }
    }

    @Test
    public void testFindParkingZoneOutsideRadius() {
        // Arrange
        ParkingZone vehicleParkingZone = new ParkingZone();
        vehicleParkingZone.setLatitude(45.0);
        vehicleParkingZone.setLongitude(25.0);

        ParkingZone nonMatchingParkingZone = new ParkingZone();
        nonMatchingParkingZone.setLatitude(46.0);
        nonMatchingParkingZone.setLongitude(26.0);

        List<ParkingZone> parkingZones = Arrays.asList(nonMatchingParkingZone);
        when(parkingZoneRepo.findAll()).thenReturn(parkingZones);
        // Mock GPSUtils static method
        try (MockedStatic<GPSUtils> gpsUtilsMockedStatic = Mockito.mockStatic(GPSUtils.class)) {
            gpsUtilsMockedStatic.when(() -> GPSUtils.isWithinRadius(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                    .thenReturn(false);

            // Act
            ParkingZone result = parkingZoneService.findParkingZone(vehicleParkingZone);

            // Assert
            assertThat(result).isEqualTo(vehicleParkingZone);
        }
    }
}
