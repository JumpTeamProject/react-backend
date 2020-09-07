package com.cognixia.jump.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.cognixia.jump.model.DatabaseSequence;

/**
 * The sequence generator service. 
 * @author Lori White
 * @version v1 (08/24/2020)
 */
@Service
public class SequenceGeneratorService {


    private MongoOperations mongoOperations;
    /**
     * The overloaded constructor.
     * @author Lori White
     * @param mongoOperations the object holding the mongo template bean
     */
    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
    /**
     * Auto generates the sequence to be used for each id in the database.
     * @author Lori White
     * @param seqName the name of sequence that pertains to a document
     * @return long - the current sequence value
     */
    public long generateSequence(String seqName) {

        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }
}
