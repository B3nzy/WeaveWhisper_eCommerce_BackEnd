package com.weavewhisper.entities;

import java.util.*;
import org.hibernate.resource.beans.internal.FallbackBeanInstanceProducer;

import com.weavewhisper.enums.CategoryType;
import com.weavewhisper.enums.GenderType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 *  {
    name: 'Allen Solly',
    description: 'Best Shirt ever!!',
    actualPrice: 2500,
    sellingPrice: 2400,
    inventoryCount: 10,
    category: 'SHIRT'
    gender: 'MEN',
    imageUrls: [],
    colors: [ 'BLUE', 'RED', 'GREEN', 'YELLOW' ],
    sizes: [ 'S', 'M' ],
    
  }
 * 
 */

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = { "sizeMap", "colorMap" })
public class Product extends BaseEntity {

	@Column(nullable = false)
	private String name;
	@Column(nullable = false, length = 1000)
	private String description;
	@Column(nullable = false)
	private Double actualPrice;
	@Column(nullable = false)
	private Double sellingPrice;
	@Column(nullable = false)
	private int inventoryCount;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private GenderType gender;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CategoryType category;

	@OneToMany(mappedBy = "productRef", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductSize> sizeMap = new HashSet<>();

	@OneToMany(mappedBy = "productRef", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductColor> colorMap = new HashSet<>();

	public Product(String name, String description, Double actualPrice, Double sellingPrice, GenderType gender,
			CategoryType category) {
		super();
		this.name = name;
		this.description = description;
		this.actualPrice = actualPrice;
		this.sellingPrice = sellingPrice;
		this.gender = gender;
		this.category = category;
	}

	public void addSize(ProductSize size) {
		sizeMap.add(size);
		size.setProductRef(this);
	}

	public void removeSize(ProductSize size) {
		sizeMap.remove(size);
		size.setProductRef(null);
	}

	public void addColor(ProductColor color) {
		colorMap.add(color);
		color.setProductRef(this);
	}

	public void removeColor(ProductColor color) {
		colorMap.remove(color);
		color.setProductRef(null);
	}

}
