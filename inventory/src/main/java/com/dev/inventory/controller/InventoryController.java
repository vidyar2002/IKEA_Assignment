package com.dev.inventory.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dev.inventory.dto.ArticleDto;
import com.dev.inventory.dto.InventoryDto;
import com.dev.inventory.dto.ResponseDto;
import com.dev.inventory.entity.Article;
import com.dev.inventory.exception.WarehouseException;
import com.dev.inventory.service.ArticleService;
import com.dev.inventory.util.ValidatorUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/inventory/api/v1")
@Api(tags = {"Api to manage inventory operations"})
public class InventoryController {

	private static final Logger LOGGER = LogManager.getLogger(InventoryController.class);
	
	@Autowired
    private ArticleService articleService;
	
	@Autowired
	private ValidatorUtil validatorUtil;
	 
	/**
	 * Get articles from inventory
	 * @return ReponseDto
	 */
	 @GetMapping("/articles")
	 @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE, value = "API to fetch article information from Inventory")
	 public ResponseDto getAllArticles() {
		 	ResponseDto apiResponse = new ResponseDto();
		 	try {
		        List<Article> articleList =  articleService.getAllArticles();
		        if(articleList == null || articleList.size() == 0)
		        {
		        	apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
		        	apiResponse.setMessage("Empty list");
		        	apiResponse.setPayload(null);
		        }
		        else
		        {
		        	apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
		        	apiResponse.setMessage("Article information retrieved");
		        	apiResponse.setPayload(articleList);
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
	 
	 /**
	  * Add articles to inventory
	  * @param inventoryObj
	  * @return ReponseDto
	  */
	 @PostMapping("/articles")
	 @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE, value = "API to add article information to Inventory", consumes = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseStatus(code = HttpStatus.CREATED)
	 public ResponseDto addArticles(@RequestBody InventoryDto inventoryObj)
	 {
		 ResponseDto apiResponse = new ResponseDto();
		 if(inventoryObj == null || inventoryObj.getArticleList() == null || inventoryObj.getArticleList().size() == 0)
		 {
			 	apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	        	apiResponse.setMessage("Article list passed is empty");
	        	apiResponse.setPayload(null);
		 }
		 else
		 {
			 for(Article article : inventoryObj.getArticleList())
			 {
				 try {
					 validatorUtil.validateArticle(article);
					 articleService.addArticle(article);
				 }
				 catch(ConstraintViolationException cvEx)
				 {
					 apiResponse.getErrorMessages().add("Error while adding article with details "+article.toString()+" : "+cvEx.getMessage());
				 }
				 catch(WarehouseException ex)
				 { 
					 apiResponse.getErrorMessages().add(ex.getMessage());
				 }
			 }
			apiResponse.setCode(String.valueOf(HttpStatus.CREATED.value()));
        	apiResponse.setMessage("Article list loaded to inventory");
		 }
		 return apiResponse;
	 }
	 
	 
	 /**
	    * Get an article by id
	    * @param articleId
	    * @return ReponseDto
	    */
	   @GetMapping(value = "/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	   public ResponseDto getArticle(@PathVariable(value="id") Integer articleId)
	   {
		   ResponseDto apiResponse = new ResponseDto();
		   if(articleId == null || articleId == 0)
		   {
			   apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
				 apiResponse.setMessage("Invalid article Id");
		   }
		   else
		   {
				ArticleDto article = articleService.getOneArticle(articleId);
				apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
	        	apiResponse.setMessage("Article information retrieved successfully ");
	        	apiResponse.setPayload(article);			  
		   }
		   return apiResponse;
	   }
	 
	 /**
	  * Update an article
	  * @param articleId - as path variable
	  * @param articleObj - as request body
	  * @return ReponseDto
	  */
	 @PutMapping(value = "/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable(value = "id") Integer articleId, @RequestBody Article articleObj){
		 ResponseDto apiResponse = new ResponseDto();
		 if(articleId == null || articleId == 0) {
			 apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			 apiResponse.setMessage("Invalid Article Id");
		 }
		 else
		 {
			 Article updatedArticle;
			try {
				updatedArticle = articleService.update(articleId, articleObj);
				apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
				apiResponse.setMessage("Article updated successfully");
				apiResponse.setPayload(updatedArticle);
			} catch (WarehouseException e) {
				apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	        	apiResponse.setMessage(e.getMessage());
	        	apiResponse.setPayload(null);
			}
			 
		 }
        return apiResponse;
    }
	 
	 /**
	  * Update the stock information of an article
	  * @param articleId - as path variable
	  * @param articleDto - as request body
	  * @return ReponseDto
	  */
	   @PostMapping(value = "/articles/{id}/stockupdate", produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseDto updateStock(@PathVariable(value = "id") Integer articleId,  @RequestBody ArticleDto articleDto){
			 ResponseDto apiResponse = new ResponseDto();
			 if(articleId == null || articleId == 0) {
				 apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
				 apiResponse.setMessage("Invalid article Id");
			 }
			 else
			 {
				 try {
					Article updatedArticle = articleService.updateStock(articleId, articleDto.getQuantityToBeUpdated(), articleDto.getReasonCode());
					apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
		        	apiResponse.setMessage("Stock updated successfully ");
		        	apiResponse.setPayload(updatedArticle);
					
				} catch (WarehouseException e) {
					apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		        	apiResponse.setMessage(e.getMessage());
		        	apiResponse.setPayload(null);
				}
			 }
	        return apiResponse;
	    }
	   
	   /**
	    * Delete an article by id
	    * @param articleId
	    * @return ReponseDto
	    */
	   @DeleteMapping(value = "/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	   public ResponseDto delete(@PathVariable(value="id") Integer articleId)
	   {
		   ResponseDto apiResponse = new ResponseDto();
		   if(articleId == null || articleId == 0)
		   {
			   apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
				 apiResponse.setMessage("Invalid article Id");
		   }
		   else
		   {
			   try {
				articleService.deleteArticle(articleId);
				apiResponse.setCode(String.valueOf(HttpStatus.OK.value()));
	        	apiResponse.setMessage("Article deleted successfully ");
			   } catch (WarehouseException e) {
				apiResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	        	apiResponse.setMessage(e.getMessage());
	        	apiResponse.setPayload(null);
			   }
		   }
		   return apiResponse;
	   }
}
