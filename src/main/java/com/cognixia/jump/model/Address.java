package com.cognixia.jump.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The model for Address.
 * @author Darreal Chambers and Lori White
 * @version v2 (09/05/2020)
 */
@Document(collection = "address")
public class Address implements Serializable {

	private static final long serialVersionUID = -6174792291767963142L;
	@Transient
	public static final String SEQUENCE_NAME = "address_sequence";
	
	@Id
	@NotNull
	private Long id;
	@NotNull(message = "Address street must not be null")
	@Pattern(regexp = "^[0-9]+\\s[a-zA-Z]\\s*$")
	private String street;
	@NotNull(message = "Address city must not be null")
	private String city;
	@NotNull(message = "Address state must not be null")
	@Pattern(regexp = "^[A-Z]{2}$")
	private String state;
	@NotNull(message = "Address zip must not be null")
	@Pattern(regexp = "^\\d{5}$")
	private String zip;
	
	/**
	 * The default constructor.
	 * @author Darreal Chambers and Lori White
	 */
	public Address() {
		this("N/A", "N/A", "N/A", "N/A");
	}
	/**
	 * The overloaded constructor.
	 * @author Darreal Chambers and Lori White
	 * @param street the address's street  
	 * @param city the address's city
	 * @param state the address's state
	 * @param zip the address's zip code
	 */
	public Address(@NotNull(message = "Address street must not be null") @Pattern(regexp = "^[0-9]+\\s[a-zA-Z]\\s*$") String street, @NotNull(message = "Address city must not be null") String city,
			@NotNull(message = "Address state must not be null") @Pattern(regexp = "^[A-Z]{2}$") String state, @NotNull(message = "Address zip must not be null") @Pattern(regexp = "^\\d{5}$") String zip) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	/**
	 * Retrieves the address id.
	 * @author Darreal Chambers
	 * @return Long - the address id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Updates the address id.
	 * @author Darreal Chambers
	 * @param id the address id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Retrieves the address's street.
	 * @author Darreal Chambers
	 * @return String - the address's street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * Updates the address's street.
	 * @author Darreal Chambers
	 * @param street the address's street
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * Retrieves the address's city.
	 * @author Darreal Chambers
	 * @return String - the address's city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * Updates the address's city.
	 * @author Darreal Chambers
	 * @param city the address's city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * Retrieves the address's state.
	 * @author Darreal Chambers
	 * @return String - the address's state
	 */
	public String getState() {
		return state;
	}
	/**
	 * Updates the address's state.
	 * @author Darreal Chambers
	 * @param state the address's state
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * Retrieves the address's zip code.
	 * @author Darreal Chambers
	 * @return String - the address's zip code
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * Updates the address's zip code.
	 * @author Darreal Chambers
	 * @param zip the address's zip code
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * Retrieves the serial version.
	 * @author Lori White
	 * @return long - the serial version
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * Creates a string representation of a address.
	 * @author Darreal Chambers
	 * @return String - a string representation of a address
	 */
	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", zip=" + zip
				+ "]";
	}
}
