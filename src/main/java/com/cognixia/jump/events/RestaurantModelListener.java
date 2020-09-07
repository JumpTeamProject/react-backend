package com.cognixia.jump.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.service.SequenceGeneratorService;

/**
 * The Model Listener for Restaurant. 
 * @author Lori White
 * @version v1 (08/24/2020)
 */
@Component
public class RestaurantModelListener extends AbstractMongoEventListener<Restaurant> {

    private SequenceGeneratorService sequenceGenerator;
    /**
     * The overloaded constructor.
     * @author Lori White
     * @param sequenceGenerator a sequence generator service instance
     */
    @Autowired
    public RestaurantModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }
    /**
     * Auto increments the restaurant id before converting.
     * @author Lori White
     * @param event the an event that occurs before converting for the Restaurant model 
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Restaurant> event) {
    	event.getSource().setId(sequenceGenerator.generateSequence(Restaurant.SEQUENCE_NAME));
    }
}
