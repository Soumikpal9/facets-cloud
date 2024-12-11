package com.facets.cloud.virtualnode.service.impl;

import com.facets.cloud.virtualnode.exception.ApiException;
import com.facets.cloud.virtualnode.enums.ErrorPriority;
import com.facets.cloud.virtualnode.pojo.model.ConnectionGroup;
import com.facets.cloud.virtualnode.pojo.model.VirtualNode;
import com.facets.cloud.virtualnode.pojo.request.CreateConnectionGroupRequest;
import com.facets.cloud.virtualnode.pojo.request.CreateVirtualNodeRequest;
import com.facets.cloud.virtualnode.pojo.response.CreateConnectionGroupResponse;
import com.facets.cloud.virtualnode.pojo.response.GetConnectionGroupResponse;
import com.facets.cloud.virtualnode.repository.IConnectionGroupRepository;
import com.facets.cloud.virtualnode.repository.IVirtualNodeRepository;
import com.facets.cloud.virtualnode.service.IConnectionGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ConnectionGroupServiceImpl implements IConnectionGroupService {

    private final IConnectionGroupRepository connectionGroupRepository;

    private final IVirtualNodeRepository virtualNodeRepository;

    @Autowired
    public ConnectionGroupServiceImpl(final IConnectionGroupRepository connectionGroupRepository, final IVirtualNodeRepository virtualNodeRepository) {
        this.connectionGroupRepository = connectionGroupRepository;
        this.virtualNodeRepository = virtualNodeRepository;
    }

    @Override
    public CreateConnectionGroupResponse createConnectionGroup(CreateConnectionGroupRequest createConnectionGroupRequest) {
        try {
            ConnectionGroup existingConnectionGroup = connectionGroupRepository.findByName(createConnectionGroupRequest.getName()).orElse(null);
            if(existingConnectionGroup != null) {
                throw new ApiException("Connection group with this name already exists!", HttpStatus.BAD_REQUEST, ErrorPriority.MEDIUM);
            }
            if (createConnectionGroupRequest.getHeadNode() == null) {
                throw new ApiException("Connection group should have atleast one virtual node!", HttpStatus.BAD_REQUEST, ErrorPriority.MEDIUM);
            }
            VirtualNode headNode = saveVirtualNodeHierarchy(createConnectionGroupRequest.getHeadNode(), null, createConnectionGroupRequest.getName());

            ConnectionGroup connectionGroup = new ConnectionGroup();
            connectionGroup.setName(createConnectionGroupRequest.getName());
            connectionGroup.setHeadNode(headNode);

            connectionGroup = connectionGroupRepository.save(connectionGroup);

            CreateConnectionGroupResponse createConnectionGroupResponse = new CreateConnectionGroupResponse();
            createConnectionGroupResponse.setId(connectionGroup.getId());
            createConnectionGroupResponse.setName(connectionGroup.getName());
            createConnectionGroupResponse.setHeadNode(headNode);
            return createConnectionGroupResponse;
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ErrorPriority.HIGH);
        }
    }

    @Override
    public GetConnectionGroupResponse getConnectionGroup(String virtualNodeName) {
        try {
            VirtualNode virtualNode = virtualNodeRepository.findByName(virtualNodeName).orElse(null);
            if (virtualNode == null) {
                throw new ApiException("Virtual node with this name is not present", HttpStatus.BAD_REQUEST, ErrorPriority.MEDIUM);
            }
            GetConnectionGroupResponse getConnectionGroupResponse = new GetConnectionGroupResponse();
            getConnectionGroupResponse.setConnectionGroup(virtualNode.getConnectionGroup());
            return getConnectionGroupResponse;
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ErrorPriority.HIGH);
        }
    }

    private VirtualNode saveVirtualNodeHierarchy(CreateVirtualNodeRequest createVirtualNodeRequest, String prevNodeId, String connectionGroup) {
        if (createVirtualNodeRequest == null) {
            return null;
        }
        List<VirtualNode> createdNodes = new ArrayList<>();
        try {
            return saveVirtualNodeWithRollback(createVirtualNodeRequest, prevNodeId, createdNodes, connectionGroup);
        } catch (ApiException ex) {
            for (VirtualNode node : createdNodes) {
                virtualNodeRepository.delete(node);
            }
            throw ex;
        }
    }

    private VirtualNode saveVirtualNodeWithRollback(CreateVirtualNodeRequest createVirtualNodeRequest, String prevNodeId, List<VirtualNode> createdNodes, String connectionGroup) {
        VirtualNode existingVirtualNode = virtualNodeRepository.findByName(createVirtualNodeRequest.getName()).orElse(null);
        if (existingVirtualNode != null) {
            throw new ApiException("Virtual node with this name already exists!", HttpStatus.BAD_REQUEST, ErrorPriority.MEDIUM);
        }

        VirtualNode virtualNode = new VirtualNode();
        virtualNode.setName(createVirtualNodeRequest.getName());
        virtualNode.setStatus(createVirtualNodeRequest.getStatus());
        virtualNode.setPrevNodeId(prevNodeId);
        virtualNode.setConnectionGroup(connectionGroup);

        VirtualNode savedVirtualNode = virtualNodeRepository.save(virtualNode);
        createdNodes.add(savedVirtualNode);

        if (createVirtualNodeRequest.getNextNode() != null && !createVirtualNodeRequest.getNextNode().isEmpty()) {
            List<VirtualNode> nextNodes = new ArrayList<>();
            for (CreateVirtualNodeRequest nextNodeRequest : createVirtualNodeRequest.getNextNode()) {
                VirtualNode nextNode = saveVirtualNodeWithRollback(nextNodeRequest, savedVirtualNode.getId(), createdNodes, connectionGroup);
                nextNodes.add(nextNode);
            }
            savedVirtualNode.setNextNode(nextNodes);
        }

        return virtualNodeRepository.save(savedVirtualNode);
    }

}
