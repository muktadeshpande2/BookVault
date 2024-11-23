package com.example.Library_Management_System.config;

import com.example.Library_Management_System.Utils.Constants;
import com.example.Library_Management_System.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Authentication
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }


    /**
     * Password Encoding
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }


    /**
     * Authorisation Bean
     * @param httpSecurity
     * @return
     * @throws Exception
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/admin/create").hasAuthority(Constants.CREATE_ADMIN_AUTHORITY)
                                .requestMatchers("/book/add").hasAuthority(Constants.CREATE_BOOK_AUTHORITY)
                                .requestMatchers("/book/search").hasAuthority(Constants.READ_BOOK_AUTHORITY)
                                .requestMatchers("/book/delete").hasAuthority(Constants.DELETE_BOOK_AUTHORITY)
                                .requestMatchers("/student/search").hasAuthority(Constants.STUDENT_INFO_AUTHORITY)
                                .requestMatchers("/student/info").hasAuthority(Constants.STUDENT_SELF_INFO_AUTHORITY)
                                .requestMatchers("/transaction/initiate").hasAuthority(Constants.INITIATE_TRANSACTION_AUTHORITY)
                                .requestMatchers("/student/add").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
