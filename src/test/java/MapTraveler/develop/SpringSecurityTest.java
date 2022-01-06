package MapTraveler.develop;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringSecurityTest {

	MockMvc mock;
	
	@Autowired
    private WebApplicationContext context;
	
	@BeforeEach
    public void setup() {
        mock = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(springSecurity())
          .build();
    }
	

	
	//開発環境のDBにあるログイン情報を使いテスト
//	@Test
//	public void getLogin() throws Exception{
//		mock.perform(formLogin().user("test333").password("aaaaa"))
//			.andExpect(authenticated());
//	}
//	
//	@Test
//	public void failLogin() throws Exception{
//		mock.perform(formLogin().user("aiueo").password("bbb")).andExpect(unauthenticated());
//	}
	
}
