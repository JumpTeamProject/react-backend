package com.cognixia.jump.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The model for Reviews.
 * @author Lori White
 * @version v2 (09/05/2020)
 */
@Document(collection = "reviews")
public class Review implements Serializable {
	private static final long serialVersionUID = -4458870115303573931L;
	@Transient
	public static final String SEQUENCE_NAME = "reviews_sequence";

	
	@Id
	@NotNull
	private Long id;
	private String description;
	@NotNull(message = "Review rating must not be null")
	private Double rating;
	@NotNull(message = "Review restaurant id must not be null")
	private Long restaurantId;
	@NotNull(message = "Review user id must not be null")
	private Long userId;
	
	/**
	 * The default constructor.
	 * @author Lori White
	 */
	public Review() {
		this("N/A", 0.00, -1L, -1L);
	}
	/**
	 * The overloaded constructor.
	 * @author Lori White
	 * @param description the review's description
	 * @param rating the review's rating
	 * @param restaurantId the restaurant linked to this review
	 * @param userId the user linked to this to this review
	 */
	public Review(String description, @NotNull(message = "Review rating must not be null") Double rating, @NotNull(message = "Review restaurant id must not be null") Long restaurantId, @NotNull(message = "Review user id must not be null") Long userId) {
		super();
		this.description = description;
		this.rating = rating;
		this.restaurantId = restaurantId;
		this.userId = userId;
	}
	/**
	 * Retrieves the review id.
	 * @author Lori White
	 * @return Long - the review id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Updates the review id.
	 * @author Lori White
	 * @param id the review id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Retrieves the review's description.
	 * @author Lori White
	 * @return String - the review's description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Updates the review's description.
	 * @author Lori White
	 * @param description the review's description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Retrieves the review's rating.
	 * @author Lori White
	 * @return Double - the review's rating
	 */
	public Double getRating() {
		return rating;
	}
	/**
	 * Updates the review's rating.
	 * @author Lori White
	 * @param rating the review's rating
	 */
	public void setRating(Double rating) {
		this.rating = rating;
	}
	/**
	 * Retrieves the restaurant linked to this review.
	 * @author Lori White
	 * @return Long - the restaurant linked to this review
	 */
	public Long getRestaurantId() {
		return restaurantId;
	}
	/**
	 * Updates the restaurant linked to this review.
	 * @author Lori White
	 * @param restaurantId the restaurant linked to this review
	 */
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	/**
	 * Retrieves the user linked to this to this review.
	 * @author Lori White
	 * @return Long - the user linked to this to this review
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * Updates the user linked to this to this review.
	 * @author Lori White
	 * @param userId the user linked to this to this review
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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
	 * Creates a string representation of a review.
	 * @author Lori White
	 * @return String - a string representation of a review
	 */
	@Override
	public String toString() {
		return "Review [id=" + id + ", description=" + description + ", rating=" + rating + ", restaurantId="
				+ restaurantId + ", userId=" + userId + "]";
	}
}
