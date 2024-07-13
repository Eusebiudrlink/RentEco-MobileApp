package com.example.serverrenteco.Service;

import com.example.serverrenteco.Domain.User;
import com.example.serverrenteco.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;;
    public User save(User user) {
        return userRepo.save(user);

    }

    public User findByEmail(String username) {
        try{
            return userRepo.findByEmail(username);
        } catch (Exception e) {
            System.out.println("User not found  ");
            return null;
        }
    }
}
