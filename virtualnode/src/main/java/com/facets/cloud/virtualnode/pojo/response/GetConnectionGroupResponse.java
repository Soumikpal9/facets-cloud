package com.facets.cloud.virtualnode.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetConnectionGroupResponse {

    private String connectionGroup;

}
