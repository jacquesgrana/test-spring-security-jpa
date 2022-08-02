package fr.jacgrana.springsecurityjpa.utils;

import fr.jacgrana.springsecurityjpa.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
/*
    private String SECRET_KEY ="12345678";

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        claims.put("userName", user.getUserName());
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR, 24);
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserName())
                .setIssuer("SPRING_SECURITY_JPA")
                .setIssuedAt(now)
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        //log.info("TOKEN {}", token);
        System.out.println("token : " + token);
        // TODO sauvegarder token?
        return token;
    }

    public String getUserNameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        Claims claims =  Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        //String username = claims.getSubject();
        return claims;
    }

    public Boolean isTokenValid(String token, UserDetails user) {
        Claims claims = getClaims(token);
        Date creation = claims.getExpiration();
        String userNameFromToken = getUserNameFromToken(token);
        Boolean isValid = userNameFromToken.equals(user.getUsername());
        Boolean isValidDate = creation.before(new Date());
        return isValid && isValidDate;
    }
*/

    private String SECRET_KEY ="12345678910111213141516171819202122232425262728293031323334353637383940";

    public String extractedUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        // TODO affecter claims?
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractedUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
