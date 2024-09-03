package com.example.restfulwebservices.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Integer  id ;


    private String description ;


    @ManyToOne(fetch = FetchType.LAZY)   /// which mean when to get the post not fetch the user
    @JsonIgnore
    private User user ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
