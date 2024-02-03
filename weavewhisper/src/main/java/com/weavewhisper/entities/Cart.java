package com.weavewhisper.entities;

import java.time.LocalDateTime;

import com.weavewhisper.enums.ColorType;
import com.weavewhisper.enums.SizeType;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString(callSuper = true, exclude = {})
@NoArgsConstructor
public class Cart extends BaseEntity {

	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false, unique = false)
	private Product productRef;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false, unique = false)
	private Customer customerRef;
	
	private ColorType color;
	
	private SizeType size;

}
