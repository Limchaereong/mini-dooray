package com.example.demo.service;

import com.example.demo.entity.Tag;
import com.example.demo.request.CreateTagRequest;
import com.example.demo.request.UpdateTagNameRequest;

import java.util.List;

public interface TagService {
    Tag createTag(Long projectId, CreateTagRequest request);
    void updateTagName(Long projectId, String tagName, String newTagName);
    void deleteTag(Long tagId);
    List<Tag> getTagsByProjectId(Long projectId);
    Tag getTagById(Long tagId);
}
