package xyz.qakashi.qreceipt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import xyz.qakashi.qreceipt.domain.Role;
import xyz.qakashi.qreceipt.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtCoder {
    private static final String key = "YW5pbWUgcGV0JJKjkkcmkgMT123IGNhdCBvcmlnaW4zxc1v1sf1=";
    private static final Integer expirationDay = 1;

    public static String generateJwt(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
        }
        return Jwts.builder().setSubject(user.getUsername())
                .claim("authorities",
                        authorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .setIssuedAt(new Date()).setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(expirationDay)))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(key)))
                .compact();
    }

    public static Claims decodeJwt(String jwt) {
        return Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(key))).parseClaimsJws(jwt).getBody();
    }
}
