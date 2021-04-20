package com.jb.couponsystem.rest.cleanup;

import com.jb.couponsystem.data.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CleanupExpiredCoupons {
    private CouponRepository couponRepo;

    @Autowired
    private CleanupExpiredCoupons(CouponRepository couponRepo) {
        this.couponRepo = couponRepo;
    }

    @Scheduled(fixedRateString = "${day}")
    private void cleanUpExpiredCoupons() {
        couponRepo.deleteAllExpiredCoupons(LocalDate.now());
    }
}
