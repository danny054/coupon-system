package com.jb.couponsystem.rest.ex;

public class CompanyUpdateFailedException extends SystemMalfunctionException {
    public CompanyUpdateFailedException(String msg) {
        super(msg);
    }
}
