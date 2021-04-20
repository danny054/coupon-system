package com.jb.couponsystem.data.repo;

import com.jb.couponsystem.data.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findAllByCompanyId(long companyId);

    @Query("select co from Coupon as co inner join co.customers as cu where cu.id = :customerId")
    List<Coupon> findCustomerCoupons(long customerId);

    @Query("select c from Coupon c where c not in " +
            "(select co from Coupon as co inner join co.customers as cu where cu.id = :customerId)")
    List<Coupon> findAllCouponsForSale(long customerId);

    @Query("select co from Coupon co where co.price < :price")
    List<Coupon> findAllCouponsLeesThan(double price);

    @Query("select co from Coupon co where co.endDate < :date")
    List<Coupon> findAllCouponsBeforeDate(LocalDate date);

    @Modifying
    @Transactional
    @Query("delete from Coupon co where co.endDate < :nowDate")
    void deleteAllExpiredCoupons(LocalDate nowDate);
}
