package com.appsdeveloperblog.estore.ordersservice.saga;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.appsdeveloperblog.estore.core.command.ReserveProductCommand;
import com.appsdeveloperblog.estore.core.event.ProductReservedEvent;
import com.appsdeveloperblog.estore.ordersservice.core.data.OrderCreatedEvent;

@Saga
public class OrderSaga {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);

	@Autowired
	private transient CommandGateway commandGateway;
	
	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderCreatedEvent orderCreatedEvent) {
		
		ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
				.orderId(orderCreatedEvent.getOrderId())
				.productId(orderCreatedEvent.getProductId())
				.quantity(orderCreatedEvent.getQuantity())
				.userId(orderCreatedEvent.getUserId())
				.build();
		LOGGER.info("OrderCreatedEvent handled for orderId : {} and productId : {}",
				reserveProductCommand.getOrderId(), reserveProductCommand.getProductId());
		
		commandGateway.send(
				reserveProductCommand,
				new CommandCallback<ReserveProductCommand, Object>() {
					@Override
					public void onResult(
							CommandMessage<? extends ReserveProductCommand> commandMessage,
							CommandResultMessage<? extends Object> commandResultMessage) {
						if (commandResultMessage.isExceptional()) {
							// Start a compensating transaction.
						}
					}
				}
		);
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ProductReservedEvent productReservedEvent) {
		// Process user payment (next section).
		LOGGER.info("ProductReservedEvent is invoked for orderId : {} and productId : {}",
				productReservedEvent.getOrderId(), productReservedEvent.getProductId());
	}
}
