package com.facets.cloud.virtualnode.pojo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document
public class ConnectionGroup {

    @Id
    private String id;

    private String name;

    private VirtualNode headNode;

}
