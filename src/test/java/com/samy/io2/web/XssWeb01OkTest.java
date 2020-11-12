package com.samy.io2.web;

import com.samy.io2.filter.XssFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class XssWeb01OkTest {
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new XssFilterWeb())
                .addFilter(new XssFilter())
                .build();
    }

    @Test
    public void checkRightText() throws Exception {
        String text = "{\"text\":\"<p>hello</p>\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/verify/xss")
                .contentType(MediaType.APPLICATION_JSON)
                .content(text))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // 得到返回代码
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        // 断言,判断返回代码和返回值是否正确
        Assert.assertEquals(200, status);
        Assert.assertEquals("okay", content);
    }
}
