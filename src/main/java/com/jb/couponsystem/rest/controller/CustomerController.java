package com.jb.couponsystem.rest.controller;

import com.jb.couponsystem.data.entity.Coupon;
import com.jb.couponsystem.data.entity.Customer;
import com.jb.couponsystem.rest.ClientSession;
import com.jb.couponsystem.rest.ex.CouponAlreadyPurchaseException;
import com.jb.couponsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService service;
    private final Map<String, ClientSession> tokensMap;

    @Autowired
    public CustomerController(CustomerService service, @Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.service = service;
        this.tokensMap = tokensMap;
    }

    @GetMapping("/coupons/customers")
    public ResponseEntity<List<Coupon>> getCustomerCoupons(@RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"customer".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Coupon> coupons = service.findAllCustomerCoupons(clientSession.getClientId());

        if (coupons != null && !coupons.isEmpty()) {
            return ResponseEntity.ok(coupons);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/coupons/for-sale")
    public ResponseEntity<List<Coupon>> getCouponsForSale(@RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"customer".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Coupon> coupons = service.findAllCouponsForSale(clientSession.getClientId());

        if (coupons != null && !coupons.isEmpty()) {
            return ResponseEntity.ok(coupons);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/coupons/purchase/{couponId}")
    public ResponseEntity<Coupon> purchaseCoupon(@PathVariable long couponId, @RequestParam String token)
            throws CouponAlreadyPurchaseException {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"customer".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Coupon> optCoupon = service.findCoupon(couponId);

        if (!optCoupon.isPresent() || optCoupon.get().getAmount() <= 0) {
            return ResponseEntity.noContent().build();
        }

        Set<Customer> customers = optCoupon.get().getCustomers();
        Optional<Customer> optCustomer = service.findCustomer(clientSession.getClientId());

        if (optCustomer.isPresent()) {
            boolean add = customers.add(optCustomer.get());

            if (!add) {
                throw new CouponAlreadyPurchaseException("You have already purchased this coupon");
            }

            Coupon newCoupon = service.purchaseCoupon(optCoupon.get());

            return ResponseEntity.ok(newCoupon);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/coupons/lees-than/{price}")
    public ResponseEntity<List<Coupon>> getAllCouponsLessThan(@PathVariable double price, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"customer".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Coupon> coupons = service.findAllCouponsLeesThan(price);

        if (coupons != null && !coupons.isEmpty()) {
            return ResponseEntity.ok(coupons);
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/customers/update")
    public ResponseEntity<Customer> updateCustomer(@RequestParam String token, @RequestBody Customer customer) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"customer".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Customer> optCustomer = service.findCustomer(customer.getId());

        if (optCustomer.isPresent()) {
            customer.setCoupons(optCustomer.get().getCoupons());
            return ResponseEntity.ok(service.updateCustomer(customer));
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers/get-me")
    public ResponseEntity<Customer> getCustomer(@RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"customer".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Customer> optCustomer = service.findCustomer(clientSession.getClientId());

        return optCustomer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}

