package com.facets.cloud.virtualnode.repository;

import com.facets.cloud.virtualnode.pojo.model.ConnectionGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IConnectionGroupRepository extends MongoRepository<ConnectionGroup, String> {

    Optional<ConnectionGroup> findByName(String name);

}
