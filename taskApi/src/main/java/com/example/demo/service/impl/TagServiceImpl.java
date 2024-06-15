package com.example.demo.service.impl;

import com.example.demo.entity.Tag;
import com.example.demo.entity.Project;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.request.CreateTagRequest;
import com.example.demo.request.UpdateTagNameRequest;
import com.example.demo.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Tag createTag(Long projectId, CreateTagRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID"));

        Tag tag = new Tag(request.getName(), project);
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void updateTagName(Long projectId, String tagName, String newTagName) {
        Tag tag = tagRepository.findByNameAndProjectProjectId(tagName, projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tag name or project ID"));
        tag.setName(newTagName);
        tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }

    @Override
    public List<Tag> getTagsByProjectId(Long projectId) {
        return tagRepository.findByProjectProjectId(projectId);
    }

    @Override
    public Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tag ID"));
    }
}
