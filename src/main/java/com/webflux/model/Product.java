package com.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used for Redis Demo
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Product {

	private String id;

	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Product(String id, String name) {
		this.id = id;
		this.name = name;
	}

}