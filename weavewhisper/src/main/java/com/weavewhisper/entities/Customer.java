package com.weavewhisper.entities;

import com.weavewhisper.enums.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@PrimaryKeyJoinColumn(name = "emp_id")
@Entity
@Table(name = "customers")
@Getter
@Setter
@ToString(callSuper = true, exclude = {})
@NoArgsConstructor
public class Customer extends BaseUser {
	@Column(length = 50, nullable = false)
	private String fullName;
	@Column(length = 10)
	private String phoneNumber;
	private String address;

	public Customer(String email, String password, UserType type, String fullName, String phoneNumber,
			String address) {
		super(email, password, type);
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

}
