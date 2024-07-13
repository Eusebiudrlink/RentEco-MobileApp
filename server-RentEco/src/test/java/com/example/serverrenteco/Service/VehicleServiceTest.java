package com.example.serverrenteco.Service;

import com.example.serverrenteco.Domain.AutoVehicle;
import com.example.serverrenteco.Repo.VehicleRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepo vehicleRepo;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    public void testUpdateRentingVehicle() {
        // Arrange
        int vehicleId = 1;
        AutoVehicle receivedVehicle = new AutoVehicle();
        receivedVehicle.setRented(true);

        AutoVehicle existingVehicle = new AutoVehicle();
        existingVehicle.setRented(false);
        when(vehicleRepo.findById(vehicleId)).thenReturn(existingVehicle);
        when(vehicleRepo.save(existingVehicle)).thenReturn(existingVehicle);

        // Act
        AutoVehicle result = vehicleService.update(vehicleId, receivedVehicle);

        // Assert
        assertThat(result).isEqualTo(existingVehicle);
    }

    @Test
    public void testUpdateReturningVehicle() {
        // Arrange
        int vehicleId = 1;
        AutoVehicle receivedVehicle = new AutoVehicle();
        receivedVehicle.setRented(false);

        AutoVehicle existingVehicle = new AutoVehicle();
        existingVehicle.setRented(true);
        when(vehicleRepo.findById(vehicleId)).thenReturn(existingVehicle);
        when(vehicleRepo.save(existingVehicle)).thenReturn(existingVehicle);

        // Act
        AutoVehicle result = vehicleService.update(vehicleId, receivedVehicle);

        // Assert
        assertThat(result).isEqualTo(existingVehicle);
    }

    @Test
    public void testUpdateVehicleLocation() {
        // Arrange
        int vehicleId = 1;
        AutoVehicle receivedVehicle = new AutoVehicle();
        receivedVehicle.setRented(false);
        receivedVehicle.setAddress("New Address");
        receivedVehicle.setLatitude(45.0);
        receivedVehicle.setLongitude(25.0);

        AutoVehicle existingVehicle = new AutoVehicle();
        existingVehicle.setRented(false);
        when(vehicleRepo.findById(vehicleId)).thenReturn(existingVehicle);
        when(vehicleRepo.save(existingVehicle)).thenReturn(existingVehicle);

        // Act
        AutoVehicle result = vehicleService.update(vehicleId, receivedVehicle);

        // Assert
        assertThat(result).isEqualTo(existingVehicle);
        assertThat(result.getAddress()).isEqualTo("New Address");
        assertThat(result.getLatitude()).isEqualTo(45.0);
        assertThat(result.getLongitude()).isEqualTo(25.0);
    }

    @Test
    public void testUpdateNoChanges() {
        // Arrange
        int vehicleId = 1;
        AutoVehicle receivedVehicle = new AutoVehicle();
        receivedVehicle.setRented(true);

        AutoVehicle existingVehicle = new AutoVehicle();
        existingVehicle.setRented(true);
        when(vehicleRepo.findById(vehicleId)).thenReturn(existingVehicle);

        // Act
        AutoVehicle result = vehicleService.update(vehicleId, receivedVehicle);

        // Assert
        assertThat(result).isNull();
    }
}
