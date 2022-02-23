package com.appsdeveloperblog.estore.ordersservice.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.estore.ordersservice.core.data.OrderCreatedEvent;
import com.appsdeveloperblog.estore.ordersservice.core.model.OrderStatus;

@Aggregate
public class OrderAggregate {

	@AggregateIdentifier
	private String orderId;
	
	private String productId;
	private String userId;
	private int quantity;
	private String addressId;
	private OrderStatus orderStatus;
	
	public OrderAggregate() {
		
	}
	
	@CommandHandler
	public OrderAggregate(CreateOrderCommand command) {   
		OrderCreatedEvent event = new OrderCreatedEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(OrderCreatedEvent event) {
		this.orderId = event.getOrderId();
		this.productId = event.getProductId();
		this.userId = event.getUserId();
		this.addressId = event.getAddressId();
		this.quantity = event.getQuantity();
		this.orderStatus = event.getOrderStatus();
	}
}
