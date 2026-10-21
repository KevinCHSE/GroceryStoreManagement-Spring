package kevinCHSE.GroceryStore.Spring.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component 
public class TokenJWTConfig {
    public static String SECRET_KEY;
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";

    @Value("${jwt.secret}")
    public void setSecretKey(String secretKey) {
        TokenJWTConfig.SECRET_KEY = secretKey;
    }
}
