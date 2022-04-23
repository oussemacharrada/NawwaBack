package com.dev.nawwa.utility;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.dev.nawwa.constant.SecurityConstant;
import com.dev.nawwa.domain.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JWTTokenProvider
{
	@Value("${jwt.secret}")
	private String secret;

	public String generateJWTToken(UserPrincipal userPricipal)
	{
		String[] claims = getClaimsFromUser(userPricipal);
		return JWT.create().withIssuer(SecurityConstant.NEOQUANT_SOLUTIONS).withAudience(SecurityConstant.NEOQUANT_ADMINISTRATION).withIssuedAt(new Date())
		      .withSubject(userPricipal.getUsername()).withArrayClaim(SecurityConstant.AUTHORITIES, claims)
		      .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME)).sign(HMAC512(secret.getBytes()));

	}

	private String[] getClaimsFromUser(UserPrincipal user)
	{
		List<String> authorities = new ArrayList<>();
		for(GrantedAuthority authority : user.getAuthorities())
		{
			authorities.add(authority.getAuthority());
		}
		return authorities.toArray(new String[0]);
	}

	// -----
	public List<GrantedAuthority> getAuthorities(String token)
	{
		String[] claims = getClaimsFromToken(token);
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	private String[] getClaimsFromToken(String token)
	{
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getClaim(SecurityConstant.AUTHORITIES).asArray(String.class);
	}

	private JWTVerifier getJWTVerifier()
	{
		JWTVerifier verifier;
		try
		{
			Algorithm algorithm = HMAC512(secret);
			verifier = JWT.require(algorithm).withIssuer(SecurityConstant.NEOQUANT_SOLUTIONS).build();
		}
		catch(JWTVerificationException e)
		{
			throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
		}
		return verifier;
	}

	// ----
	
	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities,
	      HttpServletRequest request)
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new
				UsernamePasswordAuthenticationToken(username, null, authorities);
		usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return usernamePasswordAuthToken;
	}
	
	public boolean isTokenValid(String username, String token) {
		JWTVerifier verifier = getJWTVerifier();
		return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
	}

	public String getSubject(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getSubject();
	}
	
	private boolean isTokenExpired(JWTVerifier verifier, String token)
	{
		Date expiration = verifier.verify(token).getExpiresAt();
		return expiration.before(new Date());
	}

}
