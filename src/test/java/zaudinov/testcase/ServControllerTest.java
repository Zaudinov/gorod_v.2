package zaudinov.testcase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/add_data_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/add_data_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ServControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deleteServiceWithNoChildrenTest() throws Exception {
        mockMvc.perform(delete("/service/8"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteServiceWithChildrenTest() throws Exception {
        mockMvc.perform(delete("/service/6"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(".message")
                        .value("Can't delete the service, it has one or more child service"));
    }

    @Test
    public void deleteServiceWithSubscriberTest() throws Exception {
        mockMvc.perform(delete("/service/7"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(".message")
                        .value("Can't delete the service, it has one or more subscriber"));
    }

    @Test
    public void invalidServiceIdToDeleteTest() throws Exception {
        mockMvc.perform(delete("/service/10"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(".message")
                        .value("there is no service with provided id"));
    }

    @Test
    public void forceDeleteTest() throws Exception {
        mockMvc.perform(delete("/service/1?force=true"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath(".account").value("1234525"))
                .andExpect(jsonPath(".totalElements").value(1));
    }

    @Test
    public void forceDeleteInvalidIdTest() throws Exception {
        mockMvc.perform(delete("/service/11?force=true"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(".message")
                        .value("there is no service with provided id"));

    }
}
