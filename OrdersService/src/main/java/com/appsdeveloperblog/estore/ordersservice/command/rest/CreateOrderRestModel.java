package com.appsdeveloperblog.estore.ordersservice.command.rest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateOrderRestModel {

	@NotBlank(message = "Order Product Id is required")
	private String productId;
	
	@Min(value = 1, message = "Quantity cannot be lower than 1")
	@Max(value = 5, message = "Quantity cannot be larger than 5")
	private int quantity;
	
	@NotBlank(message = "Order Address Id is required")
	private String addressId;
}
