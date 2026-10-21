package kevinCHSE.GroceryStore.Spring.security;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import kevinCHSE.GroceryStore.Spring.security.Filter.JWTAuthenticationFilter;
import kevinCHSE.GroceryStore.Spring.security.Filter.JwtValidationFilter;



@Configuration
@EnableMethodSecurity    //Habilita el @Preauthorized que hay en los controladores  
public class springSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration; 
    
    public springSecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean 
    AuthenticationManager authenticationManager(){
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean 
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean 
    SecurityFilterChain filterChain(HttpSecurity http){
        return http.authorizeHttpRequests(auth->
            auth
            .requestMatchers("/error").permitAll() //to know the error, without this any error is 403 
            .requestMatchers(HttpMethod.POST, "/login").permitAll()//to allow the login 
            .requestMatchers(HttpMethod.GET, "/apiImage/*/image").permitAll()// to show all the images 
            .requestMatchers(HttpMethod.POST, "/User/saveUser").permitAll()
            .requestMatchers(HttpMethod.POST, "/apiRoles/saveRole").permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()//this is something that CORS needs 
            .anyRequest().authenticated())
            .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))// to manage errors after the token expired 
            .addFilter(new JWTAuthenticationFilter(authenticationManager()))
            .addFilterBefore(new JwtValidationFilter(authenticationManager()),BasicAuthenticationFilter.class)
            .csrf(config->config.disable())
            .cors(cors->cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(manage->manage.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }

    @Bean 
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new  CorsConfiguration();
        // Usa wildcard pattern para permitir el dominio principal Y cualquier preview URL de Vercel (*.vercel.app)
        config.setAllowedOriginPatterns(List.of(
            "https://landi-angular.vercel.app", 
            "https://*.vercel.app", 
            "http://localhost:4200"
        ));
        
        // Obligatorio: Agregar OPTIONS para que pase el preflight del navegador
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Permitir todas las cabeceras estándar
        config.setAllowedHeaders(List.of("*"));
        
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    
    }

        @Bean 
        FilterRegistrationBean<CorsFilter>corsFilter(){
            FilterRegistrationBean<CorsFilter> corsBean= 
                new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
            corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
            return corsBean;
        } 


}
