package HelpTodo.helptodoBackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {

    public static String getMemberId(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            .getBody().get("memberId", String.class);
    }

    public static boolean isExpired(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            .getBody().getExpiration().before(new Date());
    }

    public static String createJwt(String memberId, String secretKey, Long expiredMs){
        Claims claims = Jwts.claims();

        claims.put("memberId", memberId);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }
}
