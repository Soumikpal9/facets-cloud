package com.facets.cloud.virtualnode.service;

import com.facets.cloud.virtualnode.pojo.request.CreateConnectionGroupRequest;
import com.facets.cloud.virtualnode.pojo.response.CreateConnectionGroupResponse;
import com.facets.cloud.virtualnode.pojo.response.GetConnectionGroupResponse;

public interface IConnectionGroupService {

    CreateConnectionGroupResponse createConnectionGroup(CreateConnectionGroupRequest createConnectionGroupRequest);

    GetConnectionGroupResponse getConnectionGroup(String virtualNodeName);

}
