package com.dev.inventory;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ProductManagementApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;

	
	
	@Test
	@Order(1)
	public void getAllProductsTest() throws Exception {
		this.mockMvc.perform(get("/pm/api/v1/products")).andDo(print()).andExpect(status().isOk()).
				andExpect(content().string(containsString("Empty list")));
	}
	
	
	

}
