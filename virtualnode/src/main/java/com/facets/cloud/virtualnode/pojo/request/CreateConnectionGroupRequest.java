package com.facets.cloud.virtualnode.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConnectionGroupRequest {

    private String name;

    private CreateVirtualNodeRequest headNode;

}
