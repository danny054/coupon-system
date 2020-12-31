package com.jb.couponsystem.service;

import com.jb.couponsystem.data.entity.Company;
import com.jb.couponsystem.data.entity.Coupon;
import com.jb.couponsystem.data.entity.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Coupon> findAllCoupons();

    List<Company> findAllCompanies();

    List<Customer> findAllCustomers();

    List<Coupon> findAllCustomerCoupons(long id);

    List<Coupon> findAllCompanyCoupons(long id);

    Optional<Coupon> findCouponById(long id);

    Optional<Company> findCompanyById(long id);

    Optional<Customer> findCustomerById(long id);

    void deleteCoupon(long id);

    void deleteCompany(long id);

    void deleteCustomer(long id);

    Company addCompany(Company company);

    Coupon addCoupon(Coupon coupon);

    Customer addCustomer(Customer customer);

    List<Coupon> findAllCouponsBeforeDate(LocalDate date);
}
