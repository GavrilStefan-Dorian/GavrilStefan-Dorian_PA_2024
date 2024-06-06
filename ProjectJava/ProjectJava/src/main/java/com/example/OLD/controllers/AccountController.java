//package com.example.OLD.controllers;
//
//import com.example.OLD.entities.AppUser;
//import com.example.OLD.entities.LoginDto;
//import com.example.OLD.entities.RegisterDto;
//import com.example.OLD.repositories.AppUserRepositoryJPA;
//import com.nimbusds.jose.jwk.source.ImmutableSecret;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//import org.springframework.security.oauth2.jwt.JwsHeader;
//import org.springframework.security.oauth2.jwt.JwtClaimsSet;
//import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.Instant;
//import java.util.HashMap;
//
//@RestController
//@RequestMapping("/account")
//public class AccountController {
//    @Value("${security.jwt.secret-key}")
//    private String jwtSecretKey;
//
//    @Value("${security.jwt.issuer")
//    private String jwtIssuer;
//
//    @Autowired
//    private AppUserRepositoryJPA appUserRepositoryJPA;
//
//    @Autowired
//    private AuthenticationManager authManager;
//
//
//
//    @PostMapping(value="/register", consumes="application/json")
//    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto, BindingResult result){
//        if(result.hasErrors()) {
//            var errorsList = result.getAllErrors();
//            var errorsMap = new HashMap<String, String>();
//
//            for(ObjectError error : errorsList) {
//                errorsMap.put(((FieldError) error).getField(), ((FieldError) error).getDefaultMessage());
//            }
//
//            return ResponseEntity.badRequest().body(errorsMap);
//        }
//
//        var bCryptEncoder = new BCryptPasswordEncoder();
//
//        AppUser appUser = new AppUser();
//        appUser.setEmail(registerDto.getEmail());
//        appUser.setUserName(registerDto.getUserName());
//        appUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
//        appUser.setRole("client");
//        appUser.setFirstName(registerDto.getFirstName());
//        appUser.setLastName(registerDto.getLastName());
//
//        try {
//            var otherUser = appUserRepositoryJPA.findByUserName(registerDto.getUserName());
//            if(otherUser != null) {
//                return ResponseEntity.badRequest().body("Username taken!");
//
//            }
//            appUserRepositoryJPA.save(appUser);
//
//            String jwtToken = createJwtToken(appUser);
//
//            var response = new HashMap<String, Object>();
//            response.put("token", jwtToken);
//            response.put("user", appUser);
//
//            return ResponseEntity.ok(response);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//        return ResponseEntity.badRequest().body("Error");
//    }
//
//
//    @PostMapping(value="/login", consumes="application/json")
//    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto, BindingResult result){
//        if(result.hasErrors()) {
//            var errorsList = result.getAllErrors();
//            var errorsMap = new HashMap<String, String>();
//
//            for(ObjectError error : errorsList) {
//                errorsMap.put(((FieldError) error).getField(), ((FieldError) error).getDefaultMessage());
//            }
//
//            return ResponseEntity.badRequest().body(errorsMap);
//        }
//
//        try {
//            authManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    loginDto.getUserName(),
//                    loginDto.getPassword()
//            ));
//
//            AppUser appUser = appUserRepositoryJPA.findByUserName(loginDto.getUserName());
//            String jwtToken = createJwtToken(appUser);
//
//            var response = new HashMap<String, Object>();
//            response.put("token", jwtToken);
//            response.put("user", appUser);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password are incorrect!");
//        }
//    }
//
//
//    @GetMapping("/profile")
//    public ResponseEntity<Object> profile(Authentication auth){
//        var response = new HashMap<String, Object>();
//        response.put("Username", auth.getName());
//        response.put("Authorities", auth.getAuthorities());
//
//        var appUser = appUserRepositoryJPA.findByUserName(auth.getName());
//        response.put("User", appUser);
//
//        return ResponseEntity.ok(response);
//    }
//
//    private String createJwtToken(AppUser appUser) {
//        Instant now = Instant.now();
//
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer(jwtIssuer)
//                .issuedAt(now)
//                .expiresAt(now.plusSeconds(24 * 3600))
//                .subject(appUser.getUserName())
//                .claim("role", appUser.getRole())
//                .build();
//
//        var encoder = new NimbusJwtEncoder(
//                new ImmutableSecret<>(jwtSecretKey.getBytes())
//        );
//
//        var params = JwtEncoderParameters.from(
//                JwsHeader.with(MacAlgorithm.HS256).build(), claims
//        );
//
//        return encoder.encode(params).getTokenValue();
//    }
//
//}
