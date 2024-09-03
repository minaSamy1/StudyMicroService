package com.example.restfulwebservices.controller;

import com.example.restfulwebservices.entity.User;
import com.example.restfulwebservices.exception.CustomUserException;
import com.example.restfulwebservices.exception.ExceptionErrorDetails;
import com.example.restfulwebservices.services.UserServices;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

@Tag(name = " users ")
@RestController
public class UserController {
    @Autowired
    private UserServices userServices;
    @Autowired
private MessageSource messageSource ;
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userServices.findAll();

    }

    @GetMapping("/users/{id}")
    @ApiResponse(responseCode = "500", description = "Business/System Error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ExceptionErrorDetails.class})))

    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    public User getUser(@PathVariable int id) {
        User user = userServices.findOne(id);
        if (user == null) {

            throw new CustomUserException(" >>>>>>>>>>>>>>>>>>>> No user Found ");
        }
        return userServices.findOne(id);
    }


    @PostMapping("/usersCreate")
    @ApiResponse(responseCode = "500", description = "Business/System Error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ExceptionErrorDetails.class})))

    @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    public ResponseEntity<User> Createusers(@Valid @RequestBody User user) {


        User savedUser = userServices.save(user);
        System.out.println(" Done ");

        URI lcoation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(lcoation).build();

    }

    @DeleteMapping("/users/{id}")
    @ApiResponse(responseCode = "500", description = "Business/System Error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ExceptionErrorDetails.class})))

    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    public void removeUser(@PathVariable int id) {
        userServices.delete(id);

    }

    @GetMapping("/testInteralization")
    public String test() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);


    }
    @GetMapping("/filitering")
    public MappingJacksonValue  filiteringAllUsers() {
        List<User> all = userServices.findAll();

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(all);

        SimpleBeanPropertyFilter filter =
                SimpleBeanPropertyFilter.filterOutAllExcept("name");  /// will display Only property "name"

        FilterProvider filters =
                new SimpleFilterProvider().addFilter("UserFilter", filter );

        mappingJacksonValue.setFilters(filters );

        return mappingJacksonValue;

    }
}
