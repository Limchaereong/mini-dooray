package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.domain.Tag;
import com.nhnacademy.minidooraydgateway.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TagControllerTest {

    @Mock
    private TagService tagService;

    @Mock
    private Model model;

    @InjectMocks
    private TagController tagController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @Test
    public void testGetTagsPage() throws Exception {
        Long projectId = 1L;
        List<Tag> tags = Arrays.asList(new Tag(), new Tag());

        when(tagService.getTagsByProjectId(projectId)).thenReturn(tags);

        mockMvc.perform(get("/projects/{projectId}/tags", projectId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attributeExists("tag"))
                .andExpect(model().attributeExists("projectId"));

        verify(tagService).getTagsByProjectId(projectId);
    }

    @Test
    public void testHandleCreateTag() throws Exception {
        Long projectId = 1L;
        String tagName = "newTag";

        mockMvc.perform(post("/projects/{projectId}/tags", projectId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("projectId", projectId.toString())
                        .param("name", tagName))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/" + projectId + "/tags"));

        verify(tagService).createTag(any(Tag.class));
    }

    @Test
    public void testHandleEditTag() throws Exception {
        Long projectId = 1L;
        String tagName = "existingTag";
        String newTagName = "updatedTag";

        mockMvc.perform(post("/projects/{projectId}/tags/edit", projectId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("projectId", projectId.toString())
                        .param("name", tagName)
                        .param("newName", newTagName))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/" + projectId + "/tags"));

        verify(tagService).updateTagName(projectId, tagName, newTagName);
    }

    @Test
    public void testHandleDeleteTag() throws Exception {
        Long projectId = 1L;
        Long tagId = 2L;

        mockMvc.perform(post("/projects/{projectId}/tags/{tagId}/delete", projectId, tagId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/" + projectId + "/tags"));

        verify(tagService).deleteTag(projectId, tagId);
    }
}
