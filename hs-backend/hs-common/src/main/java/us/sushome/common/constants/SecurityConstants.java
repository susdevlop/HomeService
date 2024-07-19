package us.sushome.common.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SecurityConstants {

    @Value("${security.auth-login-url:/api/auth/login}")
    private String authLoginUrl;

    @Value("${security.jwt-secret-key:p2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdS}")
    private String jwtSecretKey;

    @Value("${security.token-prefix:Bearer }")
    private String tokenPrefix;

    @Value("${security.token-header:Authorization}")
    private String tokenHeader;

    @Value("${security.token-type:JWT}")
    private String tokenType;

    @Value("${security.token-role-claim:role}")
    private String tokenRoleClaim;

    @Value("${security.token-issuer:security}")
    private String tokenIssuer;

    @Value("${security.token-audience:security-all}")
    private String tokenAudience;

    @Value("${security.expiration-time:7200}")
    private long expirationTime;

    @Value("${security.expiration-remember-time:604800}")
    private long expirationRememberTime;

    public static String AUTH_LOGIN_URL;
    public static String JWT_SECRET_KEY;
    public static String TOKEN_PREFIX;
    public static String TOKEN_HEADER;
    public static String TOKEN_TYPE;
    public static String TOKEN_ROLE_CLAIM;
    public static String TOKEN_ISSUER;
    public static String TOKEN_AUDIENCE;
    public static long EXPIRATION_TIME;
    public static long EXPIRATION_REMEMBER_TIME;

    @PostConstruct
    public void init() {
        AUTH_LOGIN_URL = authLoginUrl;
        JWT_SECRET_KEY = jwtSecretKey;
        TOKEN_PREFIX = tokenPrefix;
        TOKEN_HEADER = tokenHeader;
        TOKEN_TYPE = tokenType;
        TOKEN_ROLE_CLAIM = tokenRoleClaim;
        TOKEN_ISSUER = tokenIssuer;
        TOKEN_AUDIENCE = tokenAudience;
        EXPIRATION_TIME = expirationTime;
        EXPIRATION_REMEMBER_TIME = expirationRememberTime;
    }
}
