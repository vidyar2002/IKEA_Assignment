package com.dev.productmanagement.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.productmanagement.dto.ProductDto;
import com.dev.productmanagement.dto.ProductStatus;
import com.dev.productmanagement.entity.Product;
import com.dev.productmanagement.entity.ProductArticleMapping;
import com.dev.productmanagement.exception.WarehouseException;
import com.dev.productmanagement.repository.ProductRepository;

@Service
public class ProductService {
	
	private static final Logger LOGGER = LogManager.getLogger(ProductService.class);

	@Autowired
	ProductRepository productRepository;

	/**
	 * Get all products
	 * 
	 * @return List<Product>
	 * @throws WarehouseException
	 */
	public List<Product> getProducts() throws WarehouseException {
		try {
			return productRepository.findAll();
		} catch (Exception ex) {
			throw new WarehouseException(ex.getMessage());
		}
	}

	/**
	 * Add a product
	 * 
	 * @param productObj
	 * @throws WarehouseException
	 */

	public void addProduct(Product productObj) throws WarehouseException {
		if (productObj == null)
			throw new WarehouseException("Tried to add an empty product");
		else {
			try {
				if (productObj != null && productObj.getArticleMappings() != null) {
					productObj.setActive(true);
					productObj.setStatus(ProductStatus.AVAILABLE.getStatusCode());
					for (ProductArticleMapping articleMapping : productObj.getArticleMappings()) {
						articleMapping.setProduct(productObj);
					}
				}
				productRepository.save(productObj);
			} catch (Exception ex) {
				throw new WarehouseException(
						"Error while adding product with details " + productObj.toString() + " : " + ex.getMessage());
			}
		}
	}

	/**
	 * Get one product details
	 * 
	 * @param productId
	 * @return
	 */
	public ProductDto getProduct(Integer productId) {
		Product product = productRepository.getOne(productId);
		ProductDto productDto = new ProductDto();
		productDto.convertEntityToDto(product);
		return productDto;
	}

	/**
	 * Update a product
	 * 
	 * @param productId
	 * @param productObj
	 * @return
	 * @throws WarehouseException
	 */
	public Product update(Integer productId, Product productObj) throws WarehouseException {
		if (productId != null && productObj != null) {
			Product productEntity = productRepository.getOne(productId);
			if (productEntity != null) {
				productEntity.setStatus((productObj.getStatus() != null && productObj.getStatus().trim().length() > 0)
						? productObj.getStatus()
						: productEntity.getStatus());
				productEntity.setName((productObj.getName() != null && productObj.getName().trim().length() > 0)
						? productObj.getName()
						: productEntity.getName());
				if (productObj.getArticleMappings() != null && productObj.getArticleMappings().size() > 0) {
					productEntity.getArticleMappings().clear();
					for (ProductArticleMapping articleMapping : productObj.getArticleMappings()) {
						articleMapping.setProduct(productEntity);
						productEntity.getArticleMappings().add(articleMapping);
					}
				}
				if (productEntity.getStatus().equals(ProductStatus.SOLD.getStatusCode()))
					productEntity.setActive(false);
				productEntity = productRepository.save(productEntity);
			}
			return productEntity;
		}
		throw new WarehouseException("Invalid product details to update");
	}

}
