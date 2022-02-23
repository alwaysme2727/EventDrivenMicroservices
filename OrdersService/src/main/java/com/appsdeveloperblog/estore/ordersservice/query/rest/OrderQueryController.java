package com.appsdeveloperblog.estore.ordersservice.query.rest;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.estore.ordersservice.query.FindOrderQuery;

@RestController
@RequestMapping("/orders")
public class OrderQueryController {

	@Autowired
	private QueryGateway queryGateway;

	@GetMapping
	public List<OrderRestModel> getOrders() {
		
		FindOrderQuery query = new FindOrderQuery();
		List<OrderRestModel> orders = queryGateway.query(
				query, ResponseTypes.multipleInstancesOf(OrderRestModel.class)).join();
		
		return orders;
	}
}
