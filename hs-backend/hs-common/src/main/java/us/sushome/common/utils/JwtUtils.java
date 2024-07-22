package us.sushome.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import us.sushome.common.constants.SecurityConstants;
import us.sushome.common.constants.UserRoleConstants;

import javax.xml.bind.DatatypeConverter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Jwt 工具类，用于生成、解析与验证 token
 *
 * @author star
 **/
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static final byte[] secretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);

    private JwtUtils() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }

    /**
     * 根据用户名和用户角色生成 token
     *
     * @param userName   用户名
     * @param roles      用户角色
     * @param isRemember 是否记住我
     * @return 返回生成的 token
     */
    public static String generateToken(String userName, Integer role, boolean isRemember) {
        byte[] jwtSecretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);
        // 过期时间
        long expiration = isRemember ? SecurityConstants.EXPIRATION_REMEMBER_TIME : SecurityConstants.EXPIRATION_TIME;
        // 生成 token
        String token = Jwts.builder()
                // 生成签证信息
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey), SignatureAlgorithm.HS256)
                .setSubject(userName)
                .claim(SecurityConstants.TOKEN_ROLE_CLAIM, role)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setIssuedAt(new Date())
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                // 设置有效时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
        return token;
    }

    /**
     * 验证 token 是否有效
     *
     * <p>
     * 如果解析失败，说明 token 是无效的
     *
     * @param token token 信息
     * @return 如果返回 true，说明 token 有效
     */
    public static boolean validateToken(String token) {
        try {
            getTokenBody(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
        }
        return false;
    }

    /**
     * 根据 token 获取用户认证信息
     *
     * @param token token 信息
     * @return 返回用户认证信息
     */
    public static Authentication getAuthentication(String token) {
        Claims claims = getTokenBody(token);
        // 获取用户角色字符串
        Integer roleId = (Integer) claims.get(SecurityConstants.TOKEN_ROLE_CLAIM);
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(roleId != null ? String.valueOf(roleId) :UserRoleConstants.GUEST_USER)
        );

        // 获取用户名
        String userName = claims.getSubject();

        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build() // 创建 JwtParser 实例
                .parseClaimsJws(token) // 解析 JWT 令牌
                .getBody(); // 获取 Claims
    }
}
