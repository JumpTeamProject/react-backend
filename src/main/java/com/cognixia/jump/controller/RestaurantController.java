package com.cognixia.jump.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceAlreadyExistsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.repository.RestaurantRepository;

import io.swagger.annotations.ApiOperation;

/**
 * The controller for the Restaurants. 
 * @author Lori White
 * @version v3 (08/31/2020)
 */
@RequestMapping("/api")
@RestController
public class RestaurantController {
	@Autowired 
	RestaurantRepository service;
	
	/**
	 * Retrieves all the restaurants in the database and sorts them by name in ascending order.
	 * @author Lori White
	 * @return List - the restaurants in the database
	 */
	@GetMapping("/restaurants")
	@ApiOperation( value = "", 
	notes = "Retrieves all the restaurants in the database and sorts them by name in ascending order.\n"
   		+ "Usage: looks up a list of all restaurants in the database\n"
   		+ "Author(s): Lori White\n"
   		+ "Execption(s): None",
   	response = List.class, produces = "application/json")
	public List<Restaurant> getAllRestaurants() {
		return service.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	/**
	 * Retrieves a restaurant pertaining to an id.
	 * @author Lori White
	 * @param id the restaurant id to search for
	 * @return Restaurant - the restaurant pertaining to the id
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database
	 */
	@GetMapping("/restaurant/{id}")
	@ApiOperation( value = "", 
	notes = "Retrieves a restaurant pertaining to an id.\n"
   		+ "Usage: provide an id to look up a restaurant in the database\n"
   		+ "Author(s): Lori White\n"
   		+ "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database",
   	response = Restaurant.class, produces = "application/json")
	public Restaurant getRestaurantById(@PathVariable Long id) throws ResourceNotFoundException {
		Optional<Restaurant> restaurant = service.findById(id);

		if(restaurant.isEmpty()) {
			throw new ResourceNotFoundException("The restaurant with id= " + id + " does not exist in the database.");
		}
		return restaurant.get();
	}
	/**
	 * Adds a restaurant to the database.
	 * @author Lori White
	 * @param newRestaurant a new restaurant to add
	 * @return ResponseEntity - a response of was created and the created restaurant
	 * @throws ResourceAlreadyExistsException is thrown when the id or the name and the address id does match an existing restaurant in the database
	 */
	@PostMapping("/add/restaurant")
	@ApiOperation( value = "", 
	notes = "Adds a restaurant to the database.\n"
   		+ "Usage: provide a new restaurant to add to the database\n"
   		+ "Author(s): Lori White\n"
   		+ "Execption(s): ResourceAlreadyExistsException is thrown when the id or the name and the address id does match an existing restaurant in the database",
   	response = ResponseEntity.class, produces = "application/json")
	public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant newRestaurant) throws ResourceAlreadyExistsException {
		if(service.existsById(newRestaurant.getId())) {
			throw new ResourceAlreadyExistsException("Restaurant with id= " + newRestaurant.getId() + " already exists.");
		}
		if(service.existsByNameAndAddressId(newRestaurant.getName(), newRestaurant.getAddressId())) {
			throw new ResourceAlreadyExistsException("Restaurant with name= " + newRestaurant.getName() + " and address= " + newRestaurant.getAddressId() + " already exists.");
		}
		Restaurant created = service.insert(newRestaurant);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	/**
	 * Deletes a restaurant from the database.
	 * @author Lori White
	 * @param id the restaurant id to search for 
	 * @return ResponseEntity - a response of was accepted and the deleted restaurant
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database
	 */
	@DeleteMapping("/delete/restaurant/{id}")
	@ApiOperation( value = "", 
	notes = "Deletes a restaurant from the database.\n"
   		+ "Usage: provide an id to remove a restaurant from the database\n"
   		+ "Author(s): Lori White\n"
   		+ "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database",
   	response = ResponseEntity.class, produces = "application/json")
	public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable Long id) throws ResourceNotFoundException {
		Optional<Restaurant> deleted = service.findById(id);
		if(deleted.isEmpty()) {
			throw new ResourceNotFoundException("Restaurant with id= " + id + " does not exist.");
		} 
		service.deleteById(id);
		return new ResponseEntity<>(deleted.get(), HttpStatus.ACCEPTED);
	}
	/**
	 * Updates the name of a restaurant.
	 * @author Lori White
	 * @param restaurantName a map that holds a restaurant id of the restaurant and the restaurant's name to update
	 * @return ResponseEntity - a response of was accepted and the updated restaurant
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database
	 * @throws ResourceAlreadyExistsException is thrown when the new name and the address id does match an existing restaurant in the database
	 */
	@PatchMapping("/patch/restaurant/name")
	@ApiOperation( value = "", 
	notes = "Updates the name of a restaurant.\n"
  		+ "Usage: provide a map that holds a restaurant id of the restaurant and the restaurant's name to update in the database\n"
  		+ "Author(s): Lori White\n"
  		+ "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database, ResourceAlreadyExistsException is thrown when the new name and the address id does match an existing restaurant in the database",
  	response = ResponseEntity.class)
	public ResponseEntity<Restaurant> patchRestaurantName(@RequestBody Map<String, String> restaurantName) throws ResourceNotFoundException, ResourceAlreadyExistsException {
		Long id = Long.parseLong(restaurantName.get("id"));
		String newName = restaurantName.get("name");
		if(!service.existsById(id)) {
			throw new ResourceNotFoundException("Restaurant with id= " + id + " was not found.");
		}
		Long addressId = service.findById(id).get().getAddressId();
		if(service.existsByNameAndAddressId(newName, addressId)) {
			throw new ResourceAlreadyExistsException("Restaurant with name= " + newName + " and address= " + addressId + " already exists.");
		}
		Restaurant updated = service.patch(id, "name", newName);	
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	/**
	 * Updates the imageUrl of a restaurant.
	 * @author Lori White
	 * @param restaurantImageUrl a map that holds a restaurant id of the restaurant and the restaurant's imageUrl to update
	 * @return ResponseEntity - a response of was accepted and the updated restaurant
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database
	 */
	@PatchMapping("/patch/restaurant/imageurl")
	@ApiOperation( value = "", 
	notes = "Updates the imageUrl of a restaurant.\n"
  		+ "Usage: provide a map that holds a restaurant id of the restaurant and the restaurant's imageUrl to update in the database\n"
  		+ "Author(s): Lori White\n"
  		+ "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database",
  	response = ResponseEntity.class)
	public ResponseEntity<Restaurant> patchRestaurantImageUrl(@RequestBody Map<String, String> restaurantImageUrl) throws ResourceNotFoundException {
		Long id = Long.parseLong(restaurantImageUrl.get("id"));
		String newImageUrl = restaurantImageUrl.get("imageUrl");
		if(!service.existsById(id)) {
			throw new ResourceNotFoundException("Restaurant with id= " + id + " was not found.");
		}
		Restaurant updated = service.patch(id, "imageUrl", newImageUrl);	
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	/**
	 * Updates the menuLink of a restaurant.
	 * @author Lori White
	 * @param restaurantMenuLink a map that holds a restaurant id of the restaurant and the restaurant's menuLink to update
	 * @return ResponseEntity - a response of was accepted and the updated restaurant
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database
	 */
	@PatchMapping("/patch/restaurant/menulink")
	@ApiOperation( value = "", 
	notes = "Updates the menuLink of a restaurant.\n"
  		+ "Usage: provide a map that holds a restaurant id of the restaurant and the restaurant's menuLink to update in the database\n"
  		+ "Author(s): Lori White\n"
  		+ "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database",
  	response = ResponseEntity.class)
	public ResponseEntity<Restaurant> patchRestaurantMenuLink(@RequestBody Map<String, String> restaurantMenuLink) throws ResourceNotFoundException {
		Long id = Long.parseLong(restaurantMenuLink.get("id"));
		String newMenuLink = restaurantMenuLink.get("menuLink");
		if(!service.existsById(id)) {
			throw new ResourceNotFoundException("Restaurant with id= " + id + " was not found.");
		}
		Restaurant updated = service.patch(id, "menuLink", newMenuLink);	
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	/**
	 * Updates the description of a restaurant.
	 * @author Lori White
	 * @param restaurantDescription a map that holds a restaurant id of the restaurant and the restaurant's description to update
	 * @return ResponseEntity - a response of was accepted and the updated restaurant
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database
	 */
	@PatchMapping("/patch/restaurant/description")
	@ApiOperation( value = "", 
	notes = "Updates the description of a restaurant.\n"
  		+ "Usage: provide a map that holds a restaurant id of the restaurant and the restaurant's description to update in the database\n"
  		+ "Author(s): Lori White\n"
  		+ "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database",
  	response = ResponseEntity.class)
	public ResponseEntity<Restaurant> patchRestaurantDescription(@RequestBody Map<String, String> restaurantDescription) throws ResourceNotFoundException {
		Long id = Long.parseLong(restaurantDescription.get("id"));
		String newDescription = restaurantDescription.get("description");
		if(!service.existsById(id)) {
			throw new ResourceNotFoundException("Restaurant with id= " + id + " was not found.");
		}
		Restaurant updated = service.patch(id, "description", newDescription);	
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	/**
	 * Updates the rating of a restaurant.
	 * @author Lori White
	 * @param restaurantRating a map that holds a restaurant id of the restaurant and the restaurant's rating to update
	 * @return ResponseEntity - a response of was accepted and the updated restaurant
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database
	 */
	@PatchMapping("/patch/restaurant/rating")
	@ApiOperation( value = "", 
	notes = "Updates the rating of a restaurant.\n"
  		+ "Usage: provide a map that holds a restaurant id of the restaurant and the restaurant's rating to update in the database\n"
  		+ "Author(s): Lori White\n"
  		+ "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database",
  	response = ResponseEntity.class)
	public ResponseEntity<Restaurant> patchRestaurantRating(@RequestBody Map<String, String> restaurantRating) throws ResourceNotFoundException {
		Long id = Long.parseLong(restaurantRating.get("id"));
		Double newRating = Double.parseDouble(restaurantRating.get("rating"));
		if(!service.existsById(id)) {
			throw new ResourceNotFoundException("Restaurant with id= " + id + " was not found.");
		}
		Restaurant updated = service.patch(id, "rating", newRating);	
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	/**
	 * Updates the owner of a restaurant.
	 * @author Lori White
	 * @param restaurantOwner a map that holds a restaurant id of the restaurant and the restaurant's owner to update
	 * @return ResponseEntity - a response of was accepted and the updated restaurant
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database
	 */
	@PatchMapping("/patch/restaurant/owner")
	@ApiOperation( value = "", 
	notes = "Updates the owner of a restaurant.\n"
  		+ "Usage: provide a map that holds a restaurant id of the restaurant and the restaurant's owner to update in the database\n"
  		+ "Author(s): Lori White\n"
  		+ "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database",
  	response = ResponseEntity.class)
	public ResponseEntity<Restaurant> patchRestaurantOwner(@RequestBody Map<String, String> restaurantOwner) throws ResourceNotFoundException {
		Long id = Long.parseLong(restaurantOwner.get("id"));
		String newOwner = restaurantOwner.get("owner");
		if(!service.existsById(id)) {
			throw new ResourceNotFoundException("Restaurant with id= " + id + " was not found.");
		}
		Restaurant updated = service.patch(id, "owner", newOwner);	
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	/**
	 * Updates the phoneNumber of a restaurant.
	 * @author Lori White
	 * @param restaurantPhoneNumber a map that holds a restaurant id of the restaurant and the restaurant's phoneNumber to update
	 * @return ResponseEntity - a response of was accepted and the updated restaurant
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database
	 */
	@PatchMapping("/patch/restaurant/phonenumber")
	@ApiOperation( value = "", 
	notes = "Updates the phoneNumber of a restaurant.\n"
  		+ "Usage: provide a map that holds a restaurant id of the restaurant and the restaurant's phoneNumber to update in the database\n"
  		+ "Author(s): Lori White\n"
  		+ "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database",
  	response = ResponseEntity.class)
	public ResponseEntity<Restaurant> patchRestaurantPhoneNumber(@RequestBody Map<String, String> restaurantPhoneNumber) throws ResourceNotFoundException {
		Long id = Long.parseLong(restaurantPhoneNumber.get("id"));
		String newPhoneNumber = restaurantPhoneNumber.get("phoneNumber");
		if(!service.existsById(id)) {
			throw new ResourceNotFoundException("Restaurant with id= " + id + " was not found.");
		}
		Restaurant updated = service.patch(id, "phoneNumber", newPhoneNumber);	
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	/**
	 * Updates the address id of a restaurant.
	 * @author Lori White
	 * @param restaurantAddressId a map that holds a restaurant id of the restaurant and the restaurant's address id to update
	 * @return ResponseEntity - a response of was accepted and the updated restaurant
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database
	 * @throws ResourceAlreadyExistsException is thrown when the name and the new address id does match an existing restaurant in the database 
	 */
	@PatchMapping("/patch/restaurant/address")
	@ApiOperation( value = "", 
	notes = "Updates the address id of a restaurant.\n"
  		+ "Usage: provide a map that holds a restaurant id of the restaurant and the restaurant's address id to update in the database\n"
  		+ "Author(s): Lori White\n"
  		+ "Execption(s): ResourceNotFoundException is thrown when the id does not match an existing restaurant in the database, ResourceAlreadyExistsException is thrown when the name and the new address id does match an existing restaurant in the database ",
  	response = ResponseEntity.class)
	public ResponseEntity<Restaurant> patchRestaurantAddressId(@RequestBody Map<String, String> restaurantAddressId) throws ResourceNotFoundException, ResourceAlreadyExistsException {
		Long id = Long.parseLong(restaurantAddressId.get("id"));
		Long newAddressId = Long.parseLong(restaurantAddressId.get("addressId"));
		if(!service.existsById(id)) {
			throw new ResourceNotFoundException("Restaurant with id= " + id + " was not found.");
		}
		String name = service.findById(id).get().getName();
		if(service.existsByNameAndAddressId(name, newAddressId)) {
			throw new ResourceAlreadyExistsException("Restaurant with name= " + name + " and address= " + newAddressId + " already exists.");
		}
		Restaurant updated = service.patch(id, "addressId", newAddressId);	
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
}
