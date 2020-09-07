package com.cognixia.jump.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The model for Restaurants.
 * @author Darreal Chambers and Lori White
 * @version v2 (09/05/2020)
 */
@Document(collection = "restaurants")
public class Restaurant implements Serializable {

	private static final long serialVersionUID = 6041514973117274821L;
	@Transient
	public static final String SEQUENCE_NAME = "restaurants_sequence";
	
	@Id
	@NotNull
	private Long id;

	@NotNull(message = "Restaurant name must not be null")
	private String name;

	private String imageUrl;

	private String menuLink;
	
	private String description;
	
	private Double rating;
	
	@NotNull(message = "Restaurant owner must not be null")
	private String owner;

	@Pattern(regexp = "^\\(\\d{3}\\)\\s?\\d{3}-\\d{4}$")
	private String phoneNumber;
	@NotNull(message = "Restaurant address id must not be null")
	private Long addressId;
	
	/**
	 * The default constructor.
	 * @author Darreal Chambers and Lori White
	 */
	public Restaurant() {
		this("N/A", "N/A", "N/A", "N/A", "N/A", "N/A", -1L);
	}
	/**
	 * The overloaded constructor.
	 * @author Darreal Chambers and Lori White
	 * @param name the restaurant's name 
	 * @param imageUrl the restaurant's image
	 * @param menuLink the restaurant's menu
	 * @param description the restaurant's description
	 * @param owner the restaurant's owner
	 * @param phoneNumber the restaurant's phone number
	 * @param addressId the restaurant's address 
	 */
	public Restaurant(@NotNull(message = "Restaurant name must not be null") String name, String imageUrl,
			String menuLink, String description, @NotNull(message = "Restaurant owner must not be null") String owner, @Pattern(regexp = "^\\(\\d{3}\\)\\s?\\d{3}-\\d{4}$") String phoneNumber,
			@NotNull(message = "Restaurant address id must not be null") Long addressId) {
		super();
		this.name = name;
		this.imageUrl = imageUrl;
		this.menuLink = menuLink;
		this.description = description;
		this.owner = owner;
		this.phoneNumber = phoneNumber;
		this.addressId = addressId;
		this.rating = 0.0;
	}
	/**
	 * Retrieves the restaurant id.
	 * @author Darreal Chambers
	 * @return Long - the restaurant id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Updates the restaurant id.
	 * @author Darreal Chambers
	 * @param id the restaurant id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Retrieves the restaurant's name.
	 * @author Darreal Chambers
	 * @return String - the restaurant's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Updates the restaurant's name.
	 * @author Darreal Chambers
	 * @param name the restaurant's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Retrieves the restaurant's image.
	 * @author Darreal Chambers
	 * @return String - the restaurant's image
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * Updates the restaurant's image.
	 * @author Darreal Chambers
	 * @param imageUrl the restaurant's image
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * Retrieves the restaurant's menu.
	 * @author Darreal Chambers
	 * @return String - the restaurant's menu
	 */
	public String getMenuLink() {
		return menuLink;
	}
	/**
	 * Updates the restaurant's menu.
	 * @author Darreal Chambers
	 * @param menuLink the restaurant's menu
	 */
	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}
	/**
	 * Retrieves the restaurant's description.
	 * @author Lori White
	 * @return String - the restaurant's description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Updates the restaurant's description.
	 * @author Lori White
	 * @param description the restaurant's description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Retrieves the restaurant's rating.
	 * @author Lori White
	 * @return Double - the restaurant's rating
	 */
	public Double getRating() {
		return rating;
	}
	/**
	 * Updates the restaurant's rating.
	 * @author Lori White
	 * @param rating the restaurant's rating
	 */
	public void setRating(Double rating) {
		this.rating = rating;
	}
	/**
	 * Retrieves the restaurant's owner.
	 * @author Darreal Chambers
	 * @return String - the restaurant's owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * Updates the restaurant's owner.
	 * @author Darreal Chambers
	 * @param owner the restaurant's owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * Retrieves the restaurant's phone number.
	 * @author Darreal Chambers
	 * @return String - the restaurant's phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * Updates the restaurant's phone number.
	 * @author Darreal Chambers
	 * @param phoneNumber the restaurant's phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * Retrieves the restaurant's address.
	 * @author Darreal Chambers
	 * @return Long - the restaurant's address
	 */
	public Long getAddressId() {
		return addressId;
	}
	/**
	 * Updates the restaurant's address.
	 * @author Darreal Chambers
	 * @param addressId the restaurant's address
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
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
	 * Creates a string representation of a restaurant.
	 * @author Darreal Chambers and Lori White
	 * @return String - a string representation of a restaurant
	 */
	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", imageUrl=" + imageUrl + ", menuLink=" + menuLink
				+ ", description=" + description + ", rating=" + rating + ", owner=" + owner + ", phoneNumber="
				+ phoneNumber + ", addressId=" + addressId + "]";
	}
}
