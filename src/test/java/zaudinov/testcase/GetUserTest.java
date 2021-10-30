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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/add_data_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/add_data_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GetUserTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getUserTest() throws Exception{
		mockMvc.perform(get("/user"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath(".content[0].account").value("1234526"))
				.andExpect(jsonPath(".content[1].account").value("1234525"))
				.andExpect(jsonPath(".totalElements").value(4));
	}

	@Test
	public void getUserFilterTest() throws Exception{
		mockMvc.perform(get("/user").param("filter", "1234516"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/user/filter/account/1234516"));
	}

	@Test
	public void getUserByAccountFilterTest() throws Exception{
		mockMvc.perform(get("/user/filter/account/123451_"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath(".content[0].account").value("1234514"))
				.andExpect(jsonPath(".content[1].account").value("1234513"))
				.andExpect(jsonPath(".totalElements").value(2));
	}

	@Test
	public void getUserByServiceTest() throws Exception{
		mockMvc.perform(get("/user/service/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath(".account").value("1234526"))
				.andExpect(jsonPath(".totalElements").value(1));
	}

	@Test
	public void getUserByServiceWithChildrenTest() throws Exception{
		mockMvc.perform(get("/user/service/all/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("content[0].account").value("1234526"))
				.andExpect(jsonPath("content[1].account").value("1234514"))
				.andExpect(jsonPath("content[2].account").value("1234513"))
				.andExpect(jsonPath(".totalElements").value(3));
	}

}
