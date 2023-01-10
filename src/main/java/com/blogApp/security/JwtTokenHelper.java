package com.blogApp.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenHelper {

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	private String secretKey = "jwtTokenKey";
	
//	retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token , Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token , Claims::getExpiration);
	}

	public<T> T getClaimFromToken(String token, Function<Claims ,T>claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
//	For retrieving any information from token we will need the secret key

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	
//	check if token has expired
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		
		return doGenerateToken(claims,userDetails.getUsername());
	}

//	While creating token
	
//	1.Define claims of the token , like issuer 
	
	
	
	private String doGenerateToken(Map<String, Object> claims, String username) {
		
		return null;
	}
	
	
}
