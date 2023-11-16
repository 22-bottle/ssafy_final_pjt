package com.ssafy.theme.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.theme.exception.UnAuthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTUtil {
	@Value("${jwt.salt}")
	private String salt;

	@Value("${jwt.access-token.expiretime}")
	private long accessTokenExpireTiem;

	@Value("${jwt.refresh-token.expiretime}")
	private long refreshTokenExpireTime;

	public String createAccessToken(String id) {
		return create(id, "access-token", accessTokenExpireTiem);
	}

	public String createRefreshToken(String id) {
		return create(id, "refresh-token", refreshTokenExpireTime);
	}

	private String create(String id, String subject, long expireTime) {
		Claims claims = Jwts.claims()
			.setSubject(subject)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + expireTime));

		claims.put("id", id);

		String jwt = Jwts.builder()
			.setHeaderParam("typ", "JWT").setClaims(claims)
			.signWith(SignatureAlgorithm.HS256, this.generateKey())
			.compact();

		return jwt;
	}

	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = salt.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (log.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		return key;
	}

	public boolean checkToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(token);
			log.debug("claims: {}", claims);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	public String getEditorId(String authorization) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(authorization);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new UnAuthorizedException();
		}
		Map<String, Object> value = claims.getBody();
		log.info("value : {}", value);
		return (String) value.get("id");
	}

}
