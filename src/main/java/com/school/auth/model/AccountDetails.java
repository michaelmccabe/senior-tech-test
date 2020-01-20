package com.school.auth.model;

import org.springframework.security.core.userdetails.UserDetails;

public class AccountDetails {
    private UserDetails userDetails;
    private Account account;

    public AccountDetails(UserDetails userDetails, Account account) {
        this.userDetails = userDetails;
        this.account = account;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public Account getAccount() {
        return account;
    }
}
