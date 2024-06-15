package com.example.demo;

import com.example.demo.dto.*;
import com.example.demo.entity.Member;
import com.example.demo.entity.MemberPk;
import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.request.CreateCommentRequest;
import com.example.demo.request.CreateTagRequest;
import com.example.demo.request.UpdateCommentRequest;
import com.example.demo.request.UpdateTaskRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ApiDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private Long validProjectId;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(restDocumentation))
                .build();

        // 유효한 프로젝트가 있는지 확인하고 없으면 생성합니다.
        Optional<Project> existingProject = projectRepository.findById(1L);
        if (existingProject.isPresent()) {
            validProjectId = existingProject.get().getProjectId();
        } else {
            Project project = Project.builder()
                    .projectName("Test Project")
                    .projectStatus(Project.ProjectStatus.DORMANT)
                    .build();

            projectRepository.save(project);
            validProjectId = project.getProjectId();
        }
    }

    // Project CRUD
    @Test
    @Order(1)
    public void documentCreateProject() throws Exception {
        ProjectCreateDto projectCreateDto = new ProjectCreateDto("New Project", Project.ProjectStatus.ACTIVE, 1L, List.of(1L, 2L));
        String projectJson = objectMapper.writeValueAsString(projectCreateDto);

        this.mockMvc.perform(post("/projects")
                        .contentType("application/json")
                        .content(projectJson))
                .andExpect(status().isCreated())
                .andDo(MockMvcRestDocumentation.document("create-project"));
    }

    @Test
    @Order(2)
    public void documentGetAllProjectsByUserId() throws Exception {
        this.mockMvc.perform(get("/projects")
                        .param("userId", "1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("get-projects-by-user-id"));
    }

    @Test
    @Order(3)
    public void documentGetProjectById() throws Exception {
        this.mockMvc.perform(get("/projects/{projectId}", 1))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("get-project-by-id"));
    }
//
//    @Test
//    @Order(4)
//    public void documentDeleteProject() throws Exception {
//        this.mockMvc.perform(delete("/projects/{projectId}", 1))
//                .andExpect(status().isNoContent())
//                .andDo(MockMvcRestDocumentation.document("delete-project"));
//    }
//
//    // Member CRUD
//    @Test
//    @Order(5)
//    public void documentCreateMember() throws Exception {
//        Project project = new Project(1L, "Test Project", Project.ProjectStatus.ACTIVE);
//        Member member = new Member(new MemberPk(1L, 1L), project, Member.MemberRole.MEMBER);
//        String memberJson = objectMapper.writeValueAsString(member);
//
//        this.mockMvc.perform(post("/projects/{projectId}/members", 1)
//                        .contentType("application/json")
//                        .content(memberJson))
//                .andExpect(status().isCreated())
//                .andDo(MockMvcRestDocumentation.document("create-member"));
//    }

    @Test
    @Order(6)
    public void documentGetMembers() throws Exception {
        this.mockMvc.perform(get("/projects/{projectId}/members", 1))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("get-members"));
    }
//
//    @Test
//    @Order(7)
//    public void documentDeleteMember() throws Exception {
//        this.mockMvc.perform(delete("/projects/{projectId}/members/{memberId}", 1, 1))
//                .andExpect(status().isNoContent())
//                .andDo(MockMvcRestDocumentation.document("delete-member"));
//    }

    // Milestone CRUD
    @Test
    @Order(8)
    public void documentCreateMilestone() throws Exception {
        MilestoneCreateRequestDto milestoneCreateRequestDto = new MilestoneCreateRequestDto("New Milestone", ZonedDateTime.now(), ZonedDateTime.now().plusDays(10));
        String milestoneJson = objectMapper.writeValueAsString(milestoneCreateRequestDto);

        this.mockMvc.perform(post("/projects/{projectId}/milestones", 1)
                        .contentType("application/json")
                        .content(milestoneJson))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("create-milestone"));
    }

    @Test
    @Order(9)
    public void documentGetMilestones() throws Exception {
        this.mockMvc.perform(get("/projects/{projectId}/milestones", 1))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("get-milestones"));
    }

    @Test
    @Order(10)
    public void documentUpdateMilestone() throws Exception {
        MilestoneUpdateRequestDto milestoneUpdateRequestDto = new MilestoneUpdateRequestDto(1L, "Updated Milestone", ZonedDateTime.now(), ZonedDateTime.now().plusDays(10));
        String milestoneJson = objectMapper.writeValueAsString(milestoneUpdateRequestDto);

        this.mockMvc.perform(put("/projects/{projectId}/milestones", 1)
                        .contentType("application/json")
                        .content(milestoneJson))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("update-milestone"));
    }

