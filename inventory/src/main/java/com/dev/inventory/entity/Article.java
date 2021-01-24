package com.dev.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@DynamicInsert
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="inventory")
public class Article {
	
	@JsonProperty(value="art_id")
	@Positive(message = "art_id must be greater than 0")
	private Integer id;
	@NotEmpty(message = "Name cannot be empty")
	@Size(min = 3, max = 250, message 
    = "Name must be between 3 and 250 characters")
	private String name;
	@PositiveOrZero(message = "Stock value should be greater than 0")
	private Integer stock;	
	
	@Column(columnDefinition = "boolean default true")
	private Boolean active;
	
	public Article() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
		
	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "[art_id="+this.id+", name='"+this.name+"', stock= "+this.stock+"]";
	}

}
