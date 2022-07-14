package com.reea.fantestic.level03.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reea.fantestic.level02.controller.SkyController;
import com.reea.fantestic.level02.dto.CloudDTO;
import com.reea.fantestic.level02.dto.SkyDTO;
import com.reea.fantestic.level02.exception.SkyNotFoundException;
import com.reea.fantestic.level02.service.SkyService;
import com.reea.fantestic.level02.utils.SkyMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controllers are special, meaning that they need something to call their endpoints.
 * That something is called MockMvc.
 *
 * What you have to do:
 * - observe the running tests
 * - correct the failing test
 * - add a test for the case when creation is failing
 */
// Take notice that we no longer use MockitoExtension, we need a new class for the controller tests
@ExtendWith(SpringExtension.class)
// Controllers are special, meaning that they need something to call their endpoints.
// That something is called MockMvc and performs calls that have headers, body, authorization, path variables etc. whatever you would need in Postman
// This annotation autoconfigures the MockMvc object
@WebMvcTest(SkyController.class)
public class SkyControllerTest {

    public static final String SKY_DOES_NOT_EXIST = "Sky does not exist!";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    // The mocks still behave the same as in level 2, we still need to block the next layer
    @MockBean
    SkyService skyService;

    //      @BeforeEach - calling this method before executing each test in the class (once per test)
    //      @AfterEach - does the same but after the test has finished executing (once per test)
    //      @BeforeAll - executed once before the tests in the class are executed (once per class)
    //      @AfterAll  - executed once after the tests in the class are executed (once per class)
    //
    // We are using @BeforeEach to do the MockMvc setup for the tests
    @BeforeEach
    public void setup() {
        // we are setting the context before each call
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private static final String NAME = "sky_test";
    public static final long DAYLIGHT = 14;
    public static final String BASE_URL = "/api/sky";

    public static final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("When trying to find a sky by its name and it exists, return it.")
    void findSkySuccessful() throws Exception {
        SkyDTO sky = getSkyDTOForTesting();
        // we usually have to use a base url and build it with the path variables we might need
        String url = BASE_URL + "/" + NAME;

        // *when* method is working just the same as in the previous level
        when(skyService.findByName(eq(NAME))).thenReturn(SkyMapper.from(sky));

        // here we are performing the call
        mockMvc.perform(
                // you can perform any HTTP method you want: get, post, put, patch etc
                        get(url)
                                // we are using json as transfer format
                                .contentType(MediaType.APPLICATION_JSON))
                // this is not mandatory, but I put it here to show you how you can print what the call is fully returning
                // very good for debugging but do not let it in production here
                .andDo(MockMvcResultHandlers.print())
                // you can check the status like this
                .andExpect(status().isOk())
                // this is how you check the entire response body
                // we have to use an object mapper to serialize the object we are comparing with to json
                .andExpect(content().json(mapper.writeValueAsString(sky)))
                // we can also check stuff about particular fields if we do not want to check the entire response body
                // after we already checked the entire body, we shouldn't check the individual fields as it is redundant, but this is for the sake of learning
                .andExpect(jsonPath("$.clouds", hasSize(sky.getClouds().size())))
                .andExpect(jsonPath("$.name").value(sky.getName()))
                // you can also verify if something is missing (and we want it to be missing)
                .andExpect(jsonPath("$.shaorma").doesNotExist());

        verify(skyService, times(1)).findByName(sky.getName());
    }

    @Test
    @DisplayName("When trying to find a sky by its name and it doesn't exist, throw a SkyNotFoundException.")
    void findSkyFailure() throws Exception {
        SkyDTO sky = getSkyDTOForTesting();
        // we usually have to use a base url and build it with the path variables we might need
        String url = BASE_URL + "/" + NAME;

        // *when* method is working just the same as in the previous level
        when(skyService.findByName(eq(NAME))).thenThrow(new SkyNotFoundException(SKY_DOES_NOT_EXIST));

        // here we are performing the call
        mockMvc.perform(
                        // you can perform any HTTP method you want: get, post, put, patch etc
                        get(url)
                                // we are using json as transfer format
                                .contentType(MediaType.APPLICATION_JSON))
                // this is not mandatory, but I put it here to show you how you can print what the call is fully returning
                // very good for debugging but do not let it in production here
                .andDo(MockMvcResultHandlers.print())
                // you can check the status like this
                .andExpect(status().isNotFound())
                // this is how you check what kind of exception was thrown
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof SkyNotFoundException))
                // you can also check the message
                .andExpect(result -> assertEquals(SKY_DOES_NOT_EXIST, result.getResolvedException().getMessage()));

        verify(skyService, times(1)).findByName(sky.getName());
    }

    // TODO: Finish and correct this test. Check the entire response body but also check some individual fields of the response
    @Test
    @DisplayName("When creating a sky with valid values, return the crated sky.")
    void createSkySuccessful() throws Exception {
        SkyDTO sky = getSkyDTOForTesting();
        // we usually have to use a base url and build it with the path variables we might need
        String url = BASE_URL + "/" + NAME;

        // *when* method is working just the same as in the previous level
        when(skyService.createSky(eq(SkyMapper.from(sky)))).thenReturn(SkyMapper.from(sky));

        // here we are performing the call
        mockMvc.perform(
                        // you can perform any HTTP method you want: get, post, put, patch etc
                        post(url)
                                // we are adding the request body as json
                                .content(mapper.writeValueAsString(sky))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                // this is not mandatory, but I put it here to show you how you can print what the call is fully returning
                // very good for debugging but do not let it in production here
                .andDo(MockMvcResultHandlers.print())
                // you can check the status like this
                .andExpect(status().isOk())
                // this is how you check the entire response body
                // we have to use an object mapper to serialize the object we are comparing with to json
                .andExpect(content().json(mapper.writeValueAsString(sky)));

        verify(skyService, times(5)).findByName(sky.getName().toUpperCase());
    }

    // TODO: create the test for the case when the creation of a sky is failing

    private SkyDTO getSkyDTOForTesting() {
        SkyDTO sky = new SkyDTO();
        sky.setClouds(new ArrayList<>());
        for (int i = 0; i < 3; i++) {
            CloudDTO cloud = new CloudDTO((i + 100) * i);
            sky.getClouds().add(cloud);
        }
        sky.setName(NAME);
        sky.setDaylightDuration(DAYLIGHT);
        return sky;
    }
}
