package com.example.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.domain.Iine;
import com.example.service.IineService;

@SpringBootTest
@AutoConfigureMockMvc
class IineControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IineService iineService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("いいね更新メソッドで期待するJSONデータが返ってくるか確認")
	void testIine() throws Exception {

		Iine iine = new Iine();
		iine.setCount(1);
		when(iineService.addIine(1, 1)).thenReturn(iine);

		// コンソールに出力されるJSONの中身が期待通り「Body = {"count":1}」になっていることを確認
		MvcResult result = mockMvc.perform(get("/api/iine").param("articleId", "1").param("userId", "1"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	@DisplayName("カウント取得メソッドで期待するJSONデータが返ってくるか確認")
	void testShowIine() throws Exception {

		Iine iine = new Iine();
		iine.setCount(1);
		when(iineService.findIine(1)).thenReturn(iine);
		
		// コンソールに出力されるJSONの中身が期待通り「Body = {"count":1}」になっていることを確認
		MvcResult result = mockMvc.perform(get("/api/iine/show").param("articleId", "1")).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

}
