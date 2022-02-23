package com.appsdeveloperblog.estore.ordersservice.command.rest;

import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.estore.ordersservice.command.CreateOrderCommand;
import com.appsdeveloperblog.estore.ordersservice.core.model.OrderStatus;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

	private final CommandGateway commandGateway;
	
	@Autowired
	public OrderCommandController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}
	
	@PostMapping
	public String createOrder(
			@Valid @RequestBody CreateOrderRestModel model) {
		
		// Hard-coded, no significance.
		String userId = "27b95829-4f3f-4ddf-8983-151ba010e35b";
		
		CreateOrderCommand command =
				CreateOrderCommand.builder()
				.addressId(model.getAddressId())
				.productId(model.getProductId())
				.userId(userId)
				.quantity(model.getQuantity())
				.orderId(UUID.randomUUID().toString())
				.orderStatus(OrderStatus.CREATED)
				.build();
		return commandGateway.sendAndWait(command);
	}
}
