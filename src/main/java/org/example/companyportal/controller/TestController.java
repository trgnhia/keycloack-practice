package org.example.companyportal.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/public")
    public String publicApi() {
        return "public";
    }
    @GetMapping("/me")
    public String me(Authentication authentication) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        return jwtAuth.getToken().getClaimAsString("preferred_username");
    }
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
