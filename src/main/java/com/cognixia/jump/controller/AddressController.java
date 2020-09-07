package com.cognixia.jump.controller;

import java.util.Optional;

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
import com.cognixia.jump.model.Address;
import com.cognixia.jump.repository.AddressRepository;

import io.swagger.annotations.ApiOperation;

/**
 * The controller for Address.
 * @author Darreal Chambers and Lori White
 * @version v4 (08/31/2020)
 */
@RequestMapping("/api")
@RestController
public class AddressController {

	@Autowired
	AddressRepository service;
	
	/**
	 * Retrieves an address by their id.
	 * @author Darreal Chambers and Lori White
	 * @param id the address id to search for
	 * @return Address - the address that pertains to the id
	 * @throws ResourceNotFoundException is thrown when the address id does not match any existing entries with that specified address id in the database
	 */
	@GetMapping("/address/{id}")
	@ApiOperation( value = "", 
		notes = "Retrieves an address by their id.\n"
	   		+ "Usage: provide an id to look up an address in the database\n"
	   		+ "Author(s): Darreal Chambers and Lori White\n"
	   		+ "Execption(s): ResourceNotFoundException is thrown when the address id does not match any existing entries with that specified address id in the database",
	   	response = Address.class, produces = "application/json")
	public Address getAddressById(@PathVariable Long id) throws ResourceNotFoundException {
		Optional<Address> found = service.findById(id);
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("Address with id= " + id + " not found");
		}
		return found.get();
	}
	/**
	 * Retrieves an address by their street and zip.
	 * @author Lori White
	 * @param street the address's street to search for
	 * @param zip the address's zip to search for
	 * @return Address - the address that pertains to the street and zip
	 * @throws ResourceNotFoundException is thrown when the address street and zip does not match any existing entries in the database
	 */
	@GetMapping("/api/address/{street}/street/{zip}/zip")
	@ApiOperation( value = "", 
	notes = "Retrieves an address by their street and zip.\n"
   		+ "Usage: provide a street and a zip to look up an address in the database\n"
   		+ "Author(s): Lori White\n"
   		+ "Execption(s): ResourceNotFoundException is thrown when the address street and zip does not match any existing entries in the database",
   	response = Address.class, produces = "application/json")
	public Address getAddressByStreetAndZip(@PathVariable String street, @PathVariable String zip) throws ResourceNotFoundException {
		if(service.existsByStreetAndZip(street, zip)) {
			throw new ResourceNotFoundException("Address with street= " + street + " and zip= " + zip + " does not exist.");
		}
		return service.findByStreetAndZip(street, zip);
	}
	/**
	 * Adds an address to the database.
	 * @author Lori White
	 * @param newAddress a new address to add
	 * @return ResponseEntity - a response of was created and the created address
	 * @throws ResourceAlreadyExistsException is thrown when the id or the street and the zip does match an existing address in the database
	 */
	@PostMapping("/add/address")
	@ApiOperation( value = "", 
	notes = "Adds an address to the database.\n"
   		+ "Usage: provide a new address to add to the database\n"
   		+ "Author(s): Lori White\n"
   		+ "Execption(s): ResourceAlreadyExistsException is thrown when the id or the street and  the zip does match an existing address in the database",
   	response = ResponseEntity.class, produces = "application/json")
	public ResponseEntity<Address> addAddress(@Valid @RequestBody Address newAddress) throws ResourceAlreadyExistsException {
		if(service.existsById(newAddress.getId())) {
			throw new ResourceAlreadyExistsException("Address with id= " + newAddress.getId() + " already exists.");
		}
		if(service.existsByStreetAndZip(newAddress.getStreet(), newAddress.getZip())) {
			throw new ResourceAlreadyExistsException("Address with street= " + newAddress.getStreet() + " and zip= " + newAddress.getZip() + " already exists.");
		}
		Address created = service.insert(newAddress);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
}
