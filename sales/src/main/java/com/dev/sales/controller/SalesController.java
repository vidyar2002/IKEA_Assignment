package com.dev.sales.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dev.sales.dto.ArticleDto;
import com.dev.sales.dto.ProductArticleDto;
import com.dev.sales.dto.ProductDto;
import com.dev.sales.dto.ProductStatus;
import com.dev.sales.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sales/api/v1")
@Api(tags = {"Api to manage sales operations"})
public class SalesController {
	private static final Logger LOGGER = LogManager.getLogger(SalesController.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${PM_UPDATE_URL}")
	private String pmUpdateUrl;
	
	@Value("${INV_STOCK_UPDATE_URL}")
	private String invStockUpdateUrl;
	
	/**
	 * Create a sales entry
	 * @param productId
	 * @return ResponseDto
	 */
	@GetMapping("/{productId}/create")
	 @ApiOperation(produces = "application/json", value = "API to create a sales request for a product", consumes = "application/json")
	 public ResponseDto createSale(@PathVariable("productId") Integer productId) {
		 	ResponseDto apiResponse = new ResponseDto();
		 	try {
		 		if(productId == null || productId == 0)
		 		{
		 			apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		        	apiResponse.setMessage("Invalid Product Id");
		        	apiResponse.setPayload(null);
		 		}
		 		else
		 		{
		 			ResponseDto getProductResponse = restTemplate.getForObject(pmUpdateUrl+productId, ResponseDto.class);
		 			if(getProductResponse.getCode().equals(String.valueOf(HttpStatus.OK.value())))
		 			{
		 				JsonFactory factory = new JsonFactory();
		 			    factory.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
		 			    
		 				ObjectMapper objectMapper = new ObjectMapper(factory);
		 				ProductDto productDetails = objectMapper.readValue(getProductResponse.getPayload().toString().getBytes(), ProductDto.class);
		 				
		 				if(productDetails.isActive() && !productDetails.getStatus().equals(ProductStatus.SOLD.getStatusCode()))
		 				{
				 			ProductDto productDto = new ProductDto();
				 			productDto.setId(productId);
				 			productDto.setStatus(ProductStatus.SOLD.getStatusCode());
				 			restTemplate.put(pmUpdateUrl+productId, productDto);
				 			
				 			ResponseDto productUpdateResponse = restTemplate.getForObject(pmUpdateUrl+productId, ResponseDto.class);
				 			if(productUpdateResponse.getCode().equals(String.valueOf(HttpStatus.OK.value())))
				 			{
				 				ProductDto updatedProduct = objectMapper.readValue(productUpdateResponse.getPayload().toString(), ProductDto.class);
					 			if(updatedProduct != null && updatedProduct.getStatus().equals(ProductStatus.SOLD.getStatusCode()))
					 			{
					 				if(updatedProduct.getProductArticles() != null && updatedProduct.getProductArticles().size() > 0)
					 				{
					 					for(ProductArticleDto productArticleDto : updatedProduct.getProductArticles())
					 					{
					 						ArticleDto articleDto = new ArticleDto();
					 						articleDto.setArticleId(productArticleDto.getArticleId());
					 						articleDto.setQuantityToBeUpdated(productArticleDto.getAmountOf());
					 						articleDto.setReasonCode("2");
					 						ResponseDto inventoryApiResponse = restTemplate.postForObject(invStockUpdateUrl+productArticleDto.getArticleId()+"/stockupdate", articleDto, ResponseDto.class);
					 						
					 					}
					 					apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
			 				        	apiResponse.setMessage("Product is sold and inventory updated");
			 				        	apiResponse.setPayload(updatedProduct);
					 				}
					 					
					 			}
					 			
				 			}
				 			else
				 			{
				 				apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
						        apiResponse.setMessage("Product sale failed with the error : "+productUpdateResponse.getPayload());
				 			}
		 				}
		 				else
		 				{
		 					apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
					        apiResponse.setMessage("Product sale failed with the error : Product with id :"+productId+" already sold out");
		 				}
		 			
		 		}
		 		}
		 	}
		 	catch(Exception ex)
		 	{
		 		apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	        	apiResponse.setMessage(ex.getMessage());
	        	apiResponse.setPayload(null);
		 	}
	        return apiResponse;
	 }
	
	
}
