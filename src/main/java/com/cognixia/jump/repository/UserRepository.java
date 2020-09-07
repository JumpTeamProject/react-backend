package com.cognixia.jump.repository;

import com.cognixia.jump.model.User;

import com.cognixia.jump.repository.custom.UserRepositoryCustomUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The Repository for Users.
 * @author David Morales and Lori White
 * @version v3 (09/07/2020)
 */
@Repository
public interface UserRepository extends MongoRepository<User, Long>, UserRepositoryCustomUpdate {
	//added by David Morales 
    Optional<User> findById(long userId);
    //added by David Morales
    void deleteById(long id);
    //added by David Morales
    Optional<User> findByEmail(String email);
    //added by Lori White
    boolean existsByEmail(String email);
}
