package com.weavewhisper.dtos;

import com.weavewhisper.enums.CategoryType;
import com.weavewhisper.enums.GenderType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartResponseDto {
	private Long id;
	private String name;
	private double actualPrice;
	private double sellingPrice;
	private GenderType gender;
	private CategoryType category;
	private String imageName;
	private boolean active;
	private int inventoryCount;
}
