package com.appsdeveloperblog.estore.ordersservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.ordersservice.core.data.OrderCreatedEvent;
import com.appsdeveloperblog.estore.ordersservice.core.data.OrderEntity;
import com.appsdeveloperblog.estore.ordersservice.core.data.OrderRepository;

@Component
@ProcessingGroup("order-group")
public class OrderEventHandler {

	private final OrderRepository repository;
	
	@Autowired
	public OrderEventHandler(OrderRepository repository) {
		this.repository = repository;
	}
	
	@EventHandler
	public void on(OrderCreatedEvent event) {
		OrderEntity entity = new OrderEntity();
		BeanUtils.copyProperties(event, entity);
		repository.save(entity);
	}
}
