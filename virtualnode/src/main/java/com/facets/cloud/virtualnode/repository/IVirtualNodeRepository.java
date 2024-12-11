package com.facets.cloud.virtualnode.repository;

import com.facets.cloud.virtualnode.pojo.model.VirtualNode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IVirtualNodeRepository extends MongoRepository<VirtualNode, String> {

    Optional<VirtualNode> findByName(String name);

}
