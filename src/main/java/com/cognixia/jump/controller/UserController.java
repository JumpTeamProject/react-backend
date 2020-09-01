package com.cognixia.jump.controller;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @PatchMapping("/patch/user/email")

    public ResponseEntity<User> patchUserEmail(@RequestBody Map<String, String> userEmail) throws ResourceNotFoundException {
        Long id = Long.parseLong(userEmail.get("id"));
        String newEmail = userEmail.get("email");
        Optional<User> user = service.findById(id);

        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User with id= " + id + " is not found");
        }

        User updated = user.get();
        updated.setEmail(newEmail);
        service.save(updated);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/patch/user/password")
    public ResponseEntity<User> patchUserPassword(@RequestBody Map<String, String> userPassword) throws ResourceNotFoundException {
        Long id = Long.parseLong(userPassword.get("id"));
        String newPassword = userPassword.get("password");
        Optional<User> user = service.findById(id);

        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User with id= " + id + " is not found");
        }

        User updated = user.get();
        updated.setEmail(newPassword);
        service.save(updated);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }
}