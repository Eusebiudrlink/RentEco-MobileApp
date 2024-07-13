package com.example.serverrenteco.Domain;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;


//import javax.persistence.*;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "user_password")
    private String user_password;
    @Column(name = "full_name")
    private String full_name;
    @Column(name = "birth_date")
    private String birth_date;
    @Column(name = "user_address")
    private String user_address;
    @Column(name = "inrent")
    private int inrent;

    public User(String email, String user_password, String full_name, String birth_date, String user_address, int inrent) {
        this.email = email;
        this.user_password = user_password;
        this.full_name = full_name;
        this.birth_date = birth_date;
        this.user_address = user_address;
        this.inrent = inrent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public int getInrent() {
        return inrent;
    }

    public void setInrent(int inrent) {
        this.inrent = inrent;
    }
}