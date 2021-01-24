package com.dev.sales.dto;

public class ArticleDto {
	private Integer articleId;
	private String name;
	private String reasonCode;
	private Integer quantityToBeUpdated;
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
	
	@Override
	public String toString() {
		return "[ articleId ="+this.articleId+", name = "+this.name+", quantityToBeUpdated= "+this.quantityToBeUpdated+", reasonCode = "+reasonCode+"]";
	}
	
}
