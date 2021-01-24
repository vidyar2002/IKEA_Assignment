package com.dev.productmanagement.dto;

import com.dev.productmanagement.entity.ProductArticleMapping;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductArticleMappingDto {

	@JsonIgnore
	private Integer id;
	
	@JsonProperty(value="art_id")
	private int articleId;
	
	@JsonProperty(value="amount_of")
	private int quantity;

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductArticleMappingDto )) return false;
        return id != null && id.equals(((ProductArticleMappingDto) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

	@Override
	public String toString() {
		return "[articleId="+this.articleId+", quantity="+this.quantity+"]";
	}
	
	public void convertEntityToDto(ProductArticleMapping articleMappingEntity)
	{
		this.articleId = articleMappingEntity.getArticleId();
		this.id = articleMappingEntity.getId();
		this.quantity = articleMappingEntity.getQuantity();
	}
}
