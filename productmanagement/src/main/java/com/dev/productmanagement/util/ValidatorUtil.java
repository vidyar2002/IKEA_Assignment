package com.dev.productmanagement.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.productmanagement.entity.Product;

@Service
public class ValidatorUtil {

	@Autowired 
	private Validator validator;
	
	
	public void validateProduct(Product productObj) throws ConstraintViolationException
	{
		Set<ConstraintViolation<Product>> violations = validator.validate(productObj);
	    if (!violations.isEmpty()) {
	      throw new ConstraintViolationException(violations);
	    }
	}
}
