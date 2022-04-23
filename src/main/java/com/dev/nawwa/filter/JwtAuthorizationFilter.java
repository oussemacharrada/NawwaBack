package com.dev.nawwa.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev.nawwa.constant.SecurityConstant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dev.nawwa.utility.JWTTokenProvider;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter
{
	private JWTTokenProvider jwtTokenProvider;

	public JwtAuthorizationFilter(JWTTokenProvider tokenProvider)
	{
		this.jwtTokenProvider = tokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	      throws ServletException, IOException
	{
		// this allows only HTTP OPTIONS method for first request
		if(request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_METHOD))
		{
			response.setStatus(HttpStatus.OK.value());
		}
		else
		{
			// takes Authorization value from the request header
			String authorizationToken = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(authorizationToken == null || !authorizationToken.startsWith(SecurityConstant.JWT_TOKEN_HEADER))
			{
				filterChain.doFilter(request, response);
				return;
			}

			String token = authorizationToken.substring(SecurityConstant.JWT_TOKEN_HEADER.length());
			String username = jwtTokenProvider.getSubject(token);
			if(jwtTokenProvider.isTokenValid(username, token)
			      && SecurityContextHolder.getContext().getAuthentication() == null)
			{
				List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
				Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}else {
				SecurityContextHolder.clearContext();
			}
			filterChain.doFilter(request, response);
		}
	}

}
