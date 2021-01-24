package com.dev.inventory.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.inventory.dto.ArticleDto;
import com.dev.inventory.dto.ReasonCode;
import com.dev.inventory.entity.Article;
import com.dev.inventory.exception.WarehouseException;
import com.dev.inventory.repository.ArticleRepository;

@Service
public class ArticleService {
	
	private static final Logger LOGGER = LogManager.getLogger(ArticleService.class);
	
	@Autowired
	ArticleRepository articleRepository;
	
	/**
	 * Get all the articles from inventory
	 * @return
	 * @throws WarehouseException
	 */
	public List<Article> getAllArticles() throws WarehouseException
	{
		try {
		return articleRepository.findAll();
		}
		catch(Exception ex)
		{
			throw new WarehouseException(ex.getMessage());
		}
	}
	
	/**
	 * Add a new article
	 * @param articleObj
	 * @throws WarehouseException
	 */
	public void addArticle(Article articleObj) throws WarehouseException
	{
		if(articleObj == null)
			throw new WarehouseException("Tried to add an empty article");
		else
		{
			try {
				articleObj.setActive(true);
				articleRepository.save(articleObj);
			}
			catch(Exception ex)
			{
				throw new WarehouseException("Error while adding article with details "+articleObj.toString()+" : " +ex.getMessage());
			}
		}
	}
	
	/**
	 * Get an article information
	 * @param articleId
	 * @return
	 */
	public ArticleDto getOneArticle(Integer articleId)
	{
		Article article = articleRepository.getOne(articleId);
		ArticleDto articleDto = new ArticleDto();
		articleDto.convertEntityToDto(article);
		return articleDto;
	}
	
	/**
	 * Update an existing article
	 * @param articleId
	 * @param articleObj
	 * @return
	 * @throws WarehouseException
	 */
	public Article update(Integer articleId, Article articleObj) throws WarehouseException
	{
		if(articleId != null && articleObj != null)
		{
			Article articleEntity = articleRepository.getOne(articleId);
			if(articleEntity != null)
			{
				articleEntity.setName((articleObj.getName() != null && articleObj.getName().trim().length() > 0)?
						articleObj.getName():articleEntity.getName());
				articleEntity.setStock((articleObj.getStock() != null)?articleObj.getStock():articleEntity.getStock());
				articleEntity = articleRepository.save(articleEntity);
				
			}
			return articleEntity;
			
		}
		throw new WarehouseException("Invalid article details to update");
	}
	
	/**
	 * Update stock details of an article
	 * @param articleId
	 * @param quantityToUpdate
	 * @param reasonCode (1 - add stock, 2- remove stock)
	 * @return
	 * @throws WarehouseException
	 */
	public Article updateStock(Integer articleId, Integer quantityToUpdate, String reasonCode) throws WarehouseException
	{
		if(articleId != null && quantityToUpdate != null)
		{
			Article inventoryObj = articleRepository.getOne(articleId);
			if(inventoryObj != null)
			{
				int updatedQuantity = inventoryObj.getStock();				
				try {
				if(Integer.parseInt(reasonCode) == ReasonCode.ADDING_STOCK.getReasonCode())
					updatedQuantity = inventoryObj.getStock()+quantityToUpdate;
				else if(Integer.parseInt(reasonCode) == ReasonCode.REDUCE_STOCK.getReasonCode())
					updatedQuantity = inventoryObj.getStock()-quantityToUpdate;
				}
				catch(NumberFormatException nfEx)
				{
					
				}
				inventoryObj.setStock(updatedQuantity);
				if(updatedQuantity <= 0)
					inventoryObj.setActive(false);
					
			}
			Article articleEntity = articleRepository.save(inventoryObj);
			return articleEntity;
		}
		else
			throw new WarehouseException("Missing details to update the stock : "+articleId +"/"+quantityToUpdate);
	}
	
	
	/**
	 * Delete an article
	 * @param articleId
	 * @throws WarehouseException
	 */
	public void deleteArticle(Integer articleId) throws WarehouseException
	{
		if(articleId != null)
		{
			articleRepository.deleteById(articleId);
		}
		else
			throw new WarehouseException("Missing details to update the stock : "+articleId);
	}
}
