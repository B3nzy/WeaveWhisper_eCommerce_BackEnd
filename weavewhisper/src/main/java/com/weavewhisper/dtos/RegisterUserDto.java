package com.weavewhisper.dtos;

import com.weavewhisper.enums.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterUserDto {
	private String email;
	private String password;
	private UserType type;
	private String fullName;
	private String phoneNumber;
	private String address;
	private String brandName;
	private String panNumber;
}
