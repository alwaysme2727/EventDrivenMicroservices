package com.appsdeveloperblog.estore.ordersservice.command.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.estore.ordersservice.core.data.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderTestController {

	@Autowired
	private OrderRepository orderRepository;
	
	@DeleteMapping
	public void deleteAllProducts() {
		orderRepository.deleteAll();
	}
}
