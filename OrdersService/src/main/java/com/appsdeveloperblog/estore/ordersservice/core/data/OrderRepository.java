package com.appsdeveloperblog.estore.ordersservice.core.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository <OrderEntity, String> {

}
