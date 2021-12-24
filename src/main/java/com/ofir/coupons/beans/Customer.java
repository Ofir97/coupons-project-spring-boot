package com.ofir.coupons.beans;

import com.ofir.coupons.utils.Utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private String email;
	private String password;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "customers_vs_coupons", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "coupon_id"))
	private List<Coupon> coupons = new ArrayList<>();

	// partial constructor
	public Customer(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	// partial constructor
	public Customer(String firstName, String lastName, String email, String password, List<Coupon> coupons) {
		this(firstName, lastName, email, password);
		this.coupons = coupons;
	}

	// partial constructor
	public Customer(int id, String firstName, String lastName, String email, String password) {
		this(firstName, lastName, email, password);
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer- id: %d, first name: %s, last name: %s, email: %s, " + "password: %s, coupons: %s", id,
				firstName, lastName, email, password, Utils.getCouponsAsStr(coupons));
	}

}
