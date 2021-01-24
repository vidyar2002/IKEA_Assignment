package com.dev.sales.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductArticleDto {

	@JsonProperty(value="art_id")
	private Integer articleId;
	@JsonProperty(value="amount_of")
    private Integer amountOf;
	
	public ProductArticleDto() {
		// TODO Auto-generated constructor stub
	}
	
	public ProductArticleDto(Integer articleId, Integer amountOf)
	{
		this.articleId = articleId;
		this.amountOf = amountOf;
	}
	
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getAmountOf() {
		return amountOf;
	}
	public void setAmountOf(Integer amountOf) {
		this.amountOf = amountOf;
	}
    
	
}
