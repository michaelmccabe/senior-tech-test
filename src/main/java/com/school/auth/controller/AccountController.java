package com.school.auth.controller;

import java.util.List;

import com.school.auth.model.Account;
import com.school.auth.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/accounts")
	public List<Account> getAllAccounts() {
		return accountService.getAllAccounts();
	}

	@GetMapping("/accounts/{username}")
	public Account getUserAccount(@PathVariable String username) {
		return accountService.getUserAccount(username);
	}

}
