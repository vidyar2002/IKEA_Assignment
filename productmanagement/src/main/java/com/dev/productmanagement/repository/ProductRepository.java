package com.dev.productmanagement.repository;

import java.util.List;

import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.productmanagement.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Where(clause = "active='TRUE'")
	public List<Product> findAll() ;
}
