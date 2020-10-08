package com.cognixia.jump.repository;

import com.cognixia.jump.model.User;

import com.cognixia.jump.repository.custom.UserRepositoryCustomUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long>, UserRepositoryCustomUpdate {
    Optional<User> findById(long userId);
    void deleteById(long id);
    Optional<User> findByEmail(String email);
}
