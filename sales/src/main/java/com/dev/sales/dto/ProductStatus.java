package com.dev.sales.dto;

public enum ProductStatus {
  AVAILABLE("AVAILABLE"),
  SOLD("SOLD-OUT");
  private final String statusCode;
  ProductStatus(String statusCode) {
	this.statusCode = statusCode;
  }
  
  public String getStatusCode()
  {
	  return this.statusCode;
  }
}
