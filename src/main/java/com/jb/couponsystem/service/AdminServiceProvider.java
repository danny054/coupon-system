package com.jb.couponsystem.service;

import com.jb.couponsystem.data.entity.Company;
import com.jb.couponsystem.data.entity.Coupon;
import com.jb.couponsystem.data.entity.Customer;
import com.jb.couponsystem.data.repo.CompanyRepository;
import com.jb.couponsystem.data.repo.CouponRepository;
import com.jb.couponsystem.data.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceProvider implements AdminService {

    private final CouponRepository couponRepo;
    private final CustomerRepository customerRepo;
    private final CompanyRepository companyRepo;

    @Autowired
    public AdminServiceProvider(CouponRepository couponRepo,
                                CustomerRepository customerRepo,
                                CompanyRepository companyRepo) {
        this.couponRepo = couponRepo;
        this.customerRepo = customerRepo;
        this.companyRepo = companyRepo;
    }

    @Override
    public List<Coupon> findAllCoupons() {
        return couponRepo.findAll();
    }

    @Override
    public List<Company> findAllCompanies() {
        return companyRepo.findAll();
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public List<Coupon> findAllCustomerCoupons(long customerId) {
        return couponRepo.findCustomerCoupons(customerId);
    }

    @Override
    public List<Coupon> findAllCompanyCoupons(long companyId) {
        return couponRepo.findAllByCompanyId(companyId);
    }

    @Override
    public Optional<Coupon> findCouponById(long id) {
        return couponRepo.findById(id);
    }

    @Override
    public Optional<Company> findCompanyById(long id) {
        return companyRepo.findById(id);
    }

    @Override
    public Optional<Customer> findCustomerById(long id) {
        return customerRepo.findById(id);
    }

    @Override
    public void deleteCoupon(long id) {
        couponRepo.deleteById(id);
    }

    @Override
    public void deleteCompany(long id) {
        companyRepo.deleteById(id);
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepo.deleteById(id);
    }

    @Override
    public Company addCompany(Company company) {
        return companyRepo.save(company);
    }

    @Override
    public Coupon addCoupon(Coupon coupon) {
        coupon.setId(0);
        return couponRepo.save(coupon);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public List<Coupon> findAllCouponsBeforeDate(LocalDate date) {
        return couponRepo.findAllCouponsBeforeDate(date);
    }
}
