package com.jb.couponsystem.service;

import com.jb.couponsystem.data.entity.Company;
import com.jb.couponsystem.data.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    List<Coupon> findAllCouponsByCompanyId(long id);

    Coupon addCoupon(Coupon coupon);

    void deleteCoupon(long id);

    Optional<Coupon> findCoupon(long id);

    Optional<Company> findCompanyById(long id);

    Company updateCompany(Company company);
}
