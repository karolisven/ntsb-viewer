package com.isa.ntsb.persistence;

import com.isa.ntsb.model.Finding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Karolis on 6/25/2019.
 */
public interface FindingRepository extends JpaRepository<Finding, Long> {

}
