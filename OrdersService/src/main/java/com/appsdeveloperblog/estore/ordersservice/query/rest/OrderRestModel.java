package com.appsdeveloperblog.estore.ordersservice.query.rest;

import com.appsdeveloperblog.estore.ordersservice.core.model.OrderStatus;

import lombok.Data;

@Data
public class OrderRestModel {

	private String orderId;
	private String productId;
	private int quantity;
	private String addressId;
	private String userId;
	private OrderStatus orderStatus;
}
