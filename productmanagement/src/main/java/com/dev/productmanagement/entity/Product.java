package com.dev.productmanagement.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
@DynamicInsert
@Entity
@Table(name="products")
public class Product implements Serializable {
	
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
		
	@NotEmpty
	private String name;
	
	@JsonProperty("contain_articles")
	@OneToMany( cascade = CascadeType.ALL, mappedBy="product", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ProductArticleMapping> articleMappings;
	
	@Column(name="status", columnDefinition = "varchar(50) default 'AVAILABLE'")
	private String status;
	@Column(columnDefinition = "boolean default true")
	private boolean active;
	
	public Product() {
		articleMappings = new ArrayList<ProductArticleMapping>();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public List<ProductArticleMapping> getArticleMappings() {
		return articleMappings;
	}


	public void setArticleMappings(List<ProductArticleMapping> articleMappings) {
		this.articleMappings = articleMappings;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}
	
	


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "[id="+this.id+", name="+this.name+", active = "+this.active+", articleMappings="+this.articleMappings+"]";
	}

}
