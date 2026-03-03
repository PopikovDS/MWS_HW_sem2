package com.mipt.popikovdmitriy;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ScopeDemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void requestScope_sameWithinSingleRequest() throws Exception {
        mockMvc.perform(get("/api/tasks/scope/request"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sameInstanceWithinRequest", is(true)));
    }

    @Test
    void requestScope_newAcrossRequests() throws Exception {
        String first = mockMvc.perform(get("/api/tasks/scope/request"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String second = mockMvc.perform(get("/api/tasks/scope/request"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertNotEquals(first, second);
    }

    @Test
    void prototypeScope_alwaysDifferent() throws Exception {
        mockMvc.perform(get("/api/tasks/scope/prototype"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.differentInstances", is(true)))
                .andExpect(jsonPath("$.instanceId1", not("")))
                .andExpect(jsonPath("$.instanceId2", not("")));
    }
}


