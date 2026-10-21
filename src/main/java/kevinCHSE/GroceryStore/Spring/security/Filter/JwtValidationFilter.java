package kevinCHSE.GroceryStore.Spring.security.Filter;

import static kevinCHSE.GroceryStore.Spring.security.TokenJWTConfig.CONTENT_TYPE;
import static kevinCHSE.GroceryStore.Spring.security.TokenJWTConfig.HEADER_AUTHORIZATION;
import static kevinCHSE.GroceryStore.Spring.security.TokenJWTConfig.PREFIX_TOKEN;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kevinCHSE.GroceryStore.Spring.security.TokenJWTConfig;
import tools.jackson.databind.ObjectMapper;

public class JwtValidationFilter extends BasicAuthenticationFilter{

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        String header=request.getHeader(HEADER_AUTHORIZATION);

        if(header==null || !header.startsWith(PREFIX_TOKEN)){
            chain.doFilter(request, response);
            return;
        }

        String token=header.replace(PREFIX_TOKEN,"");

        try {
            Algorithm algorithm= Algorithm.HMAC256(TokenJWTConfig.SECRET_KEY);
            DecodedJWT decodedJWT= JWT.require(algorithm).build().verify(token);
            
            String id=decodedJWT.getSubject();
            Claim authoritiesClaim= decodedJWT.getClaim("authorities");
            
            if(authoritiesClaim.isMissing() || authoritiesClaim.isNull()){
                throw new JWTVerificationException("El token no contiene el claim Authorities");
            }


            List<String>roles= authoritiesClaim.asList(String.class);

            Collection<GrantedAuthority>authorities=roles.stream()
                .map(role->new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(id,null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
            
        } catch (JWTVerificationException | ServletException | IOException | IllegalArgumentException e) {
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token JWT no es válido");

            response.setContentType(CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(new ObjectMapper().writeValueAsString(body)); 
        }
    }
}
