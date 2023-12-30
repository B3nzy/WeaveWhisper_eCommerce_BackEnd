package com.weavewhisper.entities;

import java.util.ArrayList;
import java.util.List;

import com.weavewhisper.enums.UserType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@PrimaryKeyJoinColumn(name = "user_id")
@Entity
@Table(name = "manufaturers")
@Getter
@Setter
@ToString(callSuper = true, exclude = {})
@NoArgsConstructor
public class Manufacturer extends BaseUser {
	@Column(length = 50, nullable = false, unique = true)
	private String brandName;
	@Column(length = 50, nullable = false, unique = true)
	private String panNumber;

	@OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, orphanRemoval = false)
	List<Product> productList = new ArrayList<>();

	public Manufacturer(String email, String password, UserType type, String brandName, String panNumber) {
		super(email, password, type);
		this.brandName = brandName;
		this.panNumber = panNumber;
	}
	
	public void addProduct(Product product) {
		productList.add(product);
		product.setManufacturer(this);
	}
	
	public void removeProduct(Product product) {
		productList.remove(product);
		product.setManufacturer(null);
	}

}
