package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.domain.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private ProjectServiceClient projectServiceClient;

    @InjectMocks
    private TagService tagService;

    private Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag();
        tag.setId(1L);
        tag.setName("Test Tag");
        tag.setProjectId(100L);
    }

    @Test
    void testGetTagsByProjectId() {
        when(projectServiceClient.getTagsByProjectId(anyLong())).thenReturn(Collections.singletonList(tag));

        List<Tag> tags = tagService.getTagsByProjectId(100L);

        assertNotNull(tags);
        assertEquals(1, tags.size());
        assertEquals(tag.getId(), tags.get(0).getId());
        assertEquals(tag.getName(), tags.get(0).getName());
        assertEquals(tag.getProjectId(), tags.get(0).getProjectId());
    }

    @Test
    void testGetTagById() {
        when(projectServiceClient.getTagById(anyLong(), anyLong())).thenReturn(tag);

        Tag foundTag = tagService.getTagById(100L, 1L);

        assertNotNull(foundTag);
        assertEquals(tag.getId(), foundTag.getId());
        assertEquals(tag.getName(), foundTag.getName());
        assertEquals(tag.getProjectId(), foundTag.getProjectId());
    }

    @Test
    void testCreateTag() {
        doNothing().when(projectServiceClient).createTag(anyLong(), any(Tag.class));

        assertDoesNotThrow(() -> tagService.createTag(tag));
        verify(projectServiceClient, times(1)).createTag(anyLong(), any(Tag.class));
    }

    @Test
    void testUpdateTagName() {
        doNothing().when(projectServiceClient).updateTagName(anyLong(), anyString(), anyString());

        assertDoesNotThrow(() -> tagService.updateTagName(100L, "Old Tag", "New Tag"));
        verify(projectServiceClient, times(1)).updateTagName(anyLong(), anyString(), anyString());
    }

    @Test
    void testDeleteTag() {
        doNothing().when(projectServiceClient).deleteTag(anyLong(), anyLong());

        assertDoesNotThrow(() -> tagService.deleteTag(100L, 1L));
        verify(projectServiceClient, times(1)).deleteTag(anyLong(), anyLong());
    }
}