//    @Test
//    @Order(11)
//    public void documentDeleteMilestone() throws Exception {
//        MilestoneGetByMilestoneIdRequestDto milestoneGetByMilestoneIdRequestDto = new MilestoneGetByMilestoneIdRequestDto(1L);
//        String milestoneJson = objectMapper.writeValueAsString(milestoneGetByMilestoneIdRequestDto);
//
//        this.mockMvc.perform(delete("/projects/{projectId}/milestones", 1)
//                        .contentType("application/json")
//                        .content(milestoneJson))
//                .andExpect(status().isConflict())
//                .andDo(MockMvcRestDocumentation.document("delete-milestone"));
//    }

    // Comment CRUD
    @Test
    @Order(12)
    public void documentCreateComment() throws Exception {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest("This is a comment.", 1L);
        String commentJson = objectMapper.writeValueAsString(createCommentRequest);

        this.mockMvc.perform(post("/projects/{projectId}/tasks/{taskId}/comments", 1, 1)
                        .contentType("application/json")
                        .content(commentJson))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("create-comment"));
    }

    @Test
    @Order(13)
    public void documentGetComments() throws Exception {
        this.mockMvc.perform(get("/projects/{projectId}/tasks/{taskId}/comments", 1, 1))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("get-comments"));
    }

    @Test
    @Order(14)
    public void documentUpdateComment() throws Exception {
        // 존재하는 유효한 commentId를 사용합니다.
        Long existingCommentId = 2L; // 실제 존재하는 commentId로 변경해야 합니다.

        // 데이터베이스에서 commentId가 존재하는지 확인합니다.
        boolean commentExists = commentRepository.existsById(existingCommentId);

        // commentId가 존재하는 경우에만 테스트를 실행합니다.
        if (commentExists) {
            UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest("Updated comment content", existingCommentId);
            String commentJson = objectMapper.writeValueAsString(updateCommentRequest);

            this.mockMvc.perform(put("/projects/{projectId}/tasks/{taskId}/comments/{commentId}", 1, 1, existingCommentId)
                            .contentType("application/json")
                            .content(commentJson))
                    .andExpect(status().isOk())
                    .andDo(MockMvcRestDocumentation.document("update-comment"));
        } else {
            // Comment ID가 존재하지 않으면 테스트를 실패하게 만듭니다.
            throw new AssertionError("Test cannot proceed because the commentId does not exist in the database.");
        }
    }

    @Test
    @Order(15)
    public void documentDeleteComment() throws Exception {
        this.mockMvc.perform(delete("/projects/{projectId}/tasks/{taskId}/comments/{commentId}", 1, 1, 1))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("delete-comment"));
    }

    // Tag CRUD
    @Test
    @Order(16)
    public void documentCreateTag() throws Exception {
        // CreateTagRequest 객체 생성 및 필드 설정
        CreateTagRequest createTagRequest = new CreateTagRequest();
        createTagRequest.setName("hi");

        // CreateTagRequest 객체를 JSON 문자열로 변환
        String tagJson = objectMapper.writeValueAsString(createTagRequest);

        // 유효한 projectId를 사용하여 POST 요청 수행
        this.mockMvc.perform(post("/projects/{projectId}/tags", validProjectId)
                        .contentType("application/json")
                        .content(tagJson))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("create-tag"));
    }

    @Test
    @Order(17)
    public void documentGetTagsByProjectId() throws Exception {
        this.mockMvc.perform(get("/projects/{projectId}/tags", 1))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("get-tags-by-project-id"));
    }

    @Test
    @Order(18)
    public void documentUpdateTagName() throws Exception {
        Map<String, String> updateRequest = new HashMap<>();
        updateRequest.put("newTagName", "new Tag");
        String updateJson = objectMapper.writeValueAsString(updateRequest);

        this.mockMvc.perform(put("/projects/{projectId}/tags/{tagName}/name", 1, "hi")
                        .contentType("application/json")
                        .content(updateJson))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("update-tag-name"));
    }

    @Test
    @Order(19)
    public void documentDeleteTag() throws Exception {
        this.mockMvc.perform(delete("/projects/{projectId}/tags/{tagId}", 1, 1))
                .andExpect(status().isNoContent())
                .andDo(MockMvcRestDocumentation.document("delete-tag"));
    }

//    @Test
//    @Order(20)
//    public void documentGetTagById() throws Exception {
//        this.mockMvc.perform(get("/projects/{projectId}/tags/{tagId}", 1, 15))
//                .andExpect(status().isOk())
//                .andDo(MockMvcRestDocumentation.document("get-tag-by-id"));
//    }
//
//    // Task CRUD
//    @Test
//    @Order(21)
//    public void documentCreateTask() throws Exception {
//        TaskCreateDto createDto = TaskCreateDto.builder()
//                .name("test")
//                .description("Task description")
//                .status(Task.TaskStatus.TODO)
//                .milestoneId(1L)
//                .build();
//        String jsonRequest = objectMapper.writeValueAsString(createDto);
//
//        this.mockMvc.perform(post("/projects/{projectId}/tasks", validProjectId)
//                        .contentType("application/json")
//                        .content(jsonRequest))
//                .andExpect(status().isOk())
//                .andDo(MockMvcRestDocumentation.document("create-task"));
//    }

    @Test
    @Order(22)
    public void documentGetTasks() throws Exception {
        TaskAllReadRequestDto requestDto = TaskAllReadRequestDto.builder().projectId(validProjectId).build();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        this.mockMvc.perform(get("/projects/{projectId}/tasks", validProjectId)
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("get-tasks-by-project-id"));
    }

    @Test
    @Order(23)
    public void documentGetTask() throws Exception {
        this.mockMvc.perform(get("/projects/{projectId}/tasks/{taskId}", validProjectId, 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("get-task-by-id"));
    }

//    @Test
//    @Order(24)
//    public void documentUpdateTask() throws Exception {
//        UpdateTaskRequest updateRequest = new UpdateTaskRequest("Updated Task", "Updated description", Task.TaskStatus.IN_PROGRESS, 1L);
//        String jsonRequest = objectMapper.writeValueAsString(updateRequest);
//
//        this.mockMvc.perform(put("/projects/{projectId}/tasks/{taskId}", validProjectId, 1L)
//                        .contentType("application/json")
//                        .content(jsonRequest))
//                .andExpect(status().isOk())
//                .andDo(MockMvcRestDocumentation.document("update-task"));
//    }
//
//    @Test
//    @Order(25)
//    public void documentDeleteTask() throws Exception {
//        this.mockMvc.perform(delete("/projects/{projectId}/tasks/{taskId}", validProjectId, 1L)
//                        .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andDo(MockMvcRestDocumentation.document("delete-task"));
//    }
}
