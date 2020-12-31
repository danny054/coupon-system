package com.jb.couponsystem.rest.ex;

public class CouponUpdateFailedException extends SystemMalfunctionException {
    public CouponUpdateFailedException(String msg) {
        super(msg);
    }
}
