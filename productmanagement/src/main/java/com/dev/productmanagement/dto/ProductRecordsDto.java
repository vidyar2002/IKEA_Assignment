package com.dev.productmanagement.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.dev.productmanagement.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductRecordsDto {

	@JsonProperty(value="products")
	@NotEmpty(message="Please provide product list")
	private List<Product> productList;

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	
}
