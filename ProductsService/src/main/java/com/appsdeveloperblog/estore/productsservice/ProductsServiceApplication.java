package com.appsdeveloperblog.estore.productsservice;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

import com.appsdeveloperblog.estore.productsservice.command.interceptor.CreateProductCommandInterceptor;
import com.appsdeveloperblog.estore.productsservice.core.errorhandling.ProductServiceEventErrorHandler;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}

	@Autowired
	public void registerMessageInterceptors(
			ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(
				context.getBean(CreateProductCommandInterceptor.class));
	}
	
	@Autowired
	public void configure(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler(
				"product-group", config -> new ProductServiceEventErrorHandler());
	}
}
