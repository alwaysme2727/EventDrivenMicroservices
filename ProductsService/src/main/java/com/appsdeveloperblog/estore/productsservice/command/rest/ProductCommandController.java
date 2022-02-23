package com.appsdeveloperblog.estore.productsservice.command.rest;

import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.estore.productsservice.command.CreateProductCommand;

@RestController
@RequestMapping("/products")
public class ProductCommandController {
	
	private final Environment env;
	private final CommandGateway commandGateway;
	
	@Autowired
	public ProductCommandController(
			Environment env, CommandGateway commandGateway) {
		this.env = env;
		this.commandGateway = commandGateway;
	}

	@PostMapping
	public String createProduct(
			@Valid @RequestBody CreateProductRestModel model) {
		
		CreateProductCommand command =
				CreateProductCommand.builder()
					.price(model.getPrice())
					.quantity(model.getQuantity())
					.title(model.getTitle())
					.productId(UUID.randomUUID().toString())
					.build();
		
		String returnValue = commandGateway.sendAndWait(command);
		return returnValue;
	}
	
	/*
	 * @GetMapping public String getProduct() { return "HTTP GET at " +
	 * env.getProperty("local.server.port"); }
	 * 
	 * @PutMapping public String updateProduct() { return "HTTP PUT"; }
	 * 
	 * @DeleteMapping public String deleteProduct() { return "HTTP DELETE"; }
	 */
}
