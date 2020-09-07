package com.cognixia.jump.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.cognixia.jump.model.Address;
import com.cognixia.jump.service.SequenceGeneratorService;

/**
 * The Model Listener for Address. 
 * @author Lori White
 * @version v1 (08/24/2020)
 */
@Component
public class AddressModelListener extends AbstractMongoEventListener<Address> {

    private SequenceGeneratorService sequenceGenerator;
    /**
     * The overloaded constructor.
     * @author Lori White
     * @param sequenceGenerator a sequence generator service instance
     */
    @Autowired
    public AddressModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }
    /**
     * Auto increments the address id before converting.
     * @author Lori White
     * @param event the an event that occurs before converting for the Address model 
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Address> event) {
    	event.getSource().setId(sequenceGenerator.generateSequence(Address.SEQUENCE_NAME));
    }
}