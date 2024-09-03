package com.example.restfulwebservices.jpaController;

import com.example.restfulwebservices.entity.Post;
import com.example.restfulwebservices.entity.User;
import com.example.restfulwebservices.exception.CustomUserException;
import com.example.restfulwebservices.exception.ExceptionErrorDetails;
import com.example.restfulwebservices.repository.PostRepository;
import com.example.restfulwebservices.repository.UserRepository;
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
import java.util.Optional;

@Tag(name = " Users JPA Controller ")
@RestController
public class UserJpaController {
@Autowired
private PostRepository postRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/jpa/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();

        //userServices.findAll();

    }

    @GetMapping("/jpa/users/{id}")
    @ApiResponse(responseCode = "500", description = "Business/System Error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ExceptionErrorDetails.class})))

    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    public Optional<User> getUser(@PathVariable int id) {
        Optional<User> user1 = userRepository.findById(id);

        if (user1.isEmpty()) {

            throw new CustomUserException(" >>>>>>>>>>>>>>>>>>>> No user Found ");
        }
        return user1;
        //userServices.findOne(id);
    }


    @GetMapping("/jpa/users/{id}/posts")
    @ApiResponse(responseCode = "500", description = "Business/System Error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ExceptionErrorDetails.class})))

    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    public List<Post>getUserPosts(@PathVariable int id) {
        Optional<User> user1 = userRepository.findById(id);

        if (user1.isEmpty()) {

            throw new CustomUserException(" >>>>>>>>>>>>>>>>>>>> No user Found ");
        }
        return user1.get().getPostList();
        //userServices.findOne(id);
    }


    @PostMapping("/jpa/usersCreate")
    @ApiResponse(responseCode = "500", description = "Business/System Error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ExceptionErrorDetails.class})))

    @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    public ResponseEntity<User> Createusers(@Valid @RequestBody User user) {


        User savedUser = userRepository.save(user);
        System.out.println(" Done ");

        URI lcoation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(lcoation).build();

    }

    @DeleteMapping("/jpa/users/{id}")
    @ApiResponse(responseCode = "500", description = "Business/System Error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ExceptionErrorDetails.class})))

    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    public void removeUser(@PathVariable int id) {
        userRepository.deleteById(id);

    }

    @PostMapping("/jpa/users/{id}/posts")
    @ApiResponse(responseCode = "500", description = "Business/System Error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ExceptionErrorDetails.class})))

    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    public ResponseEntity <Post>createPostForUser(@PathVariable int id , @RequestBody Post post) {
        Optional<User> user1 = userRepository.findById(id);

        if (user1.isEmpty()) {

            throw new CustomUserException(" >>>>>>>>>>>>>>>>>>>> No user Found ");
        }

        post.setUser(user1.get());
       Post savedPost= postRepo.save(post);
         /// it will return in Response Header
        URI lcoation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(lcoation).build();

        //userServices.findOne(id);
    }
}
