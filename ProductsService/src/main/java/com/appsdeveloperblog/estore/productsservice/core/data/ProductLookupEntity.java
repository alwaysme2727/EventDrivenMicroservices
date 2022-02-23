package com.appsdeveloperblog.estore.productsservice.core.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_lookup")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductLookupEntity implements Serializable {

	private static final long serialVersionUID = -4335846183840964398L;

	@Id
	private String productId;
	
	@Column(unique = true)
	private String title;
}
