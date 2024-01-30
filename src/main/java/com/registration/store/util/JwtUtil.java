package com.registration.store.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	private static final String SECRET_KEY= "learn_Programing";
	private static final int TOKEN_VALIDITITY = 3600*5;
	public String getUserNameFromToken(String token) {
		
		return getClaimFromToken(token,Claims::getSubject);
		
	}
	
	private <T> T getClaimFromToken(String token,Function<Claims,T> claimResolver) {
		final Claims claims =  getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
		
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public boolean validatedToken(String token , UserDetails userDetails) {
		
		String userName = getUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token)); // isTokenExpired(token) if token is Expired it return true
		
		
	}
	
	private boolean isTokenExpired(String token) {
		final Date experitationDate= getExpirationDateFromToken(token);
		return experitationDate.before(new Date());
		
	}
	
	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	 /**
	 * @param userDetails
	 * @return
	 */
	public String generateToken(UserDetails userDetails) {
		 Map<String, Object> claims = new HashMap<>();
		 return Jwts.builder().setClaims(claims)
				 .setSubject(userDetails.getUsername())
				 .setIssuedAt(new Date(System.currentTimeMillis()))
				 .setExpiration(new Date(System.currentTimeMillis()+TOKEN_VALIDITITY * 1000))
				 .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				 .compact()
				 ;
	 }
	
	

}
