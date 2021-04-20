package com.jb.couponsystem.rest;

import com.jb.couponsystem.data.entity.Company;
import com.jb.couponsystem.data.entity.Customer;
import com.jb.couponsystem.data.repo.CompanyRepository;
import com.jb.couponsystem.data.repo.CustomerRepository;
import com.jb.couponsystem.rest.ex.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientSystem {

    private final CustomerRepository customerRepo;
    private final CompanyRepository companyRepo;

    @Autowired
    public ClientSystem(CustomerRepository customerRepo, CompanyRepository companyRepo) {
        this.customerRepo = customerRepo;
        this.companyRepo = companyRepo;
    }

    public ClientSession createSession(String email, String password, String type) throws InvalidLoginException {
        switch (type) {
            case "admin":
                if ("admin@gmail.com".equals(email) && "1234".equals(password)) {
                    return ClientSession.create(1, "admin");
                }
                throw new InvalidLoginException("The email or password is incorrect... Try again!");
            case "company":
                Optional<Company> optCompany = companyRepo.findByEmailAndPassword(email, password);
                if (optCompany.isPresent()) {
                    return ClientSession.create(optCompany.get().getId(), "company");
                }
                throw new InvalidLoginException("The email or password is incorrect... Try again!");
            case "customer":
                Optional<Customer> optCustomer = customerRepo.findByEmailAndPassword(email, password);
                if (optCustomer.isPresent()) {
                    return ClientSession.create(optCustomer.get().getId(), "customer");
                }
                throw new InvalidLoginException("The email or password is incorrect... Try again!");
            default:
                throw new InvalidLoginException("Login type is not supported!");
        }
    }
}

