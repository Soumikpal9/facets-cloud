package com.facets.cloud.virtualnode.pojo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document
public class VirtualNode {

    @Id
    private String id;

    private String prevNodeId;

    private String name;

    private String status;

    private String connectionGroup;

    private List<VirtualNode> nextNode;

}
