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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Coupon> coupons = new ArrayList<>();

    //partial constructor
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //partial constructor
    public Company(String name, String email, String password, List<Coupon> coupons) {
        this(name, email, password);
        this.coupons = coupons;
    }

    //partial constructor
    public Company(int id, String name, String email, String password) {
        this(name, email, password);
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Company- id: %d, name: %s, email: %s, password: %s, coupons: %s",
                id, name, email, password, Utils.getCouponsAsStr(coupons));
    }

}
