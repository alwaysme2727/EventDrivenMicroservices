package com.appsdeveloperblog.estore.ordersservice.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.appsdeveloperblog.estore.ordersservice.core.model.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateOrderCommand {

	@TargetAggregateIdentifier
	private final String orderId;
	
	private final String userId;
	private final String productId;
	private final int quantity;
	private final String addressId;
	private final OrderStatus orderStatus;
}
