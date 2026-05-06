package com.example.demo.config;

// import com.example.demo.filter.JwtAuthenticationFilter;

import com.example.demo.security.CustomPermissionEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Potentially insecure
                // Disabling CSRF protection for simplicity (not recommended for production)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }

//    // This bean customizes how Spring evaluates method-level security expressions
//    // such as @PreAuthorize("hasPermission(...)").
//    @Bean
//    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(
//            CustomPermissionEvaluator customPermissionEvaluator
//    ) {
//        // Default handler knows how to process common expressions:
//        // hasRole(...), isAuthenticated(), hasPermission(...), etc.
//        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
//        // Plug in our custom evaluator so hasPermission(...) can run
//        // our own ownership/admin checks (for example on reviews).
//        expressionHandler.setPermissionEvaluator(customPermissionEvaluator);
//        return expressionHandler;
//    }
}
