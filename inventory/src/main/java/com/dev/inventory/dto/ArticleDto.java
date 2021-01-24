package com.dev.inventory.dto;

import com.dev.inventory.entity.Article;

public class ArticleDto {
	private Integer articleId;
	private String name;
	private String reasonCode;
	private Integer quantityToBeUpdated;
	private Integer stock;
	private boolean active;
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public Integer getQuantityToBeUpdated() {
		return quantityToBeUpdated;
	}
	public void setQuantityToBeUpdated(Integer quantityToBeUpdated) {
		this.quantityToBeUpdated = quantityToBeUpdated;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	public void convertEntityToDto(Article article)
	{
		this.articleId = article.getId();
		this.name = article.getName();
		this.stock = article.getStock();
		this.active = article.isActive();
	}
}
