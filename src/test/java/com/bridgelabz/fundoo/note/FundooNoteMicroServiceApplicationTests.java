package com.bridgelabz.fundoo.note;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.fundoo.note.testcasesdemo.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FundooNoteMicroServiceApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Resource casesFile;

	private Map<String, Json> cases;

	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		casesFile = new ClassPathResource("testcases.json");
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		cases = objectMapper.readValue(casesFile.getInputStream(), new TypeReference<Map<String, Json>>() {
		});
	}

	private void happyTest(Json json) throws JsonProcessingException, Exception {
		ResultActions actions = mockMvc
				.perform(getMethod(json).headers(json.getRequest().getHeaders()).contentType(MediaType.APPLICATION_JSON)
						.content(getRequestBody(json)).accept(MediaType.APPLICATION_JSON));

		actions.andExpect(status().is(json.getResponse().getStatus().value()));

		MockHttpServletResponse response = actions.andReturn().getResponse();

		for (String key : json.getResponse().getHeaders().keySet()) {
			assertEquals(json.getResponse().getHeaders().get(key), response.getHeader(key));
		}

		actions.andExpect(jsonPath("$.*", hasSize(12)));
	}

	private void sadTest(Json json) throws JsonProcessingException, Exception {
		ResultActions actions = mockMvc
				.perform(getMethod(json).headers(json.getRequest().getHeaders()).contentType(MediaType.APPLICATION_JSON)
						.content(getRequestBody(json)).accept(MediaType.APPLICATION_JSON));

		actions.andExpect(status().is(json.getResponse().getStatus().value()));

		MockHttpServletResponse response = actions.andReturn().getResponse();

		for (String key : json.getResponse().getHeaders().keySet()) {
			assertEquals(json.getResponse().getHeaders().get(key), response.getHeader(key));
		}

		assertEquals(getResponseBody(json), response.getContentAsString());
	}

	private MockHttpServletRequestBuilder getMethod(Json json) {

		return MockMvcRequestBuilders.request(HttpMethod.resolve(json.getRequest().getMethod()),
				json.getRequest().getUrl());
	}

	private String getRequestBody(Json json) throws JsonProcessingException {
		return objectMapper.writeValueAsString(json.getRequest().getBody());

	}
	
	private String getResponseBody(Json json) throws JsonProcessingException {
		return objectMapper.writeValueAsString(json.getResponse().getBody());

	}

	@Test
	public void test1() throws Exception {
		Json json = cases.get("successfullyNoteCreation");
		happyTest(json);
	}

	@Test
	public void test2() throws Exception {
		Json json = cases.get("noteCreationWithoutTitle");
		happyTest(json);
	}
	
	@Test
	public void test3() throws Exception {
		Json json = cases.get("noteCreationWithReminder");
		happyTest(json);
	}

	@Test
	public void test4() throws Exception {
		Json json = cases.get("noteCreationWithEmptyTitleAndDesc");
		sadTest(json);
	}
	
	@Test
	public void test5() throws Exception {
		Json json = cases.get("noteCreationWithWrongReminder");
		sadTest(json);
	}
	
	@Test
	public void test6() throws Exception {
		Json json = cases.get("addingWrongReminderToNote");
		sadTest(json);
	}
	
	@Test
	public void test7() throws Exception {
		Json json = cases.get("addingReminderToNote");
		sadTest(json);
	}
	@Test
	public void test8() throws Exception {
		Json json = cases.get("createLabelWithoutLabelName");
		sadTest(json);
	}
	
	@Test
	public void test9() throws Exception {
		Json json = cases.get("createLabel");
		sadTest(json);
	}
	
	@Test
	public void test10() throws Exception {
		Json json = cases.get("createLabelWithLongName");
		sadTest(json);
	}
	
	
}
