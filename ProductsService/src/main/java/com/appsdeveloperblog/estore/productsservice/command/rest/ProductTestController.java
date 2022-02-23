package com.appsdeveloperblog.estore.productsservice.command.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.estore.productsservice.core.data.ProductLookupRepository;
import com.appsdeveloperblog.estore.productsservice.core.data.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductTestController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductLookupRepository productLookupRepository;
	
	@DeleteMapping
	public void deleteAllProducts() {
		productLookupRepository.deleteAll();
		productRepository.deleteAll();
	}
}
