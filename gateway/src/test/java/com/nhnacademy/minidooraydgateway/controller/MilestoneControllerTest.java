package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.dto.*;
import com.nhnacademy.minidooraydgateway.service.MilestoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MilestoneControllerTest {

    @Mock
    private MilestoneService milestoneService;

    @InjectMocks
    private MilestoneController milestoneController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(milestoneController).build();
    }

    @Test
    public void testGetMilestonesPage() throws Exception {
        Long projectId = 1L;
        List<MilestoneGetDto> milestones = List.of(
                MilestoneGetDto.builder()
                        .milestoneName("milestone1")
                        .milestoneId(1L)
                        .milestoneStartDate(ZonedDateTime.now())
                        .milestoneEndDate(ZonedDateTime.now())
                        .build()
        );
        when(milestoneService.getMilestonesByProjectId(projectId)).thenReturn(milestones);

        mockMvc.perform(get("/projects/1/milestones"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("milestones", milestones))
                .andExpect(model().attribute("projectId", projectId))
                .andExpect(view().name("project/milestoneProperty"));

        verify(milestoneService).getMilestonesByProjectId(projectId);
    }

    @Test
    public void testGetNewMilestonePage() throws Exception {
        Long projectId = 1L;

        mockMvc.perform(get("/projects/1/milestones/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("milestone"))
                .andExpect(model().attribute("projectId", projectId))
                .andExpect(view().name("project/milestonePropertyAdd"));
    }

    @Test
    public void testGetUpdateMilestonePage() throws Exception {
        Long projectId = 1L;
        Long milestoneId = 1L;

        mockMvc.perform(get("/projects/1/milestones/update/1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("milestone", MilestoneUpdateRequestDto.builder().milestoneId(milestoneId).build()))
                .andExpect(model().attribute("projectId", projectId))
                .andExpect(view().name("project/milestonePropertyUpdate"));
    }

    @Test
    public void testHandleCreateMilestone() throws Exception {
        Long projectId = 1L;
        String milestoneName = "milestone1";
        String milestoneStartDate = "2024-06-10T10:00";
        String milestoneEndDate = "2024-06-11T10:00";

        mockMvc.perform(post("/projects/1/milestones")
                        .param("milestoneName", milestoneName)
                        .param("milestoneStartDate", milestoneStartDate)
                        .param("milestoneEndDate", milestoneEndDate))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/milestones"));

        verify(milestoneService).createMilestone(eq(projectId), any(MilestoneCreateRequestDto.class));
    }

    @Test
    public void testHandleEditMilestone() throws Exception {
        Long projectId = 1L;
        Long milestoneId = 1L;
        String milestoneName = "milestone1";
        String milestoneStartDate = "2024-06-10T10:00";
        String milestoneEndDate = "2024-06-11T10:00";

        mockMvc.perform(post("/projects/1/milestones/update/1")
                        .param("milestoneName", milestoneName)
                        .param("milestoneStartDate", milestoneStartDate)
                        .param("milestoneEndDate", milestoneEndDate))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/milestones"));

        verify(milestoneService).updateMilestone(eq(projectId), any(MilestoneUpdateRequestDto.class));
    }

    @Test
    public void testHandleDeleteMilestone() throws Exception {
        Long projectId = 1L;
        Long milestoneId = 1L;

        mockMvc.perform(post("/projects/1/milestones/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/milestones"));

        verify(milestoneService).deleteMilestone(eq(projectId), any(MilestoneGetByMilestoneIdRequestDto.class));
    }
}
