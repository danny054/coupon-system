package com.jb.couponsystem.rest.ex;

public class CouponAlreadyPurchaseException extends SystemMalfunctionException {
    public CouponAlreadyPurchaseException(String msg) {
        super(msg);
    }
}
