package com.example.demo.controller;

import com.example.demo.entity.Tag;
import com.example.demo.request.CreateTagRequest;
import com.example.demo.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TagControllerTest {

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTagsByProjectId() {
        // Arrange
        Long projectId = 1L;
        List<Tag> tagsList = Arrays.asList(
                new Tag(1L, "Tag1", null, null),
                new Tag(2L, "Tag2", null, null)
        );
        when(tagService.getTagsByProjectId(projectId)).thenReturn(tagsList);

        // Act
        ResponseEntity<List<Tag>> response = tagController.getTagsByProjectId(projectId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(tagsList);
        verify(tagService, times(1)).getTagsByProjectId(projectId);
    }

//    @Test
//    void createTag() {
//        // Arrange
//        Long projectId = 1L;
//        CreateTagRequest request = new CreateTagRequest();
//        request.setName("New Tag");
//        Tag tag = new Tag(1L, "New Tag", null, null);
//        when(tagService.createTag(eq(projectId), any(CreateTagRequest.class))).thenReturn(tag);
//
//        // Act
//        ResponseEntity<Tag> response = tagController.createTag(projectId, request);
//
//        // Assert
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isEqualTo(tag);
//        verify(tagService, times(1)).createTag(eq(projectId), any(CreateTagRequest.class));
//    }

    @Test
    void updateTagName() {
        // Arrange
        Long projectId = 1L;
        String tagName = "Old Tag";
        String newTagName = "New Tag";

        // Act
        ResponseEntity<Void> response = tagController.updateTagName(projectId, tagName, newTagName);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(tagService, times(1)).updateTagName(projectId, tagName, newTagName);
    }

    @Test
    void deleteTag() {
        // Arrange
        Long tagId = 1L;

        // Act
        ResponseEntity<Void> response = tagController.deleteTag(tagId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(tagService, times(1)).deleteTag(tagId);
    }

    @Test
    void getTagById() {
        // Arrange
        Long tagId = 1L;
        Tag tag = new Tag(tagId, "Tag Name", null, null);
        when(tagService.getTagById(tagId)).thenReturn(tag);

        // Act
        ResponseEntity<Tag> response = tagController.getTagById(tagId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(tag);
        verify(tagService, times(1)).getTagById(tagId);
    }
}
