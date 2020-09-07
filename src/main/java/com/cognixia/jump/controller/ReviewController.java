package com.cognixia.jump.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceAlreadyExistsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.repository.ReviewRepository;

import io.swagger.annotations.ApiOperation;

/**
 * The controller for the Reviews. 
 * @author Lori White
 * @version v2 (08/25/2020)
 */
@RequestMapping("/api")
@RestController
public class ReviewController {
	@Autowired
	ReviewRepository service;
	
	/**
	 * Retrieves a list of reviews pertaining to a restaurant id.
	 * @author Lori White
	 * @param id the restaurant id to search for
	 * @return List - the reviews in the database pertaining to the restaurant id
	 * @throws ResourceNotFoundException is thrown when the restaurant id does not match an existing review's restaurant id in the database
	 */
	@GetMapping("/reviews/{id}/restaurant") 
	@ApiOperation( value = "", 
	notes = "Retrieves a list of reviews pertaining to a restaurant id.\n"
   		+ "Usage: provide a restaurant id to look up a list of reviews in the database\n"
   		+ "Author(s): Lori White\n"
   		+ "Execption(s): ResourceNotFoundException is thrown when the restaurant id does not match an existing review's restaurant id in the database",
   	response = List.class, produces = "application/json")
	public List<Review> getReviewsByRestaurantID(@PathVariable Long id) throws ResourceNotFoundException {
		if(!service.existsByRestaurantId(id)) {
			throw new ResourceNotFoundException("There are no reviews for the restaurant with id= " + id);
		}
		return service.findByRestaurantId(id);
	}
	/**
	 * Adds a review to the database.
	 * @author Lori White
	 * @param newReview a new review to add
	 * @return ResponseEntity - a response of was created and the created review
	 * @throws ResourceAlreadyExistsException is thrown when the id or the restaurant id and the user id does match an existing review in the database
	 */
	@PostMapping("/add/review")
	@ApiOperation( value = "", 
	notes = "Adds a review to the database.\n"
   		+ "Usage: provide a restaurant id to look up a list of reviews in the database\n"
   		+ "Author(s): Lori White\n"
   		+ "Execption(s): ResourceAlreadyExistsException is thrown when the id or the restaurant id and the user id does match an existing review in the database",
   	response = ResponseEntity.class, produces = "application/json")
    public ResponseEntity<Review> addReview(@Valid @RequestBody Review newReview) throws ResourceAlreadyExistsException {
		if(service.existsById(newReview.getId())) {
			throw new ResourceAlreadyExistsException("The review with id= " + newReview.getId() + " already exists.");
		}
		if(service.existsByRestaurantIdAndUserId(newReview.getRestaurantId(), newReview.getUserId()) ) {
			throw new ResourceAlreadyExistsException("The review with restaurant id= " + newReview.getRestaurantId() + " and user id= " + newReview.getUserId() + " already exists.");
		}
        Review added  = service.insert(newReview);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }
}
