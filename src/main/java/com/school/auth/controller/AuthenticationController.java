package com.school.auth.controller;

import com.school.auth.service.AccountUserDetailsService;
import com.school.auth.service.AuthTokenService;
import com.school.auth.token.RegisterRequest;
import com.school.auth.token.TokenRequest;
import com.school.auth.token.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private AccountUserDetailsService accountUserDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody TokenRequest tokenRequest) throws Exception {

        authenticate(tokenRequest.getUsername(), tokenRequest.getPassword());
        final UserDetails userDetails = accountUserDetailsService.loadUserByUsername(tokenRequest.getUsername());
        final String token = authTokenService.generateToken(userDetails);

        return ResponseEntity.ok(new TokenResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterRequest registerRequest) throws Exception {
        accountUserDetailsService.registerNewUser(registerRequest);
        return ResponseEntity.ok("registered user: " + registerRequest.getUsername());
    }

}
