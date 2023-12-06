package com.example.common.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    public static final long JWT_TTL = 60 * 60 * 1000L * 24 * 14;  // 有效期14天


    private static String jwtKey = "fwhiofawheogaeurghaworpgiawryghawprgiawergyawrogaw4igyawrgtaer87t3q4t83q4tyq3974t8q3948thq29tgq234tquy34gtq38t7q248q723g4q34yf";


    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String createJWT(String subjectk, String role) {
        JwtBuilder builder = getJwtBuilder(subjectk, role, null, getUUID());
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, String role, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .claim("role", role)
                .setIssuer("米叔")
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey)
                .setExpiration(expDate);
    }

    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(jwtKey);
        return new SecretKeySpec(encodeKey, 0, encodeKey.length, "HmacSHA256");
    }

//    public UsernamePasswordAuthenticationToken getRoleList(String token) throws Exception {
//        String role;
//        String name = parseJWT(token).getSubject();
//
//        try {
//            role = parseJWT(token).get("role").toString();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        List<SimpleGrantedAuthority> roleList
//                = Stream.of(Optional.of("ROLE_" +  role).orElse(""))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        return new UsernamePasswordAuthenticationToken(name
//                , null
//                ,roleList);
//
//    }

//解析Token
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
