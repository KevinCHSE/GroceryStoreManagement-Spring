package kevinCHSE.GroceryStore.Spring.security.Filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kevinCHSE.GroceryStore.Spring.entities.Login.User;
import static kevinCHSE.GroceryStore.Spring.security.TokenJWTConfig.CONTENT_TYPE;
import static kevinCHSE.GroceryStore.Spring.security.TokenJWTConfig.HEADER_AUTHORIZATION;
import static kevinCHSE.GroceryStore.Spring.security.TokenJWTConfig.PREFIX_TOKEN;
import static kevinCHSE.GroceryStore.Spring.security.TokenJWTConfig.SECRET_KEY;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager=authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user=null;
        String id=null;
        String password=null;

        try {
            user=new ObjectMapper().readValue(request.getInputStream(), User.class);
            id=user.getId();
            password=user.getPassword();

        } catch (JacksonException | IOException e) {
        }
        UsernamePasswordAuthenticationToken authenticationToken=
        new UsernamePasswordAuthenticationToken(id,password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        

        List<String>roles=authResult.getAuthorities().stream()
            .map(role->role.getAuthority()).toList();

        String id = authResult.getName();

        String accessToken=JWT.create()
            .withSubject(authResult.getName())
            .withExpiresAt(new Date(System.currentTimeMillis()+30*60*1000))
            .withIssuer(request.getRequestURL().toString())
            .withClaim("authorities", roles)
            .sign(Algorithm.HMAC256(SECRET_KEY));

        Map<String, String> body= new HashMap<>();
        body.put("token", accessToken);
        body.put("id", id);
        body.put("message", String.format("Hola %s has iniciado sesion con exito!", id));

        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN+ accessToken);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException 
    {
        Map<String, String> body= new HashMap<>();
        body.put("Message", "Error en al autenticacion id o pasword incorrecto");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }

    

    
    

}
