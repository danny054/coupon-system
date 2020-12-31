package com.jb.couponsystem.service;

import com.jb.couponsystem.data.entity.Coupon;
import com.jb.couponsystem.data.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Coupon> findAllCustomerCoupons(long id);

    List<Coupon> findAllCouponsForSale(long id);

    Coupon purchaseCoupon(Coupon coupon);

    Optional<Coupon> findCoupon(long id);

    Optional<Customer> findCustomer(long id);

    List<Coupon> findAllCouponsLeesThan(double price);
}
