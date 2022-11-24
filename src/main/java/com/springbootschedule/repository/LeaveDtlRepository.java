package com.springbootschedule.repository;

import com.springbootschedule.document.LeaveDtl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveDtlRepository extends MongoRepository<LeaveDtl,String> {
}
