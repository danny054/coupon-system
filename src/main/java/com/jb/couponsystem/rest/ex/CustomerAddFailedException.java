package com.jb.couponsystem.rest.ex;

public class CustomerAddFailedException extends SystemMalfunctionException {
    public CustomerAddFailedException(String msg) {
        super(msg);
    }
}
