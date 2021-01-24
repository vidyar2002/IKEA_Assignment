package com.dev.productmanagement.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.productmanagement.dto.ProductDto;
import com.dev.productmanagement.dto.ProductRecordsDto;
import com.dev.productmanagement.dto.ResponseDto;
import com.dev.productmanagement.entity.Product;
import com.dev.productmanagement.exception.WarehouseException;
import com.dev.productmanagement.service.ProductService;
import com.dev.productmanagement.util.ValidatorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pm/api/v1")
@Api(tags = { "Api to manage product operations" })
public class ProductController {

	private static final Logger LOGGER = LogManager.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;

	@Autowired
	private ValidatorUtil validatorUtil;

	/**
	 * Get all articles
	 * 
	 * @return ResponseDto
	 */
	@GetMapping("/products")
	@ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE, value = "API to fetch product information from warehouse")
	public ResponseDto getAllArticles() {
		ResponseDto apiResponse = new ResponseDto();
		try {
			List<Product> productList = productService.getProducts();
			if (productList == null || productList.size() == 0) {
				apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
				apiResponse.setMessage("Empty list");
				apiResponse.setPayload(null);
			} else {
				apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
				apiResponse.setMessage("Product information retrieved");
				apiResponse.setPayload(productList);
			}
		} catch (Exception ex) {
			apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			apiResponse.setMessage(ex.getMessage());
			apiResponse.setPayload(null);
		}
		return apiResponse;
	}

	/**
	 * Get article of a given id
	 * 
	 * @param productId
	 * @return ResponseDto
	 */
	@GetMapping("/products/{id}")
	@ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE, value = "API to fetch product information from warehouse")
	public ResponseDto getProduct(@PathVariable(value = "id") Integer productId) {
		ResponseDto apiResponse = new ResponseDto();
		try {
			ProductDto productDto = productService.getProduct(productId);
			if (productDto == null) {
				apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
				apiResponse.setMessage("Invalid product");
				apiResponse.setPayload(null);
			} else {
				apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
				apiResponse.setMessage("Product information retrieved");
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					String payloadStr = objectMapper.writeValueAsString(productDto);
					apiResponse.setPayload(payloadStr);
				} catch (JsonProcessingException e) {
					apiResponse.setPayload(null);
				}
			}
		} catch (Exception ex) {
			apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			apiResponse.setMessage(ex.getMessage());
			apiResponse.setPayload(null);
		}
		return apiResponse;
	}

	/**
	 * Load the product details
	 * 
	 * @param productDto
	 * @return ResponseDto
	 */
	@PostMapping("/products")
	@ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE, value = "API to add product information to Inventory", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseDto loadProducts(@RequestBody ProductRecordsDto productDto) {
		ResponseDto apiResponse = new ResponseDto();
		if (productDto == null || productDto.getProductList() == null || productDto.getProductList().size() == 0) {
			apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
			apiResponse.setMessage("Product list passed is empty");
			apiResponse.setPayload(null);
		} else {
			for (Product product : productDto.getProductList()) {
				try {
					validatorUtil.validateProduct(product);
					productService.addProduct(product);
				} catch (ConstraintViolationException cvEx) {
					apiResponse.getErrorMessages().add("Error while adding Product with details " + product.toString()
							+ " : " + cvEx.getMessage());
				} catch (WarehouseException ex) {
					apiResponse.getErrorMessages().add(ex.getMessage());
				}
			}
			apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
			apiResponse.setMessage("Product list loaded to inventory");
		}
		return apiResponse;
	}

	/**
	 * Update product details 
	 * @param productId
	 * @param productObj
	 * @return ResponseDto
	 */
	@PutMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseDto update(@PathVariable(value = "id") Integer productId, @RequestBody Product productObj) {
		ResponseDto apiResponse = new ResponseDto();
		if (productId == null || productId == 0) {
			apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			apiResponse.setMessage("Invalid Product Id");
		} else {
			Product updatedProject;
			try {
				updatedProject = productService.update(productId, productObj);
				apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
				apiResponse.setMessage("Product updated successfully");
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					String payloadStr = objectMapper.writeValueAsString(updatedProject);
					apiResponse.setPayload(payloadStr);
				} catch (JsonProcessingException e) {
					apiResponse.setPayload(null);
				}

			} catch (WarehouseException e) {
				apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
				apiResponse.setMessage(e.getMessage());
				apiResponse.setPayload(null);
			}

		}
		return apiResponse;
	}
}
