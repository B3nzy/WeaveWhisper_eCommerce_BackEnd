package com.weavewhisper.entities;

import com.weavewhisper.enums.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "users")
public class BaseUser extends BaseEntity {
	@Column(length = 50, nullable = false, unique = true)
	private String email;
	@Column(length = 20, nullable = false)
	private String password;
	@Column(length = 50)
	@Enumerated(EnumType.STRING)
	private UserType type;

	private boolean verified;

	public BaseUser(String email, String password, UserType type) {
		super();
		this.email = email;
		this.password = password;
		this.type = type;
		// Need to change default verified value to false
		this.verified = true;
		
	}

}
