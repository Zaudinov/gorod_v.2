package zaudinov.testcase.userControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import zaudinov.testcase.domain.Serv;
import zaudinov.testcase.domain.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/add_data_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/add_data_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CreateUserTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createNewUserWithoutServiceTest() throws Exception{

        User expectedUser = getTestUserWithoutServices();


        String userJson = new ObjectMapper().writeValueAsString(expectedUser);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(userJson))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(".message").value("Can't create user with no service"));
    }

    @Test
    public void createNewUserWithValidServiceTest() throws Exception{

        User expectedUser = getTestUserWithoutServices();
        expectedUser.setService(new Serv(1l, "Жилищно-коммунальные услуги"));


        String userJson = new ObjectMapper().writeValueAsString(expectedUser);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(userJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/user/filter/account/5"));
    }

    @Test
    public void createNewUserWithInvalidServiceTest() throws Exception{

        User expectedUser = getTestUserWithoutServices();
        expectedUser.setService(new Serv(1l, "Заведомо ложная услуга"));


        String userJson = new ObjectMapper().writeValueAsString(expectedUser);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(userJson))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(".message").value("invalid service is provided"));;
    }

    @Test
    public void createNewUserWithInvalidServiceIdTest() throws Exception{

        User expectedUser = getTestUserWithoutServices();
        expectedUser.setService(new Serv(12l, "Несуществующая услуга"));


        String userJson = new ObjectMapper().writeValueAsString(expectedUser);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(userJson))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(".message").value("invalid service is provided"));
    }

    @Test
    public void createNewUserWithInvalidIdTest() throws Exception{

        User expectedUser = getTestUserWithoutServices();
        expectedUser.setId(1l);
        expectedUser.setService(new Serv(1l, "Жилищно-коммунальные услуги"));


        String userJson = new ObjectMapper().writeValueAsString(expectedUser);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(userJson))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(".message").value("There is an existing user with provided id"));
    }

    @Test
    public void createNewUserWithValidIdTest() throws Exception{

        User expectedUser = getTestUserWithoutServices();
        expectedUser.setId(25l);
        expectedUser.setService(new Serv(1l, "Жилищно-коммунальные услуги"));


        String userJson = new ObjectMapper().writeValueAsString(expectedUser);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(userJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/user/filter/account/5"));
    }

    public User getTestUserWithoutServices(){
        User expectedUser = new User();
        expectedUser.setFio("Pechkin Yurii Vadimirovich");
        expectedUser.setAccount("1234601");
        return expectedUser;
    }
}
