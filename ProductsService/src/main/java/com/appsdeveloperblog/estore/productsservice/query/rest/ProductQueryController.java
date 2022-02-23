package com.appsdeveloperblog.estore.productsservice.query.rest;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.estore.productsservice.query.FindProductQuery;

@RestController
@RequestMapping("/products")
public class ProductQueryController {
	
	@Autowired
	private QueryGateway queryGateway;

	@GetMapping
	public List<ProductRestModel> getProducts() {
		
		FindProductQuery query = new FindProductQuery();
		List<ProductRestModel> products = queryGateway.query(
				query, ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
		
		return products;
	}
}
