package com.school.auth.service;

import com.school.auth.data.AccountDao;
import com.school.auth.model.Account;
import com.school.auth.token.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private AccountDao accountDao;

    public AccountUserDetailsService(PasswordEncoder bcryptEncoder, AccountDao accountDao) {
        this.bcryptEncoder = bcryptEncoder;
        this.accountDao = accountDao;
    }

    public AccountUserDetailsService() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = findAccountByUsername(username);
        User.UserBuilder builder = null;

        if (account != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(account.getPassword());
            builder.roles(account.getType());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    private Account findAccountByUsername(String username) {
        Optional<Account> account = accountDao.findById(username);
        if(account.isPresent()) {
            return account.get();
        }
        return null;
    }


    public void registerNewUser(RegisterRequest registerRequest) {
        Account account = new Account(registerRequest.getUsername(), bcryptEncoder.encode(registerRequest.getPassword()),
                registerRequest.getDetails(), registerRequest.getType());
        accountDao.save(account);
    }
}
