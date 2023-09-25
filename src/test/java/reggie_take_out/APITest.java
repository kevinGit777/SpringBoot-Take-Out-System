package reggie_take_out;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.myProject.reggie.ReggieApplication;

@SpringBootTest(classes = ReggieApplication.class)
@AutoConfigureMockMvc
public class APITest {



	@Autowired
	private MockMvc mockMvc;
	
	MockHttpSession session;
	
	@BeforeEach
	public void setup(){
		this.session = new MockHttpSession();
		this.session.setAttribute("user", 1695107187721949186L);
	}

	
	@RepeatedTest(value = 100)
	public void testDishList() throws Exception {
		//  2.668 s - 0.023s
		
		Long categoryId = 1397844263642378242L;
		 
		 
		this.mockMvc.perform( get("/dish/list").param("categoryId", String.valueOf(categoryId))).andDo(print()).andExpect(status().isOk() );
		 
		
	}
	

}
