package com.jb.couponsystem.service;

import com.jb.couponsystem.data.entity.Coupon;
import com.jb.couponsystem.data.entity.Customer;
import com.jb.couponsystem.data.repo.CouponRepository;
import com.jb.couponsystem.data.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceProvider implements CustomerService {

    private final CustomerRepository customerRepo;
    private final CouponRepository couponRepo;

    @Autowired
    public CustomerServiceProvider(CustomerRepository customerRepo, CouponRepository couponRepo) {
        this.customerRepo = customerRepo;
        this.couponRepo = couponRepo;
    }

    @Override
    public List<Coupon> findAllCustomerCoupons(long id) {
        return couponRepo.findCustomerCoupons(id);
    }

    @Override
    public List<Coupon> findAllCouponsForSale(long id) {
        return couponRepo.findAllCouponsForSale(id);
    }

    @Override
    public Coupon purchaseCoupon(Coupon coupon) {
        coupon.setAmount(coupon.getAmount() - 1);
        return couponRepo.save(coupon);
    }

    @Override
    public Optional<Coupon> findCoupon(long id) {
        return couponRepo.findById(id);
    }

    @Override
    public Optional<Customer> findCustomer(long id) {
        return customerRepo.findById(id);
    }

    @Override
    public List<Coupon> findAllCouponsLeesThan(double price) {
        return couponRepo.findAllCouponsLeesThan(price);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepo.save(customer);
    }
}
