package com.springbootschedule.repository;

import com.springbootschedule.document.LeaveSetup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeavesRepository extends MongoRepository<LeaveSetup,String> {
}
