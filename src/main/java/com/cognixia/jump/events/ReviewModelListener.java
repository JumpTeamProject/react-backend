package com.cognixia.jump.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.cognixia.jump.model.Review;
import com.cognixia.jump.service.SequenceGeneratorService;

/**
 * The Model Listener for Review. 
 * @author Lori White
 * @version v1 (08/24/2020)
 */
@Component
public class ReviewModelListener extends AbstractMongoEventListener<Review> {

    private SequenceGeneratorService sequenceGenerator;
    /**
     * The overloaded constructor.
     * @author Lori White
     * @param sequenceGenerator a sequence generator service instance
     */
    @Autowired
    public ReviewModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }
    /**
     * Auto increments the review id before converting.
     * @author Lori White
     * @param event the an event that occurs before converting for the Review model 
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Review> event) {
    	event.getSource().setId(sequenceGenerator.generateSequence(Review.SEQUENCE_NAME));
    }
}
