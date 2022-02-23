package com.appsdeveloperblog.estore.productsservice.command;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.estore.core.command.ReserveProductCommand;
import com.appsdeveloperblog.estore.core.event.ProductReservedEvent;
import com.appsdeveloperblog.estore.productsservice.core.event.ProductCreatedEvent;

@Aggregate
public class ProductAggregate {
	
	@AggregateIdentifier
	private String productId;
	
	private String title;
	private BigDecimal price;
	private Integer quantity;

	public ProductAggregate() {
		
	}
	
	@CommandHandler
	public ProductAggregate(CreateProductCommand command) {
		
		// Validate Create Product Command.
		if (command.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException(
					"Price cannot be less than or equal to zero");
		}
		if (StringUtils.isBlank(command.getTitle())) {
			throw new IllegalArgumentException("Title cannot be empty");
		}
		
		ProductCreatedEvent event = new ProductCreatedEvent();
		BeanUtils.copyProperties(command, event);		// Another way to populate properties.
		
		AggregateLifecycle.apply(event);
		//throw new RuntimeException("Dummy exception thrown from Command Handler.");
	}
	
	@CommandHandler
	public void handle(ReserveProductCommand reserveProductCommand) {
		
		if (quantity < reserveProductCommand.getQuantity()) {
			throw new IllegalArgumentException(
					"Insufficient number of items in stock");
		}
		
		ProductReservedEvent productReservedEvent = ProductReservedEvent.builder()
				.productId(reserveProductCommand.getProductId())
				.quantity(reserveProductCommand.getQuantity())
				.orderId(reserveProductCommand.getOrderId())
				.userId(reserveProductCommand.getUserId())
				.build();
		AggregateLifecycle.apply(productReservedEvent);
	}
	
	@EventSourcingHandler
	public void on(ProductCreatedEvent event) {
		this.productId = event.getProductId();
		this.title = event.getTitle();
		this.price = event.getPrice();
		this.quantity = event.getQuantity();
	}
	
	@EventSourcingHandler
	public void on(ProductReservedEvent event) {
		// Update quantity only. Other fields are not affected by this event.
		this.quantity -= event.getQuantity();
	}
}
