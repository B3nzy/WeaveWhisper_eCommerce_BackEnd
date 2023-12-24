package com.weavewhisper.dtos;

import com.weavewhisper.enums.UserType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponseDto {
	private String email;
	private UserType type;
	private String fullName;
	private String phoneNumber;
	private String address;
	private String brandName;
	private String panNumber;
}
