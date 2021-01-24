package com.dev.sales.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDto {

	private Integer id;
	private String status;
	private boolean active;
	private String name;
	@JsonProperty(value="contain_articles")
	private List<ProductArticleDto> productArticles;
	
	public ProductDto() {
		// TODO Auto-generated constructor stub
	}
	
	ProductDto(Integer id, String status, boolean active, List<ProductArticleDto> productArticles)
	{
		this.id = id;
		this.status = status;
		this.active = active;
		this.productArticles = productArticles;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<ProductArticleDto> getProductArticles() {
		return productArticles;
	}
	public void setProductArticles(List<ProductArticleDto> productArticles) {
		this.productArticles = productArticles;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
