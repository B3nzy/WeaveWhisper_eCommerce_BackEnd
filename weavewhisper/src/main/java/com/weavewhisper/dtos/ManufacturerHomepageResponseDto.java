package com.weavewhisper.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ManufacturerHomepageResponseDto {

	private double totalEarning;

	private long productsTotalCurrentStock;
	private long productsSold;
	private long productsReturned;

	private long mensListing;
	private long womenListing;
	
	private long trackingSoldProductsCount;
	private long returnedProductsCount;
}
