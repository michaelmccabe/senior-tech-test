package com.school.auth.controller;

import com.school.auth.model.Account;
import com.school.auth.service.AccountService;
import com.school.auth.service.AccountUserDetailsService;
import com.school.auth.service.AuthTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	@MockBean
	private AuthTokenService authTokenService;

	@MockBean
	private AccountUserDetailsService accountUserDetailsService;

	@Mock
	UserDetails userDetails;

	private List<Account> accounts = new ArrayList<>();

	private Account account = new Account( "username",  "password",  "details",  "type");

	@BeforeEach
	public void setupAccount(){
		accounts.add(account);
	}

	@Test
	@DisplayName("Get all accounts must be a valid role: ROLE_ADMIN or ROLE_TEACHER")
	public void getAllAccounts() throws Exception {
		String username = "userOne";

		List authorities = Arrays.asList(
				new SimpleGrantedAuthority("ROLE_ADMIN")
		);

		Mockito.when(userDetails.getAuthorities()).thenReturn( authorities);
		Mockito.when(authTokenService.getUsernameFromToken("mytoken")).thenReturn(username);
		Mockito.when(authTokenService.validateToken("mytoken", userDetails)).thenReturn(true);
		Mockito.when(accountService.getAllAccounts()).thenReturn(accounts);
		Mockito.when(accountUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

		RequestBuilder requestBuilder =
				MockMvcRequestBuilders
						.get("/accounts")
						.accept(MediaType.APPLICATION_JSON)
						.header("authorization", "Bearer mytoken");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[{\"username\":\"username\",\"password\":\"password\",\"details\":\"details\",\"type\":\"type\"}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
