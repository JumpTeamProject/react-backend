package com.cognixia.jump.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The model for Database Sequence.
 * @author Lori White
 * @version v2 (09/05/2020)
 */
@Document(collection = "database_sequences")
public class DatabaseSequence {

    @Id
    private String id;

    private long seq;
    
    /**
	 * The default constructor.
	 * @author Lori White
	 */
    public DatabaseSequence() {}
    /**
     * Retrieves the sequence id.
     * @author Lori White
     * @return String - the sequence id
     */
    public String getId() {
        return id;
    }
    /**
     * Updates the sequence id.
     * @author Lori White
     * @param id the sequence id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Retrieves the sequence value.
     * @author Lori White
     * @return long - the sequence value
     */
    public long getSeq() {
        return seq;
    }
    /**
     * Updates the sequence value.
     * @author Lori White
     * @param seq the sequence value
     */
    public void setSeq(long seq) {
        this.seq = seq;
    }
}
