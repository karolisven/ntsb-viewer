package com.isa.ntsb.persistence;

import com.isa.ntsb.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * Created by Karolis on 6/25/2019.
 */

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    State findByAbbreviation(String abbreviation);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "MANUAL")})
    @Query("SELECT state FROM State state WHERE state.abbreviation = ?1")
    State findByAbbreviationNoFlush(String abbreviation);

    State findByName(String name);

}
