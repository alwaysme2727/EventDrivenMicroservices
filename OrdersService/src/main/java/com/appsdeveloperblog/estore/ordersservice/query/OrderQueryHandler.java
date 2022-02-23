package com.appsdeveloperblog.estore.ordersservice.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.ordersservice.core.data.OrderEntity;
import com.appsdeveloperblog.estore.ordersservice.core.data.OrderRepository;
import com.appsdeveloperblog.estore.ordersservice.query.rest.OrderRestModel;

@Component
public class OrderQueryHandler {

	private final OrderRepository orderRepository;
	
	@Autowired
	public OrderQueryHandler(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@QueryHandler
	public List<OrderRestModel> findOrders(FindOrderQuery query) {
		
		List<OrderRestModel> orders = new ArrayList<>();
		
		List<OrderEntity> storedOrders = orderRepository.findAll();
		for (OrderEntity orderEntity : storedOrders) {
			OrderRestModel order = new OrderRestModel();
			BeanUtils.copyProperties(orderEntity, order);
			orders.add(order);
		}
		
		return orders;
	}
}
