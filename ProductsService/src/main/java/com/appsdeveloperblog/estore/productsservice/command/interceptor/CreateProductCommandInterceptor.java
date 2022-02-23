package com.appsdeveloperblog.estore.productsservice.command.interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.productsservice.command.CreateProductCommand;
import com.appsdeveloperblog.estore.productsservice.core.data.ProductLookupEntity;
import com.appsdeveloperblog.estore.productsservice.core.data.ProductLookupRepository;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
	
	private static final Logger LOGGER =
			LoggerFactory.getLogger(CreateProductCommandInterceptor.class);
	
	private final ProductLookupRepository repository;
	
	@Autowired
	public CreateProductCommandInterceptor(ProductLookupRepository repository) {
		this.repository = repository;
	}

	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {
		
		return (index, command) -> {
			
			LOGGER.info("Intercepted Command : {}", command.getPayloadType());
			
			// This is required as interceptor would intercept all Command objects dispatched.
			// For example, DeleteProductCommand, etc.
			if (CreateProductCommand.class.equals(command.getPayloadType())) {
				
				CreateProductCommand createProductCommand =
						(CreateProductCommand) command.getPayload();
				
				ProductLookupEntity lookupEntity = repository.findByProductIdOrTitle(
						createProductCommand.getProductId(), createProductCommand.getTitle());
				if (lookupEntity != null) {
					throw new IllegalStateException(
							String.format(
									"Product with productId %s or title %s already exists",
									createProductCommand.getProductId(),
									createProductCommand.getTitle()));
				}
			}
			
			return command;
		};
	}
}
