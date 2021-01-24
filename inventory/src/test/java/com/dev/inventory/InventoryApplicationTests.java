package com.dev.inventory;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import com.dev.inventory.dto.InventoryDto;
import com.dev.inventory.entity.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
class InventoryApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;

	
	
	@Test
	@Order(1)
	public void getAllArticlesTest() throws Exception {
		this.mockMvc.perform(get("/inventory/api/v1/articles")).andDo(print()).andExpect(status().isOk()).
				andExpect(content().string(containsString("Empty list")));
	}
	
	
	@Test
	@Order(2)
	@Rollback(false)
	public void addArticlesTest() throws Exception {
		
		Article articleOne = new Article();
		articleOne.setId(1);
		articleOne.setActive(true);
		articleOne.setName("Table1");
		articleOne.setStock(20);
		InventoryDto inventoryDto = new InventoryDto();
		List<Article> articleList = new ArrayList<Article>();
		articleList.add(articleOne);
		inventoryDto.setArticleList(articleList);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
		String requestBodyAsJson=ow.writeValueAsString(inventoryDto );
		
		this.mockMvc.perform(post("/inventory/api/v1/articles").contentType(MediaType.APPLICATION_JSON)
				.content(requestBodyAsJson))
				.andDo(print()).andExpect(status().isCreated()).
				andExpect(content().string(containsString("Article list loaded to inventory")));
	}
	
	
	
	@Test
	@Order(3)
	public void getArticleTest() throws Exception
	{
		this.mockMvc.perform(get("/inventory/api/v1/articles/1")).andDo(print()).andExpect(status().isOk()).
		andExpect(content().string(containsString("Article information retrieved successfully ")));
		
	}
	
	
	@Test
	@Order(4)
	@Rollback(false)
	public void updateArticleTest() throws Exception {
		
		Article articleOne = new Article();
		articleOne.setId(1);
		articleOne.setActive(true);
		articleOne.setName("Table-updated");
		articleOne.setStock(10);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
		String requestBodyAsJson=ow.writeValueAsString(articleOne );
		
		this.mockMvc.perform(put("/inventory/api/v1/articles/1").contentType(MediaType.APPLICATION_JSON)
				.content(requestBodyAsJson))
				.andDo(print()).andExpect(status().isCreated()).
				andExpect(content().string(containsString("Article updated successfully")));
	}


}
