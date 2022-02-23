package com.appsdeveloperblog.estore.productsservice.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.productsservice.core.data.ProductEntity;
import com.appsdeveloperblog.estore.productsservice.core.data.ProductRepository;
import com.appsdeveloperblog.estore.productsservice.query.rest.ProductRestModel;

@Component
public class ProductQueryHandler {

	private final ProductRepository productRepository;
	
	@Autowired
	public ProductQueryHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@QueryHandler
	public List<ProductRestModel> findProducts(FindProductQuery query) {
		
		List<ProductRestModel> products = new ArrayList<>();
		
		List<ProductEntity> storedProducts = productRepository.findAll();
		for (ProductEntity productEntity : storedProducts) {
			ProductRestModel product = new ProductRestModel();
			BeanUtils.copyProperties(productEntity, product);
			products.add(product);
		}
		
		return products;
	}
}
