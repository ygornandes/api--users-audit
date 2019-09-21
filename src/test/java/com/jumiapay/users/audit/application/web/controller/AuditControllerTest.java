package com.jumiapay.users.audit.application.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.jumiapay.users.audit.utils.MockUtil.getRequestBody;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
public class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        initialData();
    }

    @Test
    public void shouldCreateAuditWithSuccess() throws Exception {
        mockMvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(getRequestBody("json/auditJsonSuccess.json")))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    public void shouldReturnNotFoundOnGetByUserId() throws Exception {
        mockMvc.perform(get("/audits/users/{id}","Gerard" ).param("page","0").param("size","10"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.timestamp",is(notNullValue())))
                .andExpect(jsonPath("$.message",is("Audit not found.")));
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidRequestBody() throws Exception {
        mockMvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(getRequestBody("json/invalidJson.json")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.timestamp",is(notNullValue())))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors", hasItem("User can not be null.")))
                .andExpect(jsonPath("$.errors", hasItem("Payload can not be null.")))
                .andExpect(jsonPath("$.errors", hasItem("Action can not be blanck.")));

    }

    @Test
    public void shouldReturnBadRequestWhenWithoutRequestBody() throws Exception {
        mockMvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.timestamp",is(notNullValue())))
                .andExpect(jsonPath("$.message",is("Request body is required.")));

    }

    @Test
    public void shouldReturnBadRequestWhenWithoutQueryParamOnGetByUserId() throws Exception {
        mockMvc.perform(get("/audits/users/{id}","Gerard" ))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.timestamp",is(notNullValue())))
                .andExpect(jsonPath("$.message",is("Query param is required.")));
    }

    @Test
    public void shouldReturnAuditOnGetByUserId() throws Exception {
        mockMvc.perform(get("/audits/users/{id}","Arnold" ).param("page","0").param("size","10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].user.id",is("Arnold")));
    }

    @Test
    public void shouldReturnAuditOnGetAll() throws Exception {
        mockMvc.perform(get("/audits" ).param("page","0").param("size","10").param("direction","DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].user.id",is("Arnold")));
    }

    @Test
    public void shouldReturnBadRequestWhenWithoutQueryParamOnGetAll() throws Exception {
        mockMvc.perform(get("/audits" ))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.timestamp",is(notNullValue())))
                .andExpect(jsonPath("$.message",is("Query param is required.")));
    }

    @Test
    public void shouldReturnNotFoundOnGetAll() throws Exception {
        mockMvc.perform(get("/audits").param("page","1").param("size","10"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.timestamp",is(notNullValue())))
                .andExpect(jsonPath("$.message",is("Audit not found.")));
    }

    private void initialData() throws Exception {
        mockMvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(getRequestBody("json/auditJsonSuccess.json")))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}