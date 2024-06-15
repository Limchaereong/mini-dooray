package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final ProjectServiceClient projectServiceClient;

    public List<Tag> getTagsByProjectId(Long projectId) {
        return projectServiceClient.getTagsByProjectId(projectId);
    }

    public Tag getTagById(Long projectId, Long tagId) {
        return projectServiceClient.getTagById(projectId, tagId);
    }

    public void createTag(Tag tag) {
        projectServiceClient.createTag(tag.getProjectId(), tag);
    }

    public void updateTagName(Long projectId, String tagName, String newTagName) {
        projectServiceClient.updateTagName(projectId, tagName, newTagName);
    }

    public void deleteTag(Long projectId, Long tagId) {
        projectServiceClient.deleteTag(projectId, tagId);
    }
}
