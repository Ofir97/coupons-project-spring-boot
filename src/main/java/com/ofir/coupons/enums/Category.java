package com.ofir.coupons.enums;

public enum Category {
    NA(0), FOOD(1), ELECTRICITY(2), RESTAURANT(3), VACATION(4), SPA(5), TECHNOLOGY(6);

    private int id;

    Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
