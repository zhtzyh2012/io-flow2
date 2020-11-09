package com.samy.xss.web;

import com.samy.xss.filter.XssFilter;
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
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class XssWebTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new XssFilter()).build();
    }

    @Test
    public void checkText() throws Exception {
        /*
         * 1、mockMvc.perform执行一个请求。
         * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
         * 3、ResultActions.param添加请求传值
         * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
         * 5、ResultActions.andExpect添加执行完成后的断言。
         * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
         *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
         * 5、ResultActions.andReturn表示执行完成后返回相应的结果。
         */

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/verify/xss")
                .contentType(MediaType.APPLICATION_JSON)
//                .content()
                .accept(MediaType.TEXT_HTML_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // 得到返回代码
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(200, status);                        //断言，判断返回代码是否正确
        Assert.assertEquals("hello lvgang", content);            //断言，判断返回的值是否正确
    }
}
