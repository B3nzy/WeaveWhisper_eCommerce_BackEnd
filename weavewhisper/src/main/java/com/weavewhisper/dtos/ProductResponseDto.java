package com.weavewhisper.dtos;

import java.util.ArrayList;
import java.util.Set;

import com.weavewhisper.enums.CategoryType;
import com.weavewhisper.enums.GenderType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponseDto {
	private Long id;
	private String name;
	private String description;
	private double actualPrice;
	private double sellingPrice;
	private int inventoryCount;
	private String brandName;
	private GenderType gender;
	private CategoryType category;
//	private ArrayList<String> imageUrls;
	private Set<String> colors;
	private Set<String> sizes;
}
