package com.cognixia.jump.repository.custom;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.cognixia.jump.model.Restaurant;

/**
 * The custom restaurant repository implementation. 
 * @author Lori White
 * @version v1 (08/31/2020)
 */
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustomUpdate{

	@Autowired
	MongoTemplate mongoTemplate;
	/**
	 * Patches a restaurant based on the id.
	 * @author Lori White
	 * @param id the restaurant id of the restaurant to patch
	 * @param field the particular field to update
	 * @param value the value to update the particular field to
	 * @return Restaurant - the patched restaurant 
	 */
	@Override
	public Restaurant patch(Long id, String field, Object value) {
		Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set(field, value);
        return mongoTemplate.findAndModify(query, update, options().returnNew(true).upsert(true), Restaurant.class);
	}
}
