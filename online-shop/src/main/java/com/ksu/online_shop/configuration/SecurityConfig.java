package com.ksu.online_shop.configuration;

import com.ksu.online_shop.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/home").hasRole("USER")
                        .requestMatchers("/home/admin").hasRole("ADMIN")
                        .requestMatchers("/home/order/changestatus", "/home/order/changeproductquantity").hasAuthority("SERVICE")
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/", "/**").permitAll()
                )
                //.httpBasic(withDefaults())
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/home/order/changestatus")
                        .ignoringRequestMatchers("/home/order/changeproductquantity")

                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            InMemoryUserDetailsManager inMemoryUserDetailsManager,
            UserService dbUserDetailsService,
            PasswordEncoder encoder
    ) {
        // Провайдер для in-memory пользователей
        DaoAuthenticationProvider inMemoryProvider = new DaoAuthenticationProvider();
        inMemoryProvider.setUserDetailsService(inMemoryUserDetailsManager);
        inMemoryProvider.setPasswordEncoder(encoder);

        // Провайдер для пользователей из БД
        DaoAuthenticationProvider dbProvider = new DaoAuthenticationProvider();
        dbProvider.setUserDetailsService(dbUserDetailsService);
        dbProvider.setPasswordEncoder(encoder);

        return new ProviderManager(inMemoryProvider, dbProvider);
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder encoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername("service-account")
                        .password(encoder.encode("secure-password"))
                        .roles("SERVICE")
                        .build()
        );
    }


}


