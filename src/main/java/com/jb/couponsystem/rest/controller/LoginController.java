package com.jb.couponsystem.rest.controller;

import com.jb.couponsystem.rest.ClientSession;
import com.jb.couponsystem.rest.ClientSystem;
import com.jb.couponsystem.rest.ex.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LoginController {
    private static final int LENGTH_TOKEN = 15;

    private ClientSystem clientSystem;
    private Map<String, ClientSession> tokensMap;

    @Autowired
    public LoginController(ClientSystem clientSystem, @Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.clientSystem = clientSystem;
        this.tokensMap = tokensMap;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email,
                                        @RequestParam String password,
                                        @RequestParam String type)
            throws InvalidLoginException {
        ClientSession session = clientSystem.createSession(email, password, type);
        String token = generateToken();
        tokensMap.put(token, session);

        return ResponseEntity.ok(token);
    }

    private String generateToken() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, LENGTH_TOKEN);
    }
}
