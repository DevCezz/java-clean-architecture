package io.github.mat3e.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mat3e.project.dto.ProjectDto;
import io.github.mat3e.project.dto.ProjectStepDto;
import io.github.mat3e.task.dto.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ProjectControllerTest {

    private static final ZonedDateTime CURRENT_TIME = ZonedDateTime.now();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectFacade projectFacade;

    @MockBean
    private ProjectQueryRepository projectQueryRepository;

    @Test
    void should_list_all_projects() throws Exception {
        given(projectQueryRepository.findBy(ProjectDto.class)).willReturn(Set.of(projectWithSteps(12)));

        mockMvc.perform(get("/projects"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[*].steps.*", hasSize(2)));
    }

    @Test
    void should_get_project() throws Exception {
        given(projectQueryRepository.findDtoById(12)).willReturn(Optional.of(projectWithSteps(12)));

        mockMvc.perform(get("/projects/" + 12))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(12)));
    }

    @Test
    void should_not_found_project() throws Exception {
        given(projectQueryRepository.findDtoById(12)).willReturn(Optional.empty());

        mockMvc.perform(get("/projects/" + 12))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_update_project() throws Exception {
        var request = "{\"id\":12, \"name\":\"new-project\", \"steps\":[]}";

        mockMvc.perform(put("/projects/" + 12)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_400_if_request_project_id_is_different_than_in_path() throws Exception {
        var request = "{\"id\":12, \"name\":\"new-project\", \"steps\":[]}";

        mockMvc.perform(put("/projects/" + 58)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("different")));
    }

    @Test
    void should_create_project() throws Exception {
        given(projectFacade.save(any())).willReturn(projectWithSteps(12));
        var request = "{\"name\":\"new-project\", \"steps\":[]}";

        mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, endsWith("/" + 12)))
                .andExpect(jsonPath("$.id", is(12)));
    }

    @Test
    void should_create_tasks_for_project() throws Exception {
        given(projectFacade.createTasks(eq(12), any())).willReturn(tasks(5));
        var request = "{\"deadline\":\"" + CURRENT_TIME + "\"}";

        mockMvc.perform(post("/projects/" + 12 + "/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(5)));
    }

    private ProjectDto projectWithSteps(int projectId) {
        return ProjectDto.create(projectId, "new-project", List.of(
                ProjectStepDto.create(1, "desc1", -1),
                ProjectStepDto.create(2, "desc2", -2)
        ));
    }

    private List<TaskDto> tasks(int amount) {
        return IntStream.range(0, amount)
                .mapToObj(i -> TaskDto.builder().build())
                .collect(Collectors.toList());
    }
}