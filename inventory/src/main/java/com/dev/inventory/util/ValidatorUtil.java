package com.dev.inventory.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.inventory.dto.InventoryDto;
import com.dev.inventory.entity.Article;

@Service
public class ValidatorUtil {

	@Autowired 
	private Validator validator;
	
	public void validateArticle(Article articleObj) throws ConstraintViolationException
	{
		Set<ConstraintViolation<Article>> violations = validator.validate(articleObj);
	    if (!violations.isEmpty()) {
	      throw new ConstraintViolationException(violations);
	    }
	}
	
	public void validateInventory(InventoryDto inventory) throws ConstraintViolationException
	{
		Set<ConstraintViolation<InventoryDto>> violations = validator.validate(inventory);
	    if (!violations.isEmpty()) {
	      throw new ConstraintViolationException(violations);
	    }
	}
	
	
}
