package com.jb.couponsystem.rest.controller;

import com.jb.couponsystem.data.entity.Company;
import com.jb.couponsystem.data.entity.Coupon;
import com.jb.couponsystem.data.entity.Customer;
import com.jb.couponsystem.rest.ClientSession;
import com.jb.couponsystem.rest.ex.CompanyAddFailedException;
import com.jb.couponsystem.rest.ex.CompanyUpdateFailedException;
import com.jb.couponsystem.rest.ex.CustomerAddFailedException;
import com.jb.couponsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AdminController {

    private final AdminService service;
    private final Map<String, ClientSession> tokensMap;

    @Autowired
    public AdminController(AdminService service, @Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.service = service;
        this.tokensMap = tokensMap;
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(@RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Coupon> coupons = service.findAllCoupons();

        if (coupons != null && !coupons.isEmpty()) {
            return ResponseEntity.ok(coupons);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompanies(@RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Company> companies = service.findAllCompanies();

        if (companies != null && !companies.isEmpty()) {
            return ResponseEntity.ok(companies);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Customer> customers = service.findAllCustomers();

        if (customers != null && !customers.isEmpty()) {
            return ResponseEntity.ok(customers);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/coupons/customers/{id}")
    public ResponseEntity<List<Coupon>> getCustomerCoupons(@PathVariable long id, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Coupon> coupons = service.findAllCustomerCoupons(id);

        if (coupons != null && !coupons.isEmpty()) {
            return ResponseEntity.ok(coupons);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/coupons/companies/{id}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable long id, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Coupon> coupons = service.findAllCompanyCoupons(id);

        if (coupons != null && !coupons.isEmpty()) {
            return ResponseEntity.ok(coupons);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/coupons/{id}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable long id, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Coupon> optCoupon = service.findCouponById(id);

        return optCoupon.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable long id, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Company> optCompany = service.findCompanyById(id);

        return optCompany.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable long id, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Customer> optCustomer = service.findCustomerById(id);

        return optCustomer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("/coupons/{id}")
    public ResponseEntity<Coupon> deleteCoupon(@PathVariable long id, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Coupon> optCoupon = service.findCouponById(id);

        if (optCoupon.isPresent()) {
            service.deleteCoupon(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Company> deleteCompany(@PathVariable long id, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Company> optCompany = service.findCompanyById(id);

        if (optCompany.isPresent()) {
            service.deleteCompany(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable long id, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Customer> optCustomer = service.findCustomerById(id);

        if (optCustomer.isPresent()) {
            service.deleteCustomer(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> addCompany(@RequestBody Company company, @RequestParam String token) throws CompanyAddFailedException {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Company> companies = service.findAllCompanies();

        if (!companies.contains(company)) {
            company.setId(0);
            return ResponseEntity.ok(service.addCompany(company));
        }
        throw new CompanyAddFailedException("The company name has already been taken...");
    }

    @PostMapping("/coupons/{companyId}")
    public ResponseEntity<Coupon> addCoupon(@PathVariable long companyId,
                                            @RequestBody Coupon coupon,
                                            @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Company> optCompany = service.findCompanyById(companyId);

        if (optCompany.isPresent()) {
            coupon.setCompany(optCompany.get());
            return ResponseEntity.ok(service.addCoupon(coupon));
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer, @RequestParam String token)
            throws CustomerAddFailedException {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Customer> customers = service.findAllCustomers();

        if (!customers.contains(customer)) {
            customer.setId(0);
            return ResponseEntity.ok(service.addCustomer(customer));
        }
        throw new CustomerAddFailedException("This email has already been taken...");
    }

    @PatchMapping("/companies")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company, @RequestParam String token)
            throws CompanyUpdateFailedException {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Company> optCompany = service.findCompanyById(company.getId());

        if (optCompany.isPresent()) {

            if (optCompany.get().getName().equals(company.getName())) {
                return ResponseEntity.ok(service.addCompany(company));
            }
            throw new CompanyUpdateFailedException("You can not change the company name...");
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/customers")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Customer> optCustomer = service.findCustomerById(customer.getId());

        if (optCustomer.isPresent()) {
            return ResponseEntity.ok(service.addCustomer(customer));
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/coupons/before-date")
    public ResponseEntity<List<Coupon>> getAllCouponsBeforeDate
            (@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
             @RequestParam LocalDate date,
             @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"admin".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Coupon> coupons = service.findAllCouponsBeforeDate(date);

        if (coupons != null && !coupons.isEmpty()) {
            return ResponseEntity.ok(coupons);
        }

        return ResponseEntity.noContent().build();
    }
}
