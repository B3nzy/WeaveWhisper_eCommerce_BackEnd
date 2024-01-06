package com.weavewhisper.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewResponseDto {
	private Long id;
	private int rating;
	private String review;
	private String customerFullName;
}
