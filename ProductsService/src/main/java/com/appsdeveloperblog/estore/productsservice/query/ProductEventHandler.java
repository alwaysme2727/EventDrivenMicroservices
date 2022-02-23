package com.appsdeveloperblog.estore.productsservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.core.event.ProductReservedEvent;
import com.appsdeveloperblog.estore.productsservice.core.data.ProductEntity;
import com.appsdeveloperblog.estore.productsservice.core.data.ProductRepository;
import com.appsdeveloperblog.estore.productsservice.core.event.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventHandler.class);
	
	private final ProductRepository productRepository;
	
	@Autowired
	public ProductEventHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@EventHandler
	public void on(ProductCreatedEvent event) {
		
		ProductEntity entity = new ProductEntity();
		BeanUtils.copyProperties(event, entity);
		
		productRepository.save(entity);
		
		/*
		 * if (true) { throw new
		 * RuntimeException("Dummy exception thrown from Event Handler"); }
		 */
	}
	
	@EventHandler
	public void on(ProductReservedEvent event) {
		
		ProductEntity entity = productRepository.findByProductId(event.getProductId());
		entity.setQuantity(entity.getQuantity() - event.getQuantity());
		productRepository.save(entity);
		
		LOGGER.info("ProductReservedEvent is handled for orderId : {} and productId : {}",
				event.getOrderId(), event.getProductId());
	}
	
	@ExceptionHandler(resultType = Exception.class)
	public void handle(Exception e) throws Exception {
		throw e;
	}
}
