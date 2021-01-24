package com.dev.productmanagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dev.productmanagement.entity.Product;
import com.dev.productmanagement.entity.ProductArticleMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
public class ProductDto implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	@JsonProperty("contain_articles")
	private List<ProductArticleMappingDto> articleMappings;
	private String status;
	private boolean active;
	
	public ProductDto() {
		articleMappings = new ArrayList<ProductArticleMappingDto>();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public List<ProductArticleMappingDto> getArticleMappings() {
		return articleMappings;
	}


	public void setArticleMappings(List<ProductArticleMappingDto> articleMappings) {
		this.articleMappings = articleMappings;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
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


	@Override
	public String toString() {
		return "[id="+this.id+", name="+this.name+", active = "+this.active+", articleMappings="+this.articleMappings+"]";
	}
	
	public void convertEntityToDto(Product product)
	{
		List<ProductArticleMappingDto> articleMappingDtoList = new ArrayList<ProductArticleMappingDto>();
		this.active = product.isActive();
		this.id = product.getId();
		this.name = product.getName();
		this.status = product.getStatus();
		if(product.getArticleMappings() != null)
		{
			for(ProductArticleMapping articleMapping : product.getArticleMappings())
			{
				ProductArticleMappingDto articleMappingDto = new ProductArticleMappingDto();
				articleMappingDto.convertEntityToDto(articleMapping);
				articleMappingDtoList.add(articleMappingDto);
				
			}
		}
		this.articleMappings = articleMappingDtoList;
		
	}

}
