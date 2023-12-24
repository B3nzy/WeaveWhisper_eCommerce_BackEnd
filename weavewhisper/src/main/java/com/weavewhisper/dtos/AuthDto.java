package com.weavewhisper.dtos;

import com.weavewhisper.enums.UserType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthDto {
	private String email;
	private String password;
//	private UserType type;
}
