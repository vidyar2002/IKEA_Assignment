package com.dev.inventory.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.dev.inventory.entity.Article;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InventoryDto {
	
	@JsonProperty(value="inventory")
	@NotEmpty(message="Please provide article list")
	private List<Article> articleList;

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}
	
}
