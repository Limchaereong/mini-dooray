package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.MilestoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void testGetAllMilestones() throws Exception {
        MilestoneGetDto milestone1 = new MilestoneGetDto("Milestone 1", 1L, ZonedDateTime.now(), ZonedDateTime.now().plusDays(1));
        MilestoneGetDto milestone2 = new MilestoneGetDto("Milestone 2", 2L, ZonedDateTime.now(), ZonedDateTime.now().plusDays(2));

        List<MilestoneGetDto> milestones = Arrays.asList(milestone1, milestone2);
        when(milestoneService.getMilestonesByProjectId(1L)).thenReturn(milestones);

        mockMvc.perform(get("/projects/1/milestones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].milestoneName").value("Milestone 1"))
                .andExpect(jsonPath("$[1].milestoneName").value("Milestone 2"));

        verify(milestoneService, times(1)).getMilestonesByProjectId(1L);
    }

    @Test
    public void testCreateMilestone() throws Exception {
        MilestoneCreateRequestDto requestDto = new MilestoneCreateRequestDto("Milestone 1", ZonedDateTime.now(), ZonedDateTime.now().plusDays(1));

        mockMvc.perform(post("/projects/1/milestones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"milestoneName\":\"Milestone 1\",\"milestoneStartDate\":\"2023-01-01T00:00:00Z\",\"milestoneEndDate\":\"2023-01-02T00:00:00Z\"}"))
                .andExpect(status().isOk());

        verify(milestoneService, times(1)).createMilestoneById(any(MilestoneCreateRequestDto.class), eq(1L));
    }

    @Test
    public void testUpdateMilestone() throws Exception {
        MilestoneUpdateRequestDto updateRequestDto = new MilestoneUpdateRequestDto(1L, "Updated Milestone", ZonedDateTime.now(), ZonedDateTime.now().plusDays(1));

        mockMvc.perform(put("/projects/1/milestones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"milestoneId\":1,\"milestoneName\":\"Updated Milestone\",\"milestoneStartDate\":\"2023-01-01T00:00:00Z\",\"milestoneEndDate\":\"2023-01-02T00:00:00Z\"}"))
                .andExpect(status().isOk());

        verify(milestoneService, times(1)).changeMilestoneById(any(MilestoneUpdateRequestDto.class), eq(1L));
    }

    @Test
    public void testDeleteMilestone() throws Exception {
        MilestoneGetByMilestoneIdRequestDto deleteRequestDto = new MilestoneGetByMilestoneIdRequestDto(1L);

        mockMvc.perform(delete("/projects/1/milestones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"milestoneId\":1}"))
                .andExpect(status().isOk());

        verify(milestoneService, times(1)).deleteMilestoneById(any(MilestoneGetByMilestoneIdRequestDto.class));
    }
}
