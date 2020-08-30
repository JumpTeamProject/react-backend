package com.cognixia.jump.controller;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
public class UserController {
    @Autowired
    UserRepo service;

    @GetMapping("/allUsers")
    public List<User> getUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        Optional<User> userFound = service.findById(id);
        System.out.println("--------------------------------------------");
        System.out.println("Found : " + userFound);
        System.out.println("--------------------------------------------");
        if(userFound.isPresent()) {
            return userFound.get();
        }

        return new User();
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
//        Optional<User> userFound = service.findById(id);
        if(service.existsById(id)) {
            service.deleteById(id);
            return ResponseEntity.status(200).body("Delete student with id = " + id);
        } else {
            return ResponseEntity.status(400).body("Student with id= " + id + " not found");
        }
    }

    @PostMapping("/add/user")
    public ResponseEntity<User> addUser(@RequestBody User newUser){
        if(service.existByEmail(newUser.getEmail())) {

            return ResponseEntity.status(400).body(newUser);
            //return a custom exception
//        throw new ResourceAlreadyExistsException("This user with email= " + newUser.getEmail() + " already exists.");
        } else {
            User added = service.save(newUser);
            return ResponseEntity.status(201).body(added);
        }


    }

    @PutMapping("/update/user")
    public @ResponseBody String updateUser(@RequestBody User updateUser) {
        Optional<User> userFound = service.findById(updateUser.getId());

        if(userFound.isPresent()) {
            service.save(updateUser);
            return "Saved: " + updateUser.toString();
        } else {
            return "Could not update student, the id = " + updateUser.getId() + " doesn't exist";
        }
    }
}