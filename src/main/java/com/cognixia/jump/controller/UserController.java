package com.cognixia.jump.controller;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import com.cognixia.jump.exception.ResourceAlreadyExistsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;

import com.cognixia.jump.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    UserRepository service;

    @Autowired
    private MongoOperations mongoTemplate;

    @GetMapping("/allUsers")
    public List<User> getUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    @ApiOperation( value = "",
            notes = "Retrieves a user by their id.\n"
                    + "Usage: provide an id to look up a user in the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceNotFoundException is thrown when the user id does not match any existing entries with that specified user id in the database",
            response = User.class, produces = "application/json")
    public User getUserById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<User> userFound = service.findById(id);
        if(userFound.isEmpty()) {
            throw new ResourceNotFoundException("User with id= " + id + " is not found");
        }
        return new User();
    }

    @DeleteMapping("/delete/user/{id}")
    @ApiOperation( value = "",
            notes = "Deletes a user from the database.\n"
                    + "Usage: provide an id to remove a user from the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing user in the database",
            response = ResponseEntity.class, produces = "application/json")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws ResourceNotFoundException {
        Optional<User> userFound = service.findById(id);
        if(userFound.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.status(200).body("Delete student with id = " + id);
        } else {
            throw new ResourceNotFoundException("Student with id= " + id + " not found");
        }
    }

    @PostMapping("/add/user")
    @ApiOperation( value = "",
            notes = "Adds a user to the database.\n"
                    + "Usage: provide a new user to add to the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceAlreadyExistsException is thrown when the email does match an existing address in the database",
            response = ResponseEntity.class)
    public ResponseEntity<User> addUser(@RequestBody User newUser) throws ResourceAlreadyExistsException {
        if (service.existsById(newUser.getId())) {
            //return a custom exception
            throw new ResourceAlreadyExistsException("This user with email= " + newUser.getEmail() + " already exists.");
        } else {
            User added = service.insert(newUser);
            return ResponseEntity.status(201).body(added);
        }
    }

    @PutMapping("/update/user")
    @ApiOperation( value = "",
            notes = "Adds a user to the database.\n"
                    + "Usage: provide a new user to add to the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceAlreadyExistsException is thrown when the email does match an existing address in the database",
            response = ResponseEntity.class)
    public ResponseEntity<User> updateUser(@RequestBody User updateUser) throws ResourceNotFoundException {
        Optional<User> userFound = service.findById(updateUser.getId());

        if(userFound.isEmpty()) {
//            service.save(updateUser);
//            return "Saved: " + updateUser.toString();
            throw new ResourceNotFoundException("Could not update student, the id = " + updateUser.getId() + " doesn't exist");

        }

        User updated = userFound.get();
        updated.setFirstName(updateUser.getFirstName());
        updated.setLastName(updateUser.getLastName());
        updated.setEmail(updateUser.getEmail());
        updated.setPassword(updateUser.getPassword());
        updated.setAddressId(updateUser.getAddressId());

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(updated.getId()));
        Update update = new Update();
        update.set("firstName", updated.getFirstName());
        update.set("lastName", updated.getLastName());
        update.set("email", updated.getEmail());
        update.set("password", updated.getPassword());
        update.set("addressId", updated.getAddressId());

        updated = mongoTemplate.findAndModify(query, update, options().returnNew(true).upsert(true), User.class);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/patch/user/email")
    @ApiOperation( value = "",
            notes = "Updates the email of a user.\n"
                    + "Usage: provide a map that holds a user id of the user and the users's email to update in the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing user in the database",
            response = ResponseEntity.class)
    public ResponseEntity<User> patchUserEmail(@RequestBody Map<String, String> userEmail) throws ResourceNotFoundException {
        Long id = Long.parseLong(userEmail.get("id"));
        String newEmail = userEmail.get("email");
        System.out.println(newEmail);
        Optional<User> user = service.findById(id);

        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User with id= " + id + " is not found");
        }

        User updated = service.patch(id, "email", newEmail);
//        updated.setEmail(newEmail);
//        Query query = new Query();
//        query.addCriteria(Criteria.where("id").is(updated.getId()));
//        Update update = new Update();
//        update.set("email", updated.getEmail());
//        updated = mongoTemplate.findAndModify(query, update, options().returnNew(true).upsert(true), User.class);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/patch/user/password")
    @ApiOperation( value = "",
            notes = "Updates the password of a user.\n"
                    + "Usage: provide a map that holds a user id of the user and the users's new password to update in the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing user in the database",
            response = ResponseEntity.class)
    public ResponseEntity<User> patchUserPassword(@RequestBody Map<String, String> userPassword) throws ResourceNotFoundException {
        Long id = Long.parseLong(userPassword.get("id"));
        String newPassword = userPassword.get("password");
        Optional<User> user = service.findById(id);

        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User with id= " + id + " is not found");
        }

//        User updated = user.get();
//        updated.setEmail(newPassword);
//        service.save(updated);
        User updated = service.patch(id, "password", newPassword);
//        updated.setEmail(newPassword);
//        Query query = new Query();
//        query.addCriteria(Criteria.where("id").is(updated.getId()));
//        Update update = new Update();
//        update.set("password", updated.getEmail());
//        updated = mongoTemplate.findAndModify(query, update, options().returnNew(true).upsert(true), User.class);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }

}
