package io.pivotal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringbeachdemoApplication.class)
@WebAppConfiguration
public class HelloControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setUp() throws Exception {
        /* do not use standAloneSetup, as it is not good for web apps */
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


     @Test
    public void getHello() throws Exception {
        mvc.perform(get("/helloworld"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("hellotemplate"))
                .andExpect(content().string(containsString("hardcoded hello")))
                .andExpect(content().string(containsString("Hello, World")));

    }

    @Test
    public void getHelloWithArgument() throws Exception {
        mvc.perform(get("/helloworld").param("name", "Navya"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("hellotemplate"))
                .andExpect(content().string(containsString("hardcoded hello")))
                .andExpect(content().string(containsString("Hello, Navya")));

    }

    @Test
    public void getHello2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/helloworld2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(xpath("//h1").exists())
                .andExpect(content().string(equalTo("<html><h1>Hello world</h1><p>Hardcoded without a template</p></html>")));
    }

}