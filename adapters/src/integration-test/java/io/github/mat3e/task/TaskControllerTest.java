package io.github.mat3e.task;

import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.dto.TaskWithChangesDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static io.github.mat3e.task.TaskFixture.taskWithId;
import static io.github.mat3e.task.TaskFixture.tasks;
import static io.github.mat3e.task.TaskFixture.tasksWithChanges;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskFacade taskFacade;

    @MockBean
    private TaskQueryRepository taskQueryRepository;

    @Test
    void should_list_all_tasks() throws Exception {
        given(taskQueryRepository.findBy(TaskDto.class)).willReturn(tasks(12));

        mockMvc.perform(get("/tasks"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(12)));
    }

    @Test
    void should_list_all_tasks_with_changes() throws Exception {
        given(taskQueryRepository.findBy(TaskWithChangesDto.class)).willReturn(tasksWithChanges(6));

        mockMvc.perform(get("/tasks?changes"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(6)));
    }

    @Test
    void should_get_task() throws Exception {
        given(taskQueryRepository.findDtoById(12)).willReturn(Optional.of(taskWithId(12)));

        mockMvc.perform(get("/tasks/" + 12))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(12)));
    }

    @Test
    void should_not_found_task() throws Exception {
        given(taskQueryRepository.findDtoById(12)).willReturn(Optional.empty());

        mockMvc.perform(get("/tasks/" + 12))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_update_task() throws Exception {
        var request = "{\"id\":12, \"description\":\"new-task\"}";

        mockMvc.perform(put("/tasks/" + 12)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_400_if_request_task_id_is_different_than_in_path() throws Exception {
        var request = "{\"id\":12, \"description\":\"new-task\"}";

        mockMvc.perform(put("/tasks/" + 58)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("different")));
    }

    @Test
    void should_create_task() throws Exception {
        given(taskFacade.save(any())).willReturn(taskWithId(12));
        var request = "{\"description\":\"new-task\"}";

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, endsWith("/" + 12)))
                .andExpect(jsonPath("$.id", is(12)));
    }

    @Test
    void should_delete_task() throws Exception {
        mockMvc.perform(delete("/tasks/12"))
                .andExpect(status().isNoContent());
    }
}