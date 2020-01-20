package com.school.auth.configuration;

import com.school.auth.service.AccountUserDetailsService;
import com.school.auth.service.AuthTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class TokenRequestFilter extends OncePerRequestFilter {

	@Autowired
	private AccountUserDetailsService accountUserDetailsService;

	@Autowired
	private AuthTokenService authTokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String token = null;

		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			token = requestTokenHeader.substring(7);
			try {
				username = authTokenService.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		//Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.accountUserDetailsService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set authentication
			if (authTokenService.validateToken(token, userDetails) && checkAccessDetails(userDetails, request.getRequestURI())) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkAccessDetails(UserDetails userDetails, String URI) {

		if(!userDetails.getAuthorities().isEmpty() ){

			AtomicBoolean returnValue = new AtomicBoolean(true);
			userDetails.getAuthorities().forEach( auth -> {
				if(auth.getAuthority().equals("ROLE_STUDENT") && !URI.equals("/accounts/"+userDetails.getUsername())){
					returnValue.set(false);
				}
			});

			return returnValue.get();
		}

		return false;
	}

}
