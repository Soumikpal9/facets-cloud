package com.facets.cloud.virtualnode.pojo.response;

import com.facets.cloud.virtualnode.pojo.model.VirtualNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConnectionGroupResponse {

    private String id;

    private String name;

    private VirtualNode headNode;

}
