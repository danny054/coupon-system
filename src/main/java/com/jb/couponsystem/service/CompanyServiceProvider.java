package com.jb.couponsystem.service;

import com.jb.couponsystem.data.entity.Company;
import com.jb.couponsystem.data.entity.Coupon;
import com.jb.couponsystem.data.repo.CompanyRepository;
import com.jb.couponsystem.data.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceProvider implements CompanyService {

    private final CompanyRepository companyRepo;
    private final CouponRepository couponRepo;

    @Autowired
    public CompanyServiceProvider(CompanyRepository companyRepo, CouponRepository couponRepo) {
        this.companyRepo = companyRepo;
        this.couponRepo = couponRepo;
    }

    @Override
    public List<Coupon> findAllCouponsByCompanyId(long companyId) {
        return couponRepo.findAllByCompanyId(companyId);
    }

    @Override
    public Coupon addCoupon(Coupon coupon) {
        return couponRepo.save(coupon);
    }

    @Override
    public void deleteCoupon(long id) {
        couponRepo.deleteById(id);
    }

    @Override
    public Optional<Coupon> findCoupon(long id) {
        return couponRepo.findById(id);
    }

    @Override
    public Optional<Company> findCompanyById(long id) {
        return companyRepo.findById(id);
    }
}
