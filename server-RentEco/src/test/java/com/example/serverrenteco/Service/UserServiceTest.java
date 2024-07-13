package com.example.serverrenteco.Service;

import com.example.serverrenteco.Domain.User;
import com.example.serverrenteco.Repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    public void testFindByEmailUserFound() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);
        when(userRepo.findByEmail(email)).thenReturn(mockUser);

        // Act
        User result = userService.findByEmail(email);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test
    public void testFindByEmailUserNotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepo.findByEmail(email)).thenReturn(null);

        // Act
        User result = userService.findByEmail(email);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void testFindByEmailException() {
        // Arrange
        String email = "exception@example.com";
        doThrow(new RuntimeException("Database error")).when(userRepo).findByEmail(email);

        // Act
        User result = userService.findByEmail(email);

        // Assert
        assertThat(result).isNull();
    }
}
