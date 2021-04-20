package com.jb.couponsystem.rest.controller;

import com.jb.couponsystem.data.entity.Company;
import com.jb.couponsystem.data.entity.Coupon;
import com.jb.couponsystem.rest.ClientSession;
import com.jb.couponsystem.rest.ex.CouponUpdateFailedException;
import com.jb.couponsystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CompanyController {

    private final CompanyService service;
    private final Map<String, ClientSession> tokensMap;

    @Autowired
    public CompanyController(CompanyService service, @Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.service = service;
        this.tokensMap = tokensMap;
    }

    private static int compareBySales(Coupon a, Coupon b) {
        return (b.getCustomers().size() - a.getCustomers().size());
    }

    @PostMapping("/coupons/add-coupon")
    public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"company".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Company> optCompany = service.findCompanyById(clientSession.getClientId());

        if (optCompany.isPresent()) {
            coupon.setId(0);
            coupon.setCompany(optCompany.get());

            return ResponseEntity.ok(service.addCoupon(coupon));
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/coupons/delete-coupon/{id}")
    public ResponseEntity deleteCoupon(@PathVariable long id, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"company".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Coupon> optCoupon = service.findCoupon(id);

        if (optCoupon.isPresent()) {

            if (optCoupon.get().getCompany() != null
                    && optCoupon.get().getCompany().getId() == clientSession.getClientId()) {
                service.deleteCoupon(id);

                return ResponseEntity.ok().build();
            }
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/coupons/update-coupon")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon, @RequestParam String token)
            throws CouponUpdateFailedException {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"company".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Coupon> optCoupon = service.findCoupon(coupon.getId());

        if (optCoupon.isPresent()) {
            Optional<Company> optCompany = service.findCompanyById(clientSession.getClientId());

            if (optCompany.isPresent()) {

                if (optCoupon.get().getCompany() != null
                        && optCoupon.get().getCompany().getId() == clientSession.getClientId()) {
                    coupon.setCompany(optCompany.get());
                    Coupon updateCoupon = service.addCoupon(coupon);

                    return ResponseEntity.ok(updateCoupon);
                }
                throw new CouponUpdateFailedException("This coupon does not belong to your company");
            }
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/coupons/companies")
    public ResponseEntity<List<Coupon>> getAllCompanyCoupons(@RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"company".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        List<Coupon> coupons = service.findAllCouponsByCompanyId(clientSession.getClientId());

        if (coupons != null && !coupons.isEmpty()) {
            coupons.sort((CompanyController::compareBySales));
            return ResponseEntity.ok(coupons);
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("companies/update")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"company".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Company> optCompany = service.findCompanyById(clientSession.getClientId());

        return optCompany.map(value -> ResponseEntity.ok(service.updateCompany(company))).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("companies/get-me")
    public ResponseEntity<Company> getCompany(@RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null || !"company".equals(clientSession.getType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        clientSession.access();

        Optional<Company> optCompany = service.findCompanyById(clientSession.getClientId());

        return optCompany.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
