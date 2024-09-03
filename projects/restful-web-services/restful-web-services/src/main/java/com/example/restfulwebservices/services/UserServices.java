package com.example.restfulwebservices.services;

import com.example.restfulwebservices.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Predicate;

@Service
public class UserServices {
    private static List<User> users = new ArrayList<>();
    private static int usercount;

    static {


        users.add(new User(++usercount, "mina samy", LocalDate.now().minusYears(30)));
        users.add(new User(++usercount, "helana melad ", LocalDate.now().minusYears(30)));
        users.add(new User(++usercount, "Stavros mina", LocalDate.now().minusYears(30)));
    }


    public User save(User user) {


        user.setId(++usercount);
        users.add(user);
        return user;

    }

    public List<User> findAll() {


        return users;
    }


    public User findOne(int id) {

        Predicate<User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);

    }

    public void delete(int id) {

        Predicate<User> predicate = user -> user.getId().equals(id);
         users.removeIf(predicate );
    }
}
