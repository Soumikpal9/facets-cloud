package com.facets.cloud.virtualnode.contoller;

import com.facets.cloud.virtualnode.payload.ApiResponse;
import com.facets.cloud.virtualnode.pojo.request.CreateConnectionGroupRequest;
import com.facets.cloud.virtualnode.pojo.response.CreateConnectionGroupResponse;
import com.facets.cloud.virtualnode.pojo.response.GetConnectionGroupResponse;
import com.facets.cloud.virtualnode.service.IConnectionGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/connectionGroups")
public class ConnectionGroupController {

    private final IConnectionGroupService connectionGroupService;

    @Autowired
    public ConnectionGroupController(final IConnectionGroupService connectionGroupService) {
        this.connectionGroupService = connectionGroupService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createConnectionGroup(
            @RequestBody CreateConnectionGroupRequest createConnectionGroupRequest) {
        CreateConnectionGroupResponse createConnectionGroupResponse = connectionGroupService.createConnectionGroup(createConnectionGroupRequest);
        ApiResponse response = ApiResponse.builder()
                .data(createConnectionGroupResponse)
                .message(HttpStatus.CREATED.getReasonPhrase())
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(response);
    }

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ApiResponse> getAllConnectionGroups() {
//
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getConnectionGroup(
            @RequestParam("virtualNodeName") String virtualNodeName) {
        GetConnectionGroupResponse getConnectionGroupResponse = connectionGroupService.getConnectionGroup(virtualNodeName);
        ApiResponse response = ApiResponse.builder()
                .data(getConnectionGroupResponse)
                .message(HttpStatus.CREATED.getReasonPhrase())
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(response);
    }

}
