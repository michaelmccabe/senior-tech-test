package com.school.auth.service;

import java.util.*;

import com.school.auth.data.AccountDao;
import com.school.auth.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountService {

	@Autowired
    AccountDao accountDao;

	public List<Account> getAllAccounts() {
		List<Account> result = new ArrayList<>();
		accountDao.findAll().forEach(result::add);
		return result;
	}

	public Account getUserAccount(String username) {
		Optional<Account> account = accountDao.findById(username);
		if(account.isPresent()) {
			return account.get();
		}
		return null;
	}
}