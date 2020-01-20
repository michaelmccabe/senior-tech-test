package com.school.auth.service;

import com.school.auth.data.AccountDao;
import com.school.auth.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountUserDetailsServiceTest {

    @Mock
    AccountDao accountDao;

    @Mock
    PasswordEncoder bcryptEncoder;

    Account studentAccount = new Account("username", "password", "", "STUDENT");

    AccountUserDetailsService underTest = null; //new AccountUserDetailsService(bcryptEncoder, accountDao);

    @BeforeEach
    void setUp(){
        Optional<Account> opt = Optional.of(studentAccount);
        Mockito.when((accountDao).findById(studentAccount.getUsername())).thenReturn(opt);
        underTest = new AccountUserDetailsService(bcryptEncoder, accountDao);

    }

    @Test
    @DisplayName("loadUserByUsername must use the AccountDao Interface")
    void testFindAccountByUsername(){
        UserDetails accountUserDetails  = underTest.loadUserByUsername(studentAccount.getUsername());
        assertEquals(accountUserDetails.getUsername(),studentAccount.getUsername());
        assertEquals(accountUserDetails.getPassword(),studentAccount.getPassword());
    }



}