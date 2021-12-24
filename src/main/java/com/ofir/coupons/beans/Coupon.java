package com.ofir.coupons.beans;

import com.ofir.coupons.enums.Category;
import com.ofir.coupons.utils.Utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "coupons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Coupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private Company company;
    
    @Column(name = "category_id")
    @Enumerated(EnumType.ORDINAL)
    private Category category;
    
    private String title;
    private String description;
    
    @Column(name = "start_date")
    private Date startDate;
    
    @Column(name = "end_date")
    private Date endDate;
    
    private int amount;
    private double price;
    private String image;

    //partial constructor
    public Coupon(Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    //partial constructor
    public Coupon(Company company, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this(category, title, description, startDate, endDate, amount, price, image);
        this.company = company;
    }
    
    @Override
    public String toString() {
        return String.format("Coupon- id: %d, company id: %d, category: %s, title: %s, description: %s, " +
                "start date: %s, end date: %s, amount: %d, price: %f, image: %s",
                id, company.getId(), Utils.convertEnumToString(category), title, description, startDate, endDate, amount, price, image);
    }

}
