package com.appsdeveloperblog.estore.productsservice.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.productsservice.core.data.ProductLookupEntity;
import com.appsdeveloperblog.estore.productsservice.core.data.ProductLookupRepository;
import com.appsdeveloperblog.estore.productsservice.core.event.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventHandler {
	
	private final ProductLookupRepository repository;
	
	@Autowired
	public ProductLookupEventHandler(ProductLookupRepository repository) {
		this.repository = repository;
	}

	@EventHandler
	public void on(ProductCreatedEvent event) {
		ProductLookupEntity lookupEntity = new ProductLookupEntity(
				event.getProductId(), event.getTitle());
		repository.save(lookupEntity);
	}
}
