package com.example.demo.service.impl;

import com.example.demo.entity.Project;
import com.example.demo.entity.Tag;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.request.CreateTagRequest;
import com.example.demo.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    private Project project;
    private Tag tag;

    @BeforeEach
    public void setUp() {
        project = Project.builder()
                .projectId(1L)
                .projectName("Test Project")
                .projectStatus(Project.ProjectStatus.ACTIVE)
                .build();

        tag = new Tag("Test Tag", project);
        tag.setId(1L);
    }

    @Test
    public void testCreateTag() {
        CreateTagRequest request = new CreateTagRequest();
        request.setName("Test Tag");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        Tag result = tagService.createTag(1L, request);

        assertEquals("Test Tag", result.getName());
        verify(projectRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    public void testCreateTag_ProjectNotFound() {
        CreateTagRequest request = new CreateTagRequest();
        request.setName("New Tag");

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tagService.createTag(1L, request);
        });

        assertEquals("Invalid project ID", exception.getMessage());
        verify(projectRepository, times(1)).findById(1L);
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @Test
    public void testUpdateTagName() {
        when(tagRepository.findByNameAndProjectProjectId("Test Tag", 1L)).thenReturn(Optional.of(tag));

        tagService.updateTagName(1L, "Test Tag", "Updated Tag");

        assertEquals("Updated Tag", tag.getName());
        verify(tagRepository, times(1)).findByNameAndProjectProjectId("Test Tag", 1L);
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    public void testUpdateTagName_TagNotFound() {
        when(tagRepository.findByNameAndProjectProjectId("Test Tag", 1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tagService.updateTagName(1L, "Test Tag", "Updated Tag");
        });

        assertEquals("Invalid tag name or project ID", exception.getMessage());
        verify(tagRepository, times(1)).findByNameAndProjectProjectId("Test Tag", 1L);
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @Test
    public void testDeleteTag() {
        tagService.deleteTag(1L);

        verify(tagRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetTagsByProjectId() {
        when(tagRepository.findByProjectProjectId(1L)).thenReturn(List.of(tag));

        List<Tag> result = tagService.getTagsByProjectId(1L);

        assertEquals(1, result.size());
        assertEquals("Test Tag", result.get(0).getName());
        verify(tagRepository, times(1)).findByProjectProjectId(1L);
    }

    @Test
    public void testGetTagById() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        Tag result = tagService.getTagById(1L);

        assertNotNull(result);
        assertEquals("Test Tag", result.getName());
        verify(tagRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTagById_TagNotFound() {
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagById(1L);
        });

        assertEquals("Invalid tag ID", exception.getMessage());
        verify(tagRepository, times(1)).findById(1L);
    }
}
