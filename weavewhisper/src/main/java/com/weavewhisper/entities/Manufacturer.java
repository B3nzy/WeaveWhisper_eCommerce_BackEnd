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
@Table(name = "manufaturers")
@Getter
@Setter
@ToString(callSuper = true, exclude = {})
@NoArgsConstructor
public class Manufacturer extends BaseUser {
	@Column(length = 50, nullable = false, unique = true)
	private String brandName;
	@Column(length = 50, nullable = false, unique = true)
	private String panNumber;

	public Manufacturer(String email, String password, UserType type, String brandName, String panNumber) {
		super(email, password, type);
		this.brandName = brandName;
		this.panNumber = panNumber;
	}

}
