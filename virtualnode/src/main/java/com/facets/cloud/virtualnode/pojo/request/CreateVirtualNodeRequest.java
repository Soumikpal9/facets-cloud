package com.facets.cloud.virtualnode.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVirtualNodeRequest {

    private String name;

    private String status;

    private List<CreateVirtualNodeRequest> nextNode;

}
